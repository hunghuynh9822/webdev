package com.home.webdev.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("customUserDetailsService") // (*)
	private UserDetailsService userDetailsService;

	@Autowired
	@Qualifier("persistentTokenRepository")
	private PersistentTokenRepository tokenRepository;

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		
		//auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
		
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
		
		//Sử dụng tiếng Việt trên web
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter,CsrfFilter.class);

    	http.csrf();
    	//Các trang không yêu cầu login
        http.authorizeRequests().antMatchers("/en/", "/en/home","/en/login","/en/logout","/en/registration","/signup").permitAll();
        http.authorizeRequests().antMatchers("/vn/", "/vn/home","/vn/login","/vn/logout","/vn/registration","/signup").permitAll();
        //Trang /userInfo yêu cầu phải login với vai trò ADMIN,STAFF,CUSTOMER
        //Nếu chưa login, nó sẽ redirect tới trang /login
        http.authorizeRequests().antMatchers("/en/user/*","/vn/user/*","/user/*").access("hasAnyRole('ADMIN','STAFF','CUSTOMER')");
        //Trang chỉ dành cho ADMIN
        http.authorizeRequests().antMatchers("/en/admin/**","/vn/admin/**","/admin/**").access("hasRole('ADMIN')");
        //Khi người dùng đã login, với vai trò XX.
        //Nhưng truy cập vào trang yêu cầu vai trò YY,
        //Ngoại lệ AccessDeniedException sẽ ném ra.
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/Access_Denied");
        http.authorizeRequests()
        .and()
        .formLogin()
        //Submit URL của trang login
        	.loginPage("/login")
            .loginProcessingUrl("/login")
            .usernameParameter("username")
            .passwordParameter("password")
            .defaultSuccessUrl("/home")
            .failureUrl("/login?error")
            .and().logout().logoutUrl("/logout").logoutSuccessUrl("/home");
        http.authorizeRequests()
        	.and()
            .rememberMe().rememberMeParameter("remember-me").tokenRepository(tokenRepository)
            .tokenValiditySeconds(86400);
        
    }
	
	 @Override
	   public UserDetailsService userDetailsService() {
	       return userDetailsService;
	   }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public PersistentTokenBasedRememberMeServices getPersistentTokenBasedRememberMeServices() {
		PersistentTokenBasedRememberMeServices tokenBasedservice = new PersistentTokenBasedRememberMeServices(
				"remember-me", userDetailsService, tokenRepository);
		return tokenBasedservice;
	}

	@Bean
	public AuthenticationTrustResolver getAuthenticationTrustResolver() {
		return new AuthenticationTrustResolverImpl();
	}

}