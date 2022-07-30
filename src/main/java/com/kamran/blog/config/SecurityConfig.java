package com.kamran.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kamran.blog.security.CustomUserDetailService;
import com.kamran.blog.security.JwtAuthenticationEntryPoint;
import com.kamran.blog.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


//extends WebSecurityConfigurerAdapter

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
			http
			.csrf().disable()
			.authorizeHttpRequests()
			.antMatchers("/api/auth/**").permitAll()
			.antMatchers(HttpMethod.GET).permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//			.httpBasic();
			
			http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.customUserDetailService).passwordEncoder(this.passwordEncoder());
	}
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	
//	@Bean
//    protected void configure() {
//		WebMvcConfigurer mvcConfigurer = new WebMvcConfigurer() {
//            public void addCorsMappings(HttpSecurity http) throws Exception {
//            	http
//            	.csrf().disable()
//            	.authorizeHttpRequests()
//            	.anyRequest()
//            	.authenticated()
//            	.and()
//            	.httpBasic();
//            	
//            }
//        };
//    }

}
