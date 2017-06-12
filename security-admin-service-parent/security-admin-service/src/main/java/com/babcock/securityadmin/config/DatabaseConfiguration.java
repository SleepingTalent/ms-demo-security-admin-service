package com.babcock.securityadmin.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.logging.Logger;

@Configuration
@EntityScan("com.babcock.securityadmin.model.domain")
@EnableJpaRepositories("com.babcock.securityadmin.model.repositories")
@PropertySource(value = "classpath:database-config/db.properties")
@EnableTransactionManagement
public class DatabaseConfiguration {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Bean
    public DataSource dataSource() {

        logger.info("Creating datasource");

        DataSource dataSource = (new EmbeddedDatabaseBuilder())
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:database-config/db-ddl.sql")
                .addScript("classpath:database-config/db-dml.sql")
                .build();

        runSanityCheck(dataSource);

        return dataSource;
    }

    private void runSanityCheck(DataSource dataSource) {
        String sql = "SELECT count(*) from SECURITY_SUBJECTS";

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        int count = jdbcTemplate.queryForObject(sql, Integer.class);

        logger.info("System has " + count + " subjects pre-loaded");
    }


}
