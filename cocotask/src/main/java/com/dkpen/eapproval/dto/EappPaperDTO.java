package com.dkpen.eapproval.dto;

import java.util.List;

public class EappPaperDTO {
    public static final String WINDOW_TYPE_MODAL = "modal";

    private long paperUid;

    private String paperSubject;

    private String paperContent;

    private String paperRegDate;

    private String paperRegUserName;

    private List<Long> paperUserUidList;

    private List<EappLineDTO> eappLineDTOList;

    private String windowType;

    private String moduleName;  //TODO: update

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getWindowType() {
        return windowType;
    }

    public void setWindowType(String windowType) {
        this.windowType = windowType;
    }

    public List<EappLineDTO> getEappLineDTOList() {
        return eappLineDTOList;
    }

    public void setEappLineDTOList(List<EappLineDTO> eappLineDTOList) {
        this.eappLineDTOList = eappLineDTOList;
    }

    public List<Long> getPaperUserUidList() {
        return paperUserUidList;
    }

    public void setPaperUserUidList(List<Long> paperUserUidList) {
        this.paperUserUidList = paperUserUidList;
    }

    public long getPaperUid() {
        return paperUid;
    }

    public void setPaperUid(long paperUid) {
        this.paperUid = paperUid;
    }

    public String getPaperSubject() {
        return paperSubject;
    }

    public void setPaperSubject(String paperSubject) {
        this.paperSubject = paperSubject;
    }

    public String getPaperContent() {
        return paperContent;
    }

    public void setPaperContent(String paperContent) {
        this.paperContent = paperContent;
    }

    public String getPaperRegDate() {
        return paperRegDate;
    }

    public void setPaperRegDate(String paperRegDate) {
        this.paperRegDate = paperRegDate;
    }

    public String getPaperRegUserName() {
        return paperRegUserName;
    }

    public void setPaperRegUserName(String paperRegUserName) {
        this.paperRegUserName = paperRegUserName;
    }
}