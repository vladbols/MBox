package com.company.mbox.screen.item;

import com.company.mbox.screen.login.LoginScreen;
import io.jmix.ui.screen.*;
import com.company.mbox.entity.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UiController("Item.browse")
@UiDescriptor("item-browse.xml")
@LookupComponent("itemsTable")
public class ItemBrowse extends StandardLookup<Item> {
    private final Logger log = LoggerFactory.getLogger(LoginScreen.class);
}