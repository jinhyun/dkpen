package com.dkpen.eapproval.repository;

import com.dkpen.eapproval.domain.EappLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EappLineRepository extends JpaRepository<EappLine, Long> {
}
