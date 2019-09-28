package com.example.api.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
