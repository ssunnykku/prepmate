package com.prepmate.backend.repository;

import com.prepmate.backend.domain.Question;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.prepmate.backend.domain.QQuestion.question1;

@Repository
@Slf4j
public class InterviewQuestionRepositoryImpl implements InterviewQuestionRepository {
    public InterviewQuestionRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Question> findByInterviewId(Long interviewId) {
        List<Question> questionList = jpaQueryFactory.selectFrom(question1)
                .where(question1.interview.id.eq(interviewId))
                .orderBy(question1.id.desc())
                .fetch();
        return questionList;
    }

    @Override
    public Page<Question> findByInterviewId(Long interviewId, Pageable pageable) {
        List<Question> questionList = jpaQueryFactory.selectFrom(question1)
                .where(question1.interview.id.eq(interviewId))
                .orderBy(question1.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory
                .select(question1.count())
                .from(question1)
                .where(question1.interview.id.eq(interviewId))
                .fetchOne();

        return new PageImpl<>(questionList, pageable, count);
    }

}
