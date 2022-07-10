package com.company.mbox.security;

import com.company.mbox.entity.Organization;
import com.company.mbox.entity.User;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "AnonymousRole", code = AnonymousRole.CODE)
public interface AnonymousRole {

    String CODE = "anonymous-role";

    @MenuPolicy(menuIds = {"registrationUser"})
    @MenuPolicy(menuIds = {"loginScreen"})
    @ScreenPolicy(screenIds = {"User.registration", "MainTop", "BrandLogin", "Organization.browse", "Organization.edit"})
    void screens();

    @EntityPolicy(entityName = "User", actions = {EntityPolicyAction.ALL})
    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    void user();

    @EntityPolicy(entityClass = Organization.class, actions = {EntityPolicyAction.CREATE, EntityPolicyAction.READ})
    void organization();
}