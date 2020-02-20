package com.jenkins.config;

import java.util.HashMap;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "portalConfigEntityManagerFactory", transactionManagerRef = "portalConfigTransactionManager", basePackages = {
		"com.jenkins.repository"})
@PropertySource("file:config/application.properties")
public class DBConfig {

		@Autowired
		private Environment env;
		
		@Bean(name = "portalConfigDataSource")
		public DataSource dataSource() {
			// return DataSourceBuilder.create().build();
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName(env.getProperty("portal.config.datasource.driverClassName"));
			dataSource.setUrl(env.getProperty("portal.config.datasource.url"));
			dataSource.setUsername(env.getProperty("portal.config.datasource.username"));
			dataSource.setPassword(env.getProperty("portal.config.datasource.password"));
			return dataSource;
		}
		
		@Bean(name = "portalConfigEntityManagerFactory")
		public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
				@Qualifier("portalConfigDataSource") DataSource dataSource) {
			Map<String, Object> properties = new HashMap<String, Object>();
			return builder.dataSource(dataSource).packages("com.jenkins.entity")
					.persistenceUnit("portalConfigPU").properties(properties).build();
		}

		@Bean(name = "portalConfigTransactionManager")
		public PlatformTransactionManager transactionManager(
				@Qualifier("portalConfigEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
			return new JpaTransactionManager(entityManagerFactory);
		}

}