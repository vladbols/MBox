package com.company.mbox.security;

import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "CustomerRole", code = CustomerRole.CODE)
public interface CustomerRole {

    String CODE = "customer-role";

    @EntityPolicy(entityName = "*", actions = {EntityPolicyAction.ALL})
    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"Items", "BasketController"})
    @MenuPolicy(menuIds = {"Items", "BasketController"})
    @SpecificPolicy(resources = "*")
    void fullAccess();

//    @MenuPolicy(menuIds = {"registrationUser"})
//    @MenuPolicy(menuIds = {"LoginScreen", "User.registration"})
//    @ScreenPolicy(screenIds = {"LoginScreen", "User.registration"})
//    void screens();
//
//    @EntityPolicy(entityClass = User.class, actions = {EntityPolicyAction.ALL})
//    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
//    void user();
//
//    @EntityPolicy(entityClass = Organization.class, actions = {EntityPolicyAction.CREATE, EntityPolicyAction.READ})
//    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
//    void organization();
//
//    @EntityPolicy(entityClass = Currency.class, actions = {EntityPolicyAction.READ})
//    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.VIEW)
//    void currency();
}