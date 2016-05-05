package com.fpj.spring.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(basePackages = { "com.fpj.spring.repository" })
@PropertySources({ @PropertySource("classpath:db.properties") })
public class DatabaseConfig {

	@Value("${driverClass}")
	private String driverClassName;
	@Value("${url}")
	private String dbUrl;
	@Value("${username}")
	private String username;
	@Value("${password}")
	private String password;
	@Value("${dialect}")
	private String databaseDialect;
	@Value("${hbm2ddl}")
	private String hbm2ddl;

	@Bean(name = "entityManagerFactory")
	public EntityManagerFactory createEntityManager() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		entityManagerFactoryBean.setJpaVendorAdapter(adapter);
		entityManagerFactoryBean.setPackagesToScan("com.fpj.spring.entities");
		entityManagerFactoryBean.setJpaProperties(getJpaProperties());
		entityManagerFactoryBean.setDataSource(createDataSource());
		entityManagerFactoryBean.afterPropertiesSet();
		return entityManagerFactoryBean.getObject();
	}

	@Bean
	public DataSource createDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(driverClassName);
		ds.setUrl(dbUrl);
		ds.setUsername(username);
		ds.setPassword(password);
		return ds;
	}

	@Bean(name = "transactionManager")
	public JpaTransactionManager getTransactionManager() throws SQLException {
		JpaTransactionManager manager = new JpaTransactionManager();
		return manager;
	}

	private Properties getJpaProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.hbm2ddl.auto", hbm2ddl);
		properties.put("hibernate.dialect", databaseDialect);
		properties.put("hibernate.show_sql", true);
		return properties;
	}
}
