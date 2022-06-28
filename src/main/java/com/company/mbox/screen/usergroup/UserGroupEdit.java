package com.company.mbox.screen.usergroup;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.UserGroup;

@UiController("UserGroup.edit")
@UiDescriptor("user-group-edit.xml")
@EditedEntityContainer("userGroupDc")
public class UserGroupEdit extends StandardEditor<UserGroup> {
}