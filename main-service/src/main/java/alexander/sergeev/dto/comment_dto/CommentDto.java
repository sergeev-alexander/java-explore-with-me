package alexander.sergeev.dto.comment_dto;

import alexander.sergeev.dto.event_dto.EventShortDto;
import alexander.sergeev.dto.user_dto.UserShortDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static alexander.sergeev.formatter.FormatterDateTime.DATE_TIME_PATTERN;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime created;

    private EventShortDto event;

    private UserShortDto author;

    private String text;

}
