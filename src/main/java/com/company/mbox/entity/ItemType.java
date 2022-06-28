package com.company.mbox.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum ItemType implements EnumClass<String> {

    WHOLESALE("WHOLESALE"),
    RETAIL("RETAIL");

    private String id;

    ItemType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ItemType fromId(String id) {
        for (ItemType at : ItemType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}