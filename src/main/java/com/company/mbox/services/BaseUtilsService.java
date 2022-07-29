package com.company.mbox.services;

import com.company.mbox.entity.*;

import java.util.Map;
import java.util.UUID;

public interface BaseUtilsService {
    String NAME = "mbox_BaseUtilsService";

    Organization getCurrentOrganization();

    Map<String, Object> getObjMap(String model);

    Currency getOrCreateCurrency(String code);

    Organization getOrCreateOrganization(Integer legacyId);

    Organization getOrCreateOrganization(String bin);

    Division getOrCreateDivision(Integer legacyId, UUID orgId);

    Warehouse getOrCreateWarehouse(Integer legacyId, UUID divisionId);

    Item getOrCreateItem(Integer legacyId, UUID warehouseId);

    User usernameExist(String username);

    User iinExist(String iin);
}
