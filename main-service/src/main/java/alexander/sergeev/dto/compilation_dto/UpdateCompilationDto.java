package alexander.sergeev.dto.compilation_dto;

import alexander.sergeev.validation.ValidationMarker;
import alexander.sergeev.validation.compilation_validation.UpdateCompilationTitleValidation;
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
public class UpdateCompilationDto {

    Set<Long> events = new HashSet<>();
    Boolean pinned;

    @UpdateCompilationTitleValidation(groups = ValidationMarker.OnUpdate.class)
    String title;

}
