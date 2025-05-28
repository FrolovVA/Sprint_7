package ru.yandex.prakticum;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.prakticum.steps.CourierSteps;
import static org.hamcrest.Matchers.*;

public class APICreateCourierTest {

    private String login;
    private String password;
    private CourierSteps courierSteps = new CourierSteps();

    @Before
    public void setUp(){
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
    }


    @Step("Создание курьера")
    public ValidatableResponse creatingCourier(String login, String password){
        ValidatableResponse validatableResponse = courierSteps.createCourier(login, password);
        return validatableResponse;
    }

    @Step("Проверка факта создания курьера с помощью получения ненулевого id после логина")
    public void loggingInAndCheckIdCourierNotNull(String login, String password){
        Assert.assertThat(courierSteps.loginCourier(login, password).extract().path("id"), notNullValue());
    }

    @Step("Проверка ok:true в ответе при успешном запросе")
    public void checkOkIsTrue(ValidatableResponse validatableResponse){
        validatableResponse.body("ok", is(true));
    }

    @Step("Проверка статуса ответа на запрос")
    public void checkStatusCode(ValidatableResponse validatableResponse,int statusCode){
        validatableResponse.statusCode(statusCode);
    }

    @Step("Сравнение сообщения ошибки с ожидаемым")
    public void checkMessage(ValidatableResponse validatableResponse, String expectedMessage){
        validatableResponse.body("message", is(expectedMessage));
    }

    @Test
    @DisplayName("Проверка: статуса 201, ok:true в body и факта создания курьера при валидном запросе")
    public void shouldReturnStatus201CreateCourierTest(){
        ValidatableResponse validatableResponse = creatingCourier(login, password);
        checkStatusCode(validatableResponse, 201);
        checkOkIsTrue(validatableResponse);
        loggingInAndCheckIdCourierNotNull(login, password);
    }

    @Test
    @DisplayName("Проверка статуса 409 и сообщения об ошибке в ответе при попытке создания аккаунта с занятым логином")
    public void shouldReturnStatus409CreateCourierSameLoginNegativeTest(){
        creatingCourier(login, password);
        ValidatableResponse validatableResponse = creatingCourier(login, password);
        checkStatusCode(validatableResponse, 409);
        checkMessage(validatableResponse, "Этот логин уже используется");
    }

    @Test
    @DisplayName("Проверка статуса 400 и сообщения об ошибке при попытке создания аккаунта без пароля")
    public void shouldReturnStatus400CreateCourierWithoutLoginNegativeTest(){
        ValidatableResponse validatableResponse = creatingCourier(null, password);
        checkStatusCode(validatableResponse, 400);
        checkMessage(validatableResponse,"Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Проверка статуса 400 и сообщения об ошибке при попытке создания аккаунта без логина")
    public void shouldReturnStatus400CreateCourierWithoutPasswordNegativeTest(){
        ValidatableResponse validatableResponse = creatingCourier(login, null);
        checkStatusCode(validatableResponse, 400);
        checkMessage(validatableResponse, "Недостаточно данных для создания учетной записи");
    }

    @After
    public void tearDown() {
        Integer id = courierSteps.loginCourier(login, password).extract().path("id");
        if (id != null) {
            courierSteps.deleteCourier(id);
        }
    }


}
