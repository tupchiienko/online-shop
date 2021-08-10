package org.cursor.shopservice.service;

import org.cursor.data.dto.PositionDto;
import org.cursor.data.model.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PositionService {

    Position getById(UUID id);

    Position getByArticle(String article);

    Page<Position> getAllPaged(Pageable pageable);

    Position save(PositionDto positionDto);

    void update(UUID id);

    void delete(UUID id);

}
