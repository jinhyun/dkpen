package com.dkpen.eapproval.repository;

import com.dkpen.eapproval.domain.EappArchiveLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EappArchiveLineRepository extends JpaRepository<EappArchiveLine, Long> {
}
