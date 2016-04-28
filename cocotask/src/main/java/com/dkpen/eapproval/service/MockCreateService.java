package com.dkpen.eapproval.service;

import com.dkpen.eapproval.domain.User;
import com.dkpen.eapproval.dto.EappApproveDTO;
import com.dkpen.eapproval.dto.EappLineDTO;
import com.dkpen.eapproval.dto.EappPaperDTO;
import com.dkpen.eapproval.dto.UserDTO;
import com.dkpen.eapproval.repository.UserRepository;
import com.dkpen.user.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

@Service
@Transactional
public class MockCreateService {
    @Autowired UserRepository userRepository;
    @Autowired EappApproveService approveService;

    @PostConstruct
    public void initMock() {
        createUserMock();
        createPaperMock();
    }

    public void createUserMock() {
        BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();
        String password = crypt.encode("1234");

        User anna = new User();
        anna.setEmail("anna@dkpen.com");
        anna.setName("안나");
        anna.setPasswordHash(password);
        anna.setRole(Role.USER);

        User elsa = new User();
        elsa.setEmail("elsa@dkpen.com");
        elsa.setName("엘사");
        elsa.setPasswordHash(password);
        elsa.setRole(Role.USER);

        User olaf = new User();
        olaf.setEmail("olaf@dkpen.com");
        olaf.setName("올라프");
        olaf.setPasswordHash(password);
        olaf.setRole(Role.USER);

        User kristoff = new User();
        kristoff.setEmail("kristoff@dkpen.com");
        kristoff.setName("크리스토프");
        kristoff.setPasswordHash(password);
        kristoff.setRole(Role.USER);

        userRepository.save(anna);
        userRepository.save(elsa);
        userRepository.save(olaf);
        userRepository.save(kristoff);
    }

    public void createPaperMock() {
        // 결재진행
        EappPaperDTO paper2 = new EappPaperDTO();
        paper2.setPaperSubject("4월12일 휴가신청서");
        paper2.setPaperContent("4박 5일 신청합니다.");
        createPaper(paper2);
        approvePaper(2L, paper2);

        // 결재진행
        EappPaperDTO paper2a = new EappPaperDTO();
        paper2a.setPaperSubject("8월22일 휴가신청서");
        paper2a.setPaperContent("9박 9일 신청합니다.");
        createPaper(paper2a);
        approvePaper(2L, paper2a);

        // 결재진행
        EappPaperDTO paper2b = new EappPaperDTO();
        paper2b.setPaperSubject("4월14일 슈퍼마리오팀 화이팅 회식비 지출외");
        paper2b.setPaperContent("회식비 100,000원 등등");
        createPaper(paper2b);

        // 결재완료
        EappPaperDTO paper1 = new EappPaperDTO();
        paper1.setPaperSubject("4월10일 휴가신청서");
        paper1.setPaperContent("1박 2일 신청합니다.");

        createPaper(paper1);
        approveDonePaper(paper1);

        // 결재완료
        EappPaperDTO paper3 = new EappPaperDTO();
        paper3.setPaperSubject("4월20일 휴가신청서");
        paper3.setPaperContent("2박 3일 신청합니다.");
        createPaper(paper3);
        approveDonePaper(paper3);

        // 결재완료
        EappPaperDTO paper4 = new EappPaperDTO();
        paper4.setPaperSubject("4월31일 휴가신청서");
        paper4.setPaperContent("7박 8일 신청합니다.");
        createPaper(paper4);
        approveDonePaper(paper4);

        // 결재완료
        EappPaperDTO paper5 = new EappPaperDTO();
        paper5.setPaperSubject("2월11일 휴가신청서");
        paper5.setPaperContent("3박 5일 신청합니다.");

        List<Long> userUidList = new ArrayList<Long>();
        userUidList.add(2L);
        userUidList.add(3L);
        userUidList.add(4L);
        paper5.setPaperUserUidList(userUidList);

        createPaper(paper5);
        approveDonePaper(paper5);
    }

    public EappPaperDTO createPaper(EappPaperDTO paperDTO) {
        paperDTO.setPaperSubject(paperDTO.getPaperSubject());
        paperDTO.setPaperContent(paperDTO.getPaperContent());

        if (paperDTO.getPaperUserUidList() == null) {
            paperDTO.setPaperUserUidList(createDefaultLine());
        } else {
            paperDTO.setPaperUserUidList(paperDTO.getPaperUserUidList());
        }

        paperDTO.setPaperUid(approveService.registerPaper(paperDTO));

        return paperDTO;
    }

    public void approvePaper(long approveUserUid, EappPaperDTO paperDTO) {
        EappApproveDTO eappApproveDTO = new EappApproveDTO();
        UserDTO loginUserDTO = approveService.getUser(approveUserUid);

        eappApproveDTO.setPaperUid(paperDTO.getPaperUid());
        eappApproveDTO.setApproveStatus(EappLineDTO.APPROVE_STATUS_DONE);

        approveService.approvePaper(eappApproveDTO, loginUserDTO);
    }

    public void approveDonePaper(EappPaperDTO paperDTO) {
        List<Long> userUidList = paperDTO.getPaperUserUidList();

        if (userUidList.size() < 1) {
            return;
        }

        for (int i = 0; i < userUidList.size(); i++) {
            if (i == 0) continue;
            approvePaper(userUidList.get(i), paperDTO);
        }
    }

    private List<Long> createDefaultLine() {
        List<Long> userUidList = new ArrayList<Long>();
        userUidList.add(1L);
        userUidList.add(2L);
        userUidList.add(3L);

        return userUidList;
    }
}
