package com.mhere.base.config;

import com.mhere.base.error.GlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@Import({
    GlobalExceptionHandler.class,
})
public class WebConfiguration {
}
