package com.company.mbox.screen.itempisitions;

import com.company.mbox.services.BaseUtilsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jmix.ui.screen.*;
import com.company.mbox.entity.ItemPisitions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@UiController("ItemPisitions.browse")
@UiDescriptor("item-pisitions-browse.xml")
@LookupComponent("itemPisitionsesTable")
public class ItemPisitionsBrowse extends StandardLookup<ItemPisitions> {

    private static final Logger log = LoggerFactory.getLogger(ItemPisitionsBrowse.class);

    @Autowired
    private BaseUtilsService baseUtilsService;

    @Subscribe
    public void onInit(InitEvent event) {

        try {
            String data = new String(Files.readAllBytes(Paths.get("/Users/rakhymkurmangali/desktop/data.json")));

            Map<String, Object> map = baseUtilsService.getObjMap(data);
//            if (!map.isEmpty()) {
//                map.forEach((key, val) -> {
//                    switch (key) {
//                        case "org":
//                            break;
//                        case "org_uid":
//                            break;
//                        case "bin":
//                            break;
//                        case "date":
//                            break;
//                        case "address":
//                        case "kbe":
//                        case "account":
//                        case "bik":
//                        case "bank":
//                        case "currency":
//                        case "divisions":
//                        case "address":
//                        case "address":
//                    }
//                });
//            }


        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}