package com.dkpen.eapproval.repository;

import com.dkpen.eapproval.domain.EappArchivePaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EappArchivePaperRepository extends JpaRepository<EappArchivePaper, Long> {
}
