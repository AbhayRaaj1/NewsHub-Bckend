package newshub.backend.controller;

import newshub.backend.model.News;
import newshub.backend.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@CrossOrigin(origins = "*") // allow frontend fetch from any origin
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    // ------------------- Fetch Latest News -------------------
    @GetMapping("/fetch-latest")
    public ResponseEntity<?> fetchLatestNews(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String category) {
        try {
            // Defaults if parameters are missing
            String selectedCountry = (country != null && !country.isEmpty()) ? country : "in";
            String selectedCategory = (category != null && !category.isEmpty()) ? category : "general";

            // Fetch latest news from API & save to DB
            List<News> newsList = newsService.fetchAndSaveNewsFromAPI(selectedCountry, selectedCategory);

            // Return JSON array to frontend
            return ResponseEntity.ok(newsList);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("Failed to fetch news: " + e.getMessage());
        }
    }

    // ------------------- Fetch News from DB by Category -------------------
    @GetMapping("/category")
    public ResponseEntity<?> getNewsByCategory(@RequestParam(required = false) String category) {
        try {
            String selectedCategory = (category != null && !category.isEmpty()) ? category : "general";
            List<News> newsList = newsService.getNewsByCategory(selectedCategory);
            return ResponseEntity.ok(newsList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("Failed to fetch news by category: " + e.getMessage());
        }
    }
}
