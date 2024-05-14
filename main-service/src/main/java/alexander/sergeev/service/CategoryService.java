package alexander.sergeev.service;

import alexander.sergeev.dto.category_dto.CategoryDto;
import alexander.sergeev.dto.category_dto.NewCategoryDto;
import alexander.sergeev.mapper.CategoryMapper;
import alexander.sergeev.model.Category;
import alexander.sergeev.repository.CategoryRepository;
import alexander.sergeev.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    public Set<CategoryDto> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .stream()
                .map(CategoryMapper::mapCategoryToDto)
                .collect(Collectors.toSet());
    }

    public CategoryDto getCategoryById(Long catId) {
        return CategoryMapper.mapCategoryToDto(categoryRepository.getCategoryById(catId));
    }

    public CategoryDto postCategory(NewCategoryDto newCategoryDto) {
        return CategoryMapper.mapCategoryToDto(categoryRepository.save(CategoryMapper.mapNewDtoToCategory(newCategoryDto)));
    }

    public CategoryDto patchCategoryById(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.getCategoryById(id);
        category.setName(categoryDto.getName());
        return CategoryMapper.mapCategoryToDto(categoryRepository.save(category));
    }

    public void deleteCategoryById(Long id) {
        eventRepository.checkDeletingCategoryById(id);
        categoryRepository.delete(categoryRepository.getCategoryById(id));
    }

}
