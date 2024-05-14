package alexander.sergeev.dto.compilation_dto;

import alexander.sergeev.dto.event_dto.EventShortDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationDto {

    Long id;

    String title;

    Boolean pinned;

    Set<EventShortDto> events = new HashSet<>();

}
