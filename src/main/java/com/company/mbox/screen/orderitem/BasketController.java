package com.company.mbox.screen.orderitem;

import com.company.mbox.dto.ItemOrderDto;
import com.company.mbox.models.NotificationModel;
import com.company.mbox.services.BaseUtilsService;
import com.company.mbox.services.BasketService;
import com.company.mbox.services.ItemsService;
import io.jmix.core.Messages;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.action.DialogAction;
import io.jmix.ui.action.list.RefreshAction;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.ContentMode;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.mbox.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@UiController("BasketController")
@UiDescriptor("basket.xml")
@LookupComponent("orderItemsTable")
public class BasketController extends StandardLookup<OrderItem> {

    @Autowired
    private BasketService basketService;

    @Autowired
    private CollectionLoader<OrderItem> orderItemsDl;

    @Autowired
    private Notifications notifications;

    @Autowired
    private Messages messages;

    @Autowired
    private Dialogs dialogs;

    @Autowired
    private CollectionContainer<OrderItem> orderItemsDc;

    @Autowired
    private DataGrid<OrderItem> orderItemsTable;

    @Autowired
    private BaseUtilsService baseUtilsService;

    @Named("orderItemsTable.refresh")
    private RefreshAction orderItemsTableRefresh;

    @Named("orderItemsTable.createOrderAction")
    private BaseAction orderItemsTableCreateOrderAction;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        orderItemsDl.setParameter("organization", baseUtilsService.getCurrentOrganization());
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        orderItemsTableRefresh.execute();
    }

    @Subscribe("orderItemsTable")
    public void onOrderItemsTableSelection(DataGrid.SelectionEvent<OrderItem> event) {
        orderItemsTableCreateOrderAction.setEnabled(event.getSelected().size() > 0);
    }

    @Subscribe("orderItemsTable.createOrderAction")
    public void onOrderItemsTableCreateOrderAction(Action.ActionPerformedEvent event) {
        Set<OrderItem> itemOrders = orderItemsTable.getSelected();
        if (!itemOrders.isEmpty()) {
            List<OrderItem> ordersList = new ArrayList<>(itemOrders);

            dialogs.createOptionDialog()
                    .withWidth("AUTO")
                    .withCaption(messages.getMessage(getClass(), "confirmSelectedRows"))
                    .withContentMode(ContentMode.HTML)
                    .withActions(
                            new BaseAction("confirmAction")
                                    .withCaption(messages.getMessage(getClass(), "confirm"))
                                    .withHandler(han -> createOrder(ordersList)),
                            new DialogAction(DialogAction.Type.CANCEL)
                                    .withCaption(messages.getMessage(getClass(), "cancel")))
                    .show();
        } else {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption(messages.getMessage(getClass(), "noRowSelected"))
                    .withDescription(messages.getMessage(getClass(), "selectMoreThanZeroRow"))
                    .show();
        }
    }

    private void createOrder(List<OrderItem> ordersList) {
        NotificationModel nm = basketService.createOrder(ordersList);
        notifications.create(nm.getNotificationType())
                .withCaption(nm.getCaption())
                .withDescription(nm.getDescription())
                .show();
    }









}