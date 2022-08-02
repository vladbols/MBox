package com.company.mbox.controller;

import com.company.mbox.controller.abstractClasses.AbstractController;
import com.company.mbox.dto.*;
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
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class GetPostOrderController extends AbstractController {

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

//    @RequestMapping(value = "/setDocs", method = RequestMethod.POST, produces = "application/pdf;charset=utf-8")
//    public ResponseEntity<?> getDocs() {
//        try {
//            PGobject singleResult = (PGobject) entityManager.createNativeQuery(getOrdersQuery()).getSingleResult();
//            return ok(singleResult != null ? singleResult.getValue() : "[]");
//        } catch (Exception e) {
//            log.error("### Error occurred. Error message: [{}]", e.getMessage());
//            return badRequest(e.getMessage());
//        }
//    }
//
//    @RequestMapping(value = "/pdfFile", method = RequestMethod.POST, produces = {"application/pdf"})
//    @ResponseBody
//    public FileSystemResource getFile(@ModelAttribute ResultDocsDto pdfFile) {
//        PdfFileGenerator pdfFileGenerator = new PdfFileGeneratorImpl();
//        File file = pdfFileGenerator.generatePdf(pdfFile);
//        return new FileSystemResource(file);
//    }

    @RequestMapping(value = "/setItems", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public ResponseEntity<?> setItems(@RequestBody List<CompanyRequestDto> companyRequestDto) {
        if (companyRequestDto == null || companyRequestDto.isEmpty()) {
            return badRequest("Request body is null or empty");
        }

        for (CompanyRequestDto cDto : companyRequestDto) {
            Organization o;
            try {
                o = baseUtilsService.getOrCreateOrganization(cDto.getOrg_uid());
                o.setActive(false);
                o.setBin(cDto.getBin());
                o.setKbe(cDto.getKbe());
                o.setBik(cDto.getBik());
                o.setName(cDto.getOrg());
                o.setBank(cDto.getBank());
                o.setAddress(cDto.getAddress());
                o.setAccount(cDto.getAccount());
                o.setLegacyId(cDto.getOrg_uid());
                o.setDate(dateTimeFormat.parse(cDto.getDate()));
                o.setCurrency(baseUtilsService.getOrCreateCurrency(cDto.getCurrency()));
                dataManager.save(o);
            } catch (Exception e) {
                log.error("### Error", e);
                continue;
            }

            for (DivisionRequestDto dDto : cDto.getDivisions()) {
                Division d;
                try {
                    d = baseUtilsService.getOrCreateDivision(dDto.getDivision_uid(), o.getId());
                    d.setOrganization(o);
                    d.setName(dDto.getDivision());
                    d.setAddress(dDto.getAddress());
                    d.setLegacyId(dDto.getDivision_uid());
                    dataManager.save(d);
                } catch (Exception e) {
                    log.error("### Error", e);
                    continue;
                }

                for (WarehouseRequestDto wDto : dDto.getStors()) {
                    Warehouse w;
                    try {
                        w = baseUtilsService.getOrCreateWarehouse(wDto.getStore_uid(), d.getId());
                        w.setDivision(d);
                        w.setName(wDto.getStore());
                        w.setAddress(wDto.getAddress());
                        w.setLegacyId(wDto.getStore_uid());
                        dataManager.save(w);
                    } catch (Exception e) {
                        log.error("### Error", e);
                        continue;
                    }

                    for (ItemsRequestDto iDto : wDto.getList()) {
                        try {
                            Item i = baseUtilsService.getOrCreateItem(iDto.getItem_uid(), w.getId());
                            i.setWarehouse(w);
                            i.setName(iDto.getItem());
                            i.setUnit(iDto.getUnit());
                            i.setType(iDto.getType());
                            i.setPrice(iDto.getPrice());
                            i.setAmount(iDto.getNumber());
                            i.setLegacyId(iDto.getItem_uid());
                            i.setCategory(iDto.getCategory());
                            dataManager.save(i);
                        } catch (Exception e) {
                            log.error("### Error", e);
                        }
                    }
                }
            }
        }
        return ok("ok");
    }

    public String getOrdersQuery() {
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
                "                    SELECT DISTINCT " +
                "                        ord.id                     AS \"order_uid\", " +
                "                        customer.name              AS \"client\", " +
                "                        customer.bin               AS \"client_bin\", " +
                "                        ord.number_                AS \"number\", " +
                "                        ord.datetime               AS \"date\", " +
                "                        c.code                     AS \"currency\", " +
                "                        d.name                     AS \"division\", " +
                "                        d.legacy_id                AS \"division_uid\", " +
                "                        w.name                     AS \"store\", " +
                "                        w.legacy_id                AS \"store_uid\", " +
                "                        customer.contacts          AS \"telephone\", " +
                "                        ord.comment_               AS \"comment\", " +
                "                        ARRAY[array_agg(oi.id)]    AS \"items_id\" " +
                "                    FROM order_ ord " +
                "                        JOIN order_item oi " +
                "                            ON oi.deleted_by IS NULL " +
                "                                AND oi.order_id = ord.id " +
                "                        JOIN item i " +
                "                            ON i.deleted_by IS NULL " +
                "                                AND i.id = oi.item_id " +
                "                        JOIN warehouse w " +
                "                            ON w.deleted_by IS NULL " +
                "                                AND w.id = i.warehouse_id " +
                "                        JOIN division d " +
                "                            ON d.deleted_by IS NULL " +
                "                                AND d.id = w.division_id " +
                "                        JOIN organization org " +
                "                            ON org.deleted_by IS NULL " +
                "                                AND org.id = d.organization_id " +
                "                        JOIN currency c " +
                "                            ON c.deleted_by IS NULL " +
                "                                AND c.id = org.currency_id " +
                "                        JOIN organization customer " +
                "                            ON customer.deleted_by IS NULL " +
                "                                AND customer.id = ord.organization_id " +
                "                    WHERE ord.deleted_by IS NULL " +
                "                        AND org.id = o.id " +
                "                        GROUP BY " +
                "                            ord.id, " +
                "                            customer.name, " +
                "                            customer.bin, " +
                "                            ord.number_, " +
                "                            ord.datetime, " +
                "                            c.code, " +
                "                            d.name, " +
                "                            d.legacy_id, " +
                "                            w.name, " +
                "                            w.legacy_id, " +
                "                            customer.contacts " +
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