package com.example.ticketing.orders.service;

import com.example.ticketing.orders.domain.Order;
import com.example.ticketing.orders.domain.OrderStatus;
import com.example.ticketing.orders.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orders;

    @Transactional
    public Order create(Long reservationId, Long userId, Long amountCents, String currency, String provider) {
        Order o = new Order();
        o.setReservationId(reservationId);
        o.setUserId(userId);
        o.setAmountCents(amountCents);
        o.setCurrency(currency);
        o.setProvider(provider);
        return orders.save(o);
    }

    @Transactional
    public void markPaid(Order o, String providerPaymentId) {
        o.setStatus(OrderStatus.PAID);
        o.setProviderPaymentId(providerPaymentId);
        orders.save(o);
    }
}
