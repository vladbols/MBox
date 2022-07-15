package com.company.mbox.scheduledTasks;

import com.company.mbox.dto.*;
import com.company.mbox.entity.*;
import com.company.mbox.services.BaseUtilsService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.core.security.SystemAuthenticator;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class UpdateItemsTask implements Job {

    private static final Logger log = LoggerFactory.getLogger(UpdateItemsTask.class);

    private static final Gson g = new Gson();

    @Autowired
    private DataManager dataManager;

    @Autowired
    private BaseUtilsService baseUtilsService;

    @Autowired
    private SystemAuthenticator systemAuthenticator;

    @Autowired
    private CurrentAuthentication currentAuthentication;


    @Override
    @ManagedOperation
    public void execute(JobExecutionContext context) throws JobExecutionException {
        systemAuthenticator.withSystem(() -> {
            String json = "";

            File file = new File("/Users/rakhymkurmangali/Desktop/data.json");

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {

                String line;
                while ((line = br.readLine()) != null) {
                    json = String.format("%s%s", json, line);
                }

                Type companyListType = new TypeToken<ArrayList<CompanyDto>>() {
                }.getType();

                List<CompanyDto> companyList = g.fromJson(json, companyListType);


                for (CompanyDto cDto : companyList) {
                    Organization o = baseUtilsService.getOrCreateOrganization(cDto.getOrg_uid());

                    o.setName(cDto.getOrg());
                    o.setLegacyId(cDto.getOrg_uid());
                    o.setBin(cDto.getBin());
                    o.setDate(new SimpleDateFormat("dd.MM.yyyy").parse(cDto.getDate()));
                    o.setAddress(cDto.getAddress());
                    o.setKbe(cDto.getKbe());
                    o.setAccount(cDto.getAccount());
                    o.setBik(cDto.getBik());
                    o.setBank(cDto.getBank());
                    o.setCurrency(baseUtilsService.getOrCreateCurrency(cDto.getCurrency()));
                    o.setActive(false);
                    dataManager.save(o);

                    for (DivisionDto dDto : cDto.getDivisions()) {
                        Division d = baseUtilsService.getOrCreateDivision(dDto.getDivision_uid());

                        d.setName(dDto.getDivision());
                        d.setLegacyId(dDto.getDivision_uid());
                        d.setAddress(dDto.getAddress());
                        d.setOrganization(o);
                        dataManager.save(d);

                        for (WarehouseDto wDto : dDto.getStors()) {
                            Warehouse w = baseUtilsService.getOrCreateWarehouse(wDto.getStore_uid());

                            w.setName(wDto.getStore());
                            w.setLegacyId(wDto.getStore_uid());
                            w.setAddress(wDto.getAddress());
                            w.setDivision(d);
                            dataManager.save(w);

                            for (ItemsDto iDto : wDto.getList()) {
                                Item i = baseUtilsService.getOrCreateItem(iDto.getItem_uid());

                                i.setName(iDto.getItem());
                                i.setLegacyId(iDto.getItem_uid());
                                i.setCategory(iDto.getCategory());
                                i.setUnit(iDto.getUnit());
                                i.setType(iDto.getType());
                                i.setPrice(iDto.getPrice());
                                i.setAmount(iDto.getNumber());
                                i.setWarehouse(w);

                                dataManager.save(i);
                            }
                        }
                    }
                }


                log.info("File to string: {}", json);
            } catch (Exception e) {
                log.error("File reading error: {}", e.getMessage());
            }
            return null;
        });

    }
}
