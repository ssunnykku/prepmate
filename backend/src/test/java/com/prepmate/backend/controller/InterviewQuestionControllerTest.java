package com.prepmate.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prepmate.backend.domain.Interview;
import com.prepmate.backend.domain.Question;
import com.prepmate.backend.service.InterviewQuestionService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class InterviewQuestionControllerTest {
    @Mock
    InterviewQuestionService interviewQuestionService;
    @InjectMocks
    InterviewQuestionController interviewQuestionController;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void initMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(interviewQuestionController)
                .build();
    }

    @Test
    void getQuestionList() throws Exception {
        //given
        Long interviewId = 1L;
        Long questionId1 = 1L;
        Long questionId2 = 2L;

        List<Question> responseList = new ArrayList<>();

        Question response1 = Question.builder()
                .id(questionId1)
                .question("spring boot란?")
                .answer("java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크, tomcat 내장")
                .interview(Interview.builder().id(interviewId).build())
                .build();

        Question response2 = Question.builder()
                .id(questionId2)
                .question("spring이란?")
                .answer("java Application 환경 제공, java bean 개발 환경 제공, bean 간의 관계를 정의하며 DI를 제공하는 프레임워크")
                .interview(Interview.builder().id(interviewId).build())
                .build();

        responseList.add(response1);
        responseList.add(response2);

        //stub
        BDDMockito.given(interviewQuestionService.getQuestionList(interviewId)).willReturn(responseList);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/interviews/" + interviewId + "/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(responseList))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(responseList.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].question").value(response1.getQuestion()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].question").value(response2.getQuestion()))
                .andDo(print());

        //then
        BDDMockito.verify(interviewQuestionService).getQuestionList(interviewId);

    }


}