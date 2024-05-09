package alexander.sergeev.mapper;

import alexander.sergeev.dto.category_dto.CategoryDto;
import alexander.sergeev.dto.category_dto.NewCategoryDto;
import alexander.sergeev.model.Category;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryMapper {

    public Category mapNewDtoToCategory(NewCategoryDto newCategoryDto) {
        return new Category(
                null,
                newCategoryDto.getName());
    }

    public Category mapDtoToCategory(CategoryDto categoryDto) {
        return new Category(
                categoryDto.getId(),
                categoryDto.getName());
    }

    public CategoryDto mapCategoryToDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName());
    }

}
