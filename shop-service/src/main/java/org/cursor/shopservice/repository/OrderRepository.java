package org.cursor.shopservice.repository;

import org.cursor.data.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findAllByUserId(UUID userId);

}