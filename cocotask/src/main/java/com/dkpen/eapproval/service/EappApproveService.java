package com.dkpen.eapproval.service;

import com.dkpen.eapproval.domain.EappLine;
import com.dkpen.eapproval.domain.EappPaper;
import com.dkpen.eapproval.domain.User;
import com.dkpen.eapproval.dto.EappPaperDTO;
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
        paper.setSubject(eappPaperDTO.getSubject());
        paper.setContent(eappPaperDTO.getContent());

        List<EappLine> lineList = new ArrayList<EappLine>();
        User drafter = new User();

        for (int i=0; i < eappPaperDTO.getUserUidList().size(); i++){
            long userUid = eappPaperDTO.getUserUidList().get(i);

            EappLine line = new EappLine();
            User user = userRepository.findOne(userUid);
            if (i == 0) {
                drafter = user;
            }

            line.setUser(user);
            line.setEappPaper(paper);
            line.setUserName(user.getName());
            line.setLineOrder(i);

            lineList.add(line);
        }

        paper.setRegUserName(drafter.getName());
        paper.setRegDate(sdf.format(new Date()));
        paper.setEappLineList(lineList);
        paperRepository.save(paper);

        return "결재문서를 작성하였습니다.";
    }
}
