package org.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableWebMvc
public class SpringConfiguration implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        final Optional<HttpMessageConverter<?>> optionalConverter = converters.stream()
                .filter(c -> c.getClass().equals(MappingJackson2HttpMessageConverter.class)).findFirst();
        if (optionalConverter.isPresent()) {
            MappingJackson2HttpMessageConverter converter = (MappingJackson2HttpMessageConverter) optionalConverter.get();
            converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
            converters.add(converter);
        }
    }
}

