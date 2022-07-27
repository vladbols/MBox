package com.company.mbox.services;

import com.company.mbox.entity.*;

import java.util.Map;

public interface BaseUtilsService {
    String NAME = "mbox_BaseUtilsService";

    Organization getCurrentOrganization();

    Map<String, Object> getObjMap(String model);

    Currency getOrCreateCurrency(String code);

    Organization getOrCreateOrganization(Integer legacyId);

    Organization getOrCreateOrganization(String bin);

    Division getOrCreateDivision(Integer legacyId);

    Warehouse getOrCreateWarehouse(Integer legacyId);

    Item getOrCreateItem(Integer legacyId);
}
