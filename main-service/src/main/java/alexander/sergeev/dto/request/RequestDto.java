package alexander.sergeev.dto.request;

import alexander.sergeev.model.RequestStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static alexander.sergeev.formatter.FormatterDateTime.DATE_TIME_PATTERN;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestDto {

    Long id;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    LocalDateTime created;

    Long event;

    Long requester;

    RequestStatus status;

}
