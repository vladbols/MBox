package com.company.mbox.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.UUID;

@JmixEntity
@Table(name = "ITEM", indexes = {
        @Index(name = "IDX_ITEM_WEARHOUSE_ID", columnList = "WEARHOUSE_ID"),
        @Index(name = "IDX_ITEM_UNIT_ID", columnList = "UNIT_ID")
})
@Entity
public class Item {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "WEARHOUSE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Wearhouse wearhouse;

    @Column(name = "LEGACY_ID")
    private Integer legacyId;

    @InstanceName
    @Column(name = "NAME")
    private String name;

    @JoinColumn(name = "UNIT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Unit unit;

    @Column(name = "TYPE_")
    private String type;

    @Column(name = "PRISE")
    private Integer prise;

    @Column(name = "AMOUNT")
    private Integer amount;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPrise() {
        return prise;
    }

    public void setPrise(Integer prise) {
        this.prise = prise;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
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

    public Wearhouse getWearhouse() {
        return wearhouse;
    }

    public void setWearhouse(Wearhouse wearhouse) {
        this.wearhouse = wearhouse;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}