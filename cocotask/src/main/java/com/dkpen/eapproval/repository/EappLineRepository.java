package com.dkpen.eapproval.repository;

import com.dkpen.eapproval.domain.EappLine;
import com.dkpen.eapproval.domain.User;
import com.dkpen.eapproval.dto.EappLineDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EappLineRepository extends JpaRepository<EappLine, Long> {
    List<EappLine> findByUserAndApproveStatus(User user, String approveStatus);

    List<EappLineDTO> searchLine(long paperUid);
}
