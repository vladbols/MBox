package com.company.mbox.screen.mainscreentopmenu;

import com.company.mbox.dto.PassScreenOptions;
import com.company.mbox.entity.DicSideBar;
import com.company.mbox.screen.item.Items;
import com.company.mbox.screen.user.UserProfileController;
import io.jmix.core.DataManager;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.ScreenTools;
import io.jmix.ui.Screens;
import io.jmix.ui.component.AppWorkArea;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Window;
import io.jmix.ui.component.mainwindow.SideMenu;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@UiController("MainTop")
@UiDescriptor("main-screen-top-menu.xml")
@Route(path = "main", root = true)
public class MainScreenTopMenu extends Screen implements Window.HasWorkArea {

    private final Logger log = LoggerFactory.getLogger(MainScreenTopMenu.class);

    @Autowired
    private ScreenTools screenTools;

    @Autowired
    private AppWorkArea workArea;

    @Autowired
    private DataManager dataManager;

    @Autowired
    private SideMenu sideMenu;

    @Autowired
    private Screens screens;

    @Autowired
    private ScreenBuilders screenBuilders;

    @Subscribe
    public void onInit(InitEvent event) {
        List<DicSideBar> sb = dataManager
                .load(DicSideBar.class)
                .query(
                        "SELECT s " +
                                "FROM DicSideBar s " +
                                "WHERE s.deletedDate IS NULL")
                .list();

        int index = 0, count = 0;

        while (count < sb.size()) {
            for (DicSideBar menu : sb) {
                if (sideMenu.getMenuItem(menu.getId().toString()) != null) continue;

                if (menu.getParentSideBar() == null) {
                    SideMenu.MenuItem rootItem = sideMenu.createMenuItem(menu.getId().toString(), menu.getName(), null, mi -> {
                    });
                    sideMenu.addMenuItem(rootItem, index++);
                } else {
                    SideMenu.MenuItem parentItem = sideMenu.getMenuItem(menu.getParentSideBar().getId().toString());
                    if (parentItem == null) continue;
                    SideMenu.MenuItem childItem = sideMenu.createMenuItem(menu.getId().toString(), menu.getName(), null, mi -> {
                    });
                    parentItem.addChildItem(childItem);
                }
                count++;
            }
        }
    }

    @Subscribe("sideMenu")
    public void onSideMenuItemSelect(SideMenu.ItemSelectEvent event) {
        Collection<Screen> scr = screens.getOpenedScreens().getActiveScreens();
        SideMenu.MenuItem item = event.getMenuItem();

        scr.forEach(screen -> {
            if (Objects.equals(screen.getId(), "Items")) {
                screen.close(StandardOutcome.CLOSE);
            }
        });

        String param = item.getCaption();
        while (item.getParent() != null) {
            item = item.getParent();
            param = String.format("%s %s", item.getCaption(), param);
        }

        Screen itemScreen = screens.create(Items.class);
        screenBuilders
                .screen(itemScreen)
                .withScreenClass(Items.class)
                .withOptions(new PassScreenOptions(event.getMenuItem().getId(), param))
                .build().show();
    }


    @Override
    public AppWorkArea getWorkArea() {
        return workArea;
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        screenTools.openDefaultScreen(
                UiControllerUtils.getScreenContext(this).getScreens());

        screenTools.handleRedirect();
    }

    @Subscribe("profileBtn")
    public void onProfileBtnClick(Button.ClickEvent event) {
        UiControllerUtils.getScreenContext(this).getScreens()
                .create(UserProfileController.class, OpenMode.NEW_TAB)
                .show();
    }
}