package com.seb39.mystackoverflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seb39.mystackoverflow.auth.PrincipalDetails;
import com.seb39.mystackoverflow.auth.PrincipalDetailsService;
import com.seb39.mystackoverflow.dto.AnswerDto;
import com.seb39.mystackoverflow.entity.Answer;
import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.mapper.AnswerMapper;
import com.seb39.mystackoverflow.repository.MemberRepository;
import com.seb39.mystackoverflow.service.AnswerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc(addFilters = false)
@WithUserDetails(value = "abcd@naver.com", userDetailsServiceBeanName = "principalDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnswerService answerService;

    @MockBean
    private AnswerMapper answerMapper;

    @MockBean
    private PrincipalDetails principal;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void beforeEach(){
        Member member = Member.builder()
                .email("abcd@naver.com")
                .roles("ROLE_USER")
                .build();
        memberRepository.save(member);
    }


    @Test
    void postAnswerTest() throws Exception {
        // given
        AnswerDto.Post requestDto = new AnswerDto.Post();
        requestDto.setContent("Create Answer");

        BDDMockito.given(answerMapper.answerPostToAnswer(any()))
                .willReturn(new Answer());

        BDDMockito.given(answerService.createAnswer(any(), any(), any()))
                .willReturn(1L);

        String requestJson = objectMapper.writeValueAsString(requestDto);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/api/answers")
                        .param("question-id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    void patchAnswerTest() throws Exception {
        // given
        AnswerDto.Patch requestDto = new AnswerDto.Patch();
        requestDto.setContent("updated Answer");

        BDDMockito.given(answerMapper.answerPatchToAnswer(any(),any()))
                .willReturn(new Answer());

        BDDMockito.doNothing().when(answerService).updateAnswer(any(),any());

        String requestJson = objectMapper.writeValueAsString(requestDto);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/answers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    void deleteAnswerTest() throws Exception {
        // given
        BDDMockito.doNothing().when(answerService).deleteAnswer(any(),any());

        // expected
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/answers/1"))
                .andExpect(status().isOk());
    }
}