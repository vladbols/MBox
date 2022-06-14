package com.company.mbox.screen.itempisitions;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.ItemPisitions;

@UiController("ItemPisitions.browse")
@UiDescriptor("item-pisitions-browse.xml")
@LookupComponent("itemPisitionsesTable")
public class ItemPisitionsBrowse extends StandardLookup<ItemPisitions> {
}