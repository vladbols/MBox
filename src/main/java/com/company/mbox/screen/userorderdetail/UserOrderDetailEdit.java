package com.company.mbox.screen.userorderdetail;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.UserOrderDetail;

@UiController("UserOrderDetail.edit")
@UiDescriptor("user-order-detail-edit.xml")
@EditedEntityContainer("userOrderDetailDc")
public class UserOrderDetailEdit extends StandardEditor<UserOrderDetail> {
}