package com.prepmate.backend.service;

import com.prepmate.backend.domain.Interview;
import com.prepmate.backend.domain.Question;
import com.prepmate.backend.dto.QuestionDTO;
import com.prepmate.backend.repository.InterviewRepository;
import com.prepmate.backend.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final InterviewRepository interviewRepository;

    public void addQuestion(QuestionDTO questionDTO) {
        Optional<Interview> interview = interviewRepository.findById(questionDTO.getInterviewId());

        interview.ifPresentOrElse(data -> {
            questionRepository.save(Question.builder()
                    .question(questionDTO.getQuestion())
                    .answer(questionDTO.getAnswer())
                    .interview(data)
                    .build());
        }, () -> {
            throw new IllegalArgumentException("Interview not found");
        });

    }
    public QuestionDTO getQuestion(Long questionId) {

       Optional<Question> data = questionRepository.findById(questionId);
        data.orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + questionId));

        QuestionDTO question = null;

       if(data.isPresent()) {
           question = QuestionDTO.builder()
                   .id(data.get().getId())
                   .question(data.get().getQuestion())
                   .answer(data.get().getAnswer())
                   .createdAt(data.get().getCreatedAt())
                   .build();
       }

      return question;
    }

    public void setQuestion(QuestionDTO questionDTO) {
        Optional<Question> data = questionRepository.findById(questionDTO.getId());

        data.ifPresentOrElse((q)->{
            Question inquiry = data.get();
            inquiry.update(questionDTO.getQuestion(),questionDTO.getAnswer());

            questionRepository.save(inquiry);
        }, ()-> new EntityNotFoundException("Question not found with id: " + questionDTO.getId()));
    }

    public List<Question> getQuestionList() {
        return questionRepository.findAll();
    }

    public void removeQuestion(Long questionId) {
        questionRepository.deleteById(questionId);
    }

}
