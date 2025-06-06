import com.fasterxml.jackson.databind.ObjectMapper;
import org.cursor.data.dto.CategoryDto;
import org.cursor.data.model.Category;
import org.cursor.shopservice.repository.CategoryRepository;
import org.cursor.shopservice.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new CategoryServiceImpl(categoryRepository, new ObjectMapper());
    }

    @Test
    void getCategoryById_returnsCategory() {
        UUID id = UUID.randomUUID();
        Category category = new Category();
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        Category result = service.getCategoryById(id);

        assertThat(result).isSameAs(category);
    }

    @Test
    void getAllCategories_returnsSortedList() {
        List<Category> list = List.of(new Category());
        when(categoryRepository.findAll(any(Sort.class))).thenReturn(list);

        List<Category> result = service.getAllCategories();

        assertThat(result).isEqualTo(list);
        verify(categoryRepository).findAll(any(Sort.class));
    }

    @Test
    void createCategory_savesConvertedEntity() {
        CategoryDto dto = new CategoryDto();
        dto.setName("n1");
        Category saved = new Category();
        when(categoryRepository.save(any(Category.class))).thenReturn(saved);

        Category result = service.createCategory(dto);

        assertThat(result).isSameAs(saved);
        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("n1");
    }

    @Test
    void updateCategory_updatesExisting() {
        UUID id = UUID.randomUUID();
        when(categoryRepository.findById(id)).thenReturn(Optional.of(new Category()));
        CategoryDto dto = new CategoryDto();
        dto.setName("upd");

        service.updateCategory(id, dto);

        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(id);
        assertThat(captor.getValue().getName()).isEqualTo("upd");
    }

    @Test
    void deleteCategory_deletesExisting() {
        UUID id = UUID.randomUUID();
        when(categoryRepository.findById(id)).thenReturn(Optional.of(new Category()));

        service.deleteCategory(id);

        verify(categoryRepository).deleteById(id);
    }
}
