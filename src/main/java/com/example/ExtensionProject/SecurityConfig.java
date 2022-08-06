package com.example.ExtensionProject;

import com.example.ebanking.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
	
   
    @Bean
    protected SecurityFilterChain  configure(HttpSecurity http) throws Exception {
//        http.cors().and()
//                .authorizeRequests()
//                .antMatchers("/resources/**", "/error", "/user/login", "/user/register", "/user/forget-password").permitAll()
//                .antMatchers("/user/**").permitAll()
//                .antMatchers("/admin/**").permitAll()
//                .anyRequest().fullyAuthenticated()
//                .and().logout().permitAll()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout", "POST"))
//                .and().formLogin().loginPage("/user/login")
//                .and().httpBasic()
//                .and().csrf().disable();
    	http.csrf().disable();
        http.headers().httpStrictTransportSecurity().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	http
    	.cors().disable();
    	 http.authorizeRequests().antMatchers("/**").permitAll();
    	return http.build();
    }


}
