package hu.comperd.befekt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class BefektRendszerApplication {

  public static void main(final String[] args) {
    SpringApplication.run(BefektRendszerApplication.class, args);
  }
}
