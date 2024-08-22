package com.prepmate.backend.repository;

import com.prepmate.backend.domain.Interview;
import com.prepmate.backend.domain.Question;
import com.prepmate.backend.domain.User;
import com.prepmate.backend.dto.QuestionDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
class InterviewQuestionRepositoryTest {
    @Autowired
    private InterviewQuestionRepository interviewQuestionRepository;
    @Autowired
    private InterviewRepository interviewRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void findByInterviewId() {
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

        Question question1 = Question.builder()
                .question("spring이란?")
                .answer("java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크")
                .interview(interview)
                .build();

        Question question2 = Question.builder()
                .question("spring boot란?")
                .answer("java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크, tomcat 내장")
                .interview(interview)
                .build();
        questionRepository.save(question1);
        questionRepository.save(question2);

       List<QuestionDTO> data = interviewQuestionRepository.findByInterviewId(interview.getId());
        assertThat(data.get(0).getInterviewId()).isEqualTo(interview.getId());
        assertThat(data.get(0).getQuestion()).isEqualTo(question1.getQuestion());
        assertThat(data.get(1).getQuestion()).isEqualTo(question2.getQuestion());
        assertThat(data.get(0).getAnswer()).isEqualTo(question1.getAnswer());
        assertThat(data.get(1).getAnswer()).isEqualTo(question2.getAnswer());
        assertThat(data.size()).isEqualTo(2);
    }
}