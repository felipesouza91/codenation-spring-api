package br.com.felipe.codenationlog.core.secutiry;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private PasswordEncoder password;

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory().withClient("api").secret(password.encode("api")).scopes("read", "write")
        .authorizedGrantTypes("password").accessTokenValiditySeconds(1800).refreshTokenValiditySeconds(3600 * 12).and()
        .withClient("checktoken").secret(password.encode("check123"));

  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security.checkTokenAccess("permitAll()");
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    var enhancerChain = new TokenEnhancerChain();
    enhancerChain.setTokenEnhancers(Arrays.asList(new JwtCustomClaimsTokenEnhancer(), jwtAccessTokenConverter()));

    endpoints.authenticationManager(authenticationManager).userDetailsService(userDetailsService)
        .reuseRefreshTokens(false).accessTokenConverter(jwtAccessTokenConverter()).tokenEnhancer(enhancerChain);
  }

  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
    accessTokenConverter.setSigningKey("$2y$12$GV71H2f2CKqio7Pxcd3vguVJBu4BzWMQ23LC7l1cpKJFLwxYrFMqS");
    ((DefaultAccessTokenConverter) accessTokenConverter.getAccessTokenConverter())
        .setUserTokenConverter(userAuthenticationConverter());
    return accessTokenConverter;
  }

  @Bean
  public UserAuthenticationConverter userAuthenticationConverter() {
    DefaultUserAuthenticationConverter defaultUserAuthenticationConverter = new DefaultUserAuthenticationConverter();
    defaultUserAuthenticationConverter.setUserDetailsService(userDetailsService);
    return defaultUserAuthenticationConverter;
  }
}