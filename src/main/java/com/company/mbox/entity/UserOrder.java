package com.company.mbox.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "USER_ORDER")
@Entity
public class UserOrder {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @OneToMany(mappedBy = "userOrder")
    private List<ItemPisitions> userOrderItem;

    public List<ItemPisitions> getUserOrderItem() {
        return userOrderItem;
    }

    public void setUserOrderItem(List<ItemPisitions> userOrderItem) {
        this.userOrderItem = userOrderItem;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}