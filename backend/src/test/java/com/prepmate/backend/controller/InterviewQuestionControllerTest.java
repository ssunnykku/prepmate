package com.prepmate.backend.controller;

import com.prepmate.backend.domain.Interview;
import com.prepmate.backend.domain.Question;
import com.prepmate.backend.dto.PagenationDTO;
import com.prepmate.backend.dto.QuestionDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Slf4j
class InterviewQuestionControllerTest {
    @Mock
    InterviewQuestionService interviewQuestionService;
    @InjectMocks
    InterviewQuestionController interviewQuestionController;
    @Autowired
    private MockMvc mockMvc;

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

        int page = 0;
        int size = 10;


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

        List<Question> responseList = Arrays.asList(response1, response2);

        Pageable pageable = PageRequest.of(page, size);
        Page<QuestionDTO> pageResult = new PageImpl<Question>(responseList, pageable, responseList.size())
                .map(question ->
                        QuestionDTO.builder()
                                .questionId(question.getId())
                                .question(question.getQuestion())
                                .answer(question.getAnswer())
                                .build());


        log.info("pageResult.getTotalPages() {}", pageResult.getTotalPages());
        log.info("pageResult.getTotalElements() {}", pageResult.getTotalElements());

        //stub
        BDDMockito.given(interviewQuestionService.getQuestionList(interviewId, page)).willReturn(new PagenationDTO<>(pageResult));

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/interviews/" + interviewId + "/questions?page=" + page)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].question").value(response1.getQuestion()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].question").value(response2.getQuestion()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPage").value(1))
                .andDo(print());

        //then
        BDDMockito.verify(interviewQuestionService).getQuestionList(interviewId, page);
    }


}