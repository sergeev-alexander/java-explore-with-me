package alexander.sergeev.repository;

import alexander.sergeev.model.App;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppRepository extends JpaRepository<App, Long> {

    Optional<App> findFirstByName(String name);

}
