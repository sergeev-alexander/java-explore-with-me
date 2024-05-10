package alexander.sergeev.service;

import alexander.sergeev.dto.user_dto.NewUserDto;
import alexander.sergeev.dto.user_dto.UserDto;
import alexander.sergeev.mapper.UserMapper;
import alexander.sergeev.model.User;
import alexander.sergeev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDto> getUsers(List<Long> ids, Pageable pageable) {
        List<User> userList = ids.isEmpty()
                ? userRepository.findBy(pageable)
                : userRepository.findByIdIn(ids, pageable);
        return userList
                .stream()
                .map(UserMapper::mapUserToDto)
                .collect(Collectors.toList());
    }

    public UserDto postUser(NewUserDto newUserDto) {
        return UserMapper.mapUserToDto(userRepository.save(UserMapper.mapNewDtoToUser(newUserDto)));
    }

    public void deleteUserById(Long id) {
        userRepository.delete(userRepository.getUserById(id));
    }

}
