package com.prepmate.backend.controller;

import com.prepmate.backend.domain.Interview;
import com.prepmate.backend.dto.InterviewResponse;
import com.prepmate.backend.dto.InterviewRequest;
import com.prepmate.backend.service.InterviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interviews")
@Slf4j
public class InterviewController {
    private final InterviewService interviewService;

    /**
     * 인터뷰 추가
     *
     * @param interviewReqDTO 인터뷰 추가시 입력 정보 (interviewName, description, userId)
     * @return 요청 결과 반환 (성공시 "success")
     */
    @PostMapping
    public ResponseEntity<String> addInterview(@Valid @RequestBody InterviewRequest interviewReqDTO) {
        interviewService.addInterview(interviewReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    /**
     * 인터뷰 수정
     *
     * @param interviewId
     * @param interviewReqDTO
     * @return 요청 결과 반환 (성공시 "success")
     */
    @PutMapping("/{interviewId}")
    public ResponseEntity<String> setInterview(@PathVariable Long interviewId, @Valid @RequestBody InterviewRequest interviewReqDTO) {
        interviewService.setInterview(interviewId, interviewReqDTO);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    /**
     * 인터뷰 삭제
     *
     * @param interviewId
     * @return 요청 결과 반환 (성공시 "success")
     */
    @DeleteMapping("/{interviewId}")
    public ResponseEntity<String> removeInterview(@PathVariable Long interviewId) {
        interviewService.removeInterview(interviewId);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    /**
     * 인터뷰 리스트 반환
     *
     * @return InterviewDTO 리스트 반환
     */
    @GetMapping
    public ResponseEntity<List<Interview>> getInterviewList(@RequestParam(defaultValue = "1") Integer page) {

//        Page<Interview> getInterviews = interviewService.getInterviewList(page);
        List<Interview> getInterviews = interviewService.getInterviewList(page);

        return ResponseEntity.status(HttpStatus.OK).body(getInterviews);
    }

}
