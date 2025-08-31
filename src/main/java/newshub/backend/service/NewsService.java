package newshub.backend.service;

import newshub.backend.model.News;
import newshub.backend.repository.NewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

@Service
public class NewsService {

    private final NewsRepository newsRepository;
    private final RestTemplate restTemplate;

    private final String API_KEY = "1c9deea10d4342fcb6710d3f44135833";
    private final String BASE_URL = "https://newsapi.org/v2/top-headlines?country=us&pageSize=10&apiKey=" + API_KEY;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
        this.restTemplate = new RestTemplate();
    }

    // --- Build URL dynamically ---
    private String buildNewsUrl(String country, String category) {
        String url = BASE_URL;
        if (country != null && !country.isEmpty()) {
            url += "&country=" + country;
        }
        if (category != null && !category.isEmpty() && !category.equalsIgnoreCase("general")) {
            url += "&category=" + category.toLowerCase();
        }
        return url;
    }

    // --- CRUD ---
    public News createNews(News news) {
        return newsRepository.save(news);
    }

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    // --- Fetch news by category from DB ---
    public List<News> getNewsByCategory(String category) {
        if (category == null || category.isEmpty() || category.equalsIgnoreCase("general")) {
            return newsRepository.findAll();
        } else {
            return newsRepository.findByCategory(category.toLowerCase());
        }
    }

    // --- Fetch and save news from NewsAPI ---
    public List<News> fetchAndSaveNewsFromAPI(String country, String category) {
        String url = buildNewsUrl(country, category);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String responseBody = response.getBody();
        if (responseBody == null) throw new RuntimeException("No response from News API");

        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray articles = jsonObject.getJSONArray("articles");

        // Clear old news if you want fresh news every time
        newsRepository.deleteAll();

        for (int i = 0; i < articles.length(); i++) {
            JSONObject article = articles.getJSONObject(i);

            // Set defaults to avoid nulls
            String title = article.optString("title", "No Title").trim();
            String description = article.optString("description", "No Description").trim();
            String urlLink = article.optString("url", "#").trim();
            String imageUrl = article.optString("urlToImage", "https://via.placeholder.com/150").trim();
            String newsCategory = category != null ? category.toLowerCase() : "general";

            // Save news
            News news = new News();
            news.setTitle(title);
            news.setDescription(description);
            news.setUrl(urlLink);
            news.setImageUrl(imageUrl);
            news.setCategory(newsCategory);

            newsRepository.save(news);
        }

        // Return filtered news
        return category == null || category.equalsIgnoreCase("general") ?
                newsRepository.findAll() :
                newsRepository.findByCategory(category.toLowerCase());
    }
}
