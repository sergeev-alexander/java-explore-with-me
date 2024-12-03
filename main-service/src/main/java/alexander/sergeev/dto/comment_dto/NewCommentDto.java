package alexander.sergeev.dto.comment_dto;

import alexander.sergeev.validation.ValidationMarker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCommentDto {

    private Long id;

    @NotBlank(groups = ValidationMarker.OnCreate.class,
            message = "Creating comment text field is blank!")
    @Size(min = 2, max = 2000, groups = ValidationMarker.OnCreate.class,
            message = "Creating comment text field is less than 2 " +
                    "or is bigger than 2000 characters!")
    private String text;

}
