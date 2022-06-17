package com.company.mbox.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "ITEM_PISITIONS")
@Entity
public class ItemPisitions {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "ITEM_NAME")
    @Lob
    private String itemName;

    @Column(name = "ITEM_TOTAL_COUNT")
    private Integer itemTotalCount;

    @Column(name = "ITRM_PRISE_FOR_ONE")
    private Integer itrmPriseForOne;

    @Column(name = "ITEM_COMPANY")
    private String itemCompany;

    @Column(name = "ITEM_COMPANY_ADDRESS")
    @Lob
    private String itemCompanyAddress;

    @Column(name = "ITEM_WEARHOME_ADDRESS")
    @Lob
    private String itemWearhomeAddress;

    @JoinTable(name = "USER_ORDER_DETAIL_ITEM_POS",
            joinColumns = @JoinColumn(name = "ITEM_PISITIONS_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ORDER_DETAIL_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<UserOrderDetail> userOrderDetails;

    public List<UserOrderDetail> getUserOrderDetails() {
        return userOrderDetails;
    }

    public void setUserOrderDetails(List<UserOrderDetail> userOrderDetails) {
        this.userOrderDetails = userOrderDetails;
    }

    public String getItemWearhomeAddress() {
        return itemWearhomeAddress;
    }

    public void setItemWearhomeAddress(String itemWearhomeAddress) {
        this.itemWearhomeAddress = itemWearhomeAddress;
    }

    public String getItemCompanyAddress() {
        return itemCompanyAddress;
    }

    public void setItemCompanyAddress(String itemCompanyAddress) {
        this.itemCompanyAddress = itemCompanyAddress;
    }

    public String getItemCompany() {
        return itemCompany;
    }

    public void setItemCompany(String itemCompany) {
        this.itemCompany = itemCompany;
    }

    public Integer getItrmPriseForOne() {
        return itrmPriseForOne;
    }

    public void setItrmPriseForOne(Integer itrmPriseForOne) {
        this.itrmPriseForOne = itrmPriseForOne;
    }

    public Integer getItemTotalCount() {
        return itemTotalCount;
    }

    public void setItemTotalCount(Integer itemTotalCount) {
        this.itemTotalCount = itemTotalCount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}