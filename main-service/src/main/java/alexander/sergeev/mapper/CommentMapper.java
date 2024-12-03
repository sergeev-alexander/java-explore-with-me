package alexander.sergeev.mapper;

import alexander.sergeev.dto.comment_dto.CommentDto;
import alexander.sergeev.model.Comment;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CommentMapper {

    public CommentDto mapCommentToDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getCreated(),
                EventMapper.mapEventToShortDto(comment.getEvent()),
                UserMapper.mapUserToShortDto(comment.getAuthor()),
                comment.getText());
    }

}
