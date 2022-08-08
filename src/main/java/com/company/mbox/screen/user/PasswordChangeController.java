package com.company.mbox.screen.user;

import com.company.mbox.entity.User;
import com.company.mbox.services.BaseUtilsService;
import com.mchange.v2.lang.StringUtils;
import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Form;
import io.jmix.ui.component.PasswordField;
import io.jmix.ui.component.ValidationErrors;
import io.jmix.ui.component.ValidationException;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@UiController("PasswordChangeController")
@UiDescriptor("passwordChange.xml")
public class PasswordChangeController extends Screen {

    @Autowired
    private Messages messages;

    @Autowired
    private Notifications notifications;

    @Autowired
    private ScreenValidation screenValidation;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataManager dataManager;

    @Autowired
    private BaseUtilsService baseUtilsService;

    @Autowired
    private PasswordField confirmPasswordField;
    @Autowired
    private PasswordField oldPasswordField;
    @Autowired
    private PasswordField passwordField;
    @Autowired
    private Form changePasswordForm;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        confirmPasswordField.addValidator(value -> {
            String password = passwordField.getValue();
            if (StringUtils.nonEmptyString(password) && password.length() >= 8) {
                if (!StringUtils.nonEmptyString(value))
                    throw new ValidationException(messages.getMessage(getClass(), "validator.empty.confirm.passwords"));
                else if (!value.equals(password))
                    throw new ValidationException(messages.getMessage(getClass(), "validator.notEqual.passwords"));
            }
        });
    }

    @Subscribe("changePassword")
    public void onChangePassword(Action.ActionPerformedEvent event) {
        User currentUser = baseUtilsService.getCurrentUser();
        ValidationErrors errors = screenValidation.validateUiComponents(changePasswordForm);
        if (!errors.isEmpty()) {
            screenValidation.showValidationErrors(this, errors);
            return;
        }

        if (compareOldPasswords(oldPasswordField.getValue(), currentUser.getPassword())) {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption(messages.getMessage("incorrect.old.password"))
                    .show();
            return;
        }

        currentUser.setPassword(passwordEncoder.encode(passwordField.getValue()));
        dataManager.save(currentUser);

        notifications.create(Notifications.NotificationType.HUMANIZED)
                .withCaption(messages.getMessage("password.changed"))
                .show();
        this.close(StandardOutcome.CLOSE);
    }

    public boolean compareOldPasswords(String oldPass, String curr) {
        return !passwordEncoder.matches(oldPass, curr);
    }

    @Subscribe("cancel")
    public void onCancel(Action.ActionPerformedEvent event) {
        this.close(StandardOutcome.CLOSE);
    }
}