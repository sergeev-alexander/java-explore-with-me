package alexander.sergeev.controller.private_controller;

import alexander.sergeev.dto.request.RequestDto;
import alexander.sergeev.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(value = "/users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateRequestController {

    private final RequestService requestService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Set<RequestDto> getAllUserRequests(
            HttpServletRequest request,
            @PathVariable(name = "userId") @Positive Long userId) {
        log.info("{} {}", request.getMethod(), request.getRequestURI());
        return requestService.getAllUserRequests(userId);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public RequestDto postRequest(
            HttpServletRequest request,
            @PathVariable(name = "userId") @Positive Long userId,
            @RequestParam(name = "eventId") @Positive Long eventId) {
        log.info("{} {}?{}", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return requestService.postRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    @ResponseStatus(code = HttpStatus.OK)
    public RequestDto patchRequestById(
            HttpServletRequest request,
            @PathVariable(name = "userId") @Positive Long userId,
            @PathVariable(name = "requestId") @Positive Long requestId) {
        log.info("{} {}", request.getMethod(), request.getRequestURI());
        return requestService.patchRequestById(userId, requestId);
    }

}
