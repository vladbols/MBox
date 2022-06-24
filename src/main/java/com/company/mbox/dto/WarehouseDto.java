package com.company.mbox.dto;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import java.util.List;
import java.util.UUID;

@JmixEntity
public class WarehouseDto {
    private UUID storeId;

    @InstanceName
    private String store;

    private String address;

    private List<ItemsDto> items;

    public List<ItemsDto> getItems() {
        return items;
    }

    public void setItems(List<ItemsDto> items) {
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UUID getStoreId() {
        return storeId;
    }

    public void setStoreId(UUID storeId) {
        this.storeId = storeId;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }
}