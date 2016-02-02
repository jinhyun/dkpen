package com.dkpen.eapproval.domain;

import javax.persistence.*;

@Entity
public class EappArchiveLine {
    @Id
    @Column(name = "ARCH_LINE_UID")
    private long uid;

    @Column(name = "ARCH_USER_NAME")
    private String userName;

    @Column(name = "ARCH_LINE_ORDER")
    private int lineOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARCH_USER_UID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARCH_PAPER_UID")
    private EappArchivePaper eappArchivePaper;

    @Column(name = "ARCH_APPROVE_STATUS")
    private String approveStatus;

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getUid() {
        return uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getLineOrder() {
        return lineOrder;
    }

    public void setLineOrder(int lineOrder) {
        this.lineOrder = lineOrder;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EappArchivePaper getEappPaper() {
        return eappArchivePaper;
    }

    public void setEappPaper(EappArchivePaper eappPaper) {
        this.eappArchivePaper = eappPaper;
    }
}
