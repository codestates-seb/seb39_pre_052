package com.seb39.mystackoverflow.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Long getPostId(){
        if(postType==PostType.QUESTION)
            return question.getId();

        if(postType==PostType.ANSWER)
            return answer.getId();

        throw new UnsupportedOperationException("Unsupported PostType. postType = " + postType);
    }

    //==연관관계 메서드==
    public void setCommentPost(PostType postType, Object post) {
        this.postType = postType;
        if (postType == PostType.QUESTION && post instanceof Question) {
            this.question = (Question) post;
            return;
        }
        if(postType == PostType.ANSWER && post instanceof Answer){
            this.answer = (Answer) post;
            return;
        }
        throw new UnsupportedOperationException("Unsupported PostType. postType = " + postType);
    }

    public void setCommentMember(Member member) {
        this.member = member;
    }
}
