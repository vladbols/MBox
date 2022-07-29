package com.company.mbox.security;

import com.company.mbox.entity.*;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "AnonymousRole", code = AnonymousRole.CODE)
public interface AnonymousRole {

    String CODE = "anonymous-role";
//
//    @EntityPolicy(entityName = "*", actions = {EntityPolicyAction.ALL})
//    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
//    @ScreenPolicy(screenIds = "*")
//    @MenuPolicy(menuIds = "*")
//    void fullAccess();
    @SpecificPolicy(resources = "*")
    void specificPolicy();

    @MenuPolicy(menuIds = {"LoginScreen", "User.registration"})
    @ScreenPolicy(screenIds = {"LoginScreen", "User.registration"})
    void screens();

    @EntityPolicy(entityClass = RoleAssignmentEntity.class, actions = {EntityPolicyAction.ALL})
    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    void roleAssignmentEntity();

    @EntityPolicy(entityClass = User.class, actions = {EntityPolicyAction.ALL})
    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    void user();

    @EntityPolicy(entityClass = Organization.class, actions = {EntityPolicyAction.ALL})
    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    void organization();

    @EntityPolicy(entityClass = Currency.class, actions = {EntityPolicyAction.ALL})
    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    void currency();

    @EntityPolicy(entityClass = Division.class, actions = {EntityPolicyAction.ALL})
    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    void division();

    @EntityPolicy(entityClass = Warehouse.class, actions = {EntityPolicyAction.ALL})
    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    void warehouse();

    @EntityPolicy(entityClass = Item.class, actions = {EntityPolicyAction.ALL})
    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    void item();
}