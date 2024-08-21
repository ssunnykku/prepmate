package com.prepmate.backend.controller;

import com.prepmate.backend.dto.QuestionDTO;
import com.prepmate.backend.dto.QuestionReqDTO;
import com.prepmate.backend.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    public ResponseEntity<String> addQuestion(@Valid @RequestBody QuestionReqDTO questionReqDTO) {
        questionService.addQuestion(questionReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    /**
     * 문제 조회
     * @param questionId
     * @return QuestionDTO
     */
    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDTO> getQuestion(@PathVariable Long questionId) {
        return ResponseEntity.status(HttpStatus.OK).body(questionService.getQuestion(questionId));
    }

    /**
     * 문제 수정
     * @param questionId
     * @param questionReqDTO
     * @return 요청 결과 반환 (성공시 "success")
     */
    @PutMapping("/{questionId}")
    public ResponseEntity<String> setQuestion(@PathVariable Long questionId, @Valid @RequestBody QuestionReqDTO questionReqDTO) {
        questionService.setQuestion(questionId,questionReqDTO);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    /**
     * 문제 리스트 전체 조회 -> 특정 인터뷰에 대한 리스트 조회로 수정 필요
     * @return QuestionDTO 리스트 반환
     */
    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getQuestionList() {
        return ResponseEntity.status(HttpStatus.OK).body(questionService.getQuestionList());
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
