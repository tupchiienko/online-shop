package org.cursor.shopservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.cursor.data.dto.PositionDto;
import org.cursor.data.model.Category;
import org.cursor.data.model.Position;
import org.cursor.shopservice.repository.CategoryRepository;
import org.cursor.shopservice.repository.PositionRepository;
import org.cursor.shopservice.service.PositionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final CategoryRepository categoryRepository;
    private final ObjectMapper mapper;

    @Override
    public Position getPositionById(UUID id) {
        return positionRepository.findById(id).orElseThrow();
    }

    @Override
    public Position getPositionByArticle(String article) {
        return positionRepository.findByArticle(article).orElseThrow();
    }

    @Override
    public Page<Position> getAllPositionsPaged(Pageable pageable) {
        return positionRepository.findAll(pageable);
    }

    @Override
    public Position createPosition(PositionDto positionDto) {
        var category = categoryRepository.findById(positionDto.getCategoryId()).orElseThrow();
        var newPosition = mapper.convertValue(positionDto, Position.class);
        newPosition.setCategory(category);
        return positionRepository.save(newPosition);
    }

    @Override
    public void updatePosition(UUID id, PositionDto positionDto) {
        var position = positionRepository.findById(id).orElseThrow();
        Category category = position.getCategory();
        if (!category.getId().equals(positionDto.getCategoryId())) {
            category = categoryRepository.findById(positionDto.getCategoryId()).orElseThrow();
        }
        var updatedPosition = mapper.convertValue(positionDto, Position.class);
        updatedPosition.setId(position.getId());
        updatedPosition.setCategory(category);
        positionRepository.save(updatedPosition);
    }

    @Override
    public void deletePosition(UUID id) {
        if (!positionRepository.existsById(id)) {
            throw new NoSuchElementException("Position with id:{" + id + "} does not exist");
        }
        positionRepository.deleteById(id);
    }
}
