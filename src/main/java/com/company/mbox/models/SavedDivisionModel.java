package com.company.mbox.models;

import java.util.List;
import java.util.UUID;

public class SavedDivisionModel {

    UUID divisionId;
    List<SavedWarehouseModel> savedW;

    public SavedDivisionModel() {
    }

    public SavedDivisionModel(UUID divisionId, List<SavedWarehouseModel> savedW) {
        this.divisionId = divisionId;
        this.savedW = savedW;
    }

    public UUID getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(UUID divisionId) {
        this.divisionId = divisionId;
    }

    public List<SavedWarehouseModel> getSavedW() {
        return savedW;
    }

    public void setSavedW(List<SavedWarehouseModel> savedW) {
        this.savedW = savedW;
    }
}
