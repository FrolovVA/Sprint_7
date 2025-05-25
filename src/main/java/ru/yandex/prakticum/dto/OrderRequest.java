package ru.yandex.prakticum.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private String firstName;
    private String lastName;
    private String address;
    private Integer metroStation;
    private String phone;
    private Integer rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;
}
