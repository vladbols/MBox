package com.company.mbox.screen.orderitem;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.OrderItem;

@UiController("OrderItem.browse")
@UiDescriptor("order-item-browse.xml")
@LookupComponent("orderItemsTable")
public class OrderItemBrowse extends StandardLookup<OrderItem> {
}