package com.dkpen.eapproval.repository;

import com.dkpen.common.dto.PagedList;
import com.dkpen.common.dto.Paging;
import com.dkpen.common.dto.PagingRequest;
import com.dkpen.eapproval.domain.QEappLine;
import com.dkpen.eapproval.domain.QEappPaper;
import com.dkpen.eapproval.domain.User;
import com.dkpen.eapproval.dto.EappPaperDTO;
import com.querydsl.core.QueryModifiers;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("eappPaperRepositoryImpl")
public class EappPaperRepositoryImpl implements CustomEappPaperRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<EappPaperDTO> searchWaitPaperList(User user, String positionPaper) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QEappPaper qEappPaper = QEappPaper.eappPaper;
        QEappLine qEappLine = QEappLine.eappLine;

        List<EappPaperDTO> paperDTOList = queryFactory.selectFrom(qEappPaper)
                .select(Projections.bean(EappPaperDTO.class,
                        qEappPaper.uid.as("paperUid"),
                        qEappPaper.subject.as("paperSubject"),
                        qEappPaper.content.as("paperContent"),
                        qEappPaper.regDate.as("paperRegDate"),
                        qEappPaper.regUserName.as("paperRegUserName")))
                .innerJoin(qEappPaper.EappLineList, qEappLine)
                .where(qEappLine.user.eq(user), qEappLine.positionPaper.eq(positionPaper))
                .fetch();

        return paperDTOList;
    }

    // TODO: searchWaitPaperList 와 같이 사용할지는 고려중
    @Override
    public List<EappPaperDTO> searchProgressPaperList(User user, String paperStatusProgress, String positionPaper) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QEappPaper qEappPaper = QEappPaper.eappPaper;
        QEappLine qEappLine = QEappLine.eappLine;

        List<EappPaperDTO> paperDTOList = queryFactory.selectFrom(qEappPaper)
                .select(Projections.bean(EappPaperDTO.class,
                        qEappPaper.uid.as("paperUid"),
                        qEappPaper.subject.as("paperSubject"),
                        qEappPaper.content.as("paperContent"),
                        qEappPaper.regDate.as("paperRegDate"),
                        qEappPaper.regUserName.as("paperRegUserName")))
                .innerJoin(qEappPaper.EappLineList, qEappLine)
                .where(qEappLine.user.eq(user),
                        qEappLine.paperStatus.eq(paperStatusProgress),
                        qEappLine.positionPaper.eq(positionPaper))
                .fetch();

        return paperDTOList;
    }

    @Override
    public EappPaperDTO searchPaper(long paperUid) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QEappPaper qEappPaper = QEappPaper.eappPaper;
        QEappLine qEappLine = QEappLine.eappLine;

        EappPaperDTO eappPaperDTO = queryFactory.selectFrom(qEappPaper)
                .select(Projections.bean(EappPaperDTO.class,
                        qEappPaper.uid.as("paperUid"),
                        qEappPaper.subject.as("paperSubject"),
                        qEappPaper.content.as("paperContent"),
                        qEappPaper.regDate.as("paperRegDate"),
                        qEappPaper.regUserName.as("paperRegUserName")))
                .where(qEappPaper.uid.eq(paperUid))
                .fetchOne();

        return eappPaperDTO;
    }

    @Override
    public PagedList<EappPaperDTO> searchWaitPaperPageList(User user, String positionPaper, PagingRequest pagingRequest) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QEappPaper qEappPaper = QEappPaper.eappPaper;
        QEappLine qEappLine = QEappLine.eappLine;

        List<EappPaperDTO> paperList = queryFactory.selectFrom(qEappPaper)
                .select(Projections.bean(EappPaperDTO.class,
                        qEappPaper.uid.as("paperUid"),
                        qEappPaper.subject.as("paperSubject"),
                        qEappPaper.content.as("paperContent"),
                        qEappPaper.regDate.as("paperRegDate"),
                        qEappPaper.regUserName.as("paperRegUserName")))
                .innerJoin(qEappPaper.EappLineList, qEappLine)
                .where(qEappLine.user.eq(user), qEappLine.positionPaper.eq(positionPaper))
                .restrict(new QueryModifiers(pagingRequest.getPageSize(), pagingRequest.getOffset()))
                .fetch();

        Long paperListTotalCount = queryFactory.selectFrom(qEappPaper)
                .select(qEappPaper.uid)
                .innerJoin(qEappPaper.EappLineList, qEappLine)
                .where(qEappLine.user.eq(user), qEappLine.positionPaper.eq(positionPaper))
                .fetchCount();

        return new PagedList(paperList, new Paging(paperListTotalCount, pagingRequest));
    }

    @Override
    public PagedList<EappPaperDTO> searchProgressPaperPageList(User user, String paperStatusProgress, String positionPaper, PagingRequest pagingRequest) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QEappPaper qEappPaper = QEappPaper.eappPaper;
        QEappLine qEappLine = QEappLine.eappLine;

        List<EappPaperDTO> paperList = queryFactory.selectFrom(qEappPaper)
                .select(Projections.bean(EappPaperDTO.class,
                        qEappPaper.uid.as("paperUid"),
                        qEappPaper.subject.as("paperSubject"),
                        qEappPaper.content.as("paperContent"),
                        qEappPaper.regDate.as("paperRegDate"),
                        qEappPaper.regUserName.as("paperRegUserName")))
                .innerJoin(qEappPaper.EappLineList, qEappLine)
                .where(qEappLine.user.eq(user),
                        qEappLine.paperStatus.eq(paperStatusProgress),
                        qEappLine.positionPaper.eq(positionPaper))
                .fetch();

        Long paperListTotalCount = queryFactory.selectFrom(qEappPaper)
                .select(qEappPaper.uid)
                .innerJoin(qEappPaper.EappLineList, qEappLine)
                .where(qEappLine.user.eq(user),
                        qEappLine.paperStatus.eq(paperStatusProgress),
                        qEappLine.positionPaper.eq(positionPaper))
                .fetchCount();

        return new PagedList<EappPaperDTO>(paperList, new Paging(paperListTotalCount, pagingRequest));
    }
}
