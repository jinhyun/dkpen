package com.dkpen.eapproval.repository;

import com.dkpen.eapproval.domain.EappPaper;
import com.dkpen.eapproval.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EappPaperRepository extends JpaRepository<EappPaper, Long>, CustomEappPaperRepository {

}