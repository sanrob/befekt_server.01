package hu.comperd.befekt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class BefektRendszerApplication {

  public static void main(final String[] args) {
    SpringApplication.run(BefektRendszerApplication.class, args);
  }

  @Bean
  public Docket productApi() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage("hu.comperd.befekt"))
        .build()
        .groupName("Swagger");
  }
  /*
  @Bean
  public Docket apiDocket() {
    final String groupName = "Swagger";
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("hu.comperd.befekt"))
        .paths(PathSelectors.ant("/main/**"))
        .build()
        .groupName(groupName)
        .apiInfo(apiInfo());
  }

  // Describe the apis
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("test")
        .description("Test")
        .version("1.0.0")
        .license("vvv")
        .build();
  }
  */
}
