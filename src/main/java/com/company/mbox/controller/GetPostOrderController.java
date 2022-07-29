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
import com.company.mbox.services.BaseUtilsService;
import com.google.gson.Gson;
import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class GetPostOrderController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(GetPostOrderController.class);

    private static final Gson g = new Gson();

    @Autowired
    private BaseUtilsService baseUtilsService;

    @PersistenceContext
    private EntityManager entityManager;


    @GetMapping("/getOrders")
    public ResponseEntity<?> getOrders() {
        try {
            PGobject singleResult = (PGobject) entityManager.createNativeQuery(getOrdersQuery()).getSingleResult();
            return ok(singleResult != null ? singleResult.getValue() : "[]");
        } catch (Exception e) {
            log.error("### Error occurred. Error message: [{}]", e.getMessage());
            return badRequest(e.getMessage());
        }
    }

    @RequestMapping(value = "/setItems", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public ResponseEntity<?> setItems(@RequestBody List<CompanyRequestDto> companyRequestDto) throws ParseException {
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
                Division d = baseUtilsService.getOrCreateDivision(dDto.getDivision_uid(), o.getId());

                d.setName(dDto.getDivision());
                d.setLegacyId(dDto.getDivision_uid());
                d.setAddress(dDto.getAddress());
                d.setOrganization(o);
                dataManager.save(d);

                for (WarehouseRequestDto wDto : dDto.getStors()) {
                    Warehouse w = baseUtilsService.getOrCreateWarehouse(wDto.getStore_uid(), d.getId());

                    w.setName(wDto.getStore());
                    w.setLegacyId(wDto.getStore_uid());
                    w.setAddress(wDto.getAddress());
                    w.setDivision(d);
                    dataManager.save(w);

                    for (ItemsRequestDto iDto : wDto.getList()) {
                        Item i = baseUtilsService.getOrCreateItem(iDto.getItem_uid(), w.getId());

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
        return ok("ok");
    }
    
    public String getOrdersQuery(){
        return "" +
                "SELECT JSON_AGG(orders) " +
                "FROM (" +
                "    SELECT " +
                "        o.id               AS \"id\", " +
                "        o.name             AS \"org\", " +
                "        o.legacy_id        AS \"org_uid\", " +
                "        o.bin              AS \"bin\", " +
                "        o.account          AS \"account\", " +
                "        (" +
                "            SELECT JSON_AGG(docs) " +
                "            FROM (" +
                "                SELECT " +
                "                    ordersJson.order_uid, " +
                "                    ordersJson.client, " +
                "                    ordersJson.client_bin, " +
                "                    ordersJson.number, " +
                "                    ordersJson.date, " +
                "                    ordersJson.currency, " +
                "                    ordersJson.division, " +
                "                    ordersJson.division_uid, " +
                "                    ordersJson.store, " +
                "                    ordersJson.store_uid, " +
                "                    ordersJson.telephone, " +
                "                    ordersJson.comment, " +
                "                    (" +
                "                        SELECT " +
                "                            JSONB_AGG(" +
                "                                JSON_BUILD_OBJECT(" +
                "                                    'uid', it.legacy_id, " +
                "                                    'item', it.name, " +
                "                                    'unit', it.unit, " +
                "                                    'type', it.type_, " +
                "                                    'price', it.price, " +
                "                                    'number', oi2.amount, " +
                "                                    'sum', oi2.cost * oi2.amount " +
                "                                )" +
                "                            )" +
                "                        FROM order_ ord2 " +
                "                            JOIN order_item oi2 " +
                "                                ON oi2.deleted_by IS NULL " +
                "                                    AND oi2.order_id = ord2.id " +
                "                            JOIN item it " +
                "                                ON it.deleted_by IS NULL " +
                "                                    AND it.id = oi2.item_id " +
                "                        WHERE ord2.deleted_by IS NULL " +
                "                            AND oi2.id = ANY(ordersJson.items_id) " +
                "                    ) AS \"list\" " +
                "                FROM (" +
                "                SELECT DISTINCT " +
                "                    ord.id                     AS \"order_uid\", " +
                "                    customer.name              AS \"client\", " +
                "                    customer.bin               AS \"client_bin\", " +
                "                    ord.number_                AS \"number\", " +
                "                    ord.datetime               AS \"date\", " +
                "                    c.code                     AS \"currency\", " +
                "                    d.name                     AS \"division\", " +
                "                    d.legacy_id                AS \"division_uid\", " +
                "                    w.name                     AS \"store\", " +
                "                    w.legacy_id                AS \"store_uid\", " +
                "                    customer.contacts          AS \"telephone\", " +
                "                    ord.comment_               AS \"comment\", " +
                "                    ARRAY[array_agg(oi.id)]    AS \"items_id\" " +
                "                FROM order_ ord " +
                "                    JOIN order_item oi " +
                "                        ON oi.deleted_by IS NULL " +
                "                            AND oi.order_id = ord.id " +
                "                    JOIN item i " +
                "                        ON i.deleted_by IS NULL " +
                "                            AND i.id = oi.item_id " +
                "                    JOIN warehouse w " +
                "                        ON w.deleted_by IS NULL " +
                "                            AND w.id = i.warehouse_id " +
                "                    JOIN division d " +
                "                        ON d.deleted_by IS NULL " +
                "                            AND d.id = w.division_id " +
                "                    JOIN organization org " +
                "                        ON org.deleted_by IS NULL " +
                "                            AND org.id = d.organization_id " +
                "                    JOIN currency c " +
                "                        ON c.deleted_by IS NULL " +
                "                            AND c.id = org.currency_id " +
                "                    JOIN organization customer " +
                "                        ON customer.deleted_by IS NULL " +
                "                            AND customer.id = ord.organization_id " +
                "                WHERE ord.deleted_by IS NULL " +
                "                    AND org.id = o.id " +
                "                    GROUP BY " +
                "                        ord.id, " +
                "                        customer.name, " +
                "                        customer.bin, " +
                "                        ord.number_, " +
                "                        ord.datetime, " +
                "                        c.code, " +
                "                        d.name, " +
                "                        d.legacy_id, " +
                "                        w.name, " +
                "                        w.legacy_id, " +
                "                        customer.contacts " +
                "                ) ordersJson " +
                "            ) docs " +
                "        ) AS \"documents\" " +
                "    FROM organization o " +
                "    WHERE o.deleted_by IS NULL " +
                "        AND o.legacy_id IS NOT NULL " +
                ") orders " +
                "WHERE orders.documents IS NOT NULL";
    }
}