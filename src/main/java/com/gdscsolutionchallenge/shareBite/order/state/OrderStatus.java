package com.gdscsolutionchallenge.shareBite.order.state;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    REQUESTED("share requested"),
    REJECTED("share request rejected"),
    IN_PROGRESS("share in progress"),
    CANCELED("share canceled"),
    COMPLETED("share completed");

    private final String status;
}