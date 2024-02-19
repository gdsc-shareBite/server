package com.gdscsolutionchallenge.shareBite.order.dto;

import com.gdscsolutionchallenge.shareBite.order.state.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderStatusDto {
    private OrderStatus orderStatus;
}
