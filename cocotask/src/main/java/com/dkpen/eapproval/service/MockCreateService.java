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
        // user: elsa 기준
        // 결재대기
        EappPaperDTO paper1a = new EappPaperDTO();
        paper1a.setPaperSubject("4월14일 슈퍼마리오팀 화이팅 회식비 지출외");
        paper1a.setPaperContent("회식비 100,000원 등등");
        createPaper(paper1a);

        // 결재대기
        EappPaperDTO paper2a = new EappPaperDTO();
        paper2a.setPaperSubject("징계의결에 따른 교육안료 보고");
        paper2a.setPaperContent("1. 관련근거 제5-6회 인사위원회 결과보고 \n 2.위 관련근거에 의거하여 실시한 성희롱예방교육의 이수 실적을 아래와 같이" +
                "보고하오니 승인여 주시기 바랍니다");
        createPaper(paper2a);

        // 결재대기
        EappPaperDTO paper3a = new EappPaperDTO();
        paper3a.setPaperSubject("33333");
        paper3a.setPaperContent("1. 관련근거 제5-6회 인사위원회 결과보고 \n 2.위 관련근거에 의거하여 실시한 성희롱예방교육의 이수 실적을 아래와 같이" +
                "보고하오니 승인여 주시기 바랍니다");
        createPaper(paper3a);

        // 결재대기
        EappPaperDTO paper4a = new EappPaperDTO();
        paper4a.setPaperSubject("44444");
        paper4a.setPaperContent("4 contents");
        createPaper(paper4a);

        // 결재대기
        EappPaperDTO paper5a = new EappPaperDTO();
        paper5a.setPaperSubject("55555");
        paper4a.setPaperContent("5 contents");
        createPaper(paper5a);

        // 결재대기
        EappPaperDTO paper6a = new EappPaperDTO();
        paper6a.setPaperSubject("66666");
        paper4a.setPaperContent("6 contents");
        createPaper(paper6a);

        // 결재대기
        EappPaperDTO paper7a = new EappPaperDTO();
        paper7a.setPaperSubject("77777");
        paper4a.setPaperContent("7 contents");
        createPaper(paper7a);

        // 결재진행
        EappPaperDTO paper1b = new EappPaperDTO();
        paper1b.setPaperSubject("정책사례개발연구용역 소형과재 최종보고서 부처의견 수렴");
        paper1b.setPaperContent("정책사례개발추진단102(2007. 4. 12)등 과 관련됩니다");
        createPaper(paper1b);
        approvePaper(2L, paper1b);

        // 결재진행
        EappPaperDTO paper2b = new EappPaperDTO();
        paper2b.setPaperSubject("4월15일 해외출장보고서");
        paper2b.setPaperContent("제3차 포럼운영위원회 참석을 위하여 시행한 해외출장 결과를 아래와 같이 보고합니다.");
        createPaper(paper2b);
        approvePaper(2L, paper2b);

        // 결재완료
        EappPaperDTO paper1c = new EappPaperDTO();
        paper1c.setPaperSubject("4월10일 휴가신청서");
        paper1c.setPaperContent("1박 2일 신청합니다.");
        createPaper(paper1c);
        approveDonePaper(paper1c);

        // 결재완료
        EappPaperDTO paper2c = new EappPaperDTO();
        paper2c.setPaperSubject("3월 19일 자금결재 기안서");
        paper2c.setPaperContent("거래은행명: 한국은행\n 결재금액: 1,000,000원");
        createPaper(paper2c);
        approveDonePaper(paper2c);

        // 결재완료
        EappPaperDTO paper3c = new EappPaperDTO();
        paper3c.setPaperSubject("사전검토 회의결과 보고");
        paper3c.setPaperContent("사전검도 신청에 대하여 의약품 사전검도 운영지침에 따라 사전검토 회의를 개최하고 그 결과를 다음과 같이 보고합니다");
        createPaper(paper3c);
        approveDonePaper(paper3c);

        // 결재완료
        EappPaperDTO paper4c = new EappPaperDTO();
        paper4c.setPaperSubject("2월11일 휴가신청서");
        paper4c.setPaperContent("3박 5일 신청합니다.");

        List<Long> userUidList = new ArrayList<Long>();
        userUidList.add(2L);
        userUidList.add(3L);
        userUidList.add(4L);
        paper4c.setPaperUserUidList(userUidList);

        createPaper(paper4c);
        approveDonePaper(paper4c);
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
