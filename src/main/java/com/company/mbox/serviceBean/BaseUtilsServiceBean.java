package com.company.mbox.serviceBean;

import com.company.mbox.abstracts.AbstractServiceBean;
import com.company.mbox.entity.*;
import com.company.mbox.services.BaseUtilsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mchange.v2.lang.StringUtils;
import io.jmix.core.DataManager;
import io.jmix.core.usersubstitution.CurrentUserSubstitution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service(BaseUtilsService.NAME)
public class BaseUtilsServiceBean extends AbstractServiceBean implements BaseUtilsService {

    @Autowired
    private DataManager dataManager;

    @Autowired
    private CurrentUserSubstitution currentUserSubstitution;

    @Override
    public Organization getCurrentOrganization() {
        return dataManager.load(Organization.class)
                .query("" +
                        "SELECT o " +
                        "FROM Organization o " +
                        "WHERE o.id IN (SELECT u.organization.id FROM User u WHERE u.username = :username)")
                .parameter("username", currentUserSubstitution.getAuthenticatedUser().getUsername())
                .optional().orElse(null);
    }

    @Override
    public User getCurrentUser() {
        return dataManager.load(User.class)
                .query("" +
                        "SELECT u " +
                        "FROM User u " +
                        "WHERE u.username = :username")
                .parameter("username", currentUserSubstitution.getAuthenticatedUser().getUsername())
                .optional().orElse(null);
    }

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
        if (!StringUtils.nonEmptyString(code)) return null;
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
        }
        return c;
    }

    @Override
    public Organization getOrCreateOrganization(UUID legacyId) {
        return dataManager.load(Organization.class)
                .query("" +
                        "SELECT o " +
                        "FROM Organization o " +
                        "WHERE o.legacyId = :legacyId ")
                .parameter("legacyId", legacyId)
                .optional().orElse(dataManager.create(Organization.class));
    }

    @Override
    public Organization getOrCreateOrganization(String bin) {
        return dataManager.load(Organization.class)
                .query("" +
                        "SELECT o " +
                        "FROM Organization o " +
                        "WHERE o.bin = :bin ")
                .parameter("bin", bin)
                .optional().orElse(dataManager.create(Organization.class));
    }

    @Override
    public Organization getOrganization(String bin) {
        return dataManager.load(Organization.class)
                .query("" +
                        "SELECT o " +
                        "FROM Organization o " +
                        "WHERE o.bin = :bin ")
                .parameter("bin", bin)
                .optional().orElse(null);
    }

    @Override
    public Division getOrCreateDivision(UUID legacyId, UUID orgId) {
        if (legacyId == null)
            return dataManager.load(Division.class)
                    .query("" +
                            "SELECT d " +
                            "FROM Division d " +
                            "WHERE d.main = TRUE " +
                            "   AND d.organization.id = :orgId")
                    .parameter("orgId", orgId)
                    .optional().orElse(dataManager.create(Division.class));

        return dataManager.load(Division.class)
                .query("" +
                        "SELECT d " +
                        "FROM Division d " +
                        "WHERE d.legacyId = :legacyId " +
                        "   AND d.organization.id = :orgId")
                .parameter("legacyId", legacyId)
                .parameter("orgId", orgId)
                .optional().orElse(dataManager.create(Division.class));
    }

    @Override
    public Warehouse getOrCreateWarehouse(UUID legacyId, UUID divisionId) {
        return dataManager.load(Warehouse.class)
                .query("" +
                        "SELECT w " +
                        "FROM Warehouse w " +
                        "WHERE w.legacyId = :legacyId " +
                        "   AND w.division.id = :divisionId")
                .parameter("legacyId", legacyId)
                .parameter("divisionId", divisionId)
                .optional().orElse(dataManager.create(Warehouse.class));
    }

    @Override
    public Item getOrCreateItem(UUID legacyId, UUID warehouseId) {
        return dataManager.load(Item.class)
                .query("" +
                        "SELECT i " +
                        "FROM Item i " +
                        "WHERE i.legacyId = :legacyId " +
                        "   AND i.warehouse.id = :warehouseId")
                .parameter("legacyId", legacyId)
                .parameter("warehouseId", warehouseId)
                .optional().orElse(dataManager.create(Item.class));
    }

    @Override
    public boolean usernameExist(String username) {
        User user = dataManager.load(User.class)
                .query("select u from User u where u.username = :username")
                .parameter("username", username)
                .optional().orElse(null);
        return user != null;
    }

    @Override
    public boolean iinExist(String iin) {
        User user = dataManager.load(User.class)
                .query("select u from User u where u.iin = :iin")
                .parameter("iin", iin)
                .optional().orElse(null);
        return user != null;
    }


}
