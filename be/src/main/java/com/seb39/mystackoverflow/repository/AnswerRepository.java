package com.seb39.mystackoverflow.repository;

import com.seb39.mystackoverflow.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
}
