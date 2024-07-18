package alexander.sergeev.dto.user_dto;

import alexander.sergeev.validation.ValidationMarker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(groups = {ValidationMarker.OnCreate.class, ValidationMarker.OnUpdate.class},
            message = "Creating or updating user email field is blank!")
    @Email(groups = {ValidationMarker.OnCreate.class, ValidationMarker.OnUpdate.class},
            message = "Creating or updating user email field has wrong email format!")
    @Size(min = 6, max = 254, groups = {ValidationMarker.OnCreate.class, ValidationMarker.OnUpdate.class},
            message = "Creating or updating user email field is smaller than 6 or is bigger than 254 characters!")
    private String email;

    @NotBlank(groups = {ValidationMarker.OnCreate.class, ValidationMarker.OnUpdate.class},
            message = "Creating or updating user name field is blank!")
    @Size(min = 2, max = 250, groups = {ValidationMarker.OnCreate.class, ValidationMarker.OnUpdate.class},
            message = "Creating or updating user email field is smaller than 2 or is bigger than 250 characters!")
    private String name;

}
