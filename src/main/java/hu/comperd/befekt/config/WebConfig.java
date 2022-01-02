package hu.comperd.befekt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.google.common.base.Predicates;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
//@Configuration
//public class Swagger2UiConfiguration extends WebMvcConfigurerAdapter {
@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Bean
  public Docket api() {
    // @formatter:off
       //Register the controllers to swagger
       //Also it is configuring the Swagger Docket
       return new Docket(DocumentationType.SWAGGER_2).select()
               // .apis(RequestHandlerSelectors.any())
               .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
               // .paths(PathSelectors.any())
               // .paths(PathSelectors.ant("/swagger2-demo"))
               .build();
       // @formatter:on
  }

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    //enabling swagger-ui part for visual documentation
    registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
  }
}