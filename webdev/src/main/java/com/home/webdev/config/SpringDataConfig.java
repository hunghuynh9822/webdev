package com.home.webdev.config;

import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
//Load to Environment.
@PropertySource("classpath:datasource-cfg.properties")
public class SpringDataConfig {
	// Lưu trữ các giá thuộc tính load bởi @PropertySource.
		@Autowired
		private Environment env;
		
		@Bean
		public LocalSessionFactoryBean sessionFactory(BasicDataSource dataSource) {
			LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
			sessionFactory.setDataSource(dataSource);
			sessionFactory.setPackagesToScan(new String[] { "com.home.webcoffee.entities" });
			//sessionFactory.setAnnotatedClasses(Product.class,Account.class,Category.class,Role.class,Bill.class,BillDetail.class,Sale.class);
			sessionFactory.setHibernateProperties(hibernateProperties());
			return sessionFactory;
		}

		private Properties hibernateProperties() {
			Properties properties = new Properties();
			properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
			properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
			properties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
			properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
			return properties;
		}

		@Bean(name = "dataSource")
		public BasicDataSource getDataSource() {
			BasicDataSource dataSource = new BasicDataSource();
			dataSource.setDriverClassName(env.getProperty("ds.database-driver"));
			dataSource.setUrl(env.getProperty("ds.url"));
			dataSource.setUsername(env.getProperty("ds.username"));
			dataSource.setPassword(env.getProperty("ds.password"));
			return dataSource;
		}

		@Bean
		public HibernateTransactionManager transactionManager(SessionFactory s) {
			HibernateTransactionManager txManager = new HibernateTransactionManager();
			txManager.setSessionFactory(s);
			return txManager;
		}
}
