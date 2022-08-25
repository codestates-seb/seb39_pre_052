package com.seb39.mystackoverflow.repository;

import com.seb39.mystackoverflow.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
