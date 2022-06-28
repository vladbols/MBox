package com.company.mbox.screen.wearhouse;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.Wearhouse;

@UiController("Wearhouse.edit")
@UiDescriptor("wearhouse-edit.xml")
@EditedEntityContainer("wearhouseDc")
public class WearhouseEdit extends StandardEditor<Wearhouse> {
}