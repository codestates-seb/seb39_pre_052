package com.seb39.mystackoverflow.mapper;

import com.seb39.mystackoverflow.dto.MemberDto;
import com.seb39.mystackoverflow.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {

    default MemberDto.Response memberToMemberResponse(Member member) {
        return MemberDto.Response.builder()
                .memberId(member.getId())
                .memberName(member.getName())
                .createdAt(member.getCreatedAt())
                .build();
    }
}
