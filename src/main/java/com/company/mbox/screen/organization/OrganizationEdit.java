package com.company.mbox.screen.organization;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.Organization;

@UiController("Organization.edit")
@UiDescriptor("organization-edit.xml")
@EditedEntityContainer("organizationDc")
public class OrganizationEdit extends StandardEditor<Organization> {
}