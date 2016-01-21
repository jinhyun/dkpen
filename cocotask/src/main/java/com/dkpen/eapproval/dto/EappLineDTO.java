package com.dkpen.eapproval.dto;

import com.dkpen.eapproval.domain.User;

public class EappLineDTO {
    public static final String APPROVE_STATUS_DRAFT = "draft";
    public static final String APPROVE_STATUS_READY = "ready";
    public static final String APPROVE_STATUS_NONE = "none";
    public static final String APPROVE_STATUS_DONE = "done";

    private long lineUid;
    private String lineUserName;
    private int lineOrder;
    private String approveStatus;
    private UserDTO userDTO;
    private User user;  //test
    private EappPaperDTO eappPaperDTO;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getLineUid() {
        return lineUid;
    }

    public void setLineUid(long lineUid) {
        this.lineUid = lineUid;
    }

    public String getLineUserName() {
        return lineUserName;
    }

    public void setLineUserName(String lineUserName) {
        this.lineUserName = lineUserName;
    }

    public int getLineOrder() {
        return lineOrder;
    }

    public void setLineOrder(int lineOrder) {
        this.lineOrder = lineOrder;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public EappPaperDTO getEappPaperDTO() {
        return eappPaperDTO;
    }

    public void setEappPaperDTO(EappPaperDTO eappPaperDTO) {
        this.eappPaperDTO = eappPaperDTO;
    }
}
