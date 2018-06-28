package com.home.webdev.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;

import com.home.webdev.interceptor.UrlLocaleResolver;

/**
 * Configuration class to get data from properties file
 * 
 */
@Configuration
public class SpringMessageConfig {
	/**
	 * Name of file property
	 */
	private static final String MESSAGES_EN = "messages_en";
	private static final String MESSAGES_VN = "messages_vn";

	private static final String UTF_8 = "UTF-8";

	@Bean(name = "messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageResource = new ReloadableResourceBundleMessageSource();
		messageResource.setBasename("classpath:messages");
		messageResource.setDefaultEncoding("UTF-8");
		return messageResource;
	}

	@Bean(name = "localeResolver")
	public LocaleResolver getLocaleResolver() {
		LocaleResolver resolver = new UrlLocaleResolver();
		return resolver;
	}
	
	/**
	 * Get value from a key code from messages_en_US.properties.
	 * 
	 * @param code
	  @return {@link String}
	 */
	public String getProperty(String code) {

		return messageSource().getMessage(code, null, null);
	}
	
	/**
	 * Get value from a key code from messages_en_US.properties.
	 * 
	 * @param code
	  @param args
	  @return {@link String}
	 */
	public String getProperty(String code, String... args) {

		return messageSource().getMessage(code, args, null);
	}
}
