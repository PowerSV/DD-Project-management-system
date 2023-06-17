package com.digdes.school;

import com.digdes.school.security.config.SecurityConfig;
import com.digdes.school.swagger.SwaggerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootApplication
@Import({SecurityConfig.class, SwaggerConfig.class})
public class MainApp {

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }
}
