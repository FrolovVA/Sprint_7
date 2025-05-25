package ru.yandex.prakticum.steps;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.yandex.prakticum.dto.OrderListRequest;
import static io.restassured.RestAssured.given;

public class OrderListSteps {

    public ValidatableResponse getOrderList(Integer courierId, String nearestStation, Integer limit, Integer page){
        OrderListRequest request = new OrderListRequest();
        request.setCourierId(courierId);
        request.setNearestStation(nearestStation);
        request.setLimit(limit);
        request.setPage(page);
        return given()
                .contentType(ContentType.JSON)
                .baseUri("https://qa-scooter.praktikum-services.ru/")
                .body(request)
                .when()
                .get("/api/v1/orders")
                .then();

    }
}
