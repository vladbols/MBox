package com.company.mbox.screen.orderitem;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.OrderItem;

@UiController("OrderItem.edit")
@UiDescriptor("order-item-edit.xml")
@EditedEntityContainer("orderItemDc")
public class OrderItemEdit extends StandardEditor<OrderItem> {
}