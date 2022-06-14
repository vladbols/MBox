package com.company.mbox.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.UUID;

@JmixEntity
@Table(name = "ITEM_PISITIONS", indexes = {
        @Index(name = "IDX_ITEMPISITIONS", columnList = "USER_ORDER_ID")
})
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

    @JoinColumn(name = "USER_ORDER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserOrder userOrder;

    public UserOrder getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(UserOrder userOrder) {
        this.userOrder = userOrder;
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