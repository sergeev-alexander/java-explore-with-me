package alexander.sergeev.service;

import alexander.sergeev.StatsClient;
import alexander.sergeev.dto.HitDto;
import alexander.sergeev.dto.StatsDto;
import alexander.sergeev.dto.event_dto.EventFullDto;
import alexander.sergeev.dto.event_dto.EventShortDto;
import alexander.sergeev.dto.event_dto.NewEventDto;
import alexander.sergeev.dto.event_dto.UpdateEventDto;
import alexander.sergeev.dto.request.RequestDto;
import alexander.sergeev.dto.request.RequestResponseDto;
import alexander.sergeev.dto.request.RequestUpdateDto;
import alexander.sergeev.exception.BadRequestException;
import alexander.sergeev.exception.ConflictException;
import alexander.sergeev.exception.NotFoundException;
import alexander.sergeev.mapper.EventMapper;
import alexander.sergeev.mapper.RequestMapper;
import alexander.sergeev.model.*;
import alexander.sergeev.repository.CategoryRepository;
import alexander.sergeev.repository.EventRepository;
import alexander.sergeev.repository.RequestRepository;
import alexander.sergeev.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final StatsClient statsClient;
    private final ObjectMapper objectmapper;

    public static final String APP_NAME = "ewm-main-service";

    public List<EventShortDto> getAllInitiatorEvents(Long userId, Pageable pageable) {
        return setViewsToEventList(eventRepository.findByInitiatorId(userId, pageable))
                .stream()
                .map(EventMapper::mapEventToShortDto)
                .collect(Collectors.toList());
    }

    public EventFullDto getInitiatorEventById(Long initiatorId, Long eventId) {
        return EventMapper.mapEventToFullDto(setViewsToEvent(getUserEventById(initiatorId, eventId)));
    }

    public List<RequestDto> getRequestsForInitiatorEvents(Long initiatorId, Long eventId) {
        getInitiatorEventById(initiatorId, eventId);
        return requestRepository.findByEventId(eventId)
                .stream()
                .map(RequestMapper::mapRequestToDto)
                .collect(Collectors.toList());
    }

    public List<EventFullDto> getEventsByAdmin(Set<Long> users,
                                               Set<EventState> states,
                                               Set<Long> categories,
                                               LocalDateTime start,
                                               LocalDateTime end,
                                               Pageable pageable) {
        if (start != null && end != null && !start.isBefore(end))
            throw new BadRequestException("Start is after end!");

//        Page<Event> events = eventRepository.findAll(((root, query, criteriaBuilder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//            if (users != null && !users.isEmpty())
//                predicates.add(root.get("initiator").in(users));
//            if (states != null && !states.isEmpty())
//                predicates.add(root.get("state").in(states));
//            if (categories != null && !categories.isEmpty())
//                predicates.add(root.get("category").in(categories));
//            if (start != null)
//                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), start));
//            if (end != null)
//                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), end));
//            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
//        }), pageable);

        Specification<Event> specification = Specification.where(null);
        if (users != null && !users.isEmpty())
            specification = specification.and((root, query, criteriaBuilder) ->
                    root.get("initiator").in(users));
        if (states != null && !states.isEmpty())
            specification = specification.and((root, query, criteriaBuilder) ->
                    root.get("state").in(states));
        if (categories != null && !categories.isEmpty())
            specification = specification.and((root, query, criteriaBuilder) ->
                    root.get("category").in(categories));
        if (start != null)
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), start));
        if (end != null)
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), end));
        List<Event> eventList = eventRepository.findAll(specification, pageable).toList();
        return setViewsToEventList(eventList)
                .stream()
                .map(EventMapper::mapEventToFullDto)
                .collect(Collectors.toList());
    }

    public List<EventShortDto> getEventsByPublic(HttpServletRequest httpServletRequest,
                                                 String text,
                                                 Set<Long> categories,
                                                 Boolean paid,
                                                 LocalDateTime start,
                                                 LocalDateTime end,
                                                 Boolean onlyAvailable,
                                                 String sort,
                                                 Integer firstElement,
                                                 Integer size) {
        if (start != null && end != null && !start.isBefore(end))
            throw new BadRequestException("Start is after end!");
        Specification<Event> specification = Specification.where((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("state"), EventState.PUBLISHED));
        if (text != null && !text.isBlank()) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("annotation")),
                                    "%" + text.toLowerCase() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                                    "%" + text.toLowerCase() + "%")));
        }
        if (categories != null && !categories.isEmpty())
            specification = specification.and((root, query, criteriaBuilder) ->
                    root.get("category").in(categories));
        if (paid != null)
            specification = specification.and(((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("paid"), paid)));
        if (start == null)
            start = LocalDateTime.now();
        final LocalDateTime startDateTime = start;
        specification = specification.and((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), startDateTime));
        if (end != null)
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), end));
        if (onlyAvailable)
            specification = specification.and(((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThan(root.get("participantLimit"), root.get("confirmedRequests"))));
        Sort sorting = Sort.by(Sort.Direction.ASC, "id");
        if (sort != null) {
            if (sort.equals(EventSort.EVENT_DATE.toString()))
                sorting = Sort.by(Sort.Direction.ASC, "event_date");
            if (sort.equals(EventSort.VIEWS.toString()))
                sorting = Sort.by(Sort.Direction.ASC, "views");
        }
        Pageable pageable = PageRequest.of(firstElement / size, size, sorting);
        sendHitDtoToStatsServer(httpServletRequest);
        return eventRepository.findAll(specification, pageable)
                .stream()
                .map(EventMapper::mapEventToShortDto)
                .collect(Collectors.toList());
    }

    public EventFullDto getEventByIdByPublic(HttpServletRequest httpServletRequest,
                                             Long eventId) {
        Event event = eventRepository.getEventById(eventId);
        if (event.getState() != EventState.PUBLISHED)
            throw new NotFoundException("Event " + eventId + " is not published yet!");
        sendHitDtoToStatsServer(httpServletRequest);
        return EventMapper.mapEventToFullDto(eventRepository.save(setViewsToEvent(event)));
    }

    public EventFullDto postEvent(Long userId, NewEventDto newEventDto) {
        Event event = EventMapper.mapNewDtoToEvent(newEventDto);
        event.setInitiator(userRepository.getUserById(userId));
        event.setCategory(categoryRepository.getCategoryById(newEventDto.getCategory()));
        return EventMapper.mapEventToFullDto(eventRepository.save(event));
    }

    public EventFullDto patchInitiatorEventById(Long userId, Long eventId, UpdateEventDto updateEventDto) {
        Event event = getUserEventById(userId, eventId);
        if (!(event.getState() == EventState.PENDING || event.getState() == EventState.REJECTED))
            throw new ConflictException("Updating event status must be PENDING or REJECTED!");
        if (updateEventDto.getStateAction() != null) {
            if (updateEventDto.getStateAction().equals(EventUserStateAction.SEND_TO_REVIEW.toString()))
                event.setState(EventState.PENDING);
            else event.setState(EventState.CANCELED);
        }
//        if (updateEventDto.getAnnotation() != null)
//            event.setAnnotation(updateEventDto.getAnnotation());
//        if (updateEventDto.getCategory() != null)
//            event.setCategory(categoryRepository.getCategoryById(updateEventDto.getCategory()));
//        if (updateEventDto.getDescription() != null)
//            event.setDescription(updateEventDto.getDescription());
//        if (updateEventDto.getEventDate() != null)
//            event.setEventDate(updateEventDto.getEventDate());
//        if (updateEventDto.getLocation() != null)
//            event.setLocation(updateEventDto.getLocation());
//        if (updateEventDto.getPaid() != null)
//            event.setPaid(updateEventDto.getPaid());
//        if (updateEventDto.getParticipantLimit() != null) {
//            if (event.getConfirmedRequests() > updateEventDto.getParticipantLimit())
//                throw new ConflictException("Updating event participation limit " +
//                        "is less than confirmed request quantity!");
//            event.setParticipantLimit(updateEventDto.getParticipantLimit());
//        }
//        if (updateEventDto.getRequestModeration() != null)
//            event.setRequestModeration(updateEventDto.getRequestModeration());
//        if (updateEventDto.getTitle() != null)
//            event.setTitle(updateEventDto.getTitle());

        updateEventFields(event, updateEventDto);

        return EventMapper.mapEventToFullDto(setViewsToEvent(eventRepository.save(event)));
    }

    public EventFullDto patchEventByIdByAdmin(Long eventId, UpdateEventDto updateEventDto) {
        Event event = eventRepository.getEventById(eventId);
        if (updateEventDto.getStateAction() != null && event.getState() != EventState.PENDING)
            throw new ConflictException("Not allowed to publish or reject not pending event!");
//        if (updateEventDto.getAnnotation() != null)
//            event.setAnnotation(updateEventDto.getAnnotation());
//        if (updateEventDto.getCategory() != null)
//            event.setCategory(categoryRepository.getCategoryById(updateEventDto.getCategory()));
//        if (updateEventDto.getDescription() != null)
//            event.setDescription(updateEventDto.getDescription());
//        if (updateEventDto.getEventDate() != null)
//            event.setEventDate(updateEventDto.getEventDate());
//        if (updateEventDto.getLocation() != null)
//            event.setLocation(updateEventDto.getLocation());
//        if (updateEventDto.getPaid() != null)
//            event.setPaid(updateEventDto.getPaid());
//        if (updateEventDto.getParticipantLimit() != null) {
//            if (event.getConfirmedRequests() > updateEventDto.getParticipantLimit())
//                throw new ConflictException("Updating event participation limit " +
//                        "is less than confirmed request quantity!");
//            event.setParticipantLimit(updateEventDto.getParticipantLimit());
//        }
//        if (updateEventDto.getRequestModeration() != null)
//            event.setRequestModeration(updateEventDto.getRequestModeration());
//        if (updateEventDto.getTitle() != null)
//            event.setTitle(updateEventDto.getTitle());

        updateEventFields(event, updateEventDto);

        if (updateEventDto.getStateAction() != null
                && updateEventDto.getStateAction().equals(EventAdminStateAction.REJECT_EVENT.toString())) {
            event.setState(EventState.REJECTED);
        }
        if (updateEventDto.getStateAction() != null
                && updateEventDto.getStateAction().equals(EventAdminStateAction.PUBLISH_EVENT.toString())) {
            event.setState(EventState.PUBLISHED);
            event.setPublishedOn(LocalDateTime.now());
        }
        return EventMapper.mapEventToFullDto(setViewsToEvent(eventRepository.save(event)));
    }

    public RequestResponseDto patchInitiatorEventRequests(Long userId,
                                                          Long eventId,
                                                          RequestUpdateDto requestUpdateDto) {
        Event event = getUserEventById(userId, eventId);
        long participantLimit = event.getParticipantLimit();
        if (participantLimit == 0 || !event.getRequestModeration())
            throw new ConflictException("Event " + eventId + " doesn't need request moderation!");
        if (requestUpdateDto.getStatus().equals(RequestUpdateStatus.REJECTED.toString()))
            requestRepository.setRequestStatusByIds(requestUpdateDto.getRequestIds(),
                    RequestStatus.REJECTED);
        else {
            Set<Request> requestSet = requestRepository.findByIdIn(requestUpdateDto.getRequestIds());
            if (!requestSet
                    .stream()
                    .allMatch(request -> requestUpdateDto.getRequestIds().contains(request.getId())))
                throw new ConflictException("All confirming requests must have pending request status!");
            if (event.getConfirmedRequests() >= participantLimit)
                throw new ConflictException("Event already reached participation limit!");
            requestRepository.setRequestStatusByIds(requestUpdateDto.getRequestIds(),
                    RequestStatus.CONFIRMED);
            eventRepository.incrementEventConfirmedRequestsById(event.getId(), (long) requestSet.size());
            if (participantLimit - event.getConfirmedRequests() - requestUpdateDto.getRequestIds().size() <= 0)
                requestRepository.rejectAllPendingRequests();
        }
        Set<Request> requestSet = requestRepository.findByEventId(eventId);
        return new RequestResponseDto(
                requestSet
                        .stream()
                        .filter(request -> request.getStatus().equals(RequestStatus.CONFIRMED))
                        .map(RequestMapper::mapRequestToDto)
                        .collect(Collectors.toSet()),
                requestSet
                        .stream()
                        .filter(request -> request.getStatus().equals(RequestStatus.REJECTED))
                        .map(RequestMapper::mapRequestToDto)
                        .collect(Collectors.toSet()));
    }

    private Event getUserEventById(Long initiatorId, Long eventId) {
        checkUserAndEvent(initiatorId, eventId);
        return setViewsToEvent(eventRepository.findByIdAndInitiatorId(eventId, initiatorId)
                .orElseThrow(() -> new NotFoundException("Event with id " + eventId
                        + " was not initiated by user " + initiatorId)));
    }

    private void checkUserAndEvent(Long userId, Long eventId) {
        userRepository.checkUserById(userId);
        eventRepository.checkEventById(eventId);
    }

    private void updateEventFields(Event event, UpdateEventDto updateEventDto) {
        if (updateEventDto.getAnnotation() != null)
            event.setAnnotation(updateEventDto.getAnnotation());
        if (updateEventDto.getCategory() != null)
            event.setCategory(categoryRepository.getCategoryById(updateEventDto.getCategory()));
        if (updateEventDto.getDescription() != null)
            event.setDescription(updateEventDto.getDescription());
        if (updateEventDto.getEventDate() != null)
            event.setEventDate(updateEventDto.getEventDate());
        if (updateEventDto.getLocation() != null)
            event.setLocation(updateEventDto.getLocation());
        if (updateEventDto.getPaid() != null)
            event.setPaid(updateEventDto.getPaid());
        if (updateEventDto.getParticipantLimit() != null) {
            if (event.getConfirmedRequests() > updateEventDto.getParticipantLimit())
                throw new ConflictException("Updating event participation limit " +
                        "is less than confirmed request quantity!");
            event.setParticipantLimit(updateEventDto.getParticipantLimit());
        }
        if (updateEventDto.getRequestModeration() != null)
            event.setRequestModeration(updateEventDto.getRequestModeration());
        if (updateEventDto.getTitle() != null)
            event.setTitle(updateEventDto.getTitle());
    }

    private void sendHitDtoToStatsServer(HttpServletRequest request) {
        statsClient.postHit(
                HitDto.builder()
                        .id(null)
                        .app(APP_NAME)
                        .uri(request.getRequestURI())
                        .ip(request.getRemoteAddr())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    private Event setViewsToEvent(Event event) {
        LocalDateTime start = event.getPublishedOn() != null
                ? event.getPublishedOn()
                : LocalDateTime.now().minusYears(10);
        LocalDateTime end = LocalDateTime.now();
        List<StatsDto> statsDtoList = statsClient.getStats(start, end,
                List.of("/events/" + event.getId()), true);
        if (statsDtoList == null || statsDtoList.isEmpty()) event.setViews(0L);
        else event.setViews(statsDtoList.get(0).getHits());
        return event;
    }

    private List<Event> setViewsToEventList(List<Event> eventList) {
        LocalDateTime start = eventList
                .stream()
                .map(Event::getPublishedOn)
                .filter(Objects::nonNull)
                .min(LocalDateTime::compareTo)
                .orElse(LocalDateTime.now().minusYears(10));
        List<String> uris = eventList
                .stream()
                .map(event -> ("/events/" + event.getId()))
                .collect(Collectors.toList());
        Map<Long, Long> hitMap = statsClient.getStats(start, LocalDateTime.now(), uris, true)
                .stream()
                .collect(Collectors.toMap(statsDto -> Long.parseLong(statsDto.getUri().substring(8)),
                        StatsDto::getHits));
        return eventList
                .stream()
                .peek(event -> event.setViews(hitMap.getOrDefault(event.getId(), 0L)))
                .collect(Collectors.toList());
    }

}
