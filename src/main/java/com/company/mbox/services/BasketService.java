package com.company.mbox.services;

import com.company.mbox.dto.ItemOrderDto;
import com.company.mbox.entity.*;
import com.company.mbox.models.NotificationModel;
import io.jmix.core.FetchPlan;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BasketService {
    String NAME = "mbox_BasketService";

    NotificationModel createOrder(List<OrderItem> ordersList);

}
