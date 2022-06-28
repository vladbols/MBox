package com.company.mbox.screen.unit;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.Unit;

@UiController("Unit.edit")
@UiDescriptor("unit-edit.xml")
@EditedEntityContainer("unitDc")
public class UnitEdit extends StandardEditor<Unit> {
}