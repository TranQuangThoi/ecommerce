package com.techmarket.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
  @Autowired
  private JwtAccessTokenConverter jwtAccessTokenConverter;

  @Autowired
  JsonToUrlEncodedAuthenticationFilter jsonFilter;

  @Bean
  public DefaultTokenServices createTokenServices() {
    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    jwtAccessTokenConverter.setAccessTokenConverter(new CustomTokenConverter());
    defaultTokenServices.setTokenStore(new JwtTokenStore(jwtAccessTokenConverter));
    return defaultTokenServices;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.addFilterAfter(jsonFilter, BasicAuthenticationFilter.class)
            .requestMatchers()
            .and()
            .authorizeRequests()
            .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui/**","/swagger-ui.html", "/index", "/pub/**", "/api/token","/api/auth/pwd/verify-token",
                    "/api/auth/activate/resend", "/api/auth/pwd", "/api/auth/logout", "/actuator/**").permitAll()
            .antMatchers("/v1/**").permitAll()
            .antMatchers("/**").authenticated()
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());

  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources.resourceId("tech-base-service");
  }
}