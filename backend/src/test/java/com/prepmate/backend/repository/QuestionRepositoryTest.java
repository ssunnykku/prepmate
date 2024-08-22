package com.prepmate.backend.repository;

import com.prepmate.backend.domain.Interview;
import com.prepmate.backend.domain.Question;
import com.prepmate.backend.domain.User;
import com.prepmate.backend.dto.QuestionDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
class QuestionRepositoryTest {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    InterviewRepository interviewRepository;

    @Test
    @Transactional
    void enrollTest() {
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

        Question question = Question.builder()
                .question("spring이란?")
                .answer("java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크")
                .interview(interview)
                .build();
        questionRepository.save(question);

        Optional<Question> result = questionRepository.findById(question.getId());

        result.ifPresent(data -> {
            assertThat(data.getQuestion()).isEqualTo(question.getQuestion());
            assertThat(data.getAnswer()).isEqualTo(question.getAnswer());
            assertThat(data.getInterview()).isEqualTo(interview);
        });

    }

    @Test
    @Transactional
    void getQuestionTest() {
        Question question = Question.builder()
                .question("spring이란?")
                .answer("java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크")
                .build();
        questionRepository.save(question);

    }

    @Test
    @Transactional
    void editTest() {
        Question question = Question.builder()
                .question("spring이란?")
                .answer("java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크")
                .build();

        questionRepository.save(question);

        Optional<Question> result = questionRepository.findById(question.getId());

        result.ifPresent(data -> {
            data.update("spring boot란?","java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크, 엄청 편합니다. tomcat을 내장");

            assertThat(data.getQuestion()).contains("boot");
            assertThat(data.getAnswer()).contains("tomcat");
        });

    }

    @Test
    @Transactional
    void getQuestionList() {
        Question question1 = Question.builder()
                .question("spring이란?")
                .answer("java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크")
                .build();
        questionRepository.save(question1);

        Question question2 = Question.builder()
                .question("spring이란?")
                .answer("java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크")
                .build();
        questionRepository.save(question2);

        List<Question> QuestionList = questionRepository.findAll();

        assertThat(QuestionList.size()).isEqualTo(2);
        Optional<Question> data = questionRepository.findById(question1.getId());
        data.ifPresent(a -> {
            assertThat(a.getQuestion()).isEqualTo(question1.getQuestion());
            assertThat(a.getAnswer()).isEqualTo(question1.getAnswer());
        });
    }


    }