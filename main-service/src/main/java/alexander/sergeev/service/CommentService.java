package alexander.sergeev.service;

import alexander.sergeev.dto.comment_dto.CommentDto;
import alexander.sergeev.dto.comment_dto.NewCommentDto;
import alexander.sergeev.dto.comment_dto.UpdateCommentDto;
import alexander.sergeev.exception.ConflictException;
import alexander.sergeev.mapper.CommentMapper;
import alexander.sergeev.model.*;
import alexander.sergeev.repository.CommentRepository;
import alexander.sergeev.repository.EventRepository;
import alexander.sergeev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;

    public List<CommentDto> getAllEventComments(Long eventId, Pageable pageable) {
        return commentRepository.findByEventIdAndState(eventId, CommentState.PUBLISHED,
                        pageable)
                .stream()
                .sorted(Comparator.comparing(Comment::getCreated))
                .map(CommentMapper::mapCommentToDto)
                .collect(Collectors.toList());
    }

    public CommentDto getCommentById(Long commentId) {
        return CommentMapper.mapCommentToDto(commentRepository.getCommentById(commentId));
    }

    public CommentDto postComment(Long userId, Long eventId, NewCommentDto newCommentDto) {
        User author = userRepository.getUserById(userId);
        Event event = eventRepository.getEventById(eventId);
        if (event.getState() != EventState.PUBLISHED)
            throw new ConflictException("Not allowed to comment not published event!");
        if (event.getInitiator().getId().equals(userId))
            throw new ConflictException("Initiator can't comment his own event!");
        Comment comment = new Comment(
                null,
                LocalDateTime.now(),
                event,
                author,
                newCommentDto.getText(),
                CommentState.PENDING);
        return CommentMapper.mapCommentToDto(commentRepository.save(comment));
    }

    public CommentDto patchCommentByIdByAdmin(Long commentId, UpdateCommentDto updateCommentDto) {
        Comment comment = commentRepository.getCommentById(commentId);
        if (comment.getState() != CommentState.PENDING)
            throw new ConflictException("Not allowed to update not pending event!");
        if (updateCommentDto.getText() != null)
            comment.setText(updateCommentDto.getText());
        if (updateCommentDto.getStateAction().equals(CommentAdminStateAction.PUBLISH_COMMENT.toString()))
            comment.setState(CommentState.PUBLISHED);
        else comment.setState(CommentState.REJECTED);
        return CommentMapper.mapCommentToDto(commentRepository.save(comment));
    }

    public CommentDto patchAuthorsCommentById(Long userId,
                                              Long eventId,
                                              Long commentId,
                                              UpdateCommentDto updateCommentDto) {
        userRepository.checkUserById(userId);
        eventRepository.checkEventById(eventId);
        Comment comment = commentRepository.getCommentById(commentId);
        if (comment.getState() != CommentState.PENDING)
            throw new ConflictException("Not allowed to update not pending event!");
        if (updateCommentDto.getText() != null)
            comment.setText(updateCommentDto.getText());
        if (updateCommentDto.getStateAction().equals(UserStateAction.CANCEL_REVIEW.toString()))
            comment.setState(CommentState.CANCELED);
        return CommentMapper.mapCommentToDto(commentRepository.save(comment));
    }

    public void deleteAuthorsCommentById(Long userId, Long eventId, Long commentId) {
        userRepository.checkUserById(userId);
        eventRepository.checkEventById(eventId);
        Comment comment = commentRepository.getCommentById(commentId);
        if (!Objects.equals(comment.getAuthor().getId(), userId))
            throw new ConflictException("User " + userId + " is not author of comment " + commentId);
        commentRepository.delete(comment);
    }

}
