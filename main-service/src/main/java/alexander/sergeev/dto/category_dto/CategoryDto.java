package alexander.sergeev.dto.category_dto;

import alexander.sergeev.validation.ValidationMarker;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDto {

    Long id;

    @NotNull(groups = ValidationMarker.OnUpdate.class,
            message = "Updating category name field is blank!")
    @Size(min = 1, max = 50, groups = ValidationMarker.OnUpdate.class,
            message = "Updating category name field is smaller than 1 or is bigger than 50 characters!")
    String name;

}
