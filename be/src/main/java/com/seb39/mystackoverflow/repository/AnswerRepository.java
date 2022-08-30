package com.seb39.mystackoverflow.repository;

import com.seb39.mystackoverflow.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
    Page<Answer> findAllByMemberId(Long memberId, Pageable pageable);
}
