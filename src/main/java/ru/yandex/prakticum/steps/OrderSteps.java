package ru.yandex.prakticum.steps;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.yandex.prakticum.dto.OrderRequest;
import static io.restassured.RestAssured.given;

public class OrderSteps {
    public ValidatableResponse createOrder(String firstName, String lastName, String address,
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

        return given()
                .contentType(ContentType.JSON)
                .baseUri("https://qa-scooter.praktikum-services.ru/")
                .body(request)
                .when()
                .post("/api/v1/orders")
                .then();
    }
}
