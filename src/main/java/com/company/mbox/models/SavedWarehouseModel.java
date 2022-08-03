package com.company.mbox.models;

import java.util.List;
import java.util.UUID;

public class SavedWarehouseModel {

    UUID warehouseId;
    List<UUID> savedI;

    public SavedWarehouseModel() {
    }

    public SavedWarehouseModel(UUID warehouseId, List<UUID> savedI) {
        this.warehouseId = warehouseId;
        this.savedI = savedI;
    }

    public UUID getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(UUID warehouseId) {
        this.warehouseId = warehouseId;
    }

    public List<UUID> getSavedI() {
        return savedI;
    }

    public void setSavedI(List<UUID> savedI) {
        this.savedI = savedI;
    }
}
