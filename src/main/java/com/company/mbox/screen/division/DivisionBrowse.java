package com.company.mbox.screen.division;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.Division;

@UiController("Division.browse")
@UiDescriptor("division-browse.xml")
@LookupComponent("divisionsTable")
public class DivisionBrowse extends StandardLookup<Division> {
}