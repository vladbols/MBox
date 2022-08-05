package com.company.mbox.serviceBean;

import com.company.mbox.abstracts.AbstractServiceBean;
import com.company.mbox.entity.*;
import com.company.mbox.models.SavedDivisionModel;
import com.company.mbox.models.SavedOrganizationModel;
import com.company.mbox.models.SavedWarehouseModel;
import com.company.mbox.services.GetPostOrderService;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

@Service(GetPostOrderService.NAME)
public class GetPostOrderServiceBean extends AbstractServiceBean implements GetPostOrderService {

    @Autowired
    private DataManager dataManager;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void deleteOlds(List<SavedOrganizationModel> savedOrganizations) {
        for (SavedOrganizationModel org : savedOrganizations) {
            for (SavedDivisionModel div : org.getSavedD()) {
                for (SavedWarehouseModel ware : div.getSavedW()) {
                    List<Item> items = dataManager.load(Item.class)
                            .query("" +
                                    "SELECT i " +
                                    "FROM Item i " +
                                    "WHERE i.id NOT IN :iIds " +
                                    "   AND i.warehouse.id = :wId " +
                                    "   AND i.warehouse.division.id = :dId " +
                                    "   AND i.warehouse.division.organization.id = :oId ")
                            .parameter("iIds", ware.getSavedI())
                            .parameter("wId", ware.getWarehouseId())
                            .parameter("dId", div.getDivisionId())
                            .parameter("oId", org.getOrganizationId())
                            .list();
                    log.info("### Items for delete: [{}]", items);
                    items.forEach(entity -> {
                        try {
                            dataManager.remove(entity);
                            log.info("### Deleted item: [{}, {}]", entity.getId(), entity.getName());
                        } catch (Exception e) {
                            log.error("### Error occurred on deleting item: [{}, {}], Error Message: [{}]",
                                    entity.getId(), entity.getName(), e.getMessage());
                        }
                    });
                }
                List<Warehouse> warehouses = dataManager.load(Warehouse.class)
                        .query("" +
                                "SELECT w " +
                                "FROM Warehouse w " +
                                "WHERE w.id NOT IN :wIds " +
                                "   AND w.division.id = :dId " +
                                "   AND w.division.organization.id = :oId ")
                        .parameter("wIds", div.getSavedW().stream().map(SavedWarehouseModel::getWarehouseId)
                                .collect(Collectors.toList()))
                        .parameter("dId", div.getDivisionId())
                        .parameter("oId", org.getOrganizationId())
                        .list();
                log.info("### Warehouses for delete: [{}]", warehouses);
                warehouses.forEach(entity -> {
                    try {
                        dataManager.remove(entity);
                        log.info("### Deleted warehouse: [{}, {}]", entity.getId(), entity.getName());
                    } catch (Exception e) {
                        log.error("### Error occurred on deleting item: [{}, {}], Error Message: [{}]",
                                entity.getId(), entity.getName(), e.getMessage());
                    }
                });
            }
            List<Division> divisions = dataManager.load(Division.class)
                    .query("" +
                            "SELECT d " +
                            "FROM Division d " +
                            "WHERE d.id NOT IN :dIds " +
                            "   AND d.organization.id = :oId ")
                    .parameter("dIds", org.getSavedD().stream().map(SavedDivisionModel::getDivisionId)
                            .collect(Collectors.toList()))
                    .parameter("oId", org.getOrganizationId())
                    .list();
            log.info("### Divisions for delete: [{}]", divisions);
            divisions.forEach(entity -> {
                try {
                    dataManager.remove(entity);
                    log.info("### Deleted division: [{}, {}]", entity.getId(), entity.getName());
                } catch (Exception e) {
                    log.error("### Error occurred on deleting item: [{}, {}], Error Message: [{}]",
                            entity.getId(), entity.getName(), e.getMessage());
                }
            });
        }
    }

    @Override
    @Transactional
    @SuppressWarnings("all")
    public void updateOrders() {
        try {
            log.info("### Updating Orders Last Taken DateTime start");
            int updated = entityManager.createNativeQuery("" +
                            "UPDATE order_ " +
                            "SET " +
                            "   last_modified_by = '1C', " +
                            "   last_modified_date = current_timestamp, " +
                            "   last_taken_date = current_timestamp " +
                            "WHERE last_taken_date IS NULL")
                    .executeUpdate();
            log.info("### Updated rows count: {}", updated);
        } catch (Exception e) {
            log.info("### Can't update");
        }
    }
}
