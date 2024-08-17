package com.prepmate.backend.service;

import com.prepmate.backend.domain.Interview;
import com.prepmate.backend.domain.Question;
import com.prepmate.backend.dto.QuestionDTO;
import com.prepmate.backend.dto.QuestionReqDTO;
import com.prepmate.backend.repository.InterviewRepository;
import com.prepmate.backend.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final InterviewRepository interviewRepository;

    public void addQuestion(QuestionReqDTO questionReqDTO) {
        Interview interview = interviewRepository.findById(questionReqDTO.getInterviewId())
                .orElseThrow(() -> new EntityNotFoundException("Interview not found with id: " + questionReqDTO.getInterviewId()));

        questionRepository.save(Question.builder()
                .question(questionReqDTO.getQuestion())
                .answer(questionReqDTO.getAnswer())
                .interview(interview)
                .build());
    }
    public QuestionDTO getQuestion(Long questionId) {

        Question data = questionRepository.findById(questionId)
               .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + questionId));

        QuestionDTO question = QuestionDTO.builder()
                   .id(data.getId())
                   .question(data.getQuestion())
                   .answer(data.getAnswer())
                   .createdAt(data.getCreatedAt())
                   .build();


      return question;
    }

    public void setQuestion(Long questionId, QuestionReqDTO questionReqDTO) {
        Question data = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + questionId));

        data.update(questionReqDTO.getQuestion(),questionReqDTO.getAnswer());

        questionRepository.save(data);
    }

    public List<QuestionDTO> getQuestionList() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionDTO> result = new ArrayList<>();

        for (Question question: questions) {
            result.add(QuestionDTO.builder()
                            .id(question.getId())
                            .question(question.getQuestion())
                            .answer(question.getAnswer())
                            .createdAt(question.getCreatedAt())
                            .interviewId(question.getInterview().getId())
                    .build());
        }
        return result;
    }

    public void removeQuestion(Long questionId) {
        questionRepository.deleteById(questionId);
    }

}
