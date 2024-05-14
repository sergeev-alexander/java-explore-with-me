package alexander.sergeev.controller.private_controller;

import alexander.sergeev.dto.comment_dto.CommentDto;
import alexander.sergeev.dto.comment_dto.NewCommentDto;
import alexander.sergeev.dto.comment_dto.UpdateCommentDto;
import alexander.sergeev.service.CommentService;
import alexander.sergeev.validation.ValidationMarker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;

@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}/events/{eventId}/comments")
@RequiredArgsConstructor
public class PrivateCommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CommentDto postComment(
            HttpServletRequest request,
            @PathVariable(name = "userId") @Positive Long userId,
            @PathVariable(name = "eventId") @Positive Long eventId,
            @RequestBody @Validated(ValidationMarker.OnCreate.class)NewCommentDto newCommentDto) {
        log.info("{} {} {}", request.getMethod(), request.getRequestURI(), newCommentDto);
        return commentService.postComment(userId, eventId, newCommentDto);
    }

    @PatchMapping("/{commentId}")
    @ResponseStatus(code = HttpStatus.OK)
    public CommentDto patchAuthorsCommentById(
            HttpServletRequest request,
            @PathVariable(name = "userId") @Positive Long userId,
            @PathVariable(name = "eventId") @Positive Long eventId,
            @PathVariable(name = "commentId") @Positive Long commentId,
            @RequestBody @Validated(ValidationMarker.OnUpdate.class) UpdateCommentDto updateCommentDto) {
        log.info("{} {} {}", request.getMethod(), request.getRequestURI(), updateCommentDto);
        return commentService.patchAuthorsCommentById(userId, eventId, commentId, updateCommentDto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAuthorsCommentById(
            HttpServletRequest request,
            @PathVariable(name = "userId") @Positive Long userId,
            @PathVariable(name = "eventId") @Positive Long eventId,
            @PathVariable(name = "commentId") @Positive Long commentId) {
        log.info("{} {}", request.getMethod(), request.getRequestURI());
        commentService.deleteAuthorsCommentById(userId, eventId, commentId);
    }

}
