package ru.yandex.prakticum.dto;


import lombok.Data;

@Data
public class CourierRequest {
    private String login;
    private String password;
    private String firstName;

}
