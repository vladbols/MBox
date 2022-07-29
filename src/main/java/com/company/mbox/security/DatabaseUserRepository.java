package com.company.mbox.security;

import com.company.mbox.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.core.event.EntityChangedEvent;
import io.jmix.core.event.EntitySavingEvent;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import io.jmix.securitydata.user.AbstractDatabaseUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Collection;

@Primary
@Component("UserRepository")
public class DatabaseUserRepository extends AbstractDatabaseUserRepository<User> {

    @Autowired
    private Metadata metadata;

    @Autowired
    private DataManager dataManager;

    @Override
    protected Class<User> getUserClass() {
        return User.class;
    }

    @Override
    protected void initSystemUser(User systemUser) {
        Collection<GrantedAuthority> authorities = getGrantedAuthoritiesBuilder()
                .addResourceRole(FullAccessRole.CODE)
                .addResourceRole(CustomerRole.CODE)
                .build();
        systemUser.setAuthorities(authorities);
    }

    @Override
    protected void initAnonymousUser(User anonymousUser) {
        Collection<GrantedAuthority> authorities = getGrantedAuthoritiesBuilder()
                .addResourceRole(AnonymousRole.CODE)
                .build();
        anonymousUser.setAuthorities(authorities);
    }
}