package com.seb39.mystackoverflow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seb39.mystackoverflow.dto.CommentDto;
import com.seb39.mystackoverflow.entity.Comment;
import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.entity.PostType;
import com.seb39.mystackoverflow.mapper.CommentMapper;
import com.seb39.mystackoverflow.repository.MemberRepository;
import com.seb39.mystackoverflow.service.CommentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.request.RequestParametersSnippet;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs(uriHost = "api.mystackoverflow.com")
@WithUserDetails(value = "abcd@naver.com", userDetailsServiceBeanName = "principalDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberRepository memberRepository;

    @MockBean
    CommentService commentService;

    @MockBean
    CommentMapper commentMapper;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        Member member = Member.builder()
                .email("abcd@naver.com")
                .roles("ROLE_USER")
                .build();
        memberRepository.save(member);
    }

    @AfterEach
    void afterEach() {
        memberRepository.deleteAll();
    }


    @Test
    void postCommentTest() throws Exception {
        // given
        PostType postType = PostType.QUESTION;
        Long questionId = 3L;
        Long memberId = 7L;

        CommentDto.Post requestDto = new CommentDto.Post();
        requestDto.setContent("Test content");
        String requestJson = objectMapper.writeValueAsString(requestDto);

        given(commentService.createComment(any(), eq(postType), eq(questionId), eq(memberId)))
                .willReturn(new Comment());

        LocalDateTime createdAt = LocalDateTime.now();
        CommentDto.Response response = CommentDto.Response.builder()
                .id(11L)
                .content("Test content")
                .createdAt(createdAt)
                .lastModifiedAt(createdAt)
                .memberId(memberId)
                .postId(questionId)
                .postType(postType)
                .build();

        given(commentMapper.commentToCommentResponse(any()))
                .willReturn(response);

        // expected
        mockMvc.perform(post("/api/comments")
                        .queryParam("post-type", postType.name())
                        .queryParam("id", questionId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(11L))
                .andExpect(jsonPath("$.data.content").value("Test content"))
//                .andExpect(jsonPath("$.data.createdAt").value(createdAt.toString()))
//                .andExpect(jsonPath("$.data.lastModifiedAt").value(createdAt.toString()))
                .andExpect(jsonPath("$.data.memberId").value(memberId))
                .andExpect(jsonPath("$.data.postInfo.postType").value(postType.name()))
                .andExpect(jsonPath("$.data.postInfo.postId").value(questionId))
                .andDo(document("create-comment",
                        requestParameters(
                                parameterWithName("post-type").description("생성한 댓글이 달린 글의 타입. QUESTION / ANSWER"),
                                parameterWithName("id").description("생성한 댓글이 달린 글의 ID")
                        ),
                        requestFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("생성된 댓글의 정보"),
                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("댓글 ID"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("댓글 내용"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("댓글 생성 시간"),
                                        fieldWithPath("data.lastModifiedAt").type(JsonFieldType.STRING).description("댓글 마지막 수정 시간"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("댓글 작성자 ID"),
                                        fieldWithPath("data.postInfo.postType").type(JsonFieldType.STRING).description("댓글이 달린 글의 타입"),
                                        fieldWithPath("data.postInfo.postId").type(JsonFieldType.NUMBER).description("댓글이 달린 글의 ID")
                                )
                        )
                ));
    }

    @Test
    void patchCommentTest() throws Exception {
        // given
        Long commentId = 23L;
        String content = "UPDATED Comment!!";
        PostType postType = PostType.ANSWER;
        Long answerId = 3L;
        Long memberId = 7L;

        CommentDto.Patch requestDto = new CommentDto.Patch(content);
        String requestJson = objectMapper.writeValueAsString(requestDto);

        given(commentService.updateComment(any(), eq(memberId)))
                .willReturn(new Comment());

        LocalDateTime createdAt = LocalDateTime.now();
        CommentDto.Response response = CommentDto.Response.builder()
                .id(commentId)
                .content(content)
                .createdAt(createdAt)
                .lastModifiedAt(createdAt)
                .memberId(memberId)
                .postId(answerId)
                .postType(postType)
                .build();

        given(commentMapper.commentToCommentResponse(any()))
                .willReturn(response);


        // expected
        mockMvc.perform(patch("/api/comments/{id}", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(commentId))
                .andExpect(jsonPath("$.data.content").value(content))
                // .andExpect(jsonPath("$.data.createdAt").value(createdAt.toString()))
                //  .andExpect(jsonPath("$.data.lastModifiedAt").value(createdAt.toString()))
                .andExpect(jsonPath("$.data.memberId").value(memberId))
                .andExpect(jsonPath("$.data.postInfo.postType").value(postType.name()))
                .andExpect(jsonPath("$.data.postInfo.postId").value(answerId))
                .andDo(document("update-comment",
                        pathParameters(
                                parameterWithName("id").description("수정할 댓글의 id")
                        ),
                        requestFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("생성된 댓글의 정보"),
                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("댓글 ID"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("댓글 내용"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("댓글 생성 시간"),
                                        fieldWithPath("data.lastModifiedAt").type(JsonFieldType.STRING).description("댓글 마지막 수정 시간"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("댓글 작성자 ID"),
                                        fieldWithPath("data.postInfo.postType").type(JsonFieldType.STRING).description("댓글이 달린 글의 타입"),
                                        fieldWithPath("data.postInfo.postId").type(JsonFieldType.NUMBER).description("댓글이 달린 글의 ID")
                                )
                        )
                ));

    }

    @Test
    void deleteCommentTest() throws Exception {
        // given
        Long commentId = 23L;
        doNothing().when(commentService).deleteComment(anyLong(), anyLong());

        // expected
        mockMvc.perform(delete("/api/comments/{id}", commentId))
                .andExpect(status().isNoContent())
                .andDo(document("delete-comment",
                        pathParameters(
                                parameterWithName("id").description("삭제할 댓글의 id")
                        )
                ));
    }

}