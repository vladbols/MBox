package com.company.mbox.screen.orderitem;

import com.company.mbox.entity.Order;
import com.company.mbox.services.BaseUtilsService;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.Table;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.mbox.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("OrdersController")
@UiDescriptor("orders.xml")
@LookupComponent("orderItemsTable")
public class OrdersController extends StandardLookup<OrderItem> {

    @Autowired
    private BaseUtilsService baseUtilsService;

    @Autowired
    private CollectionLoader<OrderItem> orderItemsDl;
    @Autowired
    private CollectionContainer<OrderItem> orderItemsDc;

    @Autowired
    private CollectionContainer<Order> ordersDc;
    @Autowired
    private CollectionLoader<Order> ordersDl;

    @Autowired
    private GroupTable<Order> ordersTable;
    @Autowired
    private GroupTable<OrderItem> orderItemsTable;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        ordersDl.setParameter("organization", baseUtilsService.getCurrentOrganization());
        ordersDl.load();
    }

    @Subscribe("ordersTable")
    public void onOrdersTableSelection(Table.SelectionEvent<Order> event) {
        Order order = event.getSource().getSingleSelected();
        orderItemsDl.setParameter("organization", baseUtilsService.getCurrentOrganization());
        orderItemsDl.setParameter("order", order);
        orderItemsDl.setParameter("param", order != null ? 1 : 2);
        orderItemsDl.load();
    }
}