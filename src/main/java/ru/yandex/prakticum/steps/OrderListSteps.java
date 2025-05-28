package ru.yandex.prakticum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.yandex.prakticum.dto.OrderListRequest;
import static io.restassured.RestAssured.given;

public class OrderListSteps {
    String baseUrl = "https://qa-scooter.praktikum-services.ru/";
    String handleGetOrderList = "/api/v1/orders";

    public ValidatableResponse getOrderList(Integer courierId, String nearestStation, Integer limit, Integer page){
        OrderListRequest request = getOrderListRequestBody(courierId,nearestStation, limit, page);
        return getOrderListRequest(baseUrl, request, handleGetOrderList);
    }

    @Step("Api запрос для получения листа заказов get /api/v1/orders")
    public ValidatableResponse getOrderListRequest(String baseUrl, OrderListRequest request, String handle){
        return given()
                .contentType(ContentType.JSON)
                .baseUri(baseUrl)
                .body(request)
                .when()
                .get(handle)
                .then();
    }

    @Step("Формируем Json тело для запроса листа заказов")
    public OrderListRequest getOrderListRequestBody(Integer courierId, String nearestStation, Integer limit, Integer page){
        OrderListRequest request = new OrderListRequest();
        request.setCourierId(courierId);
        request.setNearestStation(nearestStation);
        request.setLimit(limit);
        request.setPage(page);
        return request;
    }


}
