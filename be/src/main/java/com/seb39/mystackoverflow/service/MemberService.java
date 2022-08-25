package com.seb39.mystackoverflow.service;

import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member findById(Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        return optionalMember
                .orElseThrow(() -> new IllegalArgumentException("Member not found. id = " + id));
    }

    @Transactional
    public Long signUp(Member member) {
        String rawPassword = member.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        member.setPassword(encodedPassword);
        member.setRoles("ROLE_USER");
        memberRepository.save(member);
        return member.getId();
    }

}
