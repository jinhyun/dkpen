package com.dkpen.eapproval.repository;

import com.dkpen.eapproval.domain.User;
import com.dkpen.eapproval.dto.EappPaperDTO;
import java.util.List;

public interface CustomEappPaperRepository {
    List<EappPaperDTO> searchWaitPaperList(User user, String positionPaper);

    List<EappPaperDTO> searchProgressPaperList(User user, String paperStatusProgress, String positionPaper);

    EappPaperDTO searchPaper(long paperUid);
}
