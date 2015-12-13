package com.dkpen.eapproval.dto;

import java.util.List;

public class EappPaperDTO {
    private long uid;

    private String subject;

    private String content;

    private String regDate;

    private String regUserName;

    private List<Long> userUidList;

    public List<Long> getUserUidList() {
        return userUidList;
    }

    public void setUserUidList(List<Long> userUidList) {
        this.userUidList = userUidList;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
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
}