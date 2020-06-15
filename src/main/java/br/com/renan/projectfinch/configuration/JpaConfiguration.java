package br.com.renan.projectfinch.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.Serializable;
import java.util.Properties;

/**
 * Configurações de persistência.
 *
 * @author Renan Peres
 */
@Configuration
public class JpaConfiguration implements Serializable {

    /**
     * Configura a JPA (Java Persistence API).
     *
     * @param dataSource
     * @return
     */
    @Bean
    @DependsOn("liquibase")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        final LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPersistenceUnitName("persistenceUnit");
        entityManagerFactory.setPackagesToScan("br.com.renan.projectfinch.domain.entity");

        final Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.format_sql", true);
        jpaProperties.put("hibernate.id.new_generator_mappings", false);
        jpaProperties.put("javax.persistence.validation.mode", "none");

        entityManagerFactory.setJpaProperties(jpaProperties);
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return entityManagerFactory;
    }

    /**
     * Configura o gerenciamento das transações.
     *
     * @param dataSource
     * @param entityManagerFactory
     * @return
     */
    @Bean
    public JpaTransactionManager transactionManager(DataSource dataSource, EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource);
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }
}
