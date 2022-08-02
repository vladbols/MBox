package com.company.mbox.dto;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.UUID;

@JmixEntity
public class CompanyRequestDto {
    private UUID organizationId;

    @InstanceName
    private String org;

    private UUID org_uid;

    @Length(min = 12, max = 12)
    private String bin;

    private String date;

    private String address;

    private Integer kbe;

    private String account;

    private String bik;

    private String bank;

    private String currency;

    private List<DivisionRequestDto> divisions;

    public void setKbe(Integer kbe) {
        this.kbe = kbe;
    }

    public Integer getKbe() {
        return kbe;
    }

    public void setOrg_uid(UUID org_uid) {
        this.org_uid = org_uid;
    }

    public UUID getOrg_uid() {
        return org_uid;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBik() {
        return bik;
    }

    public void setBik(String bik) {
        this.bik = bik;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<DivisionRequestDto> getDivisions() {
        return divisions;
    }

    public void setDivisions(List<DivisionRequestDto> divisions) {
        this.divisions = divisions;
    }
}