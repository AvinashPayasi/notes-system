package com.notes.system.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.UrlHandlerFilter;

@Configuration
public class WebConfig {

    //Trailing Slash Handler
    @Bean
    UrlHandlerFilter urlHandlerFilter(){
        return UrlHandlerFilter
                .trailingSlashHandler("/**")
                .wrapRequest()
                .build();
    }
}
