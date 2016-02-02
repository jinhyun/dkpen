package com.dkpen.eapproval.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class EappArchivePaper {
    @Id
    @Column(name = "ARCH_PAPER_UID")
    private long uid;

    @Column(name = "ARCH_PAPER_SUBJECT")
    private String subject;

    @Column(name = "ARCH_PAPER_CONTENT")
    private String content;

    @Column(name = "ARCH_PAPER_REG_DATE")
    private String regDate;

    @Column(name = "ARCH_PAPER_REG_USER_NAME")
    private String regUserName;

    @OneToMany(mappedBy = "eappArchivePaper", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EappArchiveLine> EappArchiveLineList = new ArrayList<EappArchiveLine>();

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getUid() {
        return uid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getRegUserName() {
        return regUserName;
    }

    public void setRegUserName(String regUserName) {
        this.regUserName = regUserName;
    }

    public List<EappArchiveLine> getEappArchiveLineList() {
        return EappArchiveLineList;
    }

    public void setEappArchiveLineList(List<EappArchiveLine> eappLineList) {
        EappArchiveLineList = eappLineList;
    }
}
