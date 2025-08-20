package com.example.ticketing.orders.repo;

import com.example.ticketing.orders.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}
