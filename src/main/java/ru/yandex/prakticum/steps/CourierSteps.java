package ru.yandex.prakticum.steps;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.yandex.prakticum.dto.CourierRequest;
import static io.restassured.RestAssured.given;

public class CourierSteps {

    public ValidatableResponse createCourier(String login, String password){
        CourierRequest request = new CourierRequest();
        request.setLogin(login);
        request.setPassword(password);
        return given()
                .contentType(ContentType.JSON)
                .baseUri("https://qa-scooter.praktikum-services.ru/")
                .body(request)
                .when()
                .post("/api/v1/courier")
                .then();

    }

    public ValidatableResponse loginCourier(String login, String password){
        CourierRequest request = new CourierRequest();
        request.setLogin(login);
        request.setPassword(password);
        return given()
                .contentType(ContentType.JSON)
                .baseUri("https://qa-scooter.praktikum-services.ru/")
                .body(request)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

    public ValidatableResponse deleteCourier(int id){
        return given()
                .contentType(ContentType.JSON)
                .baseUri("https://qa-scooter.praktikum-services.ru/")
                .pathParam("id", id)
                .when()
                .delete("/api/v1/courier/{id}")
                .then();
    }

}
