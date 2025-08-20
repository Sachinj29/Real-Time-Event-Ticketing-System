package com.example.ticketing.payments.razorpay;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class RazorpayService {

    private final RazorpayClient client;

    public RazorpayService(RazorpayClient client) {
        this.client = client;
    }

    public Order createOrder(long amountCents, String currency, String receiptId) throws Exception {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amountCents); // in paise for INR
        orderRequest.put("currency", currency);
        orderRequest.put("receipt", receiptId);
        orderRequest.put("payment_capture", 1);
        return client.orders.create(orderRequest);
    }
}
