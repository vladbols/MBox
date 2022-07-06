package com.company.mbox.screen.item;

import com.company.mbox.dto.PassScreenOptions;
import com.company.mbox.screen.login.LoginScreen;
import io.jmix.ui.component.PropertyFilter;
import io.jmix.ui.screen.*;
import com.company.mbox.entity.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Item.browse")
@UiDescriptor("item-browse.xml")
@LookupComponent("itemsTable")
public class ItemBrowse extends StandardLookup<Item> {

    private final Logger log = LoggerFactory.getLogger(LoginScreen.class);

    @Autowired
    private PropertyFilter<String> name_property;

    @Subscribe
    public void onInit(InitEvent event) {
        ScreenOptions options = event.getOptions();
        if (options instanceof PassScreenOptions) {
            String param = ((PassScreenOptions) options).getName();
            name_property.setValue(param);
        }
    }
}