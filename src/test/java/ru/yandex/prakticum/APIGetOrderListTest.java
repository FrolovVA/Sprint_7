package ru.yandex.prakticum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import org.junit.Assert;
import org.junit.Test;
import ru.yandex.prakticum.dto.OrderListResponse;
import ru.yandex.prakticum.steps.OrderListSteps;
import static org.hamcrest.Matchers.*;

public class APIGetOrderListTest {

    OrderListSteps orderListSteps = new OrderListSteps();
    @Test
    public void shouldReturnListOrderTest() throws JsonProcessingException {

        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());

        String bodyOrderListResponse = orderListSteps.getOrderList(null, null, null, null).extract().asString();
        OrderListResponse orderListResponse = new ObjectMapper().readValue(bodyOrderListResponse, OrderListResponse.class);

        Assert.assertThat(orderListResponse.getOrders(), is(notNullValue()));
    }
}
