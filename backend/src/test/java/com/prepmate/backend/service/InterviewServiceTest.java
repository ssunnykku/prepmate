package com.prepmate.backend.service;

import com.prepmate.backend.domain.Interview;
import com.prepmate.backend.domain.User;
import com.prepmate.backend.dto.InterviewRequest;
import com.prepmate.backend.dto.InterviewsDTO;
import com.prepmate.backend.repository.InterviewRepository;
import com.prepmate.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class InterviewServiceTest {

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InterviewService interviewService;

    @Test
    @Transactional
    void addInterview() {
        // given
        User user = User.builder()
                .email("sun@gmail.com")
                .name("김선희")
                .password("1234").build();
        userRepository.save(user);

        InterviewRequest interview =
                InterviewRequest.builder()
                        .interviewName("백엔드 개발자 면접 연습1")
                        .description("백엔드 개발자 면접 연습(java)")
                        .userId(user.getUserId())
                        .build();
        // when
        interviewService.addInterview(interview);
        // then
        List<Interview> interviewList = interviewRepository.findAll();
        assertThat(interviewList.get(0).getInterviewName()).isEqualTo(interview.getInterviewName());
        assertThat(interviewList.get(0).getDescription()).isEqualTo(interview.getDescription());
    }

    @Test
    @Transactional
    void setInterview() {
        // given
        User user = User.builder()
                .email("sun@gmail.com")
                .name("김선희")
                .password("1234").build();
        userRepository.save(user);

        Interview interview =
                Interview.builder()
                        .interviewName("백엔드 개발자 면접 연습1")
                        .description("백엔드 개발자 면접 연습(java)")
                        .user(user)
                        .build();
        interviewRepository.save(interview);

        InterviewRequest editInterview =
                InterviewRequest.builder()
                        .interviewName("프론트엔드 개발자 면접 연습")
                        .description("프론트엔드 개발자 면접 연습(javaScript)")
                        .build();

        // when
        interviewService.setInterview(interview.getId(), editInterview);

        // then
        interviewRepository.findById(interview.getId()).ifPresent((data) -> {
            assertThat(data.getInterviewName()).isEqualTo(editInterview.getInterviewName());
            assertThat(data.getDescription()).isEqualTo(editInterview.getDescription());

        });

    }

    @Test
    @Transactional
    void removeInterview() {
        // given
        User user = User.builder()
                .email("sun@gmail.com")
                .name("김선희")
                .password("1234").build();
        userRepository.save(user);

        Interview interview =
                Interview.builder()
                        .interviewName("백엔드 개발자 면접 연습1")
                        .description("백엔드 개발자 면접 연습(java)")
                        .user(user)
                        .build();
        interviewRepository.save(interview);

        // when
        interviewService.removeInterview(interview.getId());

        // then
        assertThat(interviewRepository.findAll()).isEmpty();
    }

    @Test
    @Transactional
    void getInterviewList() {
        int page = 0;
        int size = 10;

        // given
        User user = User.builder()
                .email("sun@gmail.com")
                .name("김선희")
                .password("1234").build();
        userRepository.save(user);

        Interview interview1 =
                Interview.builder()
                        .interviewName("백엔드 개발자 면접 연습1")
                        .description("백엔드 개발자 면접 연습(java)")
                        .user(user)
                        .build();
        Interview interview2 =
                Interview.builder()
                        .interviewName("백엔드 개발자 면접 연습2")
                        .description("백엔드 개발자 면접 연습(java)")
                        .user(user)
                        .build();
        interviewRepository.save(interview1);
        interviewRepository.save(interview2);

        // when
        InterviewsDTO interviewList = interviewService.getInterviewList(page);

        // then
        assertThat(interviewList.getTotalElements()).isEqualTo(2);

    }
}