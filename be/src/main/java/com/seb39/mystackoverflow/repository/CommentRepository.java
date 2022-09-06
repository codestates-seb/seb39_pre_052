package com.seb39.mystackoverflow.repository;

import com.seb39.mystackoverflow.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
