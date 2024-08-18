package com.prepmate.backend.service;

import com.prepmate.backend.domain.Interview;
import com.prepmate.backend.domain.Question;
import com.prepmate.backend.domain.User;
import com.prepmate.backend.dto.QuestionDTO;
import com.prepmate.backend.dto.QuestionReqDTO;
import com.prepmate.backend.repository.InterviewRepository;
import com.prepmate.backend.repository.QuestionRepository;
import com.prepmate.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
class QuestionServiceTest {
    @Autowired
    private QuestionService questionService;

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

    private Question question() {
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

        return question;
    }

    @Test
    @Transactional
    @DisplayName("질문-답변 등록")
    void addQuestionTest() {
        // given
        Question question = question();
        QuestionReqDTO questionReqDTO = QuestionReqDTO.builder()
                .question(question.getQuestion())
                .answer(question.getAnswer())
                .interviewId(question.getInterview().getId())
                .build();
        // when
        questionService.addQuestion(questionReqDTO);

        // then
        List<Question> list = questionRepository.findAll();

        assertThat(list.size()).isEqualTo(1);

        assertThat(list.get(0).getQuestion()).isEqualTo(question.getQuestion());

    }

    @Test
    @Transactional
    @DisplayName("interviewId로 Interview 찾을 수 없을 때")
    void addQuestion_exceptionTest() {

        // given
        QuestionReqDTO question = QuestionReqDTO.builder()
                .question("spring이란?")
                .answer("java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크")
                .interviewId(1L)
                .build();

        // then
        assertThatThrownBy(()->questionService.addQuestion(question))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Interview not found with id: " + question.getInterviewId());
    }

    @Test
    @Transactional
    @DisplayName("질문+답변 하나 가져오기")
    void getQuestionTest() {
        // given
        Question question = question();
        questionRepository.save(question);

        // when
        QuestionDTO data = questionService.getQuestion(question.getId());

        // then
        assertThat(data.getQuestion()).isEqualTo(question.getQuestion());
        assertThat(data.getAnswer()).isEqualTo(question.getAnswer());
    }

    @Test
    @Transactional
    void getQuestion_exceptionTest() {
        assertThatThrownBy(() -> questionService.getQuestion(1L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @Transactional
    @DisplayName("질문+답변 수정")
    void editQuestionTest() {
        // given
        Question question = question();
        questionRepository.save(question);

        QuestionReqDTO editData = QuestionReqDTO.builder()
                .question("spring boot란?")
                .answer("java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크, 엄청 편합니다. tomcat을 내장")
                .build();
        // when
        questionService.setQuestion(question.getId(), editData);
        // then
        Optional<Question> getData = questionRepository.findById(question.getId());

        getData.ifPresent((data)->{
            assertThat(data.getQuestion()).contains("boot");
            assertThat(data.getAnswer()).contains("tomcat");
        });
    }

    @Test
    @Transactional
    @DisplayName("질문-답변 리스트")
    void getQuestionList() {
        // given
        for (int i = 0; i < 10; i++) {
            Question question = question();
            questionRepository.save(question);
        }

        // when
        List<QuestionDTO> list = questionService.getQuestionList();

        // then
        assertThat(list.size()).isEqualTo(10);
    }

    @Test
    @Transactional
    @DisplayName("질문+답변 삭제")
    void removeTest() {
        // given
        Question question = question();
        questionRepository.save(question);
        // when
        questionService.removeQuestion(question.getId());
        // then
        log.info("삭제 결과 {}: " + questionRepository.findById(question.getId()));
        assertThat(questionRepository.findById(question.getId())).isEmpty();

    }
}