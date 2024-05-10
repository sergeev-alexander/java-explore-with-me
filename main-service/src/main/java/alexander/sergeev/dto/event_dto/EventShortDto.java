package alexander.sergeev.dto.event_dto;

import alexander.sergeev.dto.category_dto.CategoryDto;
import alexander.sergeev.dto.user_dto.UserShortDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static alexander.sergeev.formatter.FormatterDateTime.DATE_TIME_PATTERN;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventShortDto {

    Long id;

    String annotation;

    CategoryDto category;

    Long confirmedRequests;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    LocalDateTime eventDate;

    UserShortDto initiator;
    Boolean paid;

    String title;

    Long views;

}
