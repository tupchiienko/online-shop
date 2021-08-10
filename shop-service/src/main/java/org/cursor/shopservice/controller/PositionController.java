package org.cursor.shopservice.controller;

import lombok.RequiredArgsConstructor;
import org.cursor.data.dto.PositionDto;
import org.cursor.data.model.Position;
import org.cursor.shopservice.service.PositionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> updatePosition(@PathVariable UUID id, @RequestBody PositionDto positionDto) {
        positionService.updatePosition(id, positionDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(
            value = "/{id}"
    )
    public ResponseEntity<?> deletePosition(@PathVariable UUID id) {
        positionService.deletePosition(id);
        return ResponseEntity.ok().build();
    }
}
