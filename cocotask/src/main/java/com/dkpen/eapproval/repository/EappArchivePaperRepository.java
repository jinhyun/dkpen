package com.dkpen.eapproval.repository;

import com.dkpen.eapproval.domain.EappArchivePaper;
import com.dkpen.eapproval.domain.User;
import com.dkpen.eapproval.dto.EappArchivePaperDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EappArchivePaperRepository extends JpaRepository<EappArchivePaper, Long>, CustomEappArchivePaperRepository {
}
