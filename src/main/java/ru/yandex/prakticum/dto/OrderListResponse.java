package ru.yandex.prakticum.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderListResponse {
    private List<OrderFieldOrderListResponse> orders;
    private PageInfoFieldOrderListResponse pageInfo;
    private List<AvailableStationFieldOrderListResponse> availableStations;
}
