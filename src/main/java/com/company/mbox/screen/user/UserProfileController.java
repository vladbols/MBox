package com.company.mbox.screen.user;

import com.company.mbox.entity.Organization;
import com.company.mbox.services.BaseUtilsService;
import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Form;
import io.jmix.ui.component.TextField;
import io.jmix.ui.component.ValidationErrors;
import io.jmix.ui.screen.*;
import com.company.mbox.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("UserProfileController")
@UiDescriptor("userProfile.xml")
public class UserProfileController extends Screen {

    @Autowired
    private DataManager dataManager;

    @Autowired
    private Notifications notifications;

    @Autowired
    private Messages messages;

    @Autowired
    private BaseUtilsService baseUtilsService;

    @Autowired
    private ScreenValidation screenValidation;

    @Autowired
    private TextField<String> email;
    @Autowired
    private TextField<String> username;
    @Autowired
    private TextField<String> organizationName;
    @Autowired
    private TextField<String> lastName;
    @Autowired
    private TextField<String> iin;
    @Autowired
    private TextField<String> firstName;
    @Autowired
    private Form profileForm;
    @Autowired
    private Action actionEditUser;
    @Autowired
    private Action actionSaveUser;
    @Autowired
    private Action actionRevertEditUser;

    private User currentUser;
    private Organization currentOrg;


    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        currentUser = baseUtilsService.getCurrentUser();
        currentOrg = baseUtilsService.getCurrentOrganization();
        if (currentUser == null) {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption(messages.getMessage(getClass(), "user.not.found"))
                    .show();
            return;
        } else if (currentOrg == null) {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption(messages.getMessage(getClass(), "organization.not.found"))
                    .show();
            return;
        }
        username.setValue(currentUser.getUsername());
        organizationName.setValue(currentOrg.getName());
        iin.setValue(currentUser.getIin());
        email.setValue(currentUser.getEmail());
        lastName.setValue(currentUser.getLastName());
        firstName.setValue(currentUser.getFirstName());
    }

    @Subscribe("actionEditUser")
    public void onActionEditUser(Action.ActionPerformedEvent event) {
        enableDisableComponents(true);
    }

    @Subscribe("actionSaveUser")
    public void onActionSaveUser(Action.ActionPerformedEvent event) {
        ValidationErrors errors = screenValidation.validateUiComponents(profileForm);
        if (!errors.isEmpty()) {
            screenValidation.showValidationErrors(this, errors);
            return;
        }
        currentUser.setFirstName(firstName.getValue());
        currentUser.setLastName(lastName.getValue());
        currentUser.setEmail(email.getValue());
        dataManager.save(currentUser);

        enableDisableComponents(false);
    }

    @Subscribe("actionRevertEditUser")
    public void onActionRevertEditUser(Action.ActionPerformedEvent event) {
        email.setValue(currentUser.getEmail());
        lastName.setValue(currentUser.getLastName());
        firstName.setValue(currentUser.getFirstName());
        enableDisableComponents(false);
    }

    private void enableDisableComponents(boolean bool) {
        actionEditUser.setEnabled(!bool);
        actionSaveUser.setEnabled(bool);
        email.setEnabled(bool);
        lastName.setEnabled(bool);
        lastName.setRequired(bool);
        firstName.setEnabled(bool);
        firstName.setRequired(bool);
        actionRevertEditUser.setEnabled(bool);
        actionRevertEditUser.setVisible(bool);
    }


}