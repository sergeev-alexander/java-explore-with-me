package alexander.sergeev.controller.admin_controller;

import alexander.sergeev.dto.event_dto.EventFullDto;
import alexander.sergeev.dto.event_dto.UpdateEventDto;
import alexander.sergeev.model.EventState;
import alexander.sergeev.service.EventService;
import alexander.sergeev.validation.ValidationMarker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class AdminEventController {

    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> getEventsByAdmin(
            HttpServletRequest request,
            @RequestParam(name = "users", required = false) Set<Long> users,
            @RequestParam(name = "states", required = false) Set<EventState> states,
            @RequestParam(name = "categories", required = false) Set<Long> categories,
            @RequestParam(value = "rangeStart", required = false)
            @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime start,
            @RequestParam(value = "rangeEnd", required = false)
            @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime end,
            @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer firstElement,
            @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        log.info("{} {} {}", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return eventService.getEventsByAdmin(users, states, categories, start, end,
                PageRequest.of(firstElement / size, size));
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEventByIdByAdmin(
            HttpServletRequest request,
            @PathVariable(name = "eventId") @Positive Long eventId,
            @RequestBody @Validated(ValidationMarker.OnAdminUpdate.class) UpdateEventDto updateEventDto) {
        log.info("{} {} {}", request.getMethod(), request.getRequestURI(), updateEventDto);
        return eventService.patchEventByIdByAdmin(eventId, updateEventDto);
    }
}
