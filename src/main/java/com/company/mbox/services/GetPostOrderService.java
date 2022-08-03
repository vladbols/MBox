package com.company.mbox.services;

import com.company.mbox.dto.ItemOrderDto;
import com.company.mbox.models.NotificationModel;
import com.company.mbox.models.SavedOrganizationModel;
import io.jmix.core.FetchPlan;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface GetPostOrderService {
    String NAME = "mbox_GetPostOrderService";

    void deleteOlds(List<SavedOrganizationModel> savedOrganizations);
}
