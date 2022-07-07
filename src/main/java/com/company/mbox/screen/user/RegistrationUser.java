package com.company.mbox.screen.user;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.User;

@UiController("User.registration")
@UiDescriptor("registration-user.xml")
@EditedEntityContainer("userDc")
public class RegistrationUser extends StandardEditor<User> {
}