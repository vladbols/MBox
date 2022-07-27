package com.company.mbox.services;

import com.company.mbox.dto.ItemOrderDto;
import com.company.mbox.models.NotificationModel;
import io.jmix.core.FetchPlan;

import java.util.Collection;
import java.util.List;

public interface ItemsService {
    String NAME = "mbox_ItemsService";

    Collection<ItemOrderDto> getItemOrders(FetchPlan fp);

    NotificationModel addItemsToBasket(List<ItemOrderDto> itemOrders);
}
