package com.dkpen.eapproval.service;

import com.dkpen.eapproval.domain.*;
import com.dkpen.eapproval.dto.*;
import com.dkpen.eapproval.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EappApproveService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EappPaperRepository paperRepository;

    @Autowired
    EappLineRepository lineRepository;

    @Autowired
    EappArchiveLineRepository archiveLineRepository;

    @Autowired
    EappArchivePaperRepository archivePaperRepository;

    public Long registerPaper(EappPaperDTO eappPaperDTO) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        EappPaper paper = new EappPaper();
        paper.setSubject(eappPaperDTO.getPaperSubject());
        paper.setContent(eappPaperDTO.getPaperContent());

        List<EappLine> lineList = new ArrayList<EappLine>();
        User drafter = new User();

        for (int i=0; i < eappPaperDTO.getPaperUserUidList().size(); i++){
            long userUid = eappPaperDTO.getPaperUserUidList().get(i);

            EappLine line = new EappLine();
            User user = userRepository.findOne(userUid);
            String approveStatus;
            String positionPaper;
            String paperStatus = EappLineDTO.PAPER_STATUS_PROGRESS;

            // TODO: 보완
            if (i == 0) {
                drafter = user;
                approveStatus = EappLineDTO.APPROVE_STATUS_DRAFT;
                positionPaper = EappLineDTO.PAPER_POSITION_NONE;
            } else if (i == 1) {
                approveStatus = EappLineDTO.APPROVE_STATUS_NONE;
                positionPaper = EappLineDTO.PAPER_POSITION_HERE;
            } else {
                approveStatus = EappLineDTO.APPROVE_STATUS_NONE;
                positionPaper = EappLineDTO.PAPER_POSITION_NONE;
            }

            line.setUser(user);
            line.setEappPaper(paper);
            line.setUserName(user.getName());
            line.setLineOrder(i);
            line.setApproveStatus(approveStatus);
            line.setPositionPaper(positionPaper);
            line.setPaperStatus(paperStatus);

            lineList.add(line);
        }

        paper.setRegUserName(drafter.getName());
        paper.setRegDate(sdf.format(new Date()));
        paper.setEappLineList(lineList);
        EappPaper createdPaper = paperRepository.save(paper);

        return createdPaper.getUid();
    }

    public List<UserDTO> getUserListExceptLoginUser(UserDTO loginUserDTO) {
        List <User> userList = userRepository.findAll();
        List <UserDTO> userDTOList = new ArrayList<UserDTO>();

        for (User user : userList) {
            if (loginUserDTO.getUid() == user.getUid()){
                continue;
            }

            UserDTO userDTO = new UserDTO();
            userDTO.setUid(user.getUid());
            userDTO.setEmail(user.getEmail());
            userDTO.setName(user.getName());
            userDTOList.add(userDTO);
        }

        return userDTOList;
    }

    public UserDTO getUser(long userUid) {
        User loginUser = userRepository.findOne(userUid);
        UserDTO loginUserDTO = new UserDTO();

        loginUserDTO.setUid(loginUser.getUid());
        loginUserDTO.setEmail(loginUser.getEmail());
        loginUserDTO.setName(loginUser.getName());

        return loginUserDTO;
    }

    public List<EappPaperDTO> getWaitPaperList(UserDTO userDTO) {
        User user = new User();
        user.setUid(userDTO.getUid());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());

        List<EappPaperDTO> waitPaperDTOList = paperRepository.searchWaitPaperList(user, EappLineDTO.PAPER_POSITION_HERE);

        return waitPaperDTOList;
    }

    public List<EappPaperDTO> getProgressPaperList(UserDTO userDTO) {
        User user = new User();
        user.setUid(userDTO.getUid());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());

        List<EappPaperDTO> progressPaperDTOList = paperRepository.searchProgressPaperList(user,
                EappLineDTO.PAPER_STATUS_PROGRESS,
                EappLineDTO.PAPER_POSITION_NONE);

        return progressPaperDTOList;
    }

    public EappPaperDTO getViewPaper(EappPaperDTO eappPaperDTO) {
        EappPaperDTO paperDTO = getPaperLine(eappPaperDTO.getPaperUid());

        return paperDTO;
    }

    public EappPaperDTO getPaperLine(long paperUid) {
        EappPaperDTO paperDTO = paperRepository.searchPaper(paperUid);
        List<EappLineDTO> eappLineDTOList = lineRepository.searchLine(paperDTO.getPaperUid());
        paperDTO.setEappLineDTOList(eappLineDTOList);

        return paperDTO;
    }

    // TODO: 변수명 수정
    public void approvePaper(EappApproveDTO eappApproveDTO, UserDTO loginUserDTO) {
        // TODO: 필수값 검증
        List<EappLineDTO> resultLineDTOList = lineRepository.searchLine(eappApproveDTO.getPaperUid());
        long loginUserUid = loginUserDTO.getUid();

        for (int i = 0; i < resultLineDTOList.size(); i++){
            EappLineDTO eappLineDTO = resultLineDTOList.get(i);
            long lineUid = eappLineDTO.getLineUid();
            long lineUserUid = eappLineDTO.getUserUid();

            String approveStatus = eappApproveDTO.getApproveStatus();
            String positionPaper = eappLineDTO.getPositionPaper();

            if (positionPaper.equals(EappLineDTO.PAPER_POSITION_HERE) && lineUserUid == loginUserUid) {
                // TODO: 결재자 구분 컬럼 생성후(결재자 클래스) 제어 & refactor
                // 반려: 작성자에게 전달
                if (approveStatus.equals(EappLineDTO.APPROVE_STATUS_REJECT)) {
                    EappLine currentEappLine = lineRepository.findOne(lineUid);
                    currentEappLine.setApproveStatus(approveStatus);
                    currentEappLine.setPositionPaper(EappLineDTO.PAPER_POSITION_NONE);

                    EappLineDTO draftEappLineDTO = resultLineDTOList.get(0);
                    EappLine draftEappLine = lineRepository.findOne(draftEappLineDTO.getLineUid());
                    draftEappLine.setPositionPaper(EappLineDTO.PAPER_POSITION_HERE);

                // 보류
                } else if (approveStatus.equals(EappLineDTO.APPROVE_STATUS_HOLD)) {
                    EappLine holdEappLine = lineRepository.findOne(lineUid);
                    holdEappLine.setApproveStatus(approveStatus);
                    holdEappLine.setPositionPaper(EappLineDTO.PAPER_POSITION_HERE);

                // 결재
                } else if (approveStatus.equals(EappLineDTO.APPROVE_STATUS_DONE)) {
                    EappLine currentEappLine = lineRepository.findOne(lineUid);
                    currentEappLine.setApproveStatus(approveStatus);
                    currentEappLine.setPositionPaper(EappLineDTO.PAPER_POSITION_NONE);

                    // 다음 결재자에게 전달
                    sendNextLine(resultLineDTOList, i);

                    // 최종 결재자 결재처리: 저장소로 저장
                    if (resultLineDTOList.size() == i+1) {
                        EappPaper eappPaper = paperRepository.findOne(eappApproveDTO.getPaperUid());
                        List<EappLine> eappLineList = lineRepository.findByEappPaper(eappPaper);

                        for (EappLine eappLine : eappLineList) {
                            eappLine.setPaperStatus(EappLineDTO.PAPER_STATUS_DONE);
                            lineRepository.saveAndFlush(eappLine);
                        }

                        System.out.println("Last Approver-Done :: Save Archive");
                        saveArchive(eappApproveDTO);
                    }

                } else {
                    System.out.println("Exception");
                }

                break;
            }
        }
    }

    /**
     * 다음 결재자에게 전달
     * @param lineList
     * @param currentIdx
     */
    public void sendNextLine(List<EappLineDTO> lineList, int currentIdx) {
        if (lineList.size() != currentIdx + 1) {
            EappLineDTO nextEappLineDTO = lineList.get(currentIdx + 1);
            EappLine nextEappLine = lineRepository.findOne(nextEappLineDTO.getLineUid());
            nextEappLine.setPositionPaper(EappLineDTO.PAPER_POSITION_HERE);
        }

    }

    /**
     * 결재문서를 저장소로 저장
     * @param eappApproveDTO
     */
    public void saveArchive(EappApproveDTO eappApproveDTO) {
        long paperUid = eappApproveDTO.getPaperUid();

        EappPaper paper = paperRepository.findOne(paperUid);
        List<EappLine> lineList = lineRepository.findByEappPaper(paper);

        EappArchivePaper archivePaper = new EappArchivePaper();
        List<EappArchiveLine> archiveLineList = new ArrayList<EappArchiveLine>();

        archivePaper.setContent(paper.getContent());
        archivePaper.setRegDate(paper.getRegDate());
        archivePaper.setRegUserName(paper.getRegUserName());
        archivePaper.setSubject(paper.getSubject());
        archivePaper.setUid(paper.getUid());

        for (EappLine eappLine : lineList) {
            EappArchiveLine archiveLine = new EappArchiveLine();
            archiveLine.setApproveStatus(eappLine.getApproveStatus());
            archiveLine.setLineOrder(eappLine.getLineOrder());
            archiveLine.setUid(eappLine.getUid());
            archiveLine.setUser(eappLine.getUser());
            archiveLine.setUserName(eappLine.getUserName());

            archiveLine.setEappPaper(archivePaper);

            archiveLineList.add(archiveLine);
        }

        archivePaper.setEappArchiveLineList(archiveLineList);

        archivePaperRepository.save(archivePaper);
    }

    /**
     * 저장소 결재완료 문서 조회
     */
    public List<EappArchivePaperDTO> getArchivePaper(UserDTO userDTO) {
        return archivePaperRepository.searchPaperList(userRepository.findOne(userDTO.getUid()));
    }
}
