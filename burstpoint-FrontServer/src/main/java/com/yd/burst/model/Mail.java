package com.yd.burst.model;

/**
 * @author Will
 */
public class Mail  extends BaseEntity {

    private String announcementTitle;

    private String announcementContent;

    private String announcementStatus;

    public String getAnnouncementTitle() {
        return announcementTitle;
    }

    public void setAnnouncementTitle(String announcementTitle) {
        this.announcementTitle = announcementTitle == null ? null : announcementTitle.trim();
    }

    public String getAnnouncementContent() {
        return announcementContent;
    }

    public void setAnnouncementContent(String announcementContent) {
        this.announcementContent = announcementContent == null ? null : announcementContent.trim();
    }

    public String getAnnouncementStatus() {
        return announcementStatus;
    }

    public void setAnnouncementStatus(String announcementStatus) {
        this.announcementStatus = announcementStatus == null ? null : announcementStatus.trim();
    }
}