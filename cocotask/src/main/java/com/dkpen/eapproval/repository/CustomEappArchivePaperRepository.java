package com.dkpen.eapproval.repository;

import com.dkpen.eapproval.domain.User;
import com.dkpen.eapproval.dto.EappArchivePaperDTO;

import java.util.List;

public interface CustomEappArchivePaperRepository {
    List<EappArchivePaperDTO> searchPaperList(User user);
}
