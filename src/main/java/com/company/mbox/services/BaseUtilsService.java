package com.company.mbox.services;

import com.company.mbox.entity.*;

import java.util.Map;

public interface BaseUtilsService {
    String NAME = "tsp_BaseUtilsService";

    Map<String, Object> getObjMap(String model);

    Currency getOrCreateCurrency(String code);

    Organization getOrCreateOrganization(Integer legacyId);

    Division getOrCreateDivision(Integer legacyId);

    Warehouse getOrCreateWarehouse(Integer legacyId);

    Item getOrCreateItem(Integer legacyId);
}
