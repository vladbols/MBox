package com.company.mbox.scheduledTasks;

import com.company.mbox.dto.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import java.util.ArrayList;
import java.util.List;

@Component
public class UpdateItemsTask implements Job {

    private static final Logger log = LoggerFactory.getLogger(UpdateItemsTask.class);

    private static final Gson g = new Gson();

    @Autowired
    private SystemAuthenticator systemAuthenticator;

    @Autowired
    private CurrentAuthentication currentAuthentication;


    @Override
    @ManagedOperation
    public void execute(JobExecutionContext context) throws JobExecutionException {
        systemAuthenticator.withSystem(() -> {
//            String json = "";
//
//            File file = new File("/Users/rakhymkurmangali/Desktop/data.json");
//
//            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//
//                String line;
//                while ((line = br.readLine()) != null) {
//                    json = String.format("%s%s", json, line);
//                }
//
//                Type companyListType = new TypeToken<ArrayList<CompanyRequestDto>>() {
//                }.getType();
//
//                List<CompanyRequestDto> companyList = g.fromJson(json, companyListType);
//
//                log.info("File to string: {}", json);
//            } catch (Exception e) {
//                log.error("File reading error: {}", e.getMessage());
//            }
            return null;
        });

    }
}
