package alexander.sergeev.dto.category_dto;

import alexander.sergeev.validation.ValidationMarker;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCategoryDto {

    Long id;

    @NotBlank(groups = ValidationMarker.OnCreate.class,
            message = "Creating category name field is blank!")
    @Size(min = 1, max = 50, groups = ValidationMarker.OnCreate.class,
            message = "Creating category name field is smaller than 1 or is bigger than 50 characters!")
    String name;

}
