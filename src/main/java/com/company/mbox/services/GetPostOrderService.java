package com.company.mbox.services;

import com.company.mbox.models.SavedOrganizationModel;

import java.util.List;

public interface GetPostOrderService {
    String NAME = "mbox_GetPostOrderService";

    void deleteOlds(List<SavedOrganizationModel> savedOrganizations);

    void updateOrders();
}
