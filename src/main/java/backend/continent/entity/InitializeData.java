package backend.continent.entity;

import backend.continent.repositories.ContinentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.List;

@Configuration
@Slf4j
class InitializeData {

    private static final String INPUT_FILE_PATH = "static/continents.txt";

    @Bean
    CommandLineRunner initializeDatabase(ContinentRepository continentRepository) {
        return args -> {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = null;
            try {
                inputStream = InitializeData.class.getClassLoader().getResourceAsStream(INPUT_FILE_PATH);
                List<Continent> inputJson = objectMapper.readValue(inputStream, new TypeReference<List<Continent>>() {
                });
                for (Continent continent :
                        inputJson) {
                    continent.getCountries().stream().forEach(c -> {
                        c.setContinent(continent);
                    });
                    continentRepository.save(continent);
                    log.info("Successfully saved new continent: '" + continent.getContinent() + "'");
                }
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        };
    }
}
