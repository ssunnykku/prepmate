package com.prepmate.backend.service;

import com.prepmate.backend.domain.Interview;
import com.prepmate.backend.domain.User;
import com.prepmate.backend.dto.InterviewRequest;
import com.prepmate.backend.repository.InterviewRepository;
import com.prepmate.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewService {
    private final InterviewRepository interviewRepository;
    private final UserRepository userRepository;

    /**
     * 인터뷰 추가
     *
     * @param interviewReqDTO 인터뷰 추가시 입력 정보 (interviewName, description, userId)
     */
    public void addInterview(InterviewRequest interviewReqDTO) {
        User user = userRepository.findByUserId(interviewReqDTO.getUserId());

        interviewRepository.save(Interview.builder()
                .interviewName(interviewReqDTO.getInterviewName())
                .description(interviewReqDTO.getDescription())
                .user(user)
                .build());
    }

    /**
     * 인터뷰 수정
     *
     * @param interviewId
     * @param interviewReqDTO
     */
    @Transactional
    public void setInterview(Long interviewId, InterviewRequest interviewReqDTO) {
        Interview data = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new EntityNotFoundException("Interview not found with id: " + interviewId));

        data.update(interviewReqDTO.getInterviewName(), interviewReqDTO.getDescription());

        interviewRepository.save(data);
    }

    /**
     * 인터뷰 삭제
     *
     * @param interviewId
     */
    public void removeInterview(Long interviewId) {
        interviewRepository.deleteById(interviewId);
    }

    /**
     * 인터뷰 리스트
     *
     * @return InterviewDTO 리스트 반환
     */
    public Page<Interview> getInterviewList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return interviewRepository.findAll(pageable);
    }

}
