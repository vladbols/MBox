package com.company.mbox.serviceBean;

import com.company.mbox.dto.ItemOrderDto;
import com.company.mbox.entity.Item;
import com.company.mbox.entity.OrderItem;
import com.company.mbox.models.NotificationModel;
import com.company.mbox.services.BaseUtilsService;
import com.company.mbox.services.ItemsService;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.Messages;
import io.jmix.core.Metadata;
import io.jmix.ui.Notifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service(ItemsService.NAME)
public class ItemsServiceBean implements ItemsService {

    private static final Logger log = LoggerFactory.getLogger(ItemsServiceBean.class);

    @Autowired
    private DataManager dataManager;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private Metadata metadata;

    @Autowired
    private BaseUtilsService baseUtilsService;

    @Autowired
    private Messages messages;

    @Override
    public Collection<ItemOrderDto> getItemOrders(FetchPlan fp) {
        List<Item> items = dataManager.load(Item.class)
                .query("SELECT i FROM Item i")
                .fetchPlan(fp)
                .list();

        Collection<ItemOrderDto> itemOrders = new ArrayList<>();

        for (Item i : items) {
            ItemOrderDto itemOrder = dataManager.create(ItemOrderDto.class);
            itemOrder.setId(i.getId());
            itemOrder.setName(i.getName());
            itemOrder.setAmount(0);
            itemOrder.setPrice(i.getPrice());
            itemOrder.setType(i.getType());
            itemOrder.setUnit(i.getUnit());
            itemOrder.setOrganization(i.getWarehouse().getDivision().getOrganization().getName());
            itemOrder.setWarehouseAddress(i.getWarehouse().getAddress());

            itemOrders.add(itemOrder);
        }
        return itemOrders;
    }

    @Override
    public NotificationModel addItemsToBasket(List<ItemOrderDto> itemOrders) {
        if (itemOrders.isEmpty()) {
            return new NotificationModel(
                    Notifications.NotificationType.ERROR,
                    messages.getMessage(getClass(), "nothingToAdd"));
        }
        List<OrderItem> createdOrderItems = new ArrayList<>();

        for (ItemOrderDto item : itemOrders) {
            try {
                OrderItem oi = dataManager.create(OrderItem.class);

                oi.setAmount(item.getAmount());
                oi.setNumber(getLastOrderItemNumber());
                oi.setItem(dataManager.load(Item.class).id(item.getId()).one());
                oi.setOrganization(baseUtilsService.getCurrentOrganization());
                oi.setCost(item.getPrice());
                dataManager.save(oi);
                createdOrderItems.add(oi);
            } catch (Exception ex) {
                log.error("### Can't create order by ItemID: [{}]. Error message: [{}]", item.getId(), ex.getMessage());
            }
        }

        String message = String.format(messages.getMessage(getClass(), "successAddedToBasket"), createdOrderItems.size());
        return new NotificationModel(
                Notifications.NotificationType.HUMANIZED, message);
    }

    @Transactional
    @SuppressWarnings("all")
    public Long getLastOrderItemNumber() {
        List<Object> numbers = entityManager.createNativeQuery("" +
                "SELECT o.number_ " +
                "FROM order_item o " +
                "ORDER BY o.number_ DESC " +
                "LIMIT 1").getResultList();

        return numbers.isEmpty() ? 1L : ((Long) numbers.get(0)) + 1;
    }
}
