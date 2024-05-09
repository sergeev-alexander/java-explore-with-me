package alexander.sergeev.controller.public_controller;

import alexander.sergeev.dto.compilation_dto.CompilationDto;
import alexander.sergeev.service.CompilationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
public class PublicCompilationController {

    private final CompilationService compilationService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<CompilationDto> getCompilationsByPublic(
            HttpServletRequest request,
            @RequestParam(name = "pinned", required = false) Boolean pinned,
            @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer firstElement,
            @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        log.info("{} {}?{}", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return compilationService.getCompilationsByPublic(pinned, PageRequest.of(firstElement / size, size));
    }

    @GetMapping(path = "/{compId}")
    @ResponseStatus(code = HttpStatus.OK)
    public CompilationDto getCompilationByIdByPublic(
            HttpServletRequest request,
            @PathVariable(name = "compId") @Positive Long compId) {
        log.info("{} {}", request.getMethod(), request.getRequestURI());
        return compilationService.getCompilationByIdByPublic(compId);
    }

}
