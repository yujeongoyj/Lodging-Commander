package com.hotel.lodgingCommander.model.PaymentRequest;

import lombok.Data;

@Data
public class PaymentResponse {
    private String authResultCode;
    private String authResultMsg;
    private String tid;
    private String clientId;
    private String orderId;
    private String amount;
    private String mallReserved;
    private String authToken;
    private String signature;
}
