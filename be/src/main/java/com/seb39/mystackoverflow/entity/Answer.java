package com.seb39.mystackoverflow.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Answer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    private String content;

    private int vote;

    private boolean accepted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="question_id")
    private Question question;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void setQuestion(Question question){
        this.question = question;
        // 양방향 매핑시 question.addAnswer 추가 필요
    }

    public void setMember(Member member){
        this.member = member;
        // 양방향 매핑시 member.addAnswer 추가 필요
    }

}
