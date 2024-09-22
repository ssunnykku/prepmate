package com.prepmate.backend.controller;

import com.prepmate.backend.domain.Question;
import com.prepmate.backend.dto.QuestionDTO;
import com.prepmate.backend.service.InterviewQuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
@Slf4j
public class InterviewQuestionController {
    private final InterviewQuestionService interviewQuestionService;
    /**
     * 문제 리스트 전체 조회 -> 특정 인터뷰에 대한 리스트 조회로 수정 필요
     * @return QuestionDTO 리스트 반환
     */
    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getQuestionList(@RequestParam Long interviewId) {
        List<Question> questions = interviewQuestionService.getQuestionList(interviewId);
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
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
