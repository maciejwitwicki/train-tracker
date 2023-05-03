package io.mwi.traintracker;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Clock;

@Configuration
@EnableScheduling
@ConfigurationPropertiesScan
public class AppConfiguration {

    @Bean
    Clock clock() {
       return Clock.systemUTC();
    }

}
