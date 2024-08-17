package com.prepmate.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prepmate.backend.domain.Interview;
import com.prepmate.backend.domain.Question;
import com.prepmate.backend.domain.User;
import com.prepmate.backend.dto.QuestionDTO;
import com.prepmate.backend.dto.QuestionReqDTO;
import com.prepmate.backend.service.QuestionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class QuestionControllerTest {
    @Mock
    QuestionService questionService;
    @InjectMocks
    QuestionController questionController;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void initMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(questionController)
                .build();
    }

    @Test
    void addQuestion() throws Exception {
        Long interviewId = 1L;
        QuestionReqDTO question = QuestionReqDTO.builder()
                .question("spring이란?")
                .answer("java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크")
                .interviewId(interviewId)
                .build();

        this.mockMvc.perform(post("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(question))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }

    @Test
    void addQuestion_exceptionTest() throws Exception {
        Long interviewId = 1L;
        QuestionReqDTO question = QuestionReqDTO.builder()
                .question("")
                .answer("java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크")
                .interviewId(interviewId)
                .build();

        //when
        this.mockMvc.perform(post("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(question))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());

    }

    @Test
    void getQuestion() throws Exception {
        //given
        Long interviewId = 1L;
        Long questionId = 1L;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        QuestionDTO question = QuestionDTO.builder()
                .id(questionId)
                .question("spring이란?")
                .answer("java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크")
                .interviewId(interviewId)
                .createdAt(LocalDateTime.parse("2024-08-15 13:47:13.248",formatter))
                .build();

        //stub
        BDDMockito.given(questionService.getQuestion(questionId)).willReturn(question);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/questions/{questionId}", questionId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.question").value(question.getQuestion()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.answer").value(question.getAnswer()))
                .andDo(print());

        //then
        BDDMockito.verify(questionService).getQuestion(questionId);

    }

    @Test
    void setQuestion() throws Exception{
        //given
        Long interviewId = 1L;
        Long questionId = 1L;

        QuestionReqDTO question = QuestionReqDTO.builder()
                .question("spring이란?")
                .answer("java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크")
                .interviewId(interviewId)
                .build();

        QuestionReqDTO editQuestion = QuestionReqDTO.builder()
                .question("spring boot란?")
                .answer("java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크, tomcat 내장")
                .interviewId(interviewId)
                .build();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        QuestionDTO response = QuestionDTO.builder()
                .id(questionId)
                .question("spring boot란?")
                .answer("java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크, tomcat 내장")
                .interviewId(interviewId)
                .build();

        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/questions/{questionId}", questionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(response))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

    }

    @Test
    void getQuestionList() throws Exception {
        //given
        Long interviewId = 1L;
        Long questionId1 = 1L;
        Long questionId2 = 2L;

        List<QuestionDTO> responseList = new ArrayList<>();

        QuestionDTO response1 = QuestionDTO.builder()
                .id(questionId1)
                .question("spring boot란?")
                .answer("java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크, tomcat 내장")
                .interviewId(interviewId)
                .build();

        QuestionDTO response2 = QuestionDTO.builder()
                .id(questionId2)
                .question("spring이란?")
                .answer("java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크")
                .interviewId(interviewId)
                .build();

        responseList.add(response1);
        responseList.add(response2);

        //stub
        BDDMockito.given(questionService.getQuestionList()).willReturn(responseList);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(responseList))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(responseList.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].question").value(response1.getQuestion()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].question").value(response2.getQuestion()))
                .andDo(print());

        //then
        BDDMockito.verify(questionService).getQuestionList();

    }

    @Test
    void removeQuestion() throws Exception {
        Long interviewId = 1L;
        Long questionId = 1L;

        QuestionDTO data = QuestionDTO.builder()
                .id(questionId)
                .question("spring boot란?")
                .answer("java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크, tomcat 내장")
                .interviewId(interviewId)
                .build();

        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/questions/{questionId}", questionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //then
        BDDMockito.verify(questionService).removeQuestion(questionId);
    }
}