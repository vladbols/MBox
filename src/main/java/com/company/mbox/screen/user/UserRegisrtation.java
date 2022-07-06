package com.company.mbox.screen.user;

import com.company.mbox.entity.User;
import io.jmix.core.EntityStates;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.PasswordField;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.TimeZone;

@UiController("UserRegisrtation")
@UiDescriptor("user-regisrtation.xml")
public class UserRegisrtation extends ScreenFragment {

    @Autowired
    private EntityStates entityStates;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordField passwordField;

    @Autowired
    private TextField<String> usernameField;

    @Autowired
    private ComboBox<String> timeZoneField;

    @Autowired
    private ComboBox<String> organizationBin;

    public UserRegisrtation(ComboBox<String> organizationBin) {
        this.organizationBin = organizationBin;
    }

    @Subscribe
    public void onInitEntity(StandardEditor.InitEntityEvent<User> event) {
        usernameField.setEditable(true);
        passwordField.setVisible(true);
    }

    @Subscribe
    public void onInit(Screen.InitEvent event) {
        timeZoneField.setOptionsList(Arrays.asList(TimeZone.getAvailableIDs()));
    }
}