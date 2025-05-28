package ru.yandex.prakticum;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
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

    @Step("Запрос на вход в систему")
    public ValidatableResponse loggingIn(String login, String password){
        ValidatableResponse validatableResponse = courierSteps.loginCourier(login, password);
        return validatableResponse;
    }

    @Step("Проверка статус кода")
    public void checkStatusCode(ValidatableResponse validatableResponse, int statusCode){
        validatableResponse.statusCode(statusCode);
    }

    @Step("Проверка сообщения об ошибке в теле ответа при невалидном запросе")
    public void checkMessage(ValidatableResponse validatableResponse, String expectedMessage){
        validatableResponse.body("message", is(expectedMessage));
    }

    @Step("Проверка наличия id в ответе при валидном запросе")
    public void checkIdIsNotNull(ValidatableResponse validatableResponse){
        validatableResponse.body("id", is(notNullValue()));
    }

    @Test
    @DisplayName("Проверка статуса 200 и наличия id в ответе на валидный запрос")
    public void successLoginReturnStatus200CourierTest(){
        ValidatableResponse validatableResponse = loggingIn(login, password);
        checkStatusCode(validatableResponse, 200);
        checkIdIsNotNull(validatableResponse);
    }

    @Test
    @DisplayName("Проверка статуса 400 и сообщения об ошибке в ответе на невалидный запрос без логина")
    public void shouldReturnStatus400LoginWithoutLoginNegativeTest(){
        ValidatableResponse validatableResponse = loggingIn(null, password);
        checkStatusCode(validatableResponse, 400);
        checkMessage(validatableResponse, "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Проверка статуса 400 и сообщения об ошибке в ответе на невалидный запрос без пароля")
    public void shouldReturnStatus400LoginWithoutPasswordNegativeTest(){
        ValidatableResponse validatableResponse = loggingIn(login, null);
        checkStatusCode(validatableResponse, 400);
        checkMessage(validatableResponse, "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Проверка статуса 404 и сообщения об ошибке в ответе на невалидный запрос с несуществующим логином")
    public void shouldReturnStatus404LoginWithIncorrectLoginNegativeTest(){
        ValidatableResponse validatableResponse = loggingIn(password, password);
        checkStatusCode(validatableResponse, 404);
        checkMessage(validatableResponse, "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Проверка статуса 404 и сообщения об ошибке в ответе на невалидный запрос с неверным паролем")
    public void shouldReturnStatus404LoginWithIncorrectPasswordNegativeTest(){
        ValidatableResponse validatableResponse = loggingIn(login, login);
        checkStatusCode(validatableResponse, 404);
        checkMessage(validatableResponse, "Учетная запись не найдена");
    }

    @After
    public void tearDown() {
        Integer id = courierSteps.loginCourier(login, password).extract().path("id");
        if (id != null) {
            courierSteps.deleteCourier(id);
        }
    }
}
