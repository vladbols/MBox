package com.company.mbox.models;

import java.util.List;
import java.util.UUID;

public class SavedOrganizationModel {
    UUID organizationId;
    List<SavedDivisionModel> savedD;

    public SavedOrganizationModel() {
    }

    public SavedOrganizationModel(UUID organizationId, List<SavedDivisionModel> savedD) {
        this.organizationId = organizationId;
        this.savedD = savedD;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
    }

    public List<SavedDivisionModel> getSavedD() {
        return savedD;
    }

    public void setSavedD(List<SavedDivisionModel> savedD) {
        this.savedD = savedD;
    }
}
