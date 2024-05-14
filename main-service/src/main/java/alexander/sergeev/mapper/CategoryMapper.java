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

    public CategoryDto mapCategoryToDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName());
    }

}
