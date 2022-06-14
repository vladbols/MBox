package com.company.mbox.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class ItemsList {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull
    @Column(name = "ITEM_NAME", nullable = false)
    @Lob
    private String itemName;

    @Column(name = "PRISE_FOR_ONE", nullable = false)
    @NotNull
    private Integer priseForOne;

    @Column(name = "TOTAL_COUNT")
    private Integer totalCount;

    @Column(name = "COMPANY", nullable = false)
    @NotNull
    private String company;

    @Column(name = "COMPANY_ADDRES", nullable = false)
    @Lob
    @NotNull
    private String companyAddres;

    @Column(name = "WEARHOUSE_ADDRESS", nullable = false)
    @NotNull
    private String wearhouseAddress;

    @Column(name = "COUNT_FOR_USER")
    private Integer countForUser;


    public void setCountForUser(Integer countForUser) {
        this.countForUser = countForUser;
    }

    public Integer getCountForUser() {
        return countForUser;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public String getCompanyAddres() {
        return companyAddres;
    }

    public void setCompanyAddres(String companyAddres) {
        this.companyAddres = companyAddres;
    }

    public Integer getPriseForOne() {
        return priseForOne;
    }

    public void setPriseForOne(Integer priseForOne) {
        this.priseForOne = priseForOne;
    }

    public String getWearhouseAddress() {
        return wearhouseAddress;
    }

    public void setWearhouseAddress(String wearhouseAddress) {
        this.wearhouseAddress = wearhouseAddress;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

}