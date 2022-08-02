package com.company.mbox.screen.user;

import io.jmix.core.Messages;
import io.jmix.core.usersubstitution.CurrentUserSubstitution;
import io.jmix.ui.Notifications;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.mbox.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("UserProfileController")
@UiDescriptor("userProfile.xml")
@LookupComponent("usersTable")
public class UserProfileController extends StandardLookup<User> {

    @Autowired
    private CollectionContainer<User> usersDc;
    @Autowired
    private CollectionLoader<User> usersDl;

    @Autowired
    private Notifications notifications;
    @Autowired
    private Messages messages;
    @Autowired
    private CurrentUserSubstitution currentUserSubstitution;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        usersDl.setParameter("username", currentUserSubstitution.getAuthenticatedUser().getUsername());
        usersDl.load();
    }



}