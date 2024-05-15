package alexander.sergeev.controller.admin_controller;

import alexander.sergeev.dto.comment_dto.CommentDto;
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
@RequestMapping("/admin/comments")
@RequiredArgsConstructor
public class AdminCommentController {

    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public CommentDto getCommentByIdByAdmin(
            HttpServletRequest request,
            @PathVariable(name = "commentId") @Positive Long commentId) {
        log.info("{} {}", request.getMethod(), request.getRequestURI());
        return commentService.getCommentByIdByAdmin(commentId);
    }

    @PatchMapping("/{commentId}")
    @ResponseStatus(code = HttpStatus.OK)
    public CommentDto patchCommentByIdByAdmin(
            HttpServletRequest request,
            @PathVariable(name = "commentId") @Positive Long commentId,
            @RequestBody @Validated(ValidationMarker.OnAdminUpdate.class) UpdateCommentDto updateCommentDto) {
        log.info("{} {} {}", request.getMethod(), request.getRequestURI(), updateCommentDto);
        return commentService.patchCommentByIdByAdmin(commentId, updateCommentDto);
    }

}
