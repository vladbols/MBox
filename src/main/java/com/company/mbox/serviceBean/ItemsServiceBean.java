package com.company.mbox.serviceBean;

import com.company.mbox.abstracts.AbstractServiceBean;
import com.company.mbox.dto.ItemOrderDto;
import com.company.mbox.entity.Item;
import com.company.mbox.entity.OrderItem;
import com.company.mbox.models.NotificationModel;
import com.company.mbox.services.BaseUtilsService;
import com.company.mbox.services.ItemsService;
import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.ui.Notifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service(ItemsService.NAME)
public class ItemsServiceBean extends AbstractServiceBean implements ItemsService {

    @Autowired
    private DataManager dataManager;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BaseUtilsService baseUtilsService;

    @Autowired
    private Messages messages;

    @Override
    @SuppressWarnings("all")
    public Collection<ItemOrderDto> getItemOrders(String name, Integer amount) {
        List<Object[]> items = entityManager.createNativeQuery("" +
                "SELECT " +
                "    i.id       AS id, " +
                "    i.name     AS name, " +
                "    i.price    AS price, " +
                "    i.type_    AS type, " +
                "    i.unit     AS unit, " +
                "    o.name     AS organization, " +
                "    w.address  AS warehouseAddress " +
                "FROM item i " +
                "    JOIN warehouse w " +
                "        ON w.deleted_by IS NULL " +
                "            AND w.id = i.warehouse_id " +
                "    JOIN division d " +
                "        ON d.deleted_by IS NULL " +
                "            AND d.id = w.division_id " +
                "    JOIN organization o " +
                "        ON o.deleted_by IS NULL " +
                "            AND o.id = d.organization_id " +
                "WHERE STRING_TO_ARRAY(LOWER(?1), ' ') <@ STRING_TO_ARRAY(LOWER(i.name), ' ')" +
                "    AND i.amount >= ?2")
                .setParameter(1, name)
                .setParameter(2, amount)
                .getResultList();

        Collection<ItemOrderDto> itemOrders = new ArrayList<>();
        for (Object[] i : items) {
            ItemOrderDto itemOrder = dataManager.create(ItemOrderDto.class);
            itemOrder.setId((UUID) i[0]);
            itemOrder.setName((String) i[1]);
            itemOrder.setAmount(0);
            itemOrder.setPrice((Double) i[2]);
            itemOrder.setType((String) i[3]);
            itemOrder.setUnit((String) i[4]);
            itemOrder.setOrganization((String) i[5]);
            itemOrder.setWarehouseAddress((String) i[6]);

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
