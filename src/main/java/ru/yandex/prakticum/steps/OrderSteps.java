package ru.yandex.prakticum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.yandex.prakticum.dto.OrderRequest;
import static io.restassured.RestAssured.given;

public class OrderSteps {
    String baseUrl = "https://qa-scooter.praktikum-services.ru/";
    String handlePostCreateOrder = "/api/v1/orders";
    public ValidatableResponse createOrder(String firstName, String lastName, String address,
                                            Integer metroStation, String phone, Integer rentTime, String deliveryDate,
                                            String comment, String[] color){
        OrderRequest request = getOrderRequestBody(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        return postCreateOrderRequest(baseUrl, request, handlePostCreateOrder);
    }

    @Step("Api запрос для создания заказа post /api/v1/orders")
    public ValidatableResponse postCreateOrderRequest(String baseUrl, OrderRequest request, String handle){
        return given()
                .contentType(ContentType.JSON)
                .baseUri(baseUrl)
                .body(request)
                .when()
                .post(handlePostCreateOrder)
                .then();
    }

    @Step
    public OrderRequest getOrderRequestBody(String firstName, String lastName, String address,
                                              Integer metroStation, String phone, Integer rentTime, String deliveryDate,
                                              String comment, String[] color){
        OrderRequest request = new OrderRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setAddress(address);
        request.setMetroStation(metroStation);
        request.setPhone(phone);
        request.setRentTime(rentTime);
        request.setDeliveryDate(deliveryDate);
        request.setComment(comment);
        request.setColor(color);
        return request;
    }

}
