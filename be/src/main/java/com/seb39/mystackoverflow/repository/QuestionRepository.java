package com.seb39.mystackoverflow.repository;

import com.seb39.mystackoverflow.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findAllByMemberId(Long memberId, Pageable pageable);
}
