package com.company.mbox.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "USER_ORDER_DETAIL")
@Entity
public class UserOrderDetail {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "ORDER_DATE_TIME", nullable = false)
    @NotNull
    private LocalDateTime orderDateTime;

    @Column(name = "ORDER_NAME")
    private String orderName;

    @JoinTable(name = "USER_ORDER_DETAIL_ITEM_POS",
            joinColumns = @JoinColumn(name = "USER_ORDER_DETAIL_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_PISITIONS_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<ItemPisitions> orderItems;

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }


    public List<ItemPisitions> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<ItemPisitions> orderItems) {
        this.orderItems = orderItems;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}