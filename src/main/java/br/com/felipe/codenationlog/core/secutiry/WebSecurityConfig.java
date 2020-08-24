package br.com.felipe.codenationlog.core.secutiry;

import java.util.Collections;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String[] AUTH_WHITELIST = { "/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs",
      "/webjars/**" };

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers(HttpMethod.POST, "/signup").permitAll().antMatchers(AUTH_WHITELIST).permitAll()
        .anyRequest().authenticated().and().csrf().disable().cors().and().oauth2ResourceServer().jwt()
        .jwtAuthenticationConverter(jwtAuthenticationConverter());
  }

  private JwtAuthenticationConverter jwtAuthenticationConverter() {
    var jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
      var authorities = jwt.getClaimAsStringList("authorities");
      if (authorities == null) {
        authorities = Collections.emptyList();
      }
      var scopeAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
      var grantedAuthorities = scopeAuthoritiesConverter.convert(jwt);

      grantedAuthorities.addAll(authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

      return grantedAuthorities;
    });
    return jwtAuthenticationConverter;
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    final var secretKey = new SecretKeySpec("$2y$12$GV71H2f2CKqio7Pxcd3vguVJBu4BzWMQ23LC7l1cpKJFLwxYrFMqS".getBytes(),
        "HmacSHA256");
    return NimbusJwtDecoder.withSecretKey(secretKey).build();
  }

  @Bean
  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
