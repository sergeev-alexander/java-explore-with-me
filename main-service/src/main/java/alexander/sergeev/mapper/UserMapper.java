package alexander.sergeev.mapper;

import alexander.sergeev.dto.user_dto.NewUserDto;
import alexander.sergeev.dto.user_dto.UserDto;
import alexander.sergeev.dto.user_dto.UserShortDto;
import alexander.sergeev.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public User mapNewDtoToUser(NewUserDto newUserDto) {
        return new User(
                null,
                newUserDto.getEmail(),
                newUserDto.getName());
    }

    public UserDto mapUserToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getName());
    }

    public UserShortDto mapUserToShortDto(User user) {
        return new UserShortDto(
                user.getId(),
                user.getName());
    }

}
