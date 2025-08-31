package newshub.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import newshub.backend.model.News;
import newshub.backend.repository.NewsRepository;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner init(NewsRepository newsRepository) {
        return args -> {
            // Print Hello World
            System.out.println("Hello World");

            // Dummy news data
            newsRepository.save(new News(
                  
            ));

            newsRepository.save(new News(
                  
            ));

            newsRepository.save(new News(
                  
            ));
        };
    }
}



