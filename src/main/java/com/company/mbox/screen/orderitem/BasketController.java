package com.company.mbox.screen.orderitem;

import com.company.mbox.models.NotificationModel;
import com.company.mbox.services.BaseUtilsService;
import com.company.mbox.services.BasketService;
import io.jmix.core.Messages;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.mbox.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.text.DecimalFormat;
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
    private DataGrid<OrderItem> orderItemsTable;

    @Autowired
    private BaseUtilsService baseUtilsService;

    @Named("orderItemsTable.createOrderAction")
    private BaseAction orderItemsTableCreateOrderAction;

    @Autowired
    private Button totalSelectedCost;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        orderItemsDl.setParameter("organization", baseUtilsService.getCurrentOrganization());
        orderItemsDl.load();
        totalSelectedCost.setCaption(String.format(
                messages.getMessage(getClass(), "totalSelectedRowsCost"),
                0));
    }

    @Subscribe("orderItemsTable")
    public void onOrderItemsTableSelection(DataGrid.SelectionEvent<OrderItem> event) {
        orderItemsTableCreateOrderAction.setEnabled(event.getSelected().size() > 0);

        Double total = event.getSelected().stream().mapToDouble(OrderItem::getTotalPrice).sum();
        DecimalFormat decimalFormat = new DecimalFormat("0.###############");

        totalSelectedCost.setCaption(String.format(
                messages.getMessage(getClass(), "totalSelectedRowsCost"),
                decimalFormat.format(total)));
    }

    @Subscribe("orderItemsTable.createOrderAction")
    public void onOrderItemsTableCreateOrderAction(Action.ActionPerformedEvent event) {
        Set<OrderItem> itemOrders = orderItemsTable.getSelected();
        if (!itemOrders.isEmpty()) {
            List<OrderItem> ordersList = new ArrayList<>(itemOrders);
            createOrder(ordersList, "");
//            dialogs.createInputDialog(this)
//                    .withWidth("500px")
//                    .withCaption(messages.getMessage(getClass(), "confirmSelectedRows"))
//                    .withParameters(
//                            InputParameter.stringParameter("comment")
//                                    .withCaption(messages.getMessage(getClass(), "comment"))
//                                    .withRequired(true)
//                    )
//                    .withActions(DialogActions.OK_CANCEL
////                            InputDialogAction.action("confirm")
////                                    .withCaption(messages.getMessage(getClass(), "confirm"))
////                            .withHandler(han -> createOrder(ordersList,
////                                    Objects.requireNonNull(han.getInputDialog())
////                                            .getValue("comment")
////                                            .toString())),
////                            InputDialogAction.action("close")
////                                    .withCaption(messages.getMessage(getClass(), "cancel"))
////                                    .withValidationRequired(false)
////                                    .withHandler(actionEvent ->
////                                            Objects.requireNonNull(actionEvent.getInputDialog()).closeWithDefaultAction())
//                    )
//                    .withCloseListener(closeEvent -> {
//                        if (closeEvent.closedWith(DialogOutcome.OK)) {
//                            String comment = closeEvent.getValue("comment");
//                            createOrder(ordersList, comment);
//                        }
//                    })
//                    .show();
        } else {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption(messages.getMessage(getClass(), "noRowSelected"))
                    .withDescription(messages.getMessage(getClass(), "selectMoreThanZeroRow"))
                    .show();
        }
    }

    private void createOrder(List<OrderItem> ordersList, String comment) {
        NotificationModel nm = basketService.createOrder(ordersList, comment);
        if (nm.getNotificationType().equals(Notifications.NotificationType.HUMANIZED)) {
            orderItemsDl.load();
        }
        notifications.create(nm.getNotificationType())
                .withCaption(nm.getCaption())
                .withDescription(nm.getDescription())
                .show();
    }

//                            new BaseAction("confirmAction")
//                                    .withCaption(messages.getMessage(getClass(), "confirm"))
//            .withHandler(han -> createOrder(ordersList)),
//            new DialogAction(DialogAction.Type.CANCEL)
//                                    .withCaption(messages.getMessage(getClass(), "cancel")))


}