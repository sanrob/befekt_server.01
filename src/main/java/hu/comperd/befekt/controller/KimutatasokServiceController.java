package hu.comperd.befekt.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import hu.comperd.befekt.dto.BefektetesEgyenleg;
import hu.comperd.befekt.dto.SzamlaEgyenleg;
import hu.comperd.befekt.etc.Response;
import hu.comperd.befekt.services.KimutatasServiceImpl;

@RestController
@RequestMapping(value = "/kimutatasok")
public class KimutatasokServiceController extends BaseController {

  @Autowired
  KimutatasServiceImpl kimutatasokService = null;

  @GetMapping(value = "/haviegyenlegek/{honap}")
  public List<SzamlaEgyenleg> getHaviEgyenlegek(@PathVariable("honap") final String honap) {
    final ResponseEntity<Response<List<SzamlaEgyenleg>>> befektFajtak = this.processRequest(
        o -> kimutatasokService.getSzamlakHaviEgyenleg(honap));
    return befektFajtak.getBody().getData();
  }

  @GetMapping(value = "/befektegyenlegek/{leknap}")
  public List<BefektetesEgyenleg> getBefektEgyenlegek(@PathVariable("leknap") final String leknap) {
    final ResponseEntity<Response<List<BefektetesEgyenleg>>> nyitottBefektetesek = this.processRequest(
        o -> kimutatasokService.getBefektetesekNapiEgyenleg(LocalDate.parse(leknap)));
    return nyitottBefektetesek.getBody().getData();
  }
}
