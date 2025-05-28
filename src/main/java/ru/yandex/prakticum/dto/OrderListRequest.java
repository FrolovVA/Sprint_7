package ru.yandex.prakticum.dto;

import lombok.Data;

@Data
public class OrderListRequest {

    private Integer courierId;
    private String nearestStation;
    private Integer limit;
    private Integer page;
}
