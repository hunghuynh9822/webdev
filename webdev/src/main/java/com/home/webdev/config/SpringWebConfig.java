package com.home.webdev.config;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.home.webdev.interceptor.UrlLocaleInterceptor;
import com.home.webdev.util.UserRoleConverter;

@EnableWebMvc
@Configuration
@ComponentScan({ "com.home.webdev" })
public class SpringWebConfig extends WebMvcConfigurerAdapter {
	private static final Charset UTF8 = Charset.forName("UTF-8");
	
	@Autowired
    private UserRoleConverter userRoleConverter;
	
	// resources của web
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	// Them vào cho Da ngon ngu
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	// Thông tin Locale trên URL
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		UrlLocaleInterceptor localeInterceptor = new UrlLocaleInterceptor();

		registry.addInterceptor(localeInterceptor).addPathPatterns("/en/*", "/en/*/*", "/vi/*", "/vn/*/*");
	}

	// To solver URL like:
	// /SpringMVCInternationalization/en/login2
	// /SpringMVCInternationalization/vi/login2
	// /SpringMVCInternationalization/fr/login2
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
		stringConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "plain", UTF8)));
		converters.add(stringConverter);
		
		//https://stackoverflow.com/questions/38066591/415-unsupported-mediatype-for-post-request-in-spring-application
		converters.add(new MappingJackson2HttpMessageConverter());
        super.configureMessageConverters(converters);

		// Add other converters ...
	}
	//Dành cho security
	/*Optional. It's only required when handling '.' in @PathVariables which otherwise ignore everything after last '.' in @PathVaidables argument.
	 * It's a known bug in Spring [<a class="vglnk" href="https://jira.spring.io/browse/SPR-6164" rel="nofollow"><span>https</span><span>://</span><span>jira</span><span>.</span><span>spring</span><span>.</span><span>io</span><span>/</span><span>browse</span><span>/</span><span>SPR</span><span>-</span><span>6164</span></a>], still present in Spring 4.1.7.
	 * This is a workaround for this issue.
	 */
	@Override
	public void configurePathMatch(PathMatchConfigurer matcher) {
		matcher.setUseRegisteredSuffixPatternMatch(true);
	}
	
	@Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(userRoleConverter);
    }
}
