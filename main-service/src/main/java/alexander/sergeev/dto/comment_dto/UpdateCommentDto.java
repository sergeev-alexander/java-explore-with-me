package alexander.sergeev.dto.comment_dto;

import alexander.sergeev.validation.ValidationMarker;
import alexander.sergeev.validation.comment_validation.UpdateCommentByAdminStateActionValidation;
import alexander.sergeev.validation.comment_validation.UpdateCommentTextValidation;
import alexander.sergeev.validation.state_action_validation.UpdateByUserStateActionValidation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCommentDto {

    @UpdateCommentTextValidation(groups = {ValidationMarker.OnUpdate.class, ValidationMarker.OnAdminUpdate.class})
    String text;

    @UpdateCommentByAdminStateActionValidation(groups = ValidationMarker.OnAdminUpdate.class)
    @UpdateByUserStateActionValidation(groups = ValidationMarker.OnUpdate.class)
    String stateAction;

}
