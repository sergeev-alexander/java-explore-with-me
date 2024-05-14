package alexander.sergeev.service;

import alexander.sergeev.dto.request.RequestDto;
import alexander.sergeev.exception.ConflictException;
import alexander.sergeev.mapper.RequestMapper;
import alexander.sergeev.model.*;
import alexander.sergeev.repository.EventRepository;
import alexander.sergeev.repository.RequestRepository;
import alexander.sergeev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public Set<RequestDto> getAllUserRequests(Long userId) {
        userRepository.checkUserById(userId);
        return requestRepository.findByRequesterId(userId)
                .stream()
                .map(RequestMapper::mapRequestToDto)
                .collect(Collectors.toSet());
    }

    public RequestDto postRequest(Long userId, Long eventId) {
        User requester = userRepository.getUserById(userId);
        Event event = eventRepository.getEventById(eventId);
        if (event.getState() != EventState.PUBLISHED)
            throw new ConflictException("Event " + eventId + " is not published yet!");
        if (event.getInitiator().getId().equals(userId))
            throw new ConflictException("User " + userId + " is initiator for event " + eventId);
        if (requestRepository.existsByEventIdAndRequesterId(eventId, userId))
            throw new ConflictException("User " + userId + " already posted request for event " + eventId);
        if (event.getParticipantLimit() > 0L
                && event.getConfirmedRequests() >= event.getParticipantLimit())
            throw new ConflictException("Event " + eventId + " already reached participation limit!");
        else {
            RequestStatus creatingRequestStatus = RequestStatus.PENDING;
            if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
                creatingRequestStatus = RequestStatus.CONFIRMED;
                eventRepository.incrementEventConfirmedRequestsById(eventId, 1L);
            }
            return RequestMapper.mapRequestToDto(requestRepository.save(new Request(
                    null,
                    LocalDateTime.now(),
                    event,
                    requester,
                    creatingRequestStatus)));
        }
    }

    public RequestDto patchRequestById(Long userId, Long requestId) {
        Request request = requestRepository.getRequestById(requestId);
        if (!request.getRequester().getId().equals(userId))
            throw new ConflictException("Request " + requestId + "don't belong user " + userId);
        if (request.getStatus() == RequestStatus.CONFIRMED) {
            eventRepository.decrementEventConfirmedRequestsById(request.getEvent().getId(), 1L);
        }
        request.setStatus(RequestStatus.CANCELED);
        return RequestMapper.mapRequestToDto(requestRepository.save(request));
    }

}
