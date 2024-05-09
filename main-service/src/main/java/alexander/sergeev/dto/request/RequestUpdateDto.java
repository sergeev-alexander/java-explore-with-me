package alexander.sergeev.dto.request;

import alexander.sergeev.validation.ValidationMarker;
import alexander.sergeev.validation.request_validation.UpdateRequestStatusValidation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestUpdateDto {

    @NotBlank(groups = ValidationMarker.OnCreate.class,
    message = "Updating participation request requestIds field is blank!")
    Set<Long> requestIds;

    @UpdateRequestStatusValidation(groups = ValidationMarker.OnCreate.class)
    String status;

}
