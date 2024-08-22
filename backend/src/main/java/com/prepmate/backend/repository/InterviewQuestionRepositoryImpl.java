package com.prepmate.backend.repository;

import static com.prepmate.backend.domain.QQuestion.question1;

import com.prepmate.backend.domain.Question;
import com.prepmate.backend.dto.QuestionDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InterviewQuestionRepositoryImpl implements InterviewQuestionRepository {
    public InterviewQuestionRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<QuestionDTO> findByInterviewId(Long interviewId) {

        List<Question> question = jpaQueryFactory.selectFrom(question1)
                .where(question1.interview.id.eq(interviewId))
                .fetch();

        List<QuestionDTO> questionList = new ArrayList<>();

        for (Question q: question) {
            questionList.add(QuestionDTO.builder()
                            .id(q.getId())
                            .question(q.getQuestion())
                            .answer(q.getAnswer())
                            .createdAt(q.getCreatedAt())
                            .interviewId(q.getInterview().getId())
                    .build());
        }

        return questionList;
    }
}
