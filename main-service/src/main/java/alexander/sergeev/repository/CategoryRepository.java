package alexander.sergeev.repository;

import alexander.sergeev.exception.NotFoundException;
import alexander.sergeev.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    default Category getCategoryById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("There's no category with id " + id));
    }

}
