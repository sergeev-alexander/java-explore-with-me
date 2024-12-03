package alexander.sergeev.dto.compilation_dto;

import alexander.sergeev.validation.ValidationMarker;
import alexander.sergeev.validation.compilation_validation.UpdateCompilationTitleValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCompilationDto {

    private Set<Long> events = new HashSet<>();
    private Boolean pinned;

    @UpdateCompilationTitleValidation(groups = ValidationMarker.OnUpdate.class)
    private String title;

}
