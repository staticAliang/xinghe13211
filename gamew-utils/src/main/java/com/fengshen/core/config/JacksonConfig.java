package com.fengshen.core.config;

import org.springframework.boot.autoconfigure.jackson.*;
import org.springframework.http.converter.json.*;
import java.time.format.*;
import java.time.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.*;

@Configuration
public class JacksonConfig
{
    @Bean
    @Order(Integer.MIN_VALUE)
    public Jackson2ObjectMapperBuilderCustomizer customJackson() {
        return (Jackson2ObjectMapperBuilderCustomizer)new Jackson2ObjectMapperBuilderCustomizer() {
            public void customize(final Jackson2ObjectMapperBuilder builder) {
                builder.serializerByType((Class)LocalDateTime.class, (JsonSerializer)new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                builder.serializerByType((Class)LocalDate.class, (JsonSerializer)new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                builder.serializerByType((Class)LocalTime.class, (JsonSerializer)new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
                builder.deserializerByType((Class)LocalDateTime.class, (JsonDeserializer)new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                builder.deserializerByType((Class)LocalDate.class, (JsonDeserializer)new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                builder.deserializerByType((Class)LocalTime.class, (JsonDeserializer)new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
                builder.serializationInclusion(JsonInclude.Include.NON_NULL);
                builder.failOnUnknownProperties(false);
                builder.featuresToDisable(new Object[] { SerializationFeature.WRITE_DATES_AS_TIMESTAMPS });
            }
        };
    }
}
