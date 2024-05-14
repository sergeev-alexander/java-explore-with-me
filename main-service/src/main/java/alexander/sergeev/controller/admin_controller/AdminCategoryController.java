package alexander.sergeev.controller.admin_controller;

import alexander.sergeev.dto.category_dto.CategoryDto;
import alexander.sergeev.dto.category_dto.NewCategoryDto;
import alexander.sergeev.service.CategoryService;
import alexander.sergeev.validation.ValidationMarker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;

@Slf4j
@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CategoryDto postCategory(
            HttpServletRequest request,
            @RequestBody @Validated(value = ValidationMarker.OnCreate.class) NewCategoryDto newCategoryDto) {
        log.info("{} {} {}", request.getMethod(), request.getRequestURI(), newCategoryDto);
        return categoryService.postCategory(newCategoryDto);
    }

    @PatchMapping(path = "/{catId}")
    @ResponseStatus(code = HttpStatus.OK)
    public CategoryDto patchCategoryById(
            HttpServletRequest request,
            @PathVariable(name = "catId") @Positive Long catId,
            @RequestBody @Validated(value = ValidationMarker.OnUpdate.class) CategoryDto categoryDto) {
        log.info("{} {}?{}", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return categoryService.patchCategoryById(catId, categoryDto);
    }

    @DeleteMapping(path = "/{catId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCategoryById(
            HttpServletRequest request,
            @PathVariable(name = "catId") @Positive Long id) {
        log.info("{} {}?{}", request.getMethod(), request.getRequestURI(), request.getQueryString());
        categoryService.deleteCategoryById(id);
    }

}
