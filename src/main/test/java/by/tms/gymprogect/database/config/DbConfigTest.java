package by.tms.gymprogect.database.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DbConfig.class)
public class DbConfigTest {
}
