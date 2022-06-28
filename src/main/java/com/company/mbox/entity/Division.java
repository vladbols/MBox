package com.company.mbox.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.UUID;

@JmixEntity
@Table(name = "DIVISION", indexes = {
        @Index(name = "IDX_DIVISION_PROVIDER_ID", columnList = "PROVIDER_ID")
})
@Entity
public class Division {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "LEGACY_ID")
    private Integer legacyId;

    @InstanceName
    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @JoinColumn(name = "PROVIDER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Organization provider;

    public Organization getProvider() {
        return provider;
    }

    public void setProvider(Organization provider) {
        this.provider = provider;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(Integer legacyId) {
        this.legacyId = legacyId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}