package com.hoa.shopbanhang.configs;

import com.hoa.shopbanhang.application.filters.CustomAuthorizationFilter;
import com.hoa.shopbanhang.application.services.MyUserDetailsService;
import com.hoa.shopbanhang.configs.exceptions.AuthEntryPointJwt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final MyUserDetailsService myUserDetailsService;
  private final AuthEntryPointJwt unauthorizedHandler;
  private final CustomAuthorizationFilter customAuthorizationFilter;


  public SecurityConfig(MyUserDetailsService myUserDetailsService, AuthEntryPointJwt unauthorizedHandler,
                        CustomAuthorizationFilter customAuthorizationFilter) {
    this.myUserDetailsService = myUserDetailsService;
    this.unauthorizedHandler = unauthorizedHandler;
    this.customAuthorizationFilter = customAuthorizationFilter;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().configurationSource(request -> corsConfiguration())
        .and().csrf().disable()
//        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)  // handle các lỗi xác thực
        .authorizeRequests()
        .anyRequest().permitAll()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        .antMatchers(COMMON_LIST_URLS).permitAll()
//        .antMatchers(STUDENT_LIST_URLS).hasAnyAuthority("ROLE_STUDENT")
//        .antMatchers(ADMIN_LIST_URLS).hasAnyAuthority("ROLE_ADMIN")
//        .anyRequest().authenticated();
//        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
    http.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
  }

  CorsConfiguration corsConfiguration() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.applyPermitDefaultValues();
    corsConfiguration.addAllowedMethod(HttpMethod.PATCH);
    corsConfiguration.addAllowedMethod(HttpMethod.PUT);
    corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
    return corsConfiguration;
  }

  @Bean
  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

}
