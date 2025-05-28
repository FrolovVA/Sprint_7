package ru.yandex.prakticum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.yandex.prakticum.dto.CourierRequest;
import static io.restassured.RestAssured.given;

public class CourierSteps {
    String baseUrl = "https://qa-scooter.praktikum-services.ru/";
    String handlePostCreateCourier = "/api/v1/courier";
    String handlePostLoginCourier = "/api/v1/courier/login";
    String handleDeleteCourier = "/api/v1/courier/{id}";

    public ValidatableResponse createCourier(String login, String password){

        CourierRequest request = getCourierRequestBody(login, password);
        return postCreateCourierRequest(baseUrl, request, handlePostCreateCourier);
    }

    public ValidatableResponse loginCourier(String login, String password){
        CourierRequest request = getCourierRequestBody(login, password);
        return postLoginCourierRequest(baseUrl, request, handlePostLoginCourier);
    }

    public ValidatableResponse deleteCourier(Integer id){
        return deleteCourierRequest(baseUrl, id, handleDeleteCourier);
    }

    @Step("Api запрос для создания курьера post /api/v1/courier")
    public ValidatableResponse postCreateCourierRequest(String baseUrl, CourierRequest request, String handle){
        return given()
                .contentType(ContentType.JSON)
                .baseUri(baseUrl)
                .body(request)
                .when()
                .post(handle)
                .then();
    }


    @Step("Api запрос для логина курьера post /api/v1/courier/login")
    public ValidatableResponse postLoginCourierRequest(String baseUrl, CourierRequest request, String handle){
        return given()
                .contentType(ContentType.JSON)
                .baseUri(baseUrl)
                .body(request)
                .when()
                .post(handle)
                .then();
    }

    @Step("Api запрос для удаления курьера delete /api/v1/courier/{id}")
    public ValidatableResponse deleteCourierRequest(String baseUrl, Integer id, String handle){
        return given()
                .contentType(ContentType.JSON)
                .baseUri(baseUrl)
                .pathParam("id", id)
                .when()
                .delete(handle)
                .then();
    }

    @Step
    public CourierRequest getCourierRequestBody(String login, String password){
        CourierRequest request = new CourierRequest();
        request.setLogin(login);
        request.setPassword(password);
        return request;
    }
}
