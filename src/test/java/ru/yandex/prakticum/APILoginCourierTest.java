package ru.yandex.prakticum;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.prakticum.steps.CourierSteps;
import static org.hamcrest.Matchers.*;

public class APILoginCourierTest {
    private String login;
    private String password;
    private CourierSteps courierSteps = new CourierSteps();

    @Before
    public void setUp(){
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        courierSteps.createCourier(login, password);
    }

    @Test
    public void successLoginReturnStatus200CourierTest(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        courierSteps.loginCourier(login, password).statusCode(200);
    }

    @Test
    public void successLoginReturnIdIsNotNullCourierTest(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        courierSteps.loginCourier(login, password).body("id", is(notNullValue()));
    }

    @Test
    public void shouldReturnStatus400LoginWithoutLoginNegativeTest(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        courierSteps.loginCourier(null, password).statusCode(400);
    }

    @Test
    public void shouldReturnMessageLoginWithoutLoginNegativeTest(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        courierSteps.loginCourier(null, password).body("message", is("Недостаточно данных для входа"));
    }

    @Test
    public void shouldReturnStatus400LoginWithoutPasswordNegativeTest(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        courierSteps.loginCourier(login, null).statusCode(400);
    }

    @Test
    public void shouldReturnMessageLoginWithoutPasswordNegativeTest(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        courierSteps.loginCourier(login, null).body("message", is("Недостаточно данных для входа"));
    }

    @Test
    public void shouldReturnStatus404LoginWithIncorrectLoginNegativeTest(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        courierSteps.loginCourier(password, password).statusCode(404);
    }

    @Test
    public void shouldReturnMessageLoginWithIncorrectLoginNegativeTest(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        courierSteps.loginCourier(password, password)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    public void shouldReturnStatus404LoginWithIncorrectPasswordNegativeTest(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        courierSteps.loginCourier(login, login).statusCode(404);
    }

    @Test
    public void shouldReturnMessageLoginWithIncorrectPasswordNegativeTest(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        courierSteps.loginCourier(login, login)
                .body("message", is("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        Integer id = courierSteps.loginCourier(login, password).extract().path("id");
        if (id != null) {
            courierSteps.deleteCourier(id);
        }
    }
}
