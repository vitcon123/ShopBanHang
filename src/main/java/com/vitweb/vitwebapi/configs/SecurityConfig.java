package com.vitweb.vitwebapi.configs;

import com.vitweb.vitwebapi.application.filters.CustomAuthorizationFilter;
import com.vitweb.vitwebapi.application.services.MyUserDetailsService;
import com.vitweb.vitwebapi.configs.exceptions.AuthEntryPointJwt;
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

@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true)
@Configuration
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

  private static final String[] COMMON_LIST_URLS = {
      "/api/v1/auth/signup/**",
      "/api/v1/tokens/verify/**",
      "/api/v1/tokens/resend/**",
      "/api/v1/auth/login/**",
      "/api/v1/auth/refresh-token/**"
  };

  private static final String[] ADMIN_LIST_URLS = {
      "/api/v1/categories/**",
      "/api/v1/courses/**",
      "/api/v1/users/**",
      "/api/v1/lessons/**"
  };

  private static final String[] MENTOR_LIST_URLS = {
      "/api/v1/categories/*",
      "/api/v1/courses/*",
      "/api/v1/users/*",
      "/api/v1/lessons/*"
  };

  private static final String[] STUDENT_LIST_URLS = {
      "/api/v1/categories/*",
      "/api/v1/courses/*",
      "/api/v1/users/*",
      "/api/v1/lessons/*"
  };

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
  }

//  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().configurationSource(request -> corsConfiguration())
        .and().csrf().disable()
//        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)  // handle các lỗi xác thực
//        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(COMMON_LIST_URLS).permitAll()
//        .antMatchers(STUDENT_LIST_URLS).hasAnyAuthority("ROLE_STUDENT")
//        .antMatchers(ADMIN_LIST_URLS).hasAnyAuthority("ROLE_ADMIN")
        .anyRequest().authenticated();
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
