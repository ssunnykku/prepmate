package com.prepmate.backend.controller;

import com.prepmate.backend.domain.Question;

import com.prepmate.backend.dto.QuestionResponse;
import com.prepmate.backend.service.InterviewQuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interviews")
@Slf4j
public class InterviewQuestionController {
    private final InterviewQuestionService interviewQuestionService;

    /**
     * 특정 인터뷰에 대한 리스트 조회
     *
     * @return QuestionResponse 리스트 반환
     */
    @GetMapping("/{interviewId}/questions")
    public ResponseEntity<List<QuestionResponse>> getQuestionList(@PathVariable Long interviewId) {
        List<Question> questions = interviewQuestionService.getQuestionList(interviewId);
        List<QuestionResponse> result = new ArrayList<>();

        for (Question question : questions) {
            result.add(QuestionResponse.builder()
                    .id(question.getId())
                    .question(question.getQuestion())
                    .answer(question.getAnswer())
                    .createdAt(question.getCreatedAt())
                    .interviewId(question.getInterview().getId())
                    .build());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
