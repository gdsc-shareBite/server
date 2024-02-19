package com.gdscsolutionchallenge.shareBite.order.controller;

import com.gdscsolutionchallenge.shareBite.order.dto.CreateOrderRequestDto;
import com.gdscsolutionchallenge.shareBite.order.dto.CreateOrderResponseDto;
import com.gdscsolutionchallenge.shareBite.order.dto.FindOrderDto;
import com.gdscsolutionchallenge.shareBite.order.dto.UpdateOrderStatusDto;
import com.gdscsolutionchallenge.shareBite.order.service.OrderService;
import com.gdscsolutionchallenge.shareBite.order.state.OrderStatus;
import com.gdscsolutionchallenge.shareBite.post.dto.FindAllPostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<CreateOrderResponseDto> createOrder(@AuthenticationPrincipal UserDetails userDetails,
                                                              @RequestBody CreateOrderRequestDto createOrderRequestDto) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        Long postId = createOrderRequestDto.getPostId();

        return new ResponseEntity<> (orderService.createOrder(memberId, postId), HttpStatus.CREATED);
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId,
                                              @RequestBody UpdateOrderStatusDto updateOrderStatusDto) {
        OrderStatus orderStatus = updateOrderStatusDto.getOrderStatus();
        orderService.setOrderStatus(orderId, orderStatus);

        return new ResponseEntity<> (HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<FindOrderDto> findOrder(@PathVariable Long orderId) {

        return new ResponseEntity<> (orderService.findOrder(orderId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<FindAllPostResponseDto> findAllOrder(@AuthenticationPrincipal UserDetails userDetails,
                                                               @RequestParam(defaultValue = "0") int pageStartIdx,
                                                               @RequestParam(defaultValue = "10") int pageSize) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        Pageable pageable = PageRequest.of(pageStartIdx, pageSize);

        return new ResponseEntity<> (orderService.findAllOrder(memberId, pageable), HttpStatus.OK);
    }

}
