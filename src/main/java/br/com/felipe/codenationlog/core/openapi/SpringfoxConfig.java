package br.com.felipe.codenationlog.core.openapi;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringfoxConfig implements WebMvcConfigurer {

  @Bean
  public Docket apiDocket() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage("br.com.felipe.codenationlog.api.controller")).build()
        .apiInfo(apiInfo()).securitySchemes(Arrays.asList(secutiryScheme()))
        .securityContexts(Arrays.asList(secutiryContext()))
        .tags(new Tag("Events", "Events Controller"), new Tag("SignUp", "SignUp Controller"));
  }

  public ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("Codenation Logs API")
        .description("Open API for web clients storage logs, \n Default client_id and client_secret is: api")
        .version("1").contact(new Contact("Felipe Souza", "https://github.com/felipesouza91", "felipedb91@gmail.com"))
        .build();
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

  private SecurityScheme secutiryScheme() {
    return new OAuthBuilder().name("CondenationApi").grantTypes(grantTypes()).build();
  }

  private List<GrantType> grantTypes() {
    return Arrays.asList(new ResourceOwnerPasswordCredentialsGrant("/oauth/token"));
  }

  private List<AuthorizationScope> scopes() {
    return Arrays.asList(new AuthorizationScope("READ", "Acesso leitura"),
        new AuthorizationScope("WRITE", "Acesso escrita"));
  }

  private SecurityContext secutiryContext() {
    var secutiryReference = SecurityReference.builder().reference("CondenationApi")
        .scopes(scopes().toArray(new AuthorizationScope[0])).build();
    return SecurityContext.builder().securityReferences(Arrays.asList(secutiryReference))
        .forPaths(PathSelectors.ant("/events/**")).build();
  }
}