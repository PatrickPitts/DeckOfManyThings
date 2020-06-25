package org.nerdcore.phylactory.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories("org.nerdcore.phylactory.repository")
@EntityScan("org.nerdcore.phylactory.model")
public class DatabaseConfig {

//    @Bean
//    public DataSource dataSource(){
//        return new DataSource() {
//        }
//    }
}
