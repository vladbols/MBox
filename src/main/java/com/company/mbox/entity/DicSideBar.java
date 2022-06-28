package com.company.mbox.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.UUID;

@JmixEntity
@Table(name = "DIC_SIDE_BAR", indexes = {
        @Index(name = "IDX_DICSIDEBAR", columnList = "PARENT_SIDE_BAR_ID")
})
@Entity
public class DicSideBar {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "NAME")
    private String name;

    @Column(name = "CODE")
    private String code;

    @JoinColumn(name = "PARENT_SIDE_BAR_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private DicSideBar parentSideBar;

    public DicSideBar getParentSideBar() {
        return parentSideBar;
    }

    public void setParentSideBar(DicSideBar parentSideBar) {
        this.parentSideBar = parentSideBar;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}