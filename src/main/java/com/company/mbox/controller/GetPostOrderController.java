package com.company.mbox.controller;

import com.company.mbox.controller.abstractClasses.AbstractController;
import com.company.mbox.dto.CompanyRequestDto;
import com.company.mbox.dto.DivisionRequestDto;
import com.company.mbox.dto.ItemsRequestDto;
import com.company.mbox.dto.WarehouseRequestDto;
import com.company.mbox.entity.Division;
import com.company.mbox.entity.Item;
import com.company.mbox.entity.Organization;
import com.company.mbox.entity.Warehouse;
import com.company.mbox.scheduledTasks.UpdateItemsTask;
import com.company.mbox.services.BaseUtilsService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class GetPostOrderController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(UpdateItemsTask.class);

    private static final Gson g = new Gson();

    @Autowired
    private BaseUtilsService baseUtilsService;


    @GetMapping("/getOrders")
    public ResponseEntity<?> getOrders() {

        return ResponseEntity.ok("ok");
    }

    @RequestMapping(value = "/setItems", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public ResponseEntity<?> setItems(@RequestBody List<CompanyRequestDto> companyRequestDto) throws ParseException {
//        String json = "";
//        log.info("File to string: {}", json);
//
//        Type companyListType = new TypeToken<ArrayList<CompanyRequestDto>>() {}.getType();
//
//        List<CompanyRequestDto> companyList = g.fromJson(json, companyListType);
        if (companyRequestDto == null || companyRequestDto.isEmpty()) {
            return badRequest("Idi nah");
        }

        for (CompanyRequestDto cDto : companyRequestDto) {
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

            for (DivisionRequestDto dDto : cDto.getDivisions()) {
                Division d = baseUtilsService.getOrCreateDivision(dDto.getDivision_uid());

                d.setName(dDto.getDivision());
                d.setLegacyId(dDto.getDivision_uid());
                d.setAddress(dDto.getAddress());
                d.setOrganization(o);
                dataManager.save(d);

                for (WarehouseRequestDto wDto : dDto.getStors()) {
                    Warehouse w = baseUtilsService.getOrCreateWarehouse(wDto.getStore_uid());

                    w.setName(wDto.getStore());
                    w.setLegacyId(wDto.getStore_uid());
                    w.setAddress(wDto.getAddress());
                    w.setDivision(d);
                    dataManager.save(w);

                    for (ItemsRequestDto iDto : wDto.getList()) {
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
        return ResponseEntity.ok("ok");
    }
}