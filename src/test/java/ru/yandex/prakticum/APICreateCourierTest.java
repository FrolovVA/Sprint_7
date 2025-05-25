package ru.yandex.prakticum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
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
    }

    @Test
    public void successCreateCourierTest(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        courierSteps.createCourier(login, password);
        Assert.assertThat(courierSteps.loginCourier(login, password).extract().path("id"), notNullValue());
    }

    @Test
    public void shouldReturnStatus201CreateCourierTest(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        courierSteps.createCourier(login, password)
                .statusCode(201);
    }

    @Test
    public void shouldReturnOkTrueCreateCourierTest(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        courierSteps.createCourier(login, password)
                .body("ok", is(true));
    }

    @Test
    public void shouldReturnStatus409CreateCourierSameLoginNegativeTest(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        courierSteps.createCourier(login, password);
        courierSteps.createCourier(login, password)
                .statusCode(409);
    }

    @Test
    public void shouldReturnMessageCreateCourierSameLoginNegativeTest(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        courierSteps.createCourier(login, password);
        courierSteps.createCourier(login, password)
                .body("message", is("Этот логин уже используется"));
    }

    @Test
    public void shouldReturnStatus400CreateCourierWithoutLoginNegativeTest(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        courierSteps.createCourier(login, null)
                .statusCode(400);
    }

    @Test
    public void shouldReturnMessageCreateCourierWithoutLoginNegativeTest(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        courierSteps.createCourier(login, null)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void shouldReturnStatus400CreateCourierWithoutPasswordNegativeTest(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        courierSteps.createCourier(null, password)
                .statusCode(400);
    }

    @Test
    public void shouldReturnMessageCreateCourierWithoutPasswordNegativeTest(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        courierSteps.createCourier(null, password)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void tearDown() {
        Integer id = courierSteps.loginCourier(login, password).extract().path("id");
        if (id != null) {
            courierSteps.deleteCourier(id);
        }
    }


}
