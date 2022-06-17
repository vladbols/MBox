package com.company.mbox.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@JmixEntity
@Table(name = "USER_ORDER", indexes = {
        @Index(name = "IDX_USERORDER_ORDER_DET_ID", columnList = "ORDER_DET_ID")
})
@Entity
public class UserOrder {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "ORDER_DET_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private UserOrderDetail orderDet;

    @Column(name = "ORDER_NAME")
    private String orderName;

    @Column(name = "ORDER_DATE")
    private LocalDateTime orderDate;

    public UserOrderDetail getOrderDet() {
        return orderDet;
    }

    public void setOrderDet(UserOrderDetail orderDet) {
        this.orderDet = orderDet;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
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