package com.prepmate.backend.controller;

import com.prepmate.backend.domain.Question;
import com.prepmate.backend.dto.QuestionResponse;
import com.prepmate.backend.dto.QuestionRequest;
import com.prepmate.backend.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
@Slf4j
public class QuestionController {
    private final QuestionService questionService;

    /**
     * 문제 추가
     * @param questionReqDTO 문제 추가시 입력 내용(question, answer)
     * @return 요청 결과 반환 (성공시 "success")
     */
    @PostMapping
    public ResponseEntity<String> addQuestion(@Valid @RequestBody QuestionRequest questionReqDTO) {
        questionService.addQuestion(questionReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    /**
     * 문제 조회
     * @param questionId
     * @return QuestionDTO
     */
    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionResponse> getQuestion(@PathVariable Long questionId) {
        Question data = questionService.getQuestion(questionId);

        QuestionResponse question = QuestionResponse.builder()
                .id(data.getId())
                .question(data.getQuestion())
                .answer(data.getAnswer())
                .createdAt(data.getCreatedAt())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(question);
    }

    /**
     * 문제 수정
     * @param questionId
     * @param questionReqDTO
     * @return 요청 결과 반환 (성공시 "success")
     */
    @PutMapping("/{questionId}")
    public ResponseEntity<String> setQuestion(@PathVariable Long questionId, @Valid @RequestBody QuestionRequest questionReqDTO) {
        questionService.setQuestion(questionId,questionReqDTO);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    /**
     * 문제 리스트 전체 조회 -> 특정 인터뷰에 대한 리스트 조회로 수정 필요
     * @return QuestionDTO 리스트 반환
     */
    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getQuestionList() {
        List<Question> getQuestions = questionService.getQuestionList();
        List<QuestionResponse> result = new ArrayList<>();

        for (Question question: getQuestions) {
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

    /**
     * 문제 삭제
     * @param questionId
     * @return 요청 결과 반환 (성공시 "success")
     */
    @DeleteMapping("/{questionId}")
    public ResponseEntity<String> removeQuestion(@PathVariable Long questionId) {
        questionService.removeQuestion(questionId);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

}
