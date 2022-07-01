package com.company.mbox.screen.warehouse;

import com.company.mbox.entity.Warehouse;
import io.jmix.ui.screen.*;

@UiController("Warehouse.browse")
@UiDescriptor("warehouse-browse.xml")
@LookupComponent("warehousesTable")
public class WarehouseBrowse extends StandardLookup<Warehouse> {
}