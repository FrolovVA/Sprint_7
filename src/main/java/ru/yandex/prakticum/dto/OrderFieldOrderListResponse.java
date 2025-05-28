package ru.yandex.prakticum.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderFieldOrderListResponse {
    private Integer id;
    private Integer courierId;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private Integer rentTime;
    private String deliveryDate;
    private Integer track;
    private List<String> color;
    private String comment;
    private String createdAt;
    private String updatedAt;
    private Integer status;
}
