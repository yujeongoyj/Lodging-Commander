package com.hotel.lodgingCommander.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PayController {

    private final String clientKey = "S2_6fac0be961254663996f37d33f448f96";
    private final String secretKey = "5fcc953b775e49c498a9dd25206915a6";
    private final String baseURL = "https://sandbox-api.nicepay.co.kr/v1/payments/";

    @PostMapping("paymentAuthorization")
    public ResponseEntity<Map<String, Object>> handleServerAuth(
            @RequestParam String authResultCode,
            @RequestParam String authResultMsg,
            @RequestParam String tid,
            @RequestParam String orderId,
            @RequestParam String amount,
            @RequestParam String clientId,
            @RequestParam String authToken,
            @RequestParam String signature) {

        // 결제 인증 결과를 처리하는 로직
        System.out.println("결제 인증 결과 코드: " + authResultCode);
        System.out.println("결제 인증 결과 메시지: " + authResultMsg);
        System.out.println("거래 ID: " + tid);
        System.out.println("주문 ID: " + orderId);
        System.out.println("결제 금액: " + amount);
        System.out.println("clientId: " + clientId);
        System.out.println("authToken: " + authToken);
        System.out.println("signature: " + signature);

        // 서명 검증
        String expectedSignature = getSignature(tid, orderId, amount, secretKey);
        if (!expectedSignature.equals(signature)) {
            return ResponseEntity.status(400).body(Map.of("status", "error", "message", "서명 검증 실패"));
        }

        // 승인 API 호출
        try {
            String apiUrl = baseURL + tid;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", getAuthorizationHeader(clientKey, secretKey));

            Map<String, Object> requestBodyForApproval = new HashMap<>();
            requestBodyForApproval.put("amount", Integer.parseInt(amount));

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBodyForApproval, headers);
            RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

            // 성공적으로 승인된 경우
            System.out.println("승인 성공: " + response.getBody());
            return ResponseEntity.ok(Map.of("status", "success", "message", "결제가 성공적으로 승인되었습니다."));
        } catch (Exception e) {
            // 승인 실패 처리
            System.err.println("승인 실패: " + e.getMessage());
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", "결제 승인 실패"));
        }
    }

    private String getSignature(String tid, String orderId, String amount, String secretKey) {
        String str = tid + orderId + amount + secretKey;
        return DigestUtils.sha256Hex(str);
    }

    private String getAuthorizationHeader(String clientKey, String secretKey) {
        String credentials = clientKey + ":" + secretKey;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}
