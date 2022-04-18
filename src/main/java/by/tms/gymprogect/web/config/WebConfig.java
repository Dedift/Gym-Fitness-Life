package by.tms.gymprogect.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("by.tms.gymprogect.web.controller")
@Import({ThymeleafConfig.class})
public class WebConfig {
}
