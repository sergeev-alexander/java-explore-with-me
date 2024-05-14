package alexander.sergeev.repository;

import alexander.sergeev.exception.NotFoundException;
import alexander.sergeev.model.Compilation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    List<Compilation> findByPinned(Boolean pinned, Pageable pageable);

    default Compilation getCompilationById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("There's no compilation with id " + id));
    }

}
