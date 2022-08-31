package com.seb39.mystackoverflow.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
//@ToString
@NoArgsConstructor
public class Question extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    private String title;

    private String content;

    @ColumnDefault("0")
    private int view;

    @ColumnDefault("0")
    private int vote;


    /**
     * 회원 연관관계
     */
//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.EAGER) //fetch 타입을 EAGER로 변환하면 question과 관련된 member 정보를 모두 response 할 수 있음
    @JoinColumn(name = "member_id")
    private Member member;

    // ==연관관계 메서드==
    public void setQuestionMember(Member member) {
        this.member = member;
        member.getQuestions().add(this);
    }
}

