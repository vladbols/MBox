package com.company.mbox.screen.warehouse;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.Warehouse;

@UiController("Warehouse.browse")
@UiDescriptor("warehouse-browse.xml")
@LookupComponent("warehousesTable")
public class WarehouseBrowse extends StandardLookup<Warehouse> {
}