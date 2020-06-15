package br.com.renan.projectfinch.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.Serializable;

/**
 * Configurações do Liquibase.
 *
 * @author Renan Peres
 */
@Configuration
public class LiquibaseConfiguration implements Serializable {

    private static final String CHANGELOG_MASTER = "classpath:db/changelog/db.changelog-master.xml";

    /**
     * Configura o Liquibase (Database Migrations) da aplicação.
     *
     * @param dataSource
     * @return
     */
    @Bean(name = "liquibase")
    public SpringLiquibase liquibase(DataSource dataSource) {
        final SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(CHANGELOG_MASTER);
        liquibase.setDataSource(dataSource);

        return liquibase;
    }
}
