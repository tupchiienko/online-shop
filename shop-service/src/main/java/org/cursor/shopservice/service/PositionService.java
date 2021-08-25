package org.cursor.shopservice.service;

import org.cursor.data.dto.PositionDto;
import org.cursor.data.model.Position;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface PositionService {

    Position getPositionById(UUID id);

    Position getPositionByArticle(String article);

    Page<Position> getAllPositionsPaged(Pageable pageable);

    Position createPosition(PositionDto positionDto);

    Position addImage(UUID positionId, MultipartFile file) throws IOException;

    InputStreamResource getImage(UUID imageId);

    void deleteImage(UUID imageId);

    void updatePosition(UUID id, PositionDto positionDto);

    void deletePosition(UUID id);

}
