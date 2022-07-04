package com.company.mbox.screen.dicsidebar;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.DicSideBar;

@UiController("DicSideBar.edit")
@UiDescriptor("dic-side-bar-menu-edit.xml")
@EditedEntityContainer("dicSideBarDc")
public class DicSideBarEdit extends StandardEditor<DicSideBar> {
}