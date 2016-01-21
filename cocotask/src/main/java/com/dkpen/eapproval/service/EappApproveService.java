package com.dkpen.eapproval.service;

import com.dkpen.eapproval.domain.EappLine;
import com.dkpen.eapproval.domain.EappPaper;
import com.dkpen.eapproval.domain.User;
import com.dkpen.eapproval.dto.EappLineDTO;
import com.dkpen.eapproval.dto.EappPaperDTO;
import com.dkpen.eapproval.dto.UserDTO;
import com.dkpen.eapproval.repository.EappLineRepository;
import com.dkpen.eapproval.repository.EappPaperRepository;
import com.dkpen.eapproval.repository.UserRepository;
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

            // TODO: 보완
            if (i == 0) {
                drafter = user;
                approveStatus = EappLineDTO.APPROVE_STATUS_DRAFT;
            } else if (i == 1) {
                approveStatus = EappLineDTO.APPROVE_STATUS_READY;
            } else {
                approveStatus = EappLineDTO.APPROVE_STATUS_NONE;
            }

            line.setUser(user);
            line.setEappPaper(paper);
            line.setUserName(user.getName());
            line.setLineOrder(i);
            line.setApproveStatus(approveStatus);

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

        EappLine eappLine = new EappLine();
        eappLine.setUser(user);
        eappLine.setApproveStatus(EappLineDTO.APPROVE_STATUS_READY);

        List<EappPaperDTO> waitPaperDTOList = paperRepository.searchWaitPaperList(user);

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
}
