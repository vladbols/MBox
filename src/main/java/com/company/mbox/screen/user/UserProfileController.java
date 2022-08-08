package com.company.mbox.screen.user;

import com.company.mbox.entity.Organization;
import com.company.mbox.services.BaseUtilsService;
import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Form;
import io.jmix.ui.component.MaskedField;
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

    @Autowired
    private TextField<Integer> orgKbeField;
    @Autowired
    private TextField<String> orgNameField;
    @Autowired
    private TextField<String> orgIIKField;
    @Autowired
    private MaskedField<String> orgContacts;
    @Autowired
    private TextField<String> orgBinField;
    @Autowired
    private TextField<String> orgBikField;
    @Autowired
    private TextField<String> orgAddressField;
    @Autowired
    private Form organizationForm;
    @Autowired
    private Action actionSaveOrg;
    @Autowired
    private Action actionRevertEditOrg;
    @Autowired
    private Action actionEditOrg;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        User currentUser = baseUtilsService.getCurrentUser();
        Organization currentOrg = baseUtilsService.getCurrentOrganization();
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
        checkAccessToEditOrg();
        setCurrentUserData();
        setCurrentOrgData();
    }

    @Subscribe("actionEditUser")
    public void onActionEditUser(Action.ActionPerformedEvent event) {
        enableDisableUserComponents(true);
    }

    @Subscribe("actionSaveUser")
    public void onActionSaveUser(Action.ActionPerformedEvent event) {
        ValidationErrors errors = screenValidation.validateUiComponents(profileForm);
        if (!errors.isEmpty()) {
            screenValidation.showValidationErrors(this, errors);
            return;
        }
        User currentUser = baseUtilsService.getCurrentUser();
        currentUser.setFirstName(firstName.getValue());
        currentUser.setLastName(lastName.getValue());
        currentUser.setEmail(email.getValue());
        dataManager.save(currentUser);

        enableDisableUserComponents(false);
        notifications.create(Notifications.NotificationType.HUMANIZED)
                .withCaption(messages.getMessage(getClass(), "successfully.updated.user"))
                .show();
    }

    @Subscribe("actionRevertEditUser")
    public void onActionRevertEditUser(Action.ActionPerformedEvent event) {
        setCurrentUserData();
        enableDisableUserComponents(false);
    }

    @Subscribe("actionChangePassword")
    public void onActionChangePassword(Action.ActionPerformedEvent event) {
        UiControllerUtils.getScreenContext(this).getScreens()
                .create(PasswordChangeController.class, OpenMode.DIALOG)
                .show();
    }

    @Subscribe("actionEditOrg")
    public void onActionEditOrg(Action.ActionPerformedEvent event) {
        enableDisableOrgComponents(true);
    }

    @Subscribe("actionRevertEditOrg")
    public void onActionRevertEditOrg(Action.ActionPerformedEvent event) {
        setCurrentOrgData();
        enableDisableOrgComponents(false);
    }

    @Subscribe("actionSaveOrg")
    public void onActionSaveOrg(Action.ActionPerformedEvent event) {
        ValidationErrors errors = screenValidation.validateUiComponents(organizationForm);
        if (!errors.isEmpty()) {
            screenValidation.showValidationErrors(this, errors);
            return;
        }
        Organization currentOrg = baseUtilsService.getCurrentOrganization();
        currentOrg.setName(orgNameField.getValue());
        currentOrg.setContacts(orgContacts.getValue());
        currentOrg.setAddress(orgAddressField.getValue());
        currentOrg.setAccount(orgIIKField.getValue());
        currentOrg.setKbe(orgKbeField.getValue());
        currentOrg.setBik(orgBikField.getValue());
        dataManager.save(currentOrg);

        enableDisableOrgComponents(false);
        notifications.create(Notifications.NotificationType.HUMANIZED)
                .withCaption(messages.getMessage(getClass(), "successfully.updated.organization"))
                .show();
    }

    private void enableDisableUserComponents(boolean bool) {
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

    private void enableDisableOrgComponents(boolean bool) {
        actionEditOrg.setEnabled(!bool);
        actionSaveOrg.setEnabled(bool);

        orgAddressField.setEnabled(bool);
        orgBikField.setEnabled(bool);
        orgContacts.setEnabled(bool);
        orgIIKField.setEnabled(bool);
        orgNameField.setEnabled(bool);
        orgKbeField.setEnabled(bool);

        orgAddressField.setRequired(bool);
        orgBikField.setRequired(bool);
        orgContacts.setRequired(bool);
        orgIIKField.setRequired(bool);
        orgNameField.setRequired(bool);
        orgKbeField.setRequired(bool);

        actionRevertEditOrg.setEnabled(bool);
        actionRevertEditOrg.setVisible(bool);
    }

    private void setCurrentUserData() {
        User currentUser = baseUtilsService.getCurrentUser();
        Organization currentOrg = baseUtilsService.getCurrentOrganization();

        username.setValue(currentUser.getUsername());
        organizationName.setValue(currentOrg.getName());
        iin.setValue(currentUser.getIin());
        email.setValue(currentUser.getEmail());
        lastName.setValue(currentUser.getLastName());
        firstName.setValue(currentUser.getFirstName());
    }

    private void setCurrentOrgData() {
        Organization currentOrg = baseUtilsService.getCurrentOrganization();

        orgAddressField.setValue(currentOrg.getAddress());
        orgBikField.setValue(currentOrg.getBik());
        orgContacts.setValue(currentOrg.getContacts());
        orgIIKField.setValue(currentOrg.getAccount());
        orgNameField.setValue(currentOrg.getName());
        organizationName.setValue(currentOrg.getName());
        orgKbeField.setValue(currentOrg.getKbe());
        orgBinField.setValue(currentOrg.getBin());
    }

    private void checkAccessToEditOrg() {
        if (baseUtilsService.getCurrentUserAuthorities().contains("org-admin-role")) {
            actionEditOrg.setVisible(true);
            actionSaveOrg.setVisible(true);
        }
    }
}