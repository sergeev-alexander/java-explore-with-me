package alexander.sergeev.dto.event_dto;

import alexander.sergeev.dto.category_dto.CategoryDto;
import alexander.sergeev.dto.user_dto.UserShortDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static alexander.sergeev.formatter.FormatterDateTime.DATE_TIME_PATTERN;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventShortDto {

    private Long id;

    private String annotation;

    private CategoryDto category;

    private Long confirmedRequests;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime eventDate;

    private UserShortDto initiator;

    private Boolean paid;

    private String title;

    private Long views;

}
