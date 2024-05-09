package alexander.sergeev.controller.public_controller;

import alexander.sergeev.dto.event_dto.EventFullDto;
import alexander.sergeev.dto.event_dto.EventShortDto;
import alexander.sergeev.service.EventService;
import alexander.sergeev.validation.event_validation.SortValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static alexander.sergeev.formatter.FormatterDateTime.DATE_TIME_PATTERN;

@Slf4j
@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class PublicEventController {

    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getEventsByPublic(
            HttpServletRequest request,
            @RequestParam(name = "text", required = false) String text,
            @RequestParam(name = "categories", required = false) Set<Long> categories,
            @RequestParam(name = "paid", required = false) Boolean paid,
            @RequestParam(name = "rangeStart", required = false)
            @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime start,
            @RequestParam(name = "rangeEnd", required = false)
            @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime end,
            @RequestParam(name = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(name = "sort", required = false) @SortValidation String sort,
            @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer firstElement,
            @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        log.info("{} {} {}", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return eventService.getEventsByPublic(request, text, categories, paid, start, end,
                onlyAvailable, sort, firstElement, size);
    }

    @GetMapping("/{id}")
    public EventFullDto getEventByIdByPublic(
            HttpServletRequest request,
            @PathVariable(name = "id") @Positive Long id) {
        log.info("{} {}", request.getMethod(), request.getRequestURI());
        return eventService.getEventByIdByPublic(request, id);
    }

}
