package com.prepmate.backend.repository;

import com.prepmate.backend.domain.Question;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.prepmate.backend.domain.QQuestion.question1;

@Repository
public class InterviewQuestionRepositoryImpl implements InterviewQuestionRepository {
    public InterviewQuestionRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Question> findByInterviewId(Long interviewId) {
        List<Question> question = jpaQueryFactory.selectFrom(question1)
                .where(question1.interview.id.eq(interviewId))
                .fetch();
        return question;
    }
}
