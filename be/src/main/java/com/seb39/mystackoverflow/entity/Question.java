package com.seb39.mystackoverflow.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.EAGER) //question과 관련된 member 정보를 모두 response 할 수 있음
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "question")
    private List<Comment> comments = new ArrayList<>();

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", view=" + view +
                ", vote=" + vote +
                ", member=" + member +
                '}';
    }
}

