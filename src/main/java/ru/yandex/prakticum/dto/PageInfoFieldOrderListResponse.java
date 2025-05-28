package ru.yandex.prakticum.dto;

import lombok.Data;

@Data
public class PageInfoFieldOrderListResponse {
    private Integer page;
    private Integer total;
    private Integer limit;
}
