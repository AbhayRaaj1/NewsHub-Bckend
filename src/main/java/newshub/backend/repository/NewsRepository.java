package newshub.backend.repository;

import newshub.backend.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {

    // Check duplicates by URL
    Optional<News> findByUrl(String url);

    // Fetch news by category
    List<News> findByCategory(String category);
     List<News> findByTitle(String title);
}

