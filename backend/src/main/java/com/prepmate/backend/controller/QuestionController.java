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

    @PostMapping
    public ResponseEntity<String> addQuestion(@Valid @RequestBody QuestionReqDTO questionReqDTO) {
        questionService.addQuestion(questionReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDTO> getQuestion(@PathVariable Long questionId) {
        return ResponseEntity.status(HttpStatus.OK).body(questionService.getQuestion(questionId));
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<String> setQuestion(@PathVariable Long questionId, @Valid @RequestBody QuestionReqDTO questionReqDTO) {
        questionService.setQuestion(questionId,questionReqDTO);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getQuestionList() {
        return ResponseEntity.status(HttpStatus.OK).body(questionService.getQuestionList());
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<String> removeQuestion(@PathVariable Long questionId) {
        questionService.removeQuestion(questionId);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }


}
