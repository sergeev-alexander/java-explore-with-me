package alexander.sergeev.repository;

import alexander.sergeev.exception.ConflictException;
import alexander.sergeev.exception.NotFoundException;
import alexander.sergeev.model.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> , JpaSpecificationExecutor<Event> {

    List<Event> findByInitiatorId(Long initiatorId, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(Long eventId, Long initiatorId);

    Boolean existsByCategoryId(Long categoryId);

    Set<Event> findByIdIn(Set<Long> ids);

    @Modifying
    @Query("UPDATE Event as e " +
            "SET e.confirmedRequests = e.confirmedRequests + :increment " +
            "WHERE e.id = :eventId")
    void incrementEventConfirmedRequestsById(Long eventId, Long increment);

    @Modifying
    @Query("UPDATE Event as e " +
            "SET e.confirmedRequests = e.confirmedRequests - :decrement " +
            "WHERE e.id = :eventId")
    void decrementEventConfirmedRequestsById(Long eventId, Long decrement);

    default Event getEventById(Long id) {
        return findById(id)
                .orElseThrow(() -> new NotFoundException("There's no event with id " + id));
    }

    default void checkDeletingCategoryById(Long categoryId) {
        if (existsByCategoryId(categoryId))
            throw new ConflictException("There are actual events with category id " + categoryId);
    }

    default void checkEventById(Long eventId) {
        if (!existsById(eventId))
            throw new NotFoundException("There's no event with id " + eventId);
    }

}
