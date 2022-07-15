package com.company.mbox.serviceBean;

import com.company.mbox.entity.*;
import com.company.mbox.scheduledTasks.UpdateItemsTask;
import com.company.mbox.services.BaseUtilsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jmix.core.DataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service(BaseUtilsService.NAME)
public class BaseUtilsServiceBean implements BaseUtilsService {

    private static final Logger log = LoggerFactory.getLogger(UpdateItemsTask.class);

    @Autowired
    private DataManager dataManager;

    @Override
    @SuppressWarnings("all")
    public Map<String, Object> getObjMap(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, Map.class);
        } catch (IOException e) {
            return new HashMap<>();
        }
    }

    @Override
    public Currency getOrCreateCurrency(String code) {
        Currency c = dataManager.load(Currency.class)
                .query("" +
                        "SELECT c " +
                        "FROM Currency c " +
                        "WHERE c.code = :code ")
                .parameter("code", code.toUpperCase())
                .optional().orElse(null);
        if (c == null) {
            c = dataManager.create(Currency.class);
            c.setCode(code.toUpperCase());
            dataManager.save(c);
            return c;
        } else {
            return c;
        }
    }

    @Override
    public Organization getOrCreateOrganization(Integer legacyId) {
        return dataManager.load(Organization.class)
                .query("" +
                        "SELECT o " +
                        "FROM Organization o " +
                        "WHERE o.legacyId = :legacyId ")
                .parameter("legacyId", legacyId)
                .optional().orElse(dataManager.create(Organization.class));
    }

    @Override
    public Division getOrCreateDivision(Integer legacyId) {
        return dataManager.load(Division.class)
                .query("" +
                        "SELECT d " +
                        "FROM Division d " +
                        "WHERE d.legacyId = :legacyId ")
                .parameter("legacyId", legacyId)
                .optional().orElse(dataManager.create(Division.class));
    }

    @Override
    public Warehouse getOrCreateWarehouse(Integer legacyId) {
        return dataManager.load(Warehouse.class)
                .query("" +
                        "SELECT d " +
                        "FROM Warehouse d " +
                        "WHERE d.legacyId = :legacyId ")
                .parameter("legacyId", legacyId)
                .optional().orElse(dataManager.create(Warehouse.class));
    }

    @Override
    public Item getOrCreateItem(Integer legacyId) {
        return dataManager.load(Item.class)
                .query("" +
                        "SELECT d " +
                        "FROM Item d " +
                        "WHERE d.legacyId = :legacyId ")
                .parameter("legacyId", legacyId)
                .optional().orElse(dataManager.create(Item.class));
    }
}
