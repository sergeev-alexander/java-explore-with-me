package alexander.sergeev.dto.comment_dto;

import alexander.sergeev.validation.ValidationMarker;
import alexander.sergeev.validation.comment_validation.UpdateCommentByAdminStateActionValidation;
import alexander.sergeev.validation.comment_validation.UpdateCommentTextValidation;
import alexander.sergeev.validation.user_state_action_validation.UpdateByUserStateActionValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommentDto {

    @UpdateCommentTextValidation(groups = {ValidationMarker.OnUpdate.class, ValidationMarker.OnAdminUpdate.class})
    private String text;

    @UpdateCommentByAdminStateActionValidation(groups = ValidationMarker.OnAdminUpdate.class)
    @UpdateByUserStateActionValidation(groups = ValidationMarker.OnUpdate.class)
    private String stateAction;

}
