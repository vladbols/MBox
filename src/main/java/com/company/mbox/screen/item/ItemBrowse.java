package com.company.mbox.screen.item;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.Item;

@UiController("Item.browse")
@UiDescriptor("item-browse.xml")
@LookupComponent("itemsTable")
public class ItemBrowse extends StandardLookup<Item> {
}