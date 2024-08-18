package com.prepmate.backend.repository;

import com.prepmate.backend.domain.Interview;
import com.prepmate.backend.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InterviewRepositoryTest {
    @Autowired
    private InterviewRepository interviewRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void addInterviewTest() {
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

        assertThat(interviewRepository.findById(interview.getId())).isPresent();
    }

    @Test
    @Transactional
    void editInterviewTest() {
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

        interview.update("CS 지식 면접 연습", "백엔드 개발자 면접 연습(CS지식)");

        Optional<Interview> editData = interviewRepository.findById(interview.getId());

        editData.ifPresent((data)->{
            assertThat(data.getInterviewName()).isEqualTo("CS 지식 면접 연습");
            assertThat(data.getDescription()).isEqualTo("백엔드 개발자 면접 연습(CS지식)");
        });

    }

    @Test
    @Transactional
    void removeInterviewTest() {
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

        interviewRepository.deleteById(interview.getId());

        assertThat(interviewRepository.findById(interview.getId())).isEmpty();
        assertThat(interviewRepository.findAll()).isEmpty();
    }

    @Test
    @Transactional
    void getInterviewListTest() {
        //    - 전체 리스트 불러오기 (제목, description, 날짜)
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
        interviewRepository.save(interview1);

        Interview interview2 =
                Interview.builder()
                        .interviewName("프론트엔드 개발자 면접 연습1")
                        .description("프론트엔드 개발자 면접 연습(javaScript)")
                        .user(user)
                        .build();
        interviewRepository.save(interview2);

        List<Interview> list = interviewRepository.findAll();
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0).getInterviewName()).isEqualTo(interview1.getInterviewName());
        assertThat(list.get(0).getDescription()).isEqualTo(interview1.getDescription());
        assertThat(list.get(1).getDescription()).isEqualTo(interview2.getDescription());
        assertThat(list.get(1).getInterviewName()).isEqualTo(interview2.getInterviewName());
    }
}