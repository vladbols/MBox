package com.company.mbox.serviceBean;

import com.company.mbox.dto.ItemOrderDto;
import com.company.mbox.entity.*;
import com.company.mbox.models.NotificationModel;
import com.company.mbox.services.BaseUtilsService;
import com.company.mbox.services.BasketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.Messages;
import io.jmix.ui.Notifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service(BasketService.NAME)
public class BasketServiceBean implements BasketService {

    private static final Logger log = LoggerFactory.getLogger(BasketServiceBean.class);

    @Autowired
    private DataManager dataManager;

    @Autowired
    private BaseUtilsService baseUtilsService;

    @Autowired
    private Messages messages;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public NotificationModel createOrder(List<OrderItem> ordersList) {
        if (ordersList.isEmpty()) {
            return new NotificationModel(
                    Notifications.NotificationType.ERROR,
                    messages.getMessage(getClass(), "nothingToCreate"));
        }

        Order order = dataManager.create(Order.class);
        try {
            order.setComment("");
            order.setOrderItemId(ordersList);
            order.setNumber(getLastOrderNumber());
            order.setOrganization(baseUtilsService.getCurrentOrganization());
            order.setDatetime(LocalDateTime.now());
            order.setTotalPrice(ordersList.stream().mapToDouble(OrderItem::getPrice).sum());
            dataManager.save(order);

            for (OrderItem oi : ordersList) {
                OrderItem uoi = dataManager.load(OrderItem.class).id(oi.getId()).one();
                uoi.setOrder(order);
                dataManager.save(uoi);
            }


        } catch (Exception ex) {
            log.error("### Can't create order by Items: [{}]. Error message: [{}]",
                    ordersList.stream().map(l -> l.getItem().getId()).collect(Collectors.toList()),
                    ex.getMessage());
            return new NotificationModel(
                    Notifications.NotificationType.ERROR,
                    messages.getMessage(getClass(), "errorOnCreation"));
        }

        return new NotificationModel(
                Notifications.NotificationType.HUMANIZED,
                messages.getMessage(getClass(), "successCreatedOrder"));
    }

    @Transactional
    @SuppressWarnings("all")
    public Long getLastOrderNumber() {
        List<Object> numbers = entityManager.createNativeQuery("" +
                "SELECT o.number_ " +
                "FROM order_ o " +
                "ORDER BY o.number_ DESC " +
                "LIMIT 1").getResultList();

        return numbers.isEmpty() ? 1L : ((Long) numbers.get(0)) + 1;
    }
}
