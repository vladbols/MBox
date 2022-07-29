package com.company.mbox.serviceBean;

import com.company.mbox.entity.*;
import com.company.mbox.security.DatabaseUserRepository;
import com.company.mbox.services.BaseUtilsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jmix.core.DataManager;
import io.jmix.core.usersubstitution.CurrentUserSubstitution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service(BaseUtilsService.NAME)
public class BaseUtilsServiceBean implements BaseUtilsService {

    private static final Logger log = LoggerFactory.getLogger(BaseUtilsServiceBean.class);

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
        }
        return c;
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
    public Division getOrCreateDivision(Integer legacyId, UUID orgId) {
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
    public Warehouse getOrCreateWarehouse(Integer legacyId, UUID divisionId) {
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
    public Item getOrCreateItem(Integer legacyId, UUID warehouseId) {
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
    public User usernameExist(String username) {
        return dataManager.load(User.class)
                .query("select u from User u where u.username = :username")
                .parameter("username", username)
                .optional().orElse(null);

    }
    @Override
    public User iinExist(String iin) {
        return dataManager.load(User.class)
                .query("select u from User u where u.iin = :iin")
                .parameter("iin", iin)
                .optional().orElse(null);

    }



}
