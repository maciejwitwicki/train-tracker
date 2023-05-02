package io.mwi.traintracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TrainTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainTrackerApplication.class, args);
    }

}
