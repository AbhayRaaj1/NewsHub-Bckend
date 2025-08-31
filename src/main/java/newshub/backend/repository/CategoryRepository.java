package newshub.backend.repository;

import newshub.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Optional: Category name se search karne ke liye custom query
    Category findByName(String name);
}

