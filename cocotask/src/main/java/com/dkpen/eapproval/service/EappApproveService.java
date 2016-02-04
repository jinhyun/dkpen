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

    public String registerPaper(EappPaperDTO eappPaperDTO) {
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

            // TODO: 보완
            if (i == 0) {
                drafter = user;
                approveStatus = EappLineDTO.APPROVE_STATUS_DRAFT;
                positionPaper = EappLineDTO.PAPER_POSITION_NONE;
            } else if (i == 1) {
                approveStatus = EappLineDTO.APPROVE_STATUS_READY;
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

            lineList.add(line);
        }

        paper.setRegUserName(drafter.getName());
        paper.setRegDate(sdf.format(new Date()));
        paper.setEappLineList(lineList);
        paperRepository.save(paper);

        return "결재문서를 작성하였습니다.";
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
        List<EappLineDTO> resultLineDTOList = lineRepository.searchLine(eappApproveDTO.getPaperUid());

        for (int i = 0; i < resultLineDTOList.size(); i++){
            EappLineDTO eappLineDTO = resultLineDTOList.get(i);
            String approveStatus = eappLineDTO.getApproveStatus();
            long userUid = eappLineDTO.getUserUid();

            if (eappLineDTO.getPositionPaper().equals(EappLineDTO.PAPER_POSITION_HERE)
                    && userUid == loginUserDTO.getUid()) {
                EappLine eappLine = lineRepository.findOne(eappLineDTO.getLineUid());
                eappLine.setApproveStatus(eappApproveDTO.getApproveStatus());
                eappLine.setPositionPaper(eappLineDTO.PAPER_POSITION_NONE);

                //TODO: lineOrder로 정렬되어있다는 전제 (보완)
                // 다음 결재자 설정
                if(resultLineDTOList.size() != i+1) {
                    EappLineDTO nextEappLineDTO = resultLineDTOList.get(i+1);
                    EappLine nextEappLine = lineRepository.findOne(nextEappLineDTO.getLineUid());
                    nextEappLine.setApproveStatus(EappLineDTO.APPROVE_STATUS_READY);
                    nextEappLine.setPositionPaper(EappLineDTO.PAPER_POSITION_HERE);
                }

                // TODO: 추후 결재자 클래스로 변경
                // 최종 결재자 결재처리: 저장소로 저장
                if (resultLineDTOList.size() == i+1) {
                    System.out.println("Last Approver-Done :: Save Archive");
                    saveArchive(eappApproveDTO);
                }

                break;
            }
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
