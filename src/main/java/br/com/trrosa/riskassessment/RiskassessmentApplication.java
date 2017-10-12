package br.com.trrosa.riskassessment;

import java.net.UnknownHostException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author tiago
 */
@ComponentScan
@SpringBootApplication
@EnableConfigurationProperties({LiquibaseProperties.class})
public class RiskassessmentApplication {
    

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(RiskassessmentApplication.class, args);
    }
    
}
