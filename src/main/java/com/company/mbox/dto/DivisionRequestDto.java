package com.company.mbox.dto;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import java.util.List;
import java.util.UUID;

@JmixEntity
public class DivisionRequestDto {
    private UUID divisionId;

    @InstanceName
    private String division;

    private Integer division_uid;

    private String address;

    private List<WarehouseRequestDto> stors;

    public UUID getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(UUID divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Integer getDivision_uid() {
        return division_uid;
    }

    public void setDivision_uid(Integer division_uid) {
        this.division_uid = division_uid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<WarehouseRequestDto> getStors() {
        return stors;
    }

    public void setStors(List<WarehouseRequestDto> stors) {
        this.stors = stors;
    }
}