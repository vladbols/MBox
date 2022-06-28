package com.company.mbox.screen.usergroup;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.UserGroup;

@UiController("UserGroup.browse")
@UiDescriptor("user-group-browse.xml")
@LookupComponent("userGroupsTable")
public class UserGroupBrowse extends StandardLookup<UserGroup> {
}