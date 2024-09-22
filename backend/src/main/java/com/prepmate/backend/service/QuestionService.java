package com.prepmate.backend.service;

import com.prepmate.backend.domain.Interview;
import com.prepmate.backend.domain.Question;
import com.prepmate.backend.dto.QuestionRequest;
import com.prepmate.backend.repository.InterviewQuestionRepository;
import com.prepmate.backend.repository.InterviewRepository;
import com.prepmate.backend.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final InterviewRepository interviewRepository;
    private final InterviewQuestionRepository interviewQuestionRepository;

    /**
     * 문제 추가
     *
     * @param questionRequest 문제 추가시 입력 내용(question, answer, interviewId)
     */
    public void addQuestion(QuestionRequest questionRequest) {
        Interview interview = interviewRepository.findById(questionRequest.getInterviewId())
                .orElseThrow(() -> new EntityNotFoundException("Interview not found with id: " + questionRequest.getInterviewId()));

        questionRepository.save(Question.builder()
                .question(questionRequest.getQuestion())
                .answer(questionRequest.getAnswer())
                .interview(interview)
                .build());
    }

    /**
     * 문제 조회
     *
     * @param questionId
     * @return QuestionDTO
     */
    public Question getQuestion(Long questionId) {
        Question data = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + questionId));
        return data;
    }

    /**
     * 문제 수정
     *
     * @param questionId
     * @param questionReqDTO 문제 수정시 입력 내용(question, answer)
     */
    public void setQuestion(Long questionId, QuestionRequest questionReqDTO) {
        Question data = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + questionId));

        data.update(questionReqDTO.getQuestion(), questionReqDTO.getAnswer());

        questionRepository.save(data);
    }

    /**
     * 문제 리스트 조회
     *
     * @return QuestionDTO 리스트 반환
     */
    public List<Question> getQuestionList() {
        return questionRepository.findAll();
    }

    /**
     * 문제 삭제
     *
     * @param questionId
     */
    public void removeQuestion(Long questionId) {
        questionRepository.deleteById(questionId);
    }

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
