package org.cursor.shopservice.repository;

import org.cursor.data.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PositionRepository extends JpaRepository<Position, UUID> {

    Optional<Position> findByArticle(String article);

}
