package com.company.mbox.security;

import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.*;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "EmployeeRole", code = EmployeeRole.CODE)
public interface EmployeeRole {

    String CODE = "employee-role";

    @EntityPolicy(entityName = "*", actions = {EntityPolicyAction.ALL})
    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"Items",
            "BasketController", "OrdersController", "MainTop",
            "UserProfileController", "PasswordChangeController"})
    @MenuPolicy(menuIds = {"Items",
            "BasketController", "OrdersController", "MainTop",
            "UserProfileController", "PasswordChangeController"})
    @SpecificPolicy(resources = "*")
    void employee();
//    @GraphQLPolicy(operations = "*")
//    @ScreenPolicy(screenIds = "*")
//    @MenuPolicy(menuIds = "*")

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