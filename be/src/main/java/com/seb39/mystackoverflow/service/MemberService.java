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

    public Member findByEmail(String email){
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember
                .orElseThrow(() -> new IllegalArgumentException("Member not found. email = " + email));
    }

    public boolean exist(String email){
        return memberRepository.existsByEmail(email);
    }

    @Transactional
    public Long signUp(Member member) {
        verify(member);
        String rawPassword = member.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        member.setPassword(encodedPassword);
        member.setRoles("ROLE_USER");
        memberRepository.save(member);
        return member.getId();
    }

    private void verify(Member member){
        if(memberRepository.existsByEmail(member.getEmail()))
            throw new IllegalArgumentException("email " + member.getEmail() + " already exist.");
    }
}
