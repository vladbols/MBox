package com.company.mbox.screen.division;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.Division;

@UiController("Division.edit")
@UiDescriptor("division-edit.xml")
@EditedEntityContainer("divisionDc")
public class DivisionEdit extends StandardEditor<Division> {
}