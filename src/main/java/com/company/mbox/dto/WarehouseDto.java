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

    private Integer store_uid;

    private String address;

    private List<ItemsDto> list;

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

    public Integer getStore_uid() {
        return store_uid;
    }

    public void setStore_uid(Integer store_uid) {
        this.store_uid = store_uid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ItemsDto> getList() {
        return list;
    }

    public void setList(List<ItemsDto> list) {
        this.list = list;
    }
}