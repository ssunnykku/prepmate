package com.prepmate.backend.service;

import com.prepmate.backend.domain.Interview;
import com.prepmate.backend.domain.Question;
import com.prepmate.backend.domain.User;
import com.prepmate.backend.repository.InterviewQuestionRepository;
import com.prepmate.backend.repository.InterviewRepository;
import com.prepmate.backend.repository.QuestionRepository;
import com.prepmate.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
class InterviewQuestionServiceTest {
    @Autowired
    private InterviewQuestionRepository interviewQuestionRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    InterviewRepository interviewRepository;
    private User user;

    @BeforeEach
    void beforeEach() {
        User user1 = User.builder()
                .email("sun@gmail.com")
                .name("김선희")
                .password("1234").build();
        userRepository.save(user1);

        user = user1;
    }

    @AfterEach
    void afterEach(){
        questionRepository.deleteAll();
        interviewRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("질문-답변 리스트")
    void getQuestionList() {
        // given
        Interview interview =
                Interview.builder()
                        .interviewName("백엔드 개발자 면접 연습1")
                        .description("백엔드 개발자 면접 연습(java)")
                        .user(user)
                        .build();
        interviewRepository.save(interview);

        for (int i = 0; i < 10; i++) {
            Question question = Question.builder()
                    .question("spring이란?")
                    .answer("java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크")
                    .interview(interview)
                    .build();
            questionRepository.save(question);
        }

        // when
        List<Question> list = interviewQuestionRepository.findByInterviewId(interview.getId());
        for(Question q: list) {
            assertThat(q.getQuestion()).isEqualTo("spring이란?");
            assertThat(q.getAnswer()).isEqualTo("java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크");
            assertThat(q.getCreatedAt()).isNotNull();
            assertThat(q.getInterview()).isEqualTo(interview);
        }
        // then
        assertThat(list.size()).isEqualTo(10);
    }

}