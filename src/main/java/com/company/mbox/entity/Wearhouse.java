package com.company.mbox.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.UUID;

@JmixEntity
@Table(name = "WEARHOUSE", indexes = {
        @Index(name = "IDX_WEARHOUSE_DIVISION_ID", columnList = "DIVISION_ID")
})
@Entity
public class Wearhouse {
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

    @JoinColumn(name = "DIVISION_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Division division;

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
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