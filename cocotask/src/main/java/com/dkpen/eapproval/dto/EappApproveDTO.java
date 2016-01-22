package com.dkpen.eapproval.dto;

public class EappApproveDTO {
    private long paperUid;
    private long lineUid;
    private String approveStatus;

    public long getPaperUid() {
        return paperUid;
    }

    public void setPaperUid(long paperUid) {
        this.paperUid = paperUid;
    }

    public long getLineUid() {
        return lineUid;
    }

    public void setLineUid(long lineUid) {
        this.lineUid = lineUid;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }
}
