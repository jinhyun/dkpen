package com.dkpen.eapproval.dto;

public class EappArchiveLineDTO {
    private long lineUid;
    private String lineUserName;
    private int lineOrder;
    private String approveStatus;
    private UserDTO userDTO;
    private long userUid;
    private EappPaperDTO eappPaperDTO;

    public long getUserUid() {
        return userUid;
    }

    public void setUserUid(long userUid) {
        this.userUid = userUid;
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
