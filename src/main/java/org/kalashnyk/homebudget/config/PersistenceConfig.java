package org.kalashnyk.homebudget.config;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

/**
 * Created by Sergii on 11.02.2017.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"org.kalashnyk.**.repository.jpa"})
public class PersistenceConfig {
    private @Value("${database.url}") String url;
    private @Value("${database.driverClassName}") String driverClassName;
    private @Value("${database.username}") String username;
    private @Value("${database.password}") String password;
    private @Value("${jpa.showSql}") boolean showSql;
    private final String domainPackage = "org.kalashnyk.**.model";

    @Bean
    public static PropertySourcesPlaceholderConfigurer myPropertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propertiesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        Resource[] resourceLocations = new Resource[]{
                new ClassPathResource("db/postgres.properties")
        };
        propertiesPlaceholderConfigurer.setLocations(resourceLocations);
        return propertiesPlaceholderConfigurer;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
/*        DriverManagerDataSource dataSource = new DriverManagerDataSource();*/
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxTotal(10);
        /*dataSource.setConnectionInitSqls(Arrays.asList("classpath:db/initDBpostgres.sql", "classpath:db/populateDB.sql"))*/;

        return dataSource;
    }


    @Bean(name = "entityManagerFactory")
    public EntityManagerFactory entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);

        Map<String, Object> jpaPropertyMap = entityManagerFactoryBean.getJpaPropertyMap();
        jpaPropertyMap.put("#{T(org.hibernate.cfg.AvailableSettings).FORMAT_SQL}", Boolean.parseBoolean("${hibernate.format_sql}"));
        jpaPropertyMap.put("#{T(org.hibernate.cfg.AvailableSettings).USE_SQL_COMMENTS}", Boolean.parseBoolean("${hibernate.use_sql_comments}"));

        entityManagerFactoryBean.setPackagesToScan(domainPackage);
        entityManagerFactoryBean.afterPropertiesSet();


        return entityManagerFactoryBean.getObject();
    }

    @Bean(name = "jpaVendorAdapter")
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(showSql);

        return jpaVendorAdapter;
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}