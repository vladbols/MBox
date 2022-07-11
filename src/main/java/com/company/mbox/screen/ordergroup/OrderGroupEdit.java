package com.company.mbox.screen.ordergroup;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.OrderGroup;

@UiController("OrderGroup.edit")
@UiDescriptor("order-group-edit.xml")
@EditedEntityContainer("orderGroupDc")
public class OrderGroupEdit extends StandardEditor<OrderGroup> {
}