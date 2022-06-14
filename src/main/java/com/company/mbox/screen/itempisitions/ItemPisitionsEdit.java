package com.company.mbox.screen.itempisitions;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.ItemPisitions;

@UiController("ItemPisitions.edit")
@UiDescriptor("item-pisitions-edit.xml")
@EditedEntityContainer("itemPisitionsDc")
public class ItemPisitionsEdit extends StandardEditor<ItemPisitions> {
}