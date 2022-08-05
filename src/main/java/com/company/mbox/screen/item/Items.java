package com.company.mbox.screen.item;

import com.company.mbox.dto.ItemOrderDto;
import com.company.mbox.dto.PassScreenOptions;
import com.company.mbox.models.NotificationModel;
import com.company.mbox.services.ItemsService;
import com.mchange.v2.lang.StringUtils;
import io.jmix.core.Messages;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.action.DialogAction;
import io.jmix.ui.component.*;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.mbox.entity.Item;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@UiController("Items")
@UiDescriptor("items.xml")
public class Items extends StandardLookup<Item> {

    private final Logger log = LoggerFactory.getLogger(Items.class);

    @Autowired
    private Notifications notifications;

    @Autowired
    private Messages messages;

    @Autowired
    private Dialogs dialogs;

    @Autowired
    private PropertyFilter<String> name_property;

    @Autowired
    private CollectionContainer<ItemOrderDto> itemOrdersDc;

    @Autowired
    private DataGrid<ItemOrderDto> itemsTable;

    @Autowired
    private ItemsService itemsService;

    @Autowired
    private PropertyFilter<Integer> amount_property;

    @Subscribe("name_property")
    public void onName_propertyValueChange(HasValue.ValueChangeEvent<String> event) {
        refreshTable();
    }

    @Subscribe("amount_property")
    public void onAmount_propertyValueChange(HasValue.ValueChangeEvent<Integer> event) {
        refreshTable();
    }

    private void refreshTable() {
        String name = StringUtils.nonNullOrBlank(name_property.getValue());
        Integer amount = ObjectUtils.defaultIfNull(amount_property.getValue(), 0);
        reloadTable(name, amount);
    }

    @Subscribe
    public void onInit(InitEvent event) {
        ScreenOptions options = event.getOptions();
        if (options instanceof PassScreenOptions) {
            String param = ((PassScreenOptions) options).getName();
            name_property.setValue(param);
        }
        refreshTable();
    }

    @Subscribe("itemsTable.addToBasketAction")
    public void onItemsTableAddToBasketAction(Action.ActionPerformedEvent event) {
        Set<ItemOrderDto> itemOrders = itemsTable.getSelected();
        if (!itemOrders.isEmpty()) {
            List<ItemOrderDto> itemsList = new ArrayList<>();
            String messageBody = "";
            for (ItemOrderDto item : itemOrders) {
                if (item.getAmount() > 0) {
                    itemsList.add(item);
                    String tableBody =
                            "<tr>" +
                                    "   <td>%s</td>" +
                                    "   <td>%s</td>" +
                                    "   <td>%s</td>" +
                                    "   <td>%s</td>" +
                                    "</tr>";
                    messageBody = String.format("%s%s", messageBody,
                            String.format(tableBody,
                                    item.getName(), item.getOrganization(),
                                    item.getPrice(), item.getAmount()));
                }
            }

            if (!itemsList.isEmpty()) {
                String tableHeader =
                        "<table>" +
                                "    <thead>" +
                                "        <tr>" +
                                "            <th>%s</th>" +
                                "            <th>%s</th>" +
                                "            <th>%s</th>" +
                                "            <th>%s</th>" +
                                "        </tr>" +
                                "    </thead>" +
                                "    <tbody>%s</tbody>" +
                                "</table>";
                String message = String.format(tableHeader,
                        messages.getMessage("com.company.mbox.screen.item/field.name"),
                        messages.getMessage("com.company.mbox.screen.item/field.organization"),
                        messages.getMessage("com.company.mbox.screen.item/field.price"),
                        messages.getMessage("com.company.mbox.screen.item/field.amount"),
                        messageBody);

                dialogs.createOptionDialog()
                        .withWidth("600px")
                        .withCaption(messages.getMessage(getClass(), "confirmSelectedRows"))
                        .withMessage(message)
                        .withContentMode(ContentMode.HTML)
                        .withActions(
                                new BaseAction("confirmAction")
                                        .withCaption(messages.getMessage(getClass(), "confirm"))
                                        .withHandler(han -> addItemsToBasket(itemsList)),
                                new DialogAction(DialogAction.Type.CANCEL)
                                        .withCaption(messages.getMessage(getClass(), "cancel")))
                        .show();
            } else {
                notifications.create(Notifications.NotificationType.WARNING)
                        .withCaption(messages.getMessage(getClass(), "amountZero"))
                        .withDescription(messages.getMessage(getClass(), "fillAmountField"))
                        .show();
            }
        } else {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption(messages.getMessage(getClass(), "noRowSelected"))
                    .withDescription(messages.getMessage(getClass(), "selectMoreThanZeroRow"))
                    .show();
        }
    }

    private void addItemsToBasket(List<ItemOrderDto> itemsList) {
        NotificationModel nm = itemsService.addItemsToBasket(itemsList);
        if (nm.getNotificationType().equals(Notifications.NotificationType.HUMANIZED)) {
            reloadTable("", 0);
        }
        notifications.create(nm.getNotificationType())
                .withCaption(nm.getCaption())
                .withDescription(nm.getDescription())
                .show();
    }

    private void reloadTable(String name, Integer amount) {
        itemOrdersDc.setItems(itemsService.getItemOrders(name, amount));
    }

}