package alexander.sergeev.dto.compilation_dto;

import alexander.sergeev.dto.event_dto.EventShortDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {

    private Long id;

    private String title;

    private Boolean pinned;

    private Set<EventShortDto> events = new HashSet<>();

}
