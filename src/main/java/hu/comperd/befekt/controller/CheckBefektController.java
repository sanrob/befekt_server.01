package hu.comperd.befekt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

@RestController
@Api(tags = "Kontrol hívás")
public class CheckBefektController {

  @GetMapping(path = "/")
  public String greetings() {
    return "Befektetés Nyilvántartó Rendszer elindult!";
  }
}
