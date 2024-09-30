package com.prepmate.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prepmate.backend.domain.Interview;
import com.prepmate.backend.domain.User;
import com.prepmate.backend.dto.*;
import com.prepmate.backend.service.InterviewService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
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
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Slf4j
class InterviewControllerTest {

    @Mock
    InterviewService interviewService;
    @InjectMocks
    InterviewController interviewController;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void initMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(interviewController)
                .build();
    }

    @Test
    @Transactional
    void addInterview() throws Exception {
        UUID userId = UUID.fromString("aa815892-d059-4efe-81b8-58dd20a34a96");
        InterviewRequest interview = InterviewRequest.builder()
                .interviewName("백엔드 개발자 면접 준비")
                .description("java 개발자 준비")
                .userId(userId)
                .build();

        this.mockMvc.perform(post("/interviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(interview))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }

    @Test
    @Transactional
    void setInterview() throws Exception {
        UUID userId = UUID.fromString("aa815892-d059-4efe-81b8-58dd20a34a96");
        Long interviewId = 1L;

        InterviewRequest interview = InterviewRequest.builder()
                .interviewName("백엔드 개발자 면접 준비")
                .description("java 개발자 준비")
                .userId(userId)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/interviews/{interviewId}", interviewId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(interview))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

        BDDMockito.verify(interviewService).setInterview(interviewId, interview);

    }

    @Test
    @Transactional
    void removeInterview() throws Exception {
        Long interviewId = 1L;

        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/interviews/{interviewId}", interviewId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

        //then
        BDDMockito.verify(interviewService).removeInterview(interviewId);
    }

    @Test
    void getQuestionList() throws Exception {
        Long interviewId1 = 1L;
        Long interviewId2 = 2L;

        int page = 0;
        int size = 10;

        Interview req1 = Interview.builder()
                .id(interviewId1)
                .interviewName("백엔드 개발자 면접 준비")
                .description("java 개발자 준비")
                .user(User.builder().userId(UUID.fromString("28eadf23-dc55-49d7-8398-d5e215f177fd")).build())
                .build();

        Interview req2 = Interview.builder()
                .id(interviewId2)
                .interviewName("영어 공부")
                .description("영어 공부")
                .user(User.builder().userId(UUID.fromString("28eadf23-dc55-49d7-8398-d5e215f177fd")).build())
                .build();

        List<Interview> requestList = Arrays.asList(req1, req2);


        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<InterviewDTO> pageResult = new PageImpl<Interview>(requestList, pageable, requestList.size())
                .map(interview -> new InterviewDTO(
                        interview.getId(),
                        interview.getInterviewName(),
                        interview.getDescription(),
                        interview.getCreatedAt(),
                        interview.getUser().getUserId()
                ));

        //stub
        BDDMockito.given(interviewService.getInterviewList(page)).willReturn(new PagenationDTO(pageResult));

        mockMvc.perform(MockMvcRequestBuilders.get("/interviews?page=" + page)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPage").value(1))
                .andDo(print());

        BDDMockito.verify(interviewService).getInterviewList(page);

    }
}