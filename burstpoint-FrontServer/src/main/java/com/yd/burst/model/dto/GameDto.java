package com.yd.burst.model.dto;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-08-18 18:06
 **/
public class GameDto {

    private Long issueNo;

    private Double burstPoint;

    public Long getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(Long issueNo) {
        this.issueNo = issueNo;
    }

    public Double getBurstPoint() {
        return burstPoint;
    }

    public void setBurstPoint(Double burstPoint) {
        this.burstPoint = burstPoint;
    }
}
