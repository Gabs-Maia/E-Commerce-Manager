package com.inn.cafeman.com.inn.cafeman.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurer;
import org.springframework.security.web.util.matcher.R
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

//Config class, can be used by spring IoC.
@Configuration
// Enables Spring's Security. Applied to the global Security class.
@EnableWebSecurity
public class SecurityConfig implements WebSecurityConfigurer{
    
    //Beans injection through matching.
    @Autowired
    CustomerUsersDetailsService customerUserDetailsService;
    
    @Autowired
    JwtFilter jwtFilter;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customerUserDetailsService);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){return SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8();}
    
    @Bean(name= BeanIds.AUTHENTICATION_MANAGER)
    
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws  Exception{
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and().csrf().disable().antMatchers("/user/login", "/user/signup", "/user/forgotPassword")
                .anyRequest().and().exceptionHandling().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
    
    @Override
    public void init(SecurityBuilder builder) throws Exception {
    
    }
    
    @Override
    public void configure(SecurityBuilder builder) throws Exception {
    
    }
}
