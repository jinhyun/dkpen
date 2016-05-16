import com.dkpen.Application;
import com.dkpen.common.dto.PagedList;
import com.dkpen.common.dto.PagingRequest;
import com.dkpen.eapproval.domain.EappLine;
import com.dkpen.eapproval.domain.EappPaper;
import com.dkpen.eapproval.domain.User;
import com.dkpen.eapproval.dto.EappPaperDTO;
import com.dkpen.eapproval.dto.UserDTO;
import com.dkpen.eapproval.repository.EappLineRepository;
import com.dkpen.eapproval.repository.EappPaperRepository;
import com.dkpen.eapproval.repository.UserRepository;
import com.dkpen.eapproval.service.EappApproveService;
import com.dkpen.eapproval.service.MockCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.junit.runner.RunWith;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class EappServiceTest {
    @Autowired private EappApproveService approveService;
    @Autowired private UserRepository userRepository;
    @Autowired private EappPaperRepository paperRepository;
    @Autowired private EappLineRepository lineRepository;
    @Autowired private MockCreateService mockCreateService;

    @Test
    public void getWaitPaperPageList() {
        UserDTO elsa = approveService.getUser(2L);

        Long currentPageNumber = 3L;
        Long pageSize = 2L;
        PagingRequest pagingRequest = new PagingRequest(currentPageNumber, pageSize);

        PagedList<EappPaperDTO> paperDTOPages = approveService.getWaitPaperPageList(elsa, pagingRequest);
        List<EappPaperDTO> paperDTOPageList = paperDTOPages.getSource();

        assertThat(paperDTOPageList.size(), is(2));
        assertThat(paperDTOPageList.get(0).getPaperSubject(), is("55555"));
    }

    @Test
    public void getUserListTest() {
        List<User> userList = userRepository.findAll();

        assertThat(userList.size(), is(4));
    }

    @Test
    public void saveDonePaperTest() {
        List<Long> userUidList = new ArrayList<Long>();
        userUidList.add(1L);
        userUidList.add(2L);
        userUidList.add(3L);

        EappPaperDTO paperDTO = new EappPaperDTO();
        paperDTO.setPaperSubject("5월20일 휴가신청서");
        paperDTO.setPaperContent("3박 5일 신청합니다.");
        EappPaperDTO createdPaperDTO = mockCreateService.createPaper(paperDTO);

        mockCreateService.approveDonePaper(createdPaperDTO);

        EappPaper eappPaper = paperRepository.findOne(createdPaperDTO.getPaperUid());
        List<EappLine> eappLineList = lineRepository.findByEappPaper(eappPaper);

        assertThat(eappLineList.size(), is(3));

        for (EappLine eappLine : eappLineList) {
            if (eappLine.getLineOrder() == 0){
                assertThat(eappLine.getApproveStatus(), is("draft"));
            } else {
                assertThat(eappLine.getApproveStatus(), is("done"));
            }
            assertThat(eappLine.getPaperStatus(), is("done"));
            assertThat(eappLine.getPositionPaper(), is("none"));
        }
    }

    // MockCreateService.java
    @Test
    public void createInitMockTest() {
        List<EappPaper> paperList = paperRepository.findAll();
        assertThat(paperList.size(), is(8));
        /*
        saveDonePaperTest() 에서 저장된 데이터 추가
        각 테스트 단위마다 독립적으로 할 수 있는 방법이 있을까?
        한 테스트에서 저장하면 삭제까지 같이 진행해야 좋을까?
         */
    }

}
