package com.prepmate.backend.controller;

import com.prepmate.backend.dto.InterviewDTO;
import com.prepmate.backend.dto.InterviewReqDTO;
import com.prepmate.backend.service.InterviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interviews")
@Slf4j
public class InterviewController {

    private final InterviewService interviewService;

    @PostMapping
    public ResponseEntity<String> addInterview(@Valid @RequestBody InterviewReqDTO interviewReqDTO) {
        interviewService.addInterview(interviewReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    @PutMapping("/{interviewId}")
    public ResponseEntity<String> setInterview(@PathVariable Long interviewId, @Valid @RequestBody InterviewReqDTO interviewReqDTO) {
        interviewService.setInterview(interviewId,interviewReqDTO);
        return ResponseEntity.status(HttpStatus.OK).body("success");    }

    @DeleteMapping("/{interviewId}")
    public ResponseEntity<String> removeInterview(@PathVariable Long interviewId) {
        interviewService.removeInterview(interviewId);
        return ResponseEntity.status(HttpStatus.OK).body("success");    }

    @GetMapping
    public ResponseEntity<List<InterviewDTO>> getInterviewList() {
        return ResponseEntity.status(HttpStatus.OK).body(interviewService.getInterviewList());
    }

    }
