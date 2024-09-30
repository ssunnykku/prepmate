package com.prepmate.backend.controller;

import com.prepmate.backend.dto.PagenationDTO;
import com.prepmate.backend.dto.QuestionDTO;
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
    public ResponseEntity<PagenationDTO<QuestionDTO>> getQuestionList(@PathVariable Long interviewId, @RequestParam(defaultValue = "1") Integer page) {
        PagenationDTO<QuestionDTO> questions = interviewQuestionService.getQuestionList(interviewId, page);
        return ResponseEntity.status(HttpStatus.OK).body(questions);
    }

}
