package com.company.mbox.screen.item;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.Item;

@UiController("Item.edit")
@UiDescriptor("item-edit.xml")
@EditedEntityContainer("itemDc")
public class ItemEdit extends StandardEditor<Item> {
}