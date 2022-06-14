package com.company.mbox.screen.userorder;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.UserOrder;

@UiController("UserOrder.edit")
@UiDescriptor("user-order-edit.xml")
@EditedEntityContainer("userOrderDc")
public class UserOrderEdit extends StandardEditor<UserOrder> {
}