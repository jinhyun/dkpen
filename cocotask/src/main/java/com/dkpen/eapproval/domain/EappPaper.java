package com.dkpen.eapproval.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class EappPaper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAPER_UID")
    private long uid;

    @Column(name = "PAPER_SUBJECT")
    private String subject;

    @Column(name = "PAPER_CONTENT")
    private String content;

    @Column(name = "PAPER_REG_DATE")
    private String regDate;

    @Column(name = "PAPER_REG_USER_NAME")
    private String regUserName;

    @OneToMany(mappedBy = "eappPaper", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EappLine> EappLineList = new ArrayList<EappLine>();

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

    public List<EappLine> getEappLineList() {
        return EappLineList;
    }

    public void setEappLineList(List<EappLine> eappLineList) {
        EappLineList = eappLineList;
    }
}

