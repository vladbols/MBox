package com.company.mbox.screen.warehouse;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.Warehouse;

@UiController("Warehouse.edit")
@UiDescriptor("warehouse-edit.xml")
@EditedEntityContainer("warehouseDc")
public class WarehouseEdit extends StandardEditor<Warehouse> {
}