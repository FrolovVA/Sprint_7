package ru.yandex.prakticum;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.prakticum.steps.OrderSteps;
import static org.hamcrest.Matchers.*;

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
    private OrderSteps orderSteps = new OrderSteps();

    @Before
    public void setUp(){
        firstName = RandomStringUtils.randomAlphabetic(10);
        lastName = RandomStringUtils.randomAlphabetic(10);
        address =  RandomStringUtils.randomAlphabetic(10);
        metroStation = 4;
        phone = "+7 800 355 35 35";
        rentTime = 5;
        deliveryDate = "2020-06-06";
        comment = null;
        color = null;
    }

    @Test
    public void shouldReturnStatus201WhenColorBlackCreateOrderTest(){
        color = new String[]{"BLACK"};
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        orderSteps.createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color)
                .statusCode(201);
    }
    @Test
    public void shouldReturnStatus201WhenColorGreyCreateOrderTest(){
        color = new String[]{"GRAY"};
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        orderSteps.createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color)
                .statusCode(201);
    }

    @Test
    public void shouldReturnStatus201WhenColorBlackAndGrayCreateOrderTest(){
        color = new String[]{"GRAY", "BLACK"};
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        orderSteps.createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color)
                .statusCode(201);
    }

    @Test
    public void shouldReturnStatus201WhenColorNullCreateOrderTest(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        orderSteps.createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color)
                .statusCode(201);
    }

    @Test
    public void shouldReturnTrackCreateOrderTest(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        orderSteps.createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color)
                .body("track", is(notNullValue()));
    }

}
