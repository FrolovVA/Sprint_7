package ru.yandex.prakticum;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.prakticum.steps.OrderSteps;
import static org.hamcrest.Matchers.*;


@RunWith(Parameterized.class)
public class APICreateOrderTest {
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;
    private int statusCode;
    private OrderSteps orderSteps = new OrderSteps();

    public APICreateOrderTest(String firstName, String lastName, String address,
                              Integer metroStation, String phone, Integer rentTime, String deliveryDate,
                              String comment, String[] color, int statusCode){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
        this.statusCode = statusCode;
    }


    @Parameterized.Parameters
    public static Object[][] data(){
        String firstName = RandomStringUtils.randomAlphabetic(10);
        String lastName = RandomStringUtils.randomAlphabetic(10);
        String address =  RandomStringUtils.randomAlphabetic(10);
        Integer metroStation = 4;
        String phone = "+7 800 355 35 35";
        Integer rentTime = 5;
        String deliveryDate = "2020-06-06";
        String comment = null;

        return new Object[][]{
                {firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, new String[]{"GRAY"}, 201},
                {firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, new String[]{"BLACK"}, 201},
                {firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, new String[]{"GRAY", "BLACK"}, 201},
                {firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, null, 201}
        };
    }

    @Before
    public void setUp(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
    }

    @Step("Задаем цвет")
    public String[] setColor(String[] color){
        return color;
    }

    @Step("Создание заказа")
    public ValidatableResponse creatingOrder(String firstName, String lastName, String address,
                                             Integer metroStation, String phone, Integer rentTime, String deliveryDate,
                                             String comment, String[] color){
        ValidatableResponse validatableResponse = orderSteps.createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        return validatableResponse;
    }

    @Step("Проверка статус кода ответа")
    public void checkStatusCode(ValidatableResponse validatableResponse, int statusCode){
        validatableResponse.statusCode(statusCode);
    }

    @Step("Проверка наличия параметра track в ответе при валидном создании заказа")
    public void checkTrackIsNotNull(ValidatableResponse validatableResponse){
        validatableResponse.body("track", is(notNullValue()));
    }

    @Test
    @DisplayName("Проверка статуса ответа 201 и наличия track в теле ответа при валидном создании заказа с цветом BLACK")
    public void shouldReturnStatus201WhenColorBlackCreateOrderTest(){
        ValidatableResponse validatableResponse = creatingOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        checkStatusCode(validatableResponse, statusCode);
        checkTrackIsNotNull(validatableResponse);
    }

}
