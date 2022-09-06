package com.seb39.mystackoverflow.service;

import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.exception.BusinessLogicException;
import com.seb39.mystackoverflow.exception.ExceptionCode;
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
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    public Member findByEmail(String email){
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
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
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }
}
