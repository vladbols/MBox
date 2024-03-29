package com.company.mbox.dto;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import java.util.List;
import java.util.UUID;

@JmixEntity
public class WarehouseRequestDto {
    private UUID storeId;

    @InstanceName
    private String store;

    private UUID store_uid;

    private String address;

    private List<ItemsRequestDto> list;

    public void setStore_uid(UUID store_uid) {
        this.store_uid = store_uid;
    }

    public UUID getStore_uid() {
        return store_uid;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ItemsRequestDto> getList() {
        return list;
    }

    public void setList(List<ItemsRequestDto> list) {
        this.list = list;
    }
}