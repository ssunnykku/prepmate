package com.prepmate.backend.service;

import com.prepmate.backend.domain.Interview;
import com.prepmate.backend.domain.Question;
import com.prepmate.backend.domain.User;
import com.prepmate.backend.dto.InterviewDTO;
import com.prepmate.backend.dto.InterviewReqDTO;
import com.prepmate.backend.repository.InterviewRepository;
import com.prepmate.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewService {
    private final InterviewRepository interviewRepository;
    private final UserRepository userRepository;

    /**
     * 인터뷰 추가
     * @param interviewReqDTO 인터뷰 추가시 입력 정보 (interviewName, description, userId)
     */
    public void addInterview(InterviewReqDTO interviewReqDTO) {
        User user = userRepository.findByUserId(interviewReqDTO.getUserId());

        interviewRepository.save(Interview.builder()
                .interviewName(interviewReqDTO.getInterviewName())
                .description(interviewReqDTO.getDescription())
                .user(user)
                .build());
    }

    /**
     * 인터뷰 수정
     * @param interviewId
     * @param interviewReqDTO
     */
    public void setInterview(Long interviewId, InterviewReqDTO interviewReqDTO) {
        Interview data = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new EntityNotFoundException("Interview not found with id: " + interviewId));

        data.update(interviewReqDTO.getInterviewName(),interviewReqDTO.getDescription());

        interviewRepository.save(data);
    }

    /**
     * 인터뷰 삭제
     * @param interviewId
     */
    public void removeInterview(Long interviewId) {
        interviewRepository.deleteById(interviewId);
    }

    /**
     * 인터뷰 리스트
     * @return InterviewDTO 리스트 반환
     */
    public List<InterviewDTO> getInterviewList(){
        List<Interview> dataList = interviewRepository.findAll();
        List<InterviewDTO> result = new ArrayList<>();
        for(Interview interview: dataList) {
            result.add(InterviewDTO.builder()
                            .id(interview.getId())
                            .interviewName(interview.getInterviewName())
                            .description(interview.getDescription())
                            .createdAt(interview.getCreatedAt())
                    .build());
        }
        return result;
    }

}
