package com.company.mbox.screen.userorder;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.UserOrder;

@UiController("UserOrder.browse")
@UiDescriptor("user-order-browse.xml")
@LookupComponent("userOrdersTable")
public class UserOrderBrowse extends StandardLookup<UserOrder> {
}