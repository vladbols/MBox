package com.company.mbox.screen.user;

import com.company.mbox.entity.Organization;
import com.company.mbox.entity.User;
import com.company.mbox.screen.login.LoginScreen;
import com.company.mbox.services.BaseUtilsService;
import io.jmix.core.DataManager;
import io.jmix.core.EntityStates;
import io.jmix.core.Messages;
import io.jmix.core.Metadata;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import io.jmix.ui.Notifications;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.*;
import io.jmix.ui.component.impl.AbstractField;
import io.jmix.ui.model.DataComponents;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

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
    private MessageBundle messageBundle;

    @Autowired
    private DataManager dataManager;

    @Autowired
    private BaseUtilsService baseUtilsService;

    @Autowired
    private CheckBox hasOrganizationField;

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
    private TextField<String> orgFactAddressField;
    @Autowired
    private TextField<String> orgBinField;
    @Autowired
    private TextField<String> orgKbeField;
    @Autowired
    private TextField<String> orgIIKField;
    @Autowired
    private TextField<String> orgBikField;

    private List<Field> userFields = new ArrayList<>();
    private List<Field> orgFields = new ArrayList<>();

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        hasOrganizationField.setValue(true);

        userFields.add(usernameField);
        userFields.add(passwordField);
        userFields.add(confirmPasswordField);
        userFields.add(firstNameField);
        userFields.add(lastNameField);
        userFields.add(emailField);
        userFields.add(iinField);
        userFields.add(binField);
        orgFields.add(orgNameField);
        orgFields.add(orgAddressField);
        orgFields.add(orgFactAddressField);
        orgFields.add(orgBinField);
        orgFields.add(orgKbeField);
        orgFields.add(orgIIKField);
        orgFields.add(orgBikField);
    }

    @Subscribe("hasOrganizationField")
    public void onHasOrganizationFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        boolean flag = Boolean.TRUE.equals(event.getValue());
        binField.setVisible(flag);
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
        if (checkAllFields()) {
            try {
                String bin = hasOrg() ? binField.getRawValue() : orgBinField.getRawValue();
                Organization org = baseUtilsService.getOrCreateOrganization(bin);

                if (entityStates.isNew(org)) {
                    org.setName(orgNameField.getRawValue());
                    org.setAddress(orgAddressField.getRawValue());
                    org.setBin(bin);
                    org.setBik(orgBikField.getRawValue());
                    org.setKbe(orgKbeField.getRawValue());
                    org.setCurrency(baseUtilsService.getOrCreateCurrency("KZT"));
                    org.setActive(false);
                    dataManager.save(org);
                }

                User user = metadata.create(User.class);
                user.setOrganization(org);
                user.setUsername(usernameField.getRawValue());
                user.setPassword(passwordEncoder.encode(passwordField.getValue()));
                user.setFirstName(firstNameField.getRawValue());
                user.setLastName(lastNameField.getRawValue());
                user.setEmail(emailField.getRawValue());
                user.setIin(iinField.getRawValue());
                user.setActive(true);

                dataManager.save(user);

                RoleAssignmentEntity entity = metadata.create(RoleAssignmentEntity.class);
                entity.setVersion(1);
                entity.setRoleCode("customer-role");
                entity.setRoleType("resource");
                entity.setUsername(user.getUsername());

                dataManager.save(entity);

                notifications.create(Notifications.NotificationType.HUMANIZED)
                        .withCaption(messages.getMessage("successful.registration"))
                        .show();
                onSignIn(null);
            } catch (Exception e) {
                log.error("### ERROR occurred on registration. Error message: [{}]", e.getMessage());
            }
        }
    }

    private boolean checkAllFields() {
        List<Errors> errors = new ArrayList<>();
        String username = usernameField.getRawValue();
        String iin = iinField.getRawValue();

        if (!username.isEmpty() && !username.isBlank() && baseUtilsService.usernameExist(username) != null) {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption(messages.getMessage(getClass(), "usernameExist"))
                    .show();
            return false;
        }
        if (!iin.isEmpty() && !iin.isBlank() && baseUtilsService.iinExist(iin) != null) {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption(messages.getMessage(getClass(), "iinExist"))
                    .show();
            return false;
        }


        for (Field f : userFields) {
            if (Objects.equals(f.getId(), "emailField") || (!hasOrg() && Objects.equals(f.getId(), "binField")))
                continue;

            Object val = f.getValue();
            if (val == null) {
                errors.add(new Errors(f.getCaption()));
            }

            if (Objects.equals(f.getId(), "passwordField")) {
                String pass = passwordField.getValue();
                String conf = confirmPasswordField.getValue();
                if (pass != null && conf != null && !Objects.equals(pass, conf)) {
                    errors.add(new Errors(f.getCaption(), messageBundle.getMessage("passwordsDoNotMatch")));
                }
            }
        }
        if (!hasOrg()) {
            for (Field f : orgFields) {
                if (Objects.equals(f.getId(), "orgFactAddressField") ||
                        Objects.equals(f.getId(), "orgIIKField") ||
                        Objects.equals(f.getId(), "binField"))
                    continue;

                Object val = f.getValue();
                if (val == null) {
                    errors.add(new Errors(f.getCaption()));
                }
            }
        }
        if (!errors.isEmpty()) {
            String notificationCaption = "";
            for (Errors str : errors) {
                notificationCaption = String.format("%s%s\n",
                        notificationCaption,
                        str.getMessage() != null ?
                                str.getMessage() :
                                String.format(messages.getMessage(getClass(), "enterCorrectData"), str.getField()));
            }

            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption(notificationCaption)
                    .show();
        }
        return errors.isEmpty();
    }

    private boolean hasOrg() {
        return Boolean.TRUE.equals(hasOrganizationField.getValue());
    }
}

class Errors {
    private String field;
    private String message;

    public Errors() {
    }

    public Errors(String field) {
        this.field = field;
        this.message = null;
    }

    public Errors(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

class Validator {
    private String id; // Field id
    private Object value; // Field value
    private Rules rules;

    public Validator(String id, Object value) {
        this.id = id;
        this.value = value;
    }

    public Validator() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}

class Rules {
    private int maxLength;
    private int minxLength;
    private Boolean notEmpty;
    private Boolean checked;

    public Rules() {
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMinxLength() {
        return minxLength;
    }

    public void setMinxLength(int minxLength) {
        this.minxLength = minxLength;
    }

    public Boolean getNotEmpty() {
        return notEmpty;
    }

    public void setNotEmpty(Boolean notEmpty) {
        this.notEmpty = notEmpty;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}