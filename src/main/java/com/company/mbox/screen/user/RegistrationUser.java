package com.company.mbox.screen.user;

import com.company.mbox.entity.Organization;
import com.company.mbox.entity.User;
import com.company.mbox.screen.login.LoginScreen;
import com.company.mbox.services.BaseUtilsService;
import com.mchange.v2.lang.StringUtils;
import io.jmix.core.DataManager;
import io.jmix.core.EntityStates;
import io.jmix.core.Messages;
import io.jmix.core.Metadata;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.*;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@UiController("User.registration")
@UiDescriptor("registration-user.xml")
@Route(path = "registration", root = true)
public class RegistrationUser extends Screen {

    private final Logger log = LoggerFactory.getLogger(RegistrationUser.class);

    @Autowired
    private EntityStates entityStates;

    @Autowired
    private Metadata metadata;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Notifications notifications;

    @Autowired
    private Messages messages;

    @Autowired
    private DataManager dataManager;

    @Autowired
    private BaseUtilsService baseUtilsService;

    @Autowired
    private CheckBox hasOrganizationField;

    @Autowired
    private Form userForm;

    @Autowired
    private Form organizationForm;

    @Autowired
    private TextField<String> usernameField;
    @Autowired
    private PasswordField passwordField;
    @Autowired
    private PasswordField confirmPasswordField;
    @Autowired
    private TextField<String> firstNameField;
    @Autowired
    private TextField<String> lastNameField;
    @Autowired
    private TextField<String> emailField;
    @Autowired
    private TextField<String> iinField;
    @Autowired
    private TextField<String> binField;
    @Autowired
    private TextField<String> orgNameField;
    @Autowired
    private TextField<String> orgAddressField;
    @Autowired
    private TextField<String> orgBinField;
    @Autowired
    private TextField<Integer> orgKbeField;
    @Autowired
    private TextField<String> orgIIKField;
    @Autowired
    private TextField<String> orgBikField;
    @Autowired
    private MaskedField<String> orgContacts;

    @Autowired
    private ScreenValidation screenValidation;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        hasOrganizationField.setValue(true);

        confirmPasswordField.addValidator(value -> {
            String password = passwordField.getValue();
            if (StringUtils.nonEmptyString(password) && password.length() >= 8) {
                if (!StringUtils.nonEmptyString(value))
                    throw new ValidationException(messages.getMessage(getClass(), "validator.empty.confirm.passwords"));
                else if (!value.equals(password))
                    throw new ValidationException(messages.getMessage(getClass(), "validator.notEqual.passwords"));
            }
        });

        iinField.addValidator(val -> iinBinValidator(val, "iin"));
        binField.addValidator(val -> iinBinValidator(val, "bin"));
        orgBinField.addValidator(val -> iinBinValidator(val, "bin"));
    }

    private void iinBinValidator(String val, String key) {
        if (!StringUtils.nonEmptyString(val)) {
            throw new ValidationException(messages.getMessage(getClass(), "validator.notEmpty." + key));
        }
        if (!val.matches("[0-9]+") || val.length() != 12) {
            throw new ValidationException(messages.getMessage(getClass(), "validator.notFull." + key));
        }
    }

    @Subscribe("hasOrganizationField")
    public void onHasOrganizationFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        boolean flag = Boolean.TRUE.equals(event.getValue());
        binField.setVisible(flag);
        binField.setRequired(flag);
        orgBinField.setRequired(!flag);
        organizationForm.setVisible(!flag);
    }

    @Subscribe("signIn")
    public void onSignIn(Action.ActionPerformedEvent event) {
        this.close(StandardOutcome.CLOSE);
        UiControllerUtils.getScreenContext(this).getScreens()
                .create(LoginScreen.class, OpenMode.ROOT)
                .show();
    }

    @Subscribe("commitAndOpenLoginPageBtn")
    public void onCommitAndOpenLoginPageBtnClick(Button.ClickEvent event) {
        ValidationErrors errors = screenValidation.validateUiComponents(userForm);
        if (!hasOrg()) {
            ValidationErrors errors2 = screenValidation.validateUiComponents(organizationForm);
            if (!errors2.isEmpty()) {
                errors.addAll(errors2);
            }
        }
        if (!errors.isEmpty()) {
            screenValidation.showValidationErrors(this, errors);
            return;
        }

        try {
            String bin = hasOrg() ? binField.getValue() : orgBinField.getValue();
            String username = usernameField.getValue();
            if (baseUtilsService.usernameExist(username)) {
                notifications.create(Notifications.NotificationType.WARNING)
                        .withCaption(messages.getMessage("username.exists"))
                        .show();
                return;
            }
            if (baseUtilsService.iinExist(iinField.getValue())) {
                notifications.create(Notifications.NotificationType.WARNING)
                        .withCaption(messages.getMessage("iin.exists"))
                        .show();
                return;
            }

            Organization org;
            if (hasOrg()) org = baseUtilsService.getOrganization(bin);
            else org = baseUtilsService.getOrCreateOrganization(bin);

            if (org == null) {
                notifications.create(Notifications.NotificationType.WARNING)
                        .withCaption(messages.getMessage("organization.not.found"))
                        .show();
                return;
            }

            if (entityStates.isNew(org)) {
                org.setBin(bin);
                org.setActive(false);
                org.setBik(orgBikField.getValue());
                org.setKbe(orgKbeField.getValue());
                org.setName(orgNameField.getValue());
                org.setAccount(orgIIKField.getValue());
                org.setContacts(orgContacts.getValue());
                org.setAddress(orgAddressField.getValue());
                org.setCurrency(baseUtilsService.getOrCreateCurrency("KZT"));
                dataManager.save(org);
            } else if (!hasOrg() && !entityStates.isNew(org)) {
                notifications.create(Notifications.NotificationType.WARNING)
                        .withCaption(messages.getMessage("organization.exists"))
                        .show();
                return;
            }

            User user = metadata.create(User.class);
            user.setOrganization(org);
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(passwordField.getValue()));
            user.setFirstName(firstNameField.getValue());
            user.setLastName(lastNameField.getValue());
            user.setEmail(emailField.getValue());
//            user.setTimeZoneId(TimeZone.getAvailableIDs()[]);
            user.setIin(iinField.getValue());
            user.setActive(true);
            dataManager.save(user);

            RoleAssignmentEntity entity = metadata.create(RoleAssignmentEntity.class);
            entity.setVersion(1);
            entity.setRoleCode("employee-role");
            entity.setRoleType("resource");
            entity.setUsername(user.getUsername());
            dataManager.save(entity);

            notifications.create(Notifications.NotificationType.HUMANIZED)
                    .withCaption(messages.getMessage("successful.registration"))
                    .show();
            onSignIn(null);
        } catch (Exception e) {
            log.error("### ERROR occurred on registration. Error message: [{}]", e.getMessage());
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption(messages.getMessage("error.registration"))
                    .show();
        }
    }

    private boolean hasOrg() {
        return Boolean.TRUE.equals(hasOrganizationField.getValue());
    }
}