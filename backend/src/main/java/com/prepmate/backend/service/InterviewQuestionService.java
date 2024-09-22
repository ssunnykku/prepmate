package com.prepmate.backend.service;

import com.prepmate.backend.domain.Question;
import com.prepmate.backend.repository.InterviewQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewQuestionService {
    private final InterviewQuestionRepository interviewQuestionRepository;

    /**
     * 문제 리스트 조회
     *
     * @return QuestionDTO 리스트 반환
     */
    public List<Question> getQuestionList(Long interviewId) {
        List<Question> questions = interviewQuestionRepository.findByInterviewId(interviewId);

        return questions;
    }
}
