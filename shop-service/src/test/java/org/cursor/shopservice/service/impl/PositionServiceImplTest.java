package org.cursor.shopservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cursor.data.dto.PositionDto;
import org.cursor.data.exception.FileFormatException;
import org.cursor.data.model.Category;
import org.cursor.data.model.Image;
import org.cursor.data.model.Position;
import org.cursor.shopservice.repository.CategoryRepository;
import org.cursor.shopservice.repository.ImageRepository;
import org.cursor.shopservice.repository.PositionRepository;
import org.cursor.shopservice.service.AwsFileService;
import org.cursor.shopservice.service.impl.PositionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PositionServiceImplTest {

    @Mock
    private PositionRepository positionRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private AwsFileService awsFileService;

    private PositionServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new PositionServiceImpl(positionRepository, categoryRepository, imageRepository, new ObjectMapper(), awsFileService);
    }

    @Test
    void getPositionById_returnsEntity() {
        UUID id = UUID.randomUUID();
        Position pos = new Position();
        when(positionRepository.findById(id)).thenReturn(Optional.of(pos));

        assertThat(service.getPositionById(id)).isSameAs(pos);
    }

    @Test
    void getPositionByArticle_returnsEntity() {
        Position pos = new Position();
        when(positionRepository.findByArticle("a")).thenReturn(Optional.of(pos));

        assertThat(service.getPositionByArticle("a")).isSameAs(pos);
    }

    @Test
    void getAllPositionsPaged_returnsPage() {
        Page<Position> page = new PageImpl<>(List.of(new Position()));
        when(positionRepository.findAll(any(PageRequest.class))).thenReturn(page);

        assertThat(service.getAllPositionsPaged(PageRequest.of(0,1))).isSameAs(page);
    }

    @Test
    void createPosition_mapsAndSaves() {
        PositionDto dto = new PositionDto();
        UUID catId = UUID.randomUUID();
        dto.setCategoryId(catId);
        Category cat = new Category();
        when(categoryRepository.findById(catId)).thenReturn(Optional.of(cat));
        when(positionRepository.save(any(Position.class))).thenAnswer(inv -> inv.getArgument(0));

        Position result = service.createPosition(dto);

        assertThat(result.getCategory()).isSameAs(cat);
        verify(positionRepository).save(any(Position.class));
    }

    @Test
    void addImage_addsAndUploads() throws IOException {
        UUID posId = UUID.randomUUID();
        Position pos = new Position();
        when(positionRepository.findById(posId)).thenReturn(Optional.of(pos));
        when(imageRepository.save(any(Image.class))).thenAnswer(inv -> {
            Image img = inv.getArgument(0);
            img.setId(UUID.randomUUID());
            return img;
        });
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("img.png");
        ByteArrayInputStream bais = new ByteArrayInputStream("d".getBytes());
        when(file.getInputStream()).thenReturn(bais);
        when(positionRepository.save(pos)).thenReturn(pos);

        Position result = service.addImage(posId, file);

        assertThat(result).isSameAs(pos);
        verify(awsFileService).upload(anyString(), eq(bais));
        verify(positionRepository).save(pos);
    }

    @Test
    void addImage_withInvalidFile_throwsException() throws IOException {
        UUID posId = UUID.randomUUID();
        when(positionRepository.findById(posId)).thenReturn(Optional.of(new Position()));
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn(null);

        assertThatThrownBy(() -> service.addImage(posId, file)).isInstanceOf(FileFormatException.class);
    }

    @Test
    void getImage_downloadsFromAws() {
        UUID id = UUID.randomUUID();
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(new byte[0]));
        when(awsFileService.download(id.toString())).thenReturn(Optional.of(resource));

        assertThat(service.getImage(id)).isSameAs(resource);
    }

    @Test
    void deleteImage_whenNotExists_throws() {
        UUID id = UUID.randomUUID();
        when(imageRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> service.deleteImage(id)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteImage_removesFromRepoAndAws() {
        UUID id = UUID.randomUUID();
        when(imageRepository.existsById(id)).thenReturn(true);

        service.deleteImage(id);

        verify(imageRepository).deleteById(id);
        verify(awsFileService).deleteAll(id.toString());
    }

    @Test
    void updatePosition_changesCategoryWhenNeeded() {
        UUID id = UUID.randomUUID();
        Position existing = new Position();
        Category existingCat = new Category();
        existingCat.setId(UUID.randomUUID());
        existing.setCategory(existingCat);
        when(positionRepository.findById(id)).thenReturn(Optional.of(existing));

        PositionDto dto = new PositionDto();
        dto.setCategoryId(UUID.randomUUID());
        Category newCat = new Category();
        when(categoryRepository.findById(dto.getCategoryId())).thenReturn(Optional.of(newCat));

        service.updatePosition(id, dto);

        verify(categoryRepository).findById(dto.getCategoryId());
        verify(positionRepository).save(any(Position.class));
    }

    @Test
    void deletePosition_whenNotExists_throws() {
        UUID id = UUID.randomUUID();
        when(positionRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> service.deletePosition(id)).isInstanceOf(NoSuchElementException.class);
    }
}
