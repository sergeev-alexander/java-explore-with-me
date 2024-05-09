package alexander.sergeev.controller.private_controller;

import alexander.sergeev.dto.event_dto.EventFullDto;
import alexander.sergeev.dto.event_dto.EventShortDto;
import alexander.sergeev.dto.event_dto.NewEventDto;
import alexander.sergeev.dto.event_dto.UpdateEventDto;
import alexander.sergeev.dto.request.RequestDto;
import alexander.sergeev.dto.request.RequestResponseDto;
import alexander.sergeev.dto.request.RequestUpdateDto;
import alexander.sergeev.service.EventService;
import alexander.sergeev.validation.ValidationMarker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventController {

    private final EventService eventService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<EventShortDto> getAllInitiatorEvents(
            HttpServletRequest request,
            @PathVariable(name = "userId") @Positive Long userId,
            @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer firstElement,
            @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        log.info("{} {}?{}", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return eventService.getAllInitiatorEvents(userId, PageRequest.of(firstElement / size, size));
    }

    @GetMapping(path = "/{eventId}")
    @ResponseStatus(code = HttpStatus.OK)
    public EventFullDto getInitiatorEventById(
            HttpServletRequest request,
            @PathVariable(name = "userId") @Positive Long userId,
            @PathVariable(name = "eventId") @Positive Long eventId) {
        log.info("{} {}", request.getMethod(), request.getRequestURI());
        return eventService.getInitiatorEventById(userId, eventId);
    }

    @GetMapping(path = "/{eventId}/requests")
    @ResponseStatus(code = HttpStatus.OK)
    public List<RequestDto> getRequestsForInitiatorEvents(
            HttpServletRequest request,
            @PathVariable(name = "userId") @Positive Long userId,
            @PathVariable(name = "eventId") @Positive Long eventId) {
        log.info("{} {}", request.getMethod(), request.getRequestURI());
        return eventService.getRequestsForInitiatorEvents(userId, eventId);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public EventFullDto postEvent(
            HttpServletRequest request,
            @PathVariable(name = "userId") @Positive Long userId,
            @RequestBody @Validated(ValidationMarker.OnCreate.class) NewEventDto newEventDto) {
        log.info("{} {} {}", request.getMethod(), request.getQueryString(), newEventDto);
        return eventService.postEvent(userId, newEventDto);
    }

    @PatchMapping(path = "/{eventId}")
    @ResponseStatus(code = HttpStatus.OK)
    public EventFullDto patchInitiatorEventById(
            HttpServletRequest request,
            @PathVariable(name = "userId") @Positive Long userId,
            @PathVariable(name = "eventId") @Positive Long eventId,
            @RequestBody @Validated(ValidationMarker.OnUpdate.class) UpdateEventDto updateEventDto) {
        log.info("{} {}", request.getMethod(), request.getRequestURI());
        return eventService.patchInitiatorEventById(userId, eventId, updateEventDto);
    }

    @PatchMapping(path = "/{eventId}/requests")
    @ResponseStatus(code = HttpStatus.OK)
    public RequestResponseDto patchInitiatorEventRequests(
            HttpServletRequest request,
            @PathVariable(name = "userId") @Positive Long userId,
            @PathVariable(name = "eventId") @Positive Long eventId,
            @RequestBody @Validated(ValidationMarker.OnUpdate.class) RequestUpdateDto requestUpdateDto) {
        log.info("{} {} {}", request.getMethod(), request.getRequestURL(), requestUpdateDto);
        return eventService.patchInitiatorEventRequests(userId, eventId, requestUpdateDto);
    }

}
