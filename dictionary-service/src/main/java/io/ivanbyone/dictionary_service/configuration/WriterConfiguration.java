package io.ivanbyone.dictionary_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tools.jackson.databind.ObjectWriter;
import tools.jackson.databind.json.JsonMapper;

@Configuration
public class WriterConfiguration {
    
    @Bean
    public ObjectWriter jsonWriter() {
        return new JsonMapper().writerWithDefaultPrettyPrinter();
    }
}
