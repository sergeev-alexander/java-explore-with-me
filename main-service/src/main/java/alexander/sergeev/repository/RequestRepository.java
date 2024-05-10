package alexander.sergeev.repository;

import alexander.sergeev.exception.NotFoundException;
import alexander.sergeev.model.Request;
import alexander.sergeev.model.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Set<Request> findByEventId(Long eventId);

    Set<Request> findByRequesterId(Long requesterId);

    Set<Request> findByIdIn(Set<Long> ids);

    Boolean existsByEventIdAndRequesterId(Long eventId, Long requesterId);

    Set<Request> findByEventIdAndStatus(Long eventId, RequestStatus requestStatus);

    default Request getRequestById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("There's no request with id " + id));
    }

}
