package com.company.mbox.screen.unit;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.Unit;

@UiController("Unit.browse")
@UiDescriptor("unit-browse.xml")
@LookupComponent("unitsTable")
public class UnitBrowse extends StandardLookup<Unit> {
}