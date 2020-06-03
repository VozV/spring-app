package config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"ru.edu.jpa"})
@EntityScan(basePackages = {"ru.edu.entity"})
@ComponentScan(basePackages = "ru.edu.service.impl")
public class TestConfig {
}
