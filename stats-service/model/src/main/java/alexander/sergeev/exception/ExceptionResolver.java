package alexander.sergeev.exception;

import alexander.sergeev.model.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static alexander.sergeev.formatter.FormatterDateTime.DATE_TIME_FORMATTER;

@Slf4j
@RestControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> notFoundExceptionHandle(Exception e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        log.error("{} : {}", e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(
                new ApiError(
                        e.getMessage(),
                        e.getCause() != null ? e.getCause().toString() : "Cause is nonexistent or unknown!",
                        httpStatus.toString(),
                        LocalDateTime.now().format(DATE_TIME_FORMATTER),
                        e.getStackTrace()),
                httpStatus);
    }

    @ExceptionHandler({ConflictException.class,
            ConstraintViolationException.class,
            SQLException.class,
            DataIntegrityViolationException.class})
    public ResponseEntity<ApiError> conflictExceptionHandle(Exception e) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        log.error("{} : {}", e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(
                new ApiError(
                        e.getMessage(),
                        e.getCause() != null ? e.getCause().toString() : "Cause is nonexistent or unknown!",
                        httpStatus.toString(),
                        LocalDateTime.now().format(DATE_TIME_FORMATTER),
                        e.getStackTrace()),
                httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> methodArgumentNotValidHandle(MethodArgumentNotValidException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Map<String, String> response = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            response.put(error.getField(), error.getDefaultMessage());
        }
        log.error("{} : {}", e.getClass().getSimpleName(), response.entrySet());
        return new ResponseEntity<>(
                new ApiError(
                        e.getMessage(),
                        e.getCause() != null
                                ? e.getCause().toString()
                                : response.isEmpty()
                                ? "Cause is nonexistent or unknown!"
                                : response.entrySet().toString(),
                        httpStatus.toString(),
                        LocalDateTime.now().format(DATE_TIME_FORMATTER),
                        e.getStackTrace()),
                httpStatus);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class,
            BadRequestException.class})
    public ResponseEntity<ApiError> badRequestExceptionHandle(Exception e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        log.error("{} : {}", e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(
                new ApiError(
                        e.getMessage(),
                        e.getCause() != null ? e.getCause().toString() : "Cause is nonexistent or unknown!",
                        httpStatus.toString(),
                        LocalDateTime.now().format(DATE_TIME_FORMATTER),
                        e.getStackTrace()),
                httpStatus);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> throwableHandle(Exception e) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        log.error("{} : {}", e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(
                new ApiError(
                        e.getMessage(),
                        e.getCause() != null ? e.getCause().toString() : "Cause is nonexistent or unknown!",
                        httpStatus.toString(),
                        LocalDateTime.now().format(DATE_TIME_FORMATTER),
                        e.getStackTrace()),
                httpStatus);
    }

}
