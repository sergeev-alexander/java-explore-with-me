package alexander.sergeev.repository;

import alexander.sergeev.exception.NotFoundException;
import alexander.sergeev.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findBy(Pageable pageable);

    List<User> findByIdIn(List<Long> ids, Pageable pageable);


    default void checkUserById(Long id) {
        if (!existsById(id)) throw new NotFoundException("There's no user with id " + id);
    }

    default User getUserById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("There's no user with id " + id));
    }

}
