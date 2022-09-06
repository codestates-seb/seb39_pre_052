package com.seb39.mystackoverflow.repository;

import com.seb39.mystackoverflow.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Page<Answer> findAllByMemberId(Long memberId, Pageable pageable);

    @Query("select distinct a from Answer a join fetch a.member left join fetch a.comments c left join fetch c.member where a.question.id = ?1")
    List<Answer> findAnswerWithCommentsByQuestionId(Long questionId);
}
