package com.seb39.mystackoverflow.repository;

import com.seb39.mystackoverflow.entity.Question;
import jdk.jfr.BooleanFlag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findAllByMemberId(Long memberId, Pageable pageable);


    Page<Question> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Question> findByContentContainingIgnoreCase(String keyword, Pageable pageable);
    
    @Query("select q from Question q join fetch q.member join fetch q.comments c join fetch c.member where q.id = ?1")
    Optional<Question> findQuestionWithCommentsById(Long id);
}
