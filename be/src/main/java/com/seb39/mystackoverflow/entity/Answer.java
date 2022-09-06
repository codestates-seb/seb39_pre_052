package com.seb39.mystackoverflow.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Answer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    @Lob
    private String content;

    private int vote;

    private boolean accepted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="question_id")
    private Question question;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "answer")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Answer(String content, Question question, Member member) {
        this.content = content;
        this.question = question;
        this.member = member;
    }

    public void changeQuestion(Question question){
        this.question = question;
        question.getAnswers().add(this);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", vote=" + vote +
                ", accepted=" + accepted +
                '}';
    }
}
