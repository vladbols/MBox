package com.company.mbox.dto;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@JmixEntity
public class CompanyDto {
    private UUID organizationId;

    @InstanceName
    private String organization;

    @Length(min = 12, max = 12)
    private String bin;

    @Temporal(TemporalType.DATE)
    private Date date;

    private String address;

    private String kbe;

    private String account;

    private String bik;

    private String bank;

    private String currency;

    private List<DivisionDto> divisions;

    public List<DivisionDto> getDivisions() {
        return divisions;
    }

    public void setDivisions(List<DivisionDto> divisions) {
        this.divisions = divisions;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBik() {
        return bik;
    }

    public void setBik(String bik) {
        this.bik = bik;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getKbe() {
        return kbe;
    }

    public void setKbe(String kbe) {
        this.kbe = kbe;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
    }
}