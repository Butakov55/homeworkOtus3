package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import services.PetNewApi;

@Configuration
public class SpringConfig {
    @Bean
    public PetNewApi getPetNewApi() {
        return new PetNewApi();
    }
}
