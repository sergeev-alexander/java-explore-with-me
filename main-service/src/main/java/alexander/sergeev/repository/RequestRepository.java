package alexander.sergeev.repository;

import alexander.sergeev.exception.NotFoundException;
import alexander.sergeev.model.Request;
import alexander.sergeev.model.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Set<Request> findByEventId(Long eventId);

    Set<Request> findByRequesterId(Long requesterId);

    @Modifying
    @Query("UPDATE Request as r " +
            "SET r.status = :requestStatus " +
            "WHERE r.id IN :requestIds")
    void setRequestStatusByIds(Set<Long> requestIds, RequestStatus requestStatus);

    Set<Request> findByIdIn(Set<Long> ids);

    @Modifying
    @Query("UPDATE Request as r " +
            "SET r.status = 'REJECTED' " +
            "WHERE r.status = 'PENDING'")
    void rejectAllPendingRequests();

    Boolean existsByEventIdAndRequesterId(Long eventId, Long requesterId);

    default Request getRequestById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("There's no request with id " + id));
    }

}
