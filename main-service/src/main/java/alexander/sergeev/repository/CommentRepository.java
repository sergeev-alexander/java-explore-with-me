package alexander.sergeev.repository;

import alexander.sergeev.exception.NotFoundException;
import alexander.sergeev.model.Comment;
import alexander.sergeev.model.CommentState;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByEventIdAndState(Long eventId, CommentState state, Pageable pageable);

    default Comment getCommentById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("There's no comment with id " + id));
    }

}
