package org.cursor.shopservice.controller;

import lombok.RequiredArgsConstructor;
import org.cursor.data.dto.PositionDto;
import org.cursor.data.model.Position;
import org.cursor.shopservice.service.PositionService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Position> getPositionById(@PathVariable UUID id) {
        return ResponseEntity.ok(positionService.getPositionById(id));
    }

    @GetMapping(
            params = "article",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Position> getPositionByArticle(@RequestParam String article) {
        return ResponseEntity.ok(positionService.getPositionByArticle(article));
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Page<Position>> getAllPositionsPageable(Pageable pageable) {
        return ResponseEntity.ok(positionService.getAllPositionsPaged(pageable));
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Position> createPosition(@RequestBody PositionDto positionDto) {
        return ResponseEntity.ok(positionService.createPosition(positionDto));
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> updatePosition(@PathVariable UUID id, @RequestBody PositionDto positionDto) {
        positionService.updatePosition(id, positionDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(
            value = "/{id}"
    )
    public ResponseEntity<Void> deletePosition(@PathVariable UUID id) {
        positionService.deletePosition(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(
            value = "/image/{positionId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Position> addImage(
            @PathVariable UUID positionId,
            @RequestBody MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(positionService.addImage(positionId, file));
    }

    @GetMapping(
            value = "/image/{id}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public ResponseEntity<InputStreamResource> getImage(@PathVariable UUID id) {
        return ResponseEntity.ok(positionService.getImage(id));
    }

    @DeleteMapping(
            value = "/image/{id}"
    )
    public ResponseEntity<Void> deleteImage(@PathVariable UUID id) {
        positionService.deleteImage(id);
        return ResponseEntity.ok().build();
    }
}
