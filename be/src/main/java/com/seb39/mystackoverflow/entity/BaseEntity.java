package com.seb39.mystackoverflow.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {


    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;
}
