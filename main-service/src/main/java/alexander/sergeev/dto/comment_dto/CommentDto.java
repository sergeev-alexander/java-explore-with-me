package alexander.sergeev.dto.comment_dto;

import alexander.sergeev.dto.event_dto.EventShortDto;
import alexander.sergeev.dto.user_dto.UserShortDto;
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
public class CommentDto {

    Long id;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    LocalDateTime created;

    EventShortDto event;

    UserShortDto author;

    String text;

}
