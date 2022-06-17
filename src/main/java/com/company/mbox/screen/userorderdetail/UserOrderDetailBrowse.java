package com.company.mbox.screen.userorderdetail;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.UserOrderDetail;

@UiController("UserOrderDetail.browse")
@UiDescriptor("user-order-detail-browse.xml")
@LookupComponent("userOrderDetailsTable")
public class UserOrderDetailBrowse extends StandardLookup<UserOrderDetail> {
}