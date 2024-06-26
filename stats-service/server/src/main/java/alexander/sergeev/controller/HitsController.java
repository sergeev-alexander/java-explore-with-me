package alexander.sergeev.controller;

import alexander.sergeev.dto.HitDto;
import alexander.sergeev.dto.StatsDto;
import alexander.sergeev.service.HitService;
import alexander.sergeev.validation.ValidationMarker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

import static alexander.sergeev.formatter.FormatterDateTime.DATE_TIME_PATTERN;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HitsController {

    private final HitService hitService;

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<StatsDto> getStats(
            HttpServletRequest request,
            @RequestParam(name = "start") @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime end,
            @RequestParam(name = "uris", defaultValue = "") List<String> uris,
            @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        log.info("{} {}?{}", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return hitService.getStats(start, end, uris, unique);
    }

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public HitDto postHit(
            HttpServletRequest request,
            @RequestBody @Validated(ValidationMarker.OnCreate.class) HitDto hitDto) {
        log.info("{} {} {}", request.getMethod(), request.getRequestURI(), hitDto);
        return hitService.postHit(hitDto);
    }

}
