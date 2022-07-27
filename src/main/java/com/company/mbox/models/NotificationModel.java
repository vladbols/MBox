package com.company.mbox.models;

import io.jmix.ui.Notifications;

public class NotificationModel {

    private Notifications.NotificationType notificationType;

    private String caption;

    private String description;

    public NotificationModel() {
    }

    public NotificationModel(Notifications.NotificationType notificationType, String caption) {
        this.notificationType = notificationType;
        this.caption = caption;
    }

    public NotificationModel(Notifications.NotificationType notificationType, String caption, String description) {
        this.notificationType = notificationType;
        this.caption = caption;
        this.description = description;
    }

    public Notifications.NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(Notifications.NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
