package com.company.mbox.security;

import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "AnonymousRole", code = "anonymous-role")
public interface AnonymousRole {
    String CODE = "anonymous-role";
    @ScreenPolicy(screenIds = {"User.registration"})
    @ScreenPolicy(screenIds =  {"Organization.browse"})
    @ScreenPolicy(screenIds =  {"Organization.edit"})
    void screens();
}