package com.dkpen.eapproval.repository;

import com.dkpen.eapproval.domain.QEappLine;
import com.dkpen.eapproval.domain.QEappPaper;
import com.dkpen.eapproval.dto.EappLineDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository("eappLineRepositoryImpl")
public class EappLineRepositoryImpl implements CustomEappLineRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<EappLineDTO> searchLine(long paperUid) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QEappPaper qEappPaper = QEappPaper.eappPaper;
        QEappLine qEappLine = QEappLine.eappLine;

        List<EappLineDTO> eappLineDTOList = queryFactory.selectFrom(qEappLine)
                .select(Projections.bean(EappLineDTO.class,
                        qEappLine.uid.as("lineUid"),
                        qEappLine.userName.as("lineUserName"),
                        qEappLine.lineOrder.as("lineOrder"),
                        qEappLine.approveStatus.as("approveStatus"),
                        qEappLine.user.uid.as("userUid")))
                .where(qEappPaper.uid.eq(paperUid))
                .orderBy(qEappLine.lineOrder.asc())
                .fetch();

        return eappLineDTOList;
    }
}
