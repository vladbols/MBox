package com.company.mbox.dto;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import java.util.List;
import java.util.UUID;

@JmixEntity
public class DivisionDto {
    private UUID divisionId;

    @InstanceName
    private String division;

    private String address;

    private List<WarehouseDto> stores;

    public List<WarehouseDto> getStores() {
        return stores;
    }

    public void setStores(List<WarehouseDto> stores) {
        this.stores = stores;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public UUID getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(UUID divisionId) {
        this.divisionId = divisionId;
    }
}