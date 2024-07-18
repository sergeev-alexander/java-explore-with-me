package alexander.sergeev.dto.compilation_dto;

import alexander.sergeev.validation.ValidationMarker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {

    private Set<Long> events = new HashSet<>();

    private Boolean pinned;

    @NotBlank(groups = ValidationMarker.OnCreate.class,
            message = "Creating compilation title field is blank!")
    @Size(min = 1, max = 50, groups = ValidationMarker.OnCreate.class,
            message = "Creating compilation title field is smaller than 1 or is bigger than 50 characters!")
    private String title;

}
