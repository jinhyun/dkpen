package com.dkpen.eapproval.domain;

import javax.persistence.*;

@Entity
public class EappLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LINE_UID")
    private long uid;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "LINE_ORDER")
    private int lineOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_UID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAPER_UID")
    private EappPaper eappPaper;

    @Column(name = "APPROVE_STATUS")
    private String approveStatus;

    @Column(name = "PAPER_STATUS")
    private String paperStatus;

    @Column(name = "POSITION_PAPER")
    private String positionPaper;

    public String getPositionPaper() {
        return positionPaper;
    }

    public String getPaperStatus() {
        return paperStatus;
    }

    public void setPaperStatus(String paperStatus) {
        this.paperStatus = paperStatus;
    }

    public void setPositionPaper(String positionPaper) {
        this.positionPaper = positionPaper;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
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

    public EappPaper getEappPaper() {
        return eappPaper;
    }

    public void setEappPaper(EappPaper eappPaper) {
        this.eappPaper = eappPaper;
    }
}
