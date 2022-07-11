package com.company.mbox.screen.ordergroup;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.OrderGroup;

@UiController("OrderGroup.browse")
@UiDescriptor("order-group-browse.xml")
@LookupComponent("orderGroupsTable")
public class OrderGroupBrowse extends StandardLookup<OrderGroup> {
}