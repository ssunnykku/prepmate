package com.prepmate.backend.repository;


import com.prepmate.backend.domain.Question;
import com.prepmate.backend.dto.QuestionDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewQuestionRepository {
    List<Question> findByInterviewId(Long interviewId);
}
