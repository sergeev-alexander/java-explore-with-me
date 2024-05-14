package alexander.sergeev.controller.public_controller;

import alexander.sergeev.dto.comment_dto.CommentDto;
import alexander.sergeev.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/comments")
@RequiredArgsConstructor
public class PublicCommentController {

    private final CommentService commentService;

    @GetMapping("/{eventId}")
    public List<CommentDto> getAllEventComments(
            HttpServletRequest request,
            @PathVariable(name = "eventId") @Positive Long eventId,
            @RequestParam(name = "from", defaultValue = "0") Integer firstElement,
            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("{} {} {}", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return commentService.getAllEventComments(eventId, PageRequest.of(firstElement / size, size));
    }

}
