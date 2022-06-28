package com.company.mbox.screen.organization;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.Organization;

@UiController("Organization.browse")
@UiDescriptor("organization-browse.xml")
@LookupComponent("organizationsTable")
public class OrganizationBrowse extends StandardLookup<Organization> {
}