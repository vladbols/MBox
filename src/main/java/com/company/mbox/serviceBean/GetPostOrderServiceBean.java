package com.company.mbox.serviceBean;

import com.company.mbox.entity.*;
import com.company.mbox.models.SavedDivisionModel;
import com.company.mbox.models.SavedOrganizationModel;
import com.company.mbox.models.SavedWarehouseModel;
import com.company.mbox.services.GetPostOrderService;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.core.Metadata;
import io.jmix.core.usersubstitution.CurrentUserSubstitution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service(GetPostOrderService.NAME)
public class GetPostOrderServiceBean implements GetPostOrderService {

    private static final Logger log = LoggerFactory.getLogger(GetPostOrderServiceBean.class);

    @Autowired
    private DataManager dataManager;

    @Autowired
    private CurrentUserSubstitution currentUserSubstitution;

    @Autowired
    private Metadata metadata;

    @Override
    public void deleteOlds(List<SavedOrganizationModel> savedOrganizations) {
//        for (SavedOrganizationModel org : savedOrganizations) {
//            for (SavedDivisionModel div : org.getSavedD()) {
//                for (SavedWarehouseModel ware : div.getSavedW()) {
//                    deleteOlds(ware.getSavedI(), Item.class);
//                }
//            }
//        }
    }

    public <T extends Object> void deleteOlds(List<UUID> ids, Class<T> entityClass) {

//        List<T> entities = dataManager.load();
//
//        for (T obj : entities) {
//            dataManager.remove(obj);
//        }

    }
}
