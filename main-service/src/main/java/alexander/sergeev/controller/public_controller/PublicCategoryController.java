package alexander.sergeev.controller.public_controller;

import alexander.sergeev.dto.category_dto.CategoryDto;
import alexander.sergeev.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
public class PublicCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Set<CategoryDto> getAllCategories(
            HttpServletRequest request,
            @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer firstElement,
            @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        log.info("{} {}?{}", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return categoryService.getAllCategories(PageRequest.of(firstElement / size, size));
    }

    @GetMapping(path = "/{catId}")
    @ResponseStatus(code = HttpStatus.OK)
    public CategoryDto getCategoryById(
            HttpServletRequest request,
            @PathVariable(name = "catId") @Positive Long catId) {
        log.info("{} {}", request.getMethod(), request.getRequestURI());
        return categoryService.getCategoryById(catId);
    }

}
