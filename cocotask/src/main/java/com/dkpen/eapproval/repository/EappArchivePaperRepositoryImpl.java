package com.dkpen.eapproval.repository;

import com.dkpen.eapproval.domain.QEappArchiveLine;
import com.dkpen.eapproval.domain.QEappArchivePaper;
import com.dkpen.eapproval.domain.User;
import com.dkpen.eapproval.dto.EappArchivePaperDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("eappArchivePaperRepositoryImpl")
public class EappArchivePaperRepositoryImpl implements CustomEappArchivePaperRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<EappArchivePaperDTO> searchPaperList(User user) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QEappArchivePaper qArchivePaper = QEappArchivePaper.eappArchivePaper;
        QEappArchiveLine qArchiveLine = QEappArchiveLine.eappArchiveLine;

        List<EappArchivePaperDTO> archivePaperDTOList = queryFactory.selectFrom(qArchivePaper)
                .select(Projections.bean(EappArchivePaperDTO.class,
                        qArchivePaper.uid.as("paperUid"),
                        qArchivePaper.subject.as("paperSubject"),
                        qArchivePaper.content.as("paperContent"),
                        qArchivePaper.regDate.as("paperRegDate"),
                        qArchivePaper.regUserName.as("paperRegUserName")))
                .innerJoin(qArchivePaper.EappArchiveLineList, qArchiveLine)
                .where(qArchiveLine.user.eq(user))
                .fetch();

        return archivePaperDTOList;
    }
}
