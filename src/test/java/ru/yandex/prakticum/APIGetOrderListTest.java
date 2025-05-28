package ru.yandex.prakticum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.prakticum.dto.OrderListResponse;
import ru.yandex.prakticum.steps.OrderListSteps;
import static org.hamcrest.Matchers.*;

public class APIGetOrderListTest {

    OrderListSteps orderListSteps = new OrderListSteps();

    @Before
    public void setUp(){
        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
    }

    @Step("Получаем body ответа в виде строки")
    public String getJsonBodyOrdersListResponseAsString(){
        return orderListSteps.getOrderList(null, null, null, null).extract().asString();
    }

    @Step("Представляем body строковую ")
    public OrderListResponse getStringAsOrderListResponseObject(String bodyOrdersListResponseString) throws JsonProcessingException {
        return new ObjectMapper().readValue(bodyOrdersListResponseString, OrderListResponse.class);
    }

    @Step("Проверяем что лист ответов не равен нулю")
    public void checkOrdersList( OrderListResponse orderListResponse){
        Assert.assertThat(orderListResponse.getOrders(), is(notNullValue()));
    }

    @Test
    @DisplayName("Проверка наличия листа заказов в ответе на валидный запрос")
    public void shouldReturnListOrderTest() throws JsonProcessingException {
        String bodyOrdersListResponseString = getJsonBodyOrdersListResponseAsString();
        OrderListResponse orderListResponse = getStringAsOrderListResponseObject(bodyOrdersListResponseString);
        checkOrdersList(orderListResponse);
    }


}
