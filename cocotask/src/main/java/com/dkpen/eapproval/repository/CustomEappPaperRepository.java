package com.dkpen.eapproval.repository;

import com.dkpen.eapproval.domain.User;
import com.dkpen.eapproval.dto.EappPaperDTO;
import java.util.List;

public interface CustomEappPaperRepository {
    List<EappPaperDTO> searchWaitPaperList(User user, String approveStatus);

    EappPaperDTO searchPaper(long paperUid);
}
