package com.prepmate.backend.repository;


import com.prepmate.backend.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewQuestionRepository {
    List<Question> findByInterviewId(Long interviewId);

    Page<Question> findByInterviewId(Long interviewId, Pageable pageable);

}
