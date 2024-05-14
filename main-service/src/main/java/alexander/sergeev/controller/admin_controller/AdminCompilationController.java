package alexander.sergeev.controller.admin_controller;

import alexander.sergeev.dto.compilation_dto.CompilationDto;
import alexander.sergeev.dto.compilation_dto.NewCompilationDto;
import alexander.sergeev.dto.compilation_dto.UpdateCompilationDto;
import alexander.sergeev.service.CompilationService;
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
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {

    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CompilationDto postCompilation(
            HttpServletRequest request,
            @RequestBody @Validated(ValidationMarker.OnCreate.class) NewCompilationDto newCompilationDto) {
        log.info("{} {} {}", request.getMethod(), request.getRequestURI(), newCompilationDto);
        return compilationService.postCompilation(newCompilationDto);
    }

    @PatchMapping(path = "/{compId}")
    @ResponseStatus(code = HttpStatus.OK)
    public CompilationDto patchCompilationById(
            HttpServletRequest request,
            @PathVariable(name = "compId") @Positive Long compId,
            @RequestBody @Validated(ValidationMarker.OnUpdate.class) UpdateCompilationDto updateCompilationDto) {
        log.info("{} {} {}", request.getMethod(), request.getRequestURI(), updateCompilationDto);
        return compilationService.patchCompilationById(compId, updateCompilationDto);
    }

    @DeleteMapping(path = "/{compId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCompilationById(
            HttpServletRequest request,
            @PathVariable(name = "compId") Long compId) {
        log.info("{} {}", request.getMethod(), request.getRequestURI());
        compilationService.deleteCompilationById(compId);
    }

}
