package com.company.mbox.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@JmixEntity
@Table(name = "USER_GROUP", indexes = {
        @Index(name = "IDX_USERGROUP_USER_ID", columnList = "USER_ID"),
        @Index(name = "IDX_USERGROUP_ORGANIZATION_ID", columnList = "ORGANIZATION_ID")
})
@Entity
public class UserGroup {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull
    @JoinColumn(name = "USER_ID", nullable = false)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @NotNull
    @JoinColumn(name = "ORGANIZATION_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Organization organization;

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}