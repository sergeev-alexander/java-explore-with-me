package alexander.sergeev.controller.admin_controller;

import alexander.sergeev.dto.user_dto.NewUserDto;
import alexander.sergeev.dto.user_dto.UserDto;
import alexander.sergeev.service.UserService;
import alexander.sergeev.validation.ValidationMarker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<UserDto> getUsers(
            HttpServletRequest request,
            @RequestParam(name = "ids", required = false) List<Long> ids,
            @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer firstElement,
            @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        log.info("{} {}?{}", request.getMethod(), request.getRequestURI(), request.getQueryString());
        ids = ids == null ? Collections.emptyList() : ids;
        return userService.getUsers(ids, PageRequest.of(firstElement / size, size));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserDto postUser(
            HttpServletRequest request,
            @RequestBody @Validated(value = ValidationMarker.OnCreate.class) NewUserDto newUserDto) {
        log.info("{} {} {}", request.getMethod(), request.getRequestURI(), newUserDto);
        return userService.postUser(newUserDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUserById(
            HttpServletRequest request,
            @PathVariable(name = "id") @Positive Long userId) {
        log.info("{} {}", request.getMethod(), request.getRequestURI());
        userService.deleteUserById(userId);
    }

}
