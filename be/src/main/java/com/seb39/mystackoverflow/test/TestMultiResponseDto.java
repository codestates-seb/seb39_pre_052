package com.seb39.mystackoverflow.test;

import com.seb39.mystackoverflow.dto.PageInfo;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class TestMultiResponseDto<T> {
    private List<T> data;
    private PageInfo pageInfo;

    public TestMultiResponseDto(List<T> data, Page page) {
        this.data = data;
        this.pageInfo = new PageInfo(page.getNumber() + 1, page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
