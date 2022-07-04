package com.company.mbox.screen.dicsidebar;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.DicSideBar;

@UiController("DicSideBar.browse")
@UiDescriptor("dic-side-bar-menu.xml")
@LookupComponent("dicSideBarsTable")
public class DicSideBarBrowse extends StandardLookup<DicSideBar> {
}