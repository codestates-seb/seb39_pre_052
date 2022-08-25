package com.seb39.mystackoverflow.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Question extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ColumnDefault("0")
    private int view;

    @ColumnDefault("0")
    private int vote;

    //시간 지정 메서드
    public void setTime() {
        this.setCreatedAt(LocalDateTime.now());
        this.setLastModifiedAt(LocalDateTime.now());
    }

}

