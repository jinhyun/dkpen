package com.dkpen.eapproval.repository;

import com.dkpen.eapproval.dto.EappLineDTO;

import java.util.List;

public interface CustomEappLineRepository {
    List<EappLineDTO> searchLine(long paperUid);
}
