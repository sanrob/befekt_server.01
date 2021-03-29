package hu.comperd.befekt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import hu.comperd.befekt.dto.Domain;
import hu.comperd.befekt.dto.KonyvelesiEv;
import hu.comperd.befekt.etc.Response;
import hu.comperd.befekt.services.AlapAdatokServiceImpl;

@RestController
@RequestMapping(value = "/alapadatok")
public class AlapAdatokController extends BaseController {
  @Autowired
  AlapAdatokServiceImpl alapAdatokService = null;

  @GetMapping(value = "/konyvelesi_evek")
  public List<KonyvelesiEv> findAll() {
    final ResponseEntity<Response<List<KonyvelesiEv>>> konyvelesiEvek = this.processRequest(o -> alapAdatokService.findAll());
    return konyvelesiEvek.getBody().getData();
  }

  @GetMapping(value = "/domains/{csoportKod}")
  public List<Domain> findAllDomainByCsoport(@PathVariable("csoportKod") final String csoportKod) {
    final ResponseEntity<Response<List<Domain>>> domains = this.processRequest(
        o -> alapAdatokService.findAllDomainByCsoport(csoportKod));
    return domains.getBody().getData();
  }
}
