package com.company.mbox.screen.wearhouse;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.Wearhouse;

@UiController("Wearhouse.browse")
@UiDescriptor("wearhouse-browse.xml")
@LookupComponent("wearhousesTable")
public class WearhouseBrowse extends StandardLookup<Wearhouse> {
}