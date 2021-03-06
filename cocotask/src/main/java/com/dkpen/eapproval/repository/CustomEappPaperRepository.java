package com.dkpen.eapproval.repository;

import com.dkpen.common.dto.PagedList;
import com.dkpen.common.dto.PagingRequest;
import com.dkpen.eapproval.domain.User;
import com.dkpen.eapproval.dto.EappPaperDTO;

import java.util.List;

public interface CustomEappPaperRepository {
    List<EappPaperDTO> searchWaitPaperList(User user, String positionPaper);

    List<EappPaperDTO> searchProgressPaperList(User user, String paperStatusProgress, String positionPaper);

    EappPaperDTO searchPaper(long paperUid);

    PagedList<EappPaperDTO> searchWaitPaperPageList(User user, String positionPaper, PagingRequest pagingRequest);

    PagedList<EappPaperDTO> searchProgressPaperPageList(User user, String paperStatusProgress, String positionPaper, PagingRequest pagingRequest);
}
