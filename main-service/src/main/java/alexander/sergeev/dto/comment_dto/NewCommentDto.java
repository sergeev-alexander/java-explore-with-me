package alexander.sergeev.dto.comment_dto;

import alexander.sergeev.validation.ValidationMarker;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCommentDto {

    Long id;

    @NotBlank(groups = ValidationMarker.OnCreate.class,
            message = "Creating comment text field is blank!")
    @Size(min = 2, max = 2000, groups = ValidationMarker.OnCreate.class,
            message = "Creating comment text field is less than 2 " +
                    "or is bigger than 2000 characters!")
    String text;

}
