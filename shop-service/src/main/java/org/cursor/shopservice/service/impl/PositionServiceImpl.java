package org.cursor.shopservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.cursor.data.dto.PositionDto;
import org.cursor.data.enums.ImageFormat;
import org.cursor.data.exception.FileFormatException;
import org.cursor.data.model.Category;
import org.cursor.data.model.Image;
import org.cursor.data.model.Position;
import org.cursor.shopservice.repository.CategoryRepository;
import org.cursor.shopservice.repository.ImageRepository;
import org.cursor.shopservice.repository.PositionRepository;
import org.cursor.shopservice.service.AwsFileService;
import org.cursor.shopservice.service.PositionService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ObjectMapper mapper;
    private final AwsFileService awsFileService;

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
    @Transactional
    public Position addImage(UUID positionId, MultipartFile file) throws IOException {
        var position = positionRepository.findById(positionId).orElseThrow();
        var image = new Image();
        var originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
            var pointIndex = originalFilename.indexOf('.');
            image.setName(originalFilename.substring(0, pointIndex));
            image.setFormat(ImageFormat.valueOf(originalFilename.substring(pointIndex + 1).toUpperCase()));
            image = imageRepository.save(image);
        } else {
            throw new FileFormatException("Invalid file");
        }
        if (position.getImages() != null) {
            position.addImage(image);
        } else {
            var images = new ArrayList<Image>();
            images.add(image);
            position.setImages(images);
        }
        awsFileService.upload(image.getId().toString(), file.getInputStream());
        return positionRepository.save(position);
    }

    @Override
    public InputStreamResource getImage(UUID imageId) {
        return awsFileService.download(imageId.toString()).orElseThrow();
    }

    @Override
    @Transactional
    public void deleteImage(UUID imageId) {
        if (!imageRepository.existsById(imageId)) {
            throw new NoSuchElementException();
        }
        imageRepository.deleteById(imageId);
        awsFileService.deleteAll(imageId.toString());
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
