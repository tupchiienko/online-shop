package org.cursor.shopservice.service;

import org.cursor.data.dto.PositionDto;
import org.cursor.data.model.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PositionService {

    Position getPositionById(UUID id);

    Position getPositionByArticle(String article);

    Page<Position> getAllPositionsPaged(Pageable pageable);

    Position createPosition(PositionDto positionDto);

    void updatePosition(UUID id, PositionDto positionDto);

    void deletePosition(UUID id);

}
