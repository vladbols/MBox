package com.company.mbox.dto;

import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@JmixEntity
public class ResultDocsDto {

    @JmixProperty(mandatory = true)
    @NotNull
    private UUID order_uid;

    @JmixProperty(mandatory = true)
    @NotNull
    private String bin;

    @JmixProperty(mandatory = true)
    @NotNull
    private String client_bin;

    private Integer number;

    private byte[] file;

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getClient_bin() {
        return client_bin;
    }

    public void setClient_bin(String client_bin) {
        this.client_bin = client_bin;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public UUID getOrder_uid() {
        return order_uid;
    }

    public void setOrder_uid(UUID order_uid) {
        this.order_uid = order_uid;
    }
}