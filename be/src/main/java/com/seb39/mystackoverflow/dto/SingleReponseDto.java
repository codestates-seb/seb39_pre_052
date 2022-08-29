package com.seb39.mystackoverflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SingleReponseDto<T> {
    private T data;
}
