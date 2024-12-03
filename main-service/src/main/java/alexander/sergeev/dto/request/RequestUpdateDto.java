package alexander.sergeev.dto.request;

import alexander.sergeev.validation.ValidationMarker;
import alexander.sergeev.validation.request_validation.UpdateRequestStatusValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateDto {

    @NotBlank(groups = ValidationMarker.OnCreate.class,
    message = "Updating participation request requestIds field is blank!")
    private Set<Long> requestIds;

    @UpdateRequestStatusValidation(groups = ValidationMarker.OnCreate.class)
    private String status;

}
