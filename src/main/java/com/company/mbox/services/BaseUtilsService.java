package com.company.mbox.services;

import com.company.mbox.entity.*;

import java.util.Map;
import java.util.UUID;

public interface BaseUtilsService {
    String NAME = "mbox_BaseUtilsService";

    Organization getCurrentOrganization();

    User getCurrentUser();

    Map<String, Object> getObjMap(String model);

    Currency getOrCreateCurrency(String code);

    Organization getOrCreateOrganization(UUID legacyId);

    Organization getOrCreateOrganization(String bin);

    Organization getOrganization(String bin);

    Division getOrCreateDivision(UUID legacyId, UUID orgId);

    Warehouse getOrCreateWarehouse(UUID legacyId, UUID divisionId);

    Item getOrCreateItem(UUID legacyId, UUID warehouseId);

    boolean usernameExist(String username);

    boolean iinExist(String iin);
}
