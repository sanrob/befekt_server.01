package hu.comperd.befekt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import hu.comperd.befekt.dto.HavKiadTeny;
import hu.comperd.befekt.etc.Response;
import hu.comperd.befekt.services.HavKiadTenyServiceImpl;

@RestController
@RequestMapping(value = "/havkiadteny")
public class HavKIadTenyServiceController extends BaseController {
  @Autowired
  HavKiadTenyServiceImpl havKiadTenyService = null;

  @GetMapping(value = "/eddigikiadások/{honap}")
  public List<HavKiadTeny> findHaviKiadasok(@PathVariable("honap") final String honap) {
    final ResponseEntity<Response<List<HavKiadTeny>>> haviKiadasok = this.processRequest(
        o -> this.havKiadTenyService.findAllByHkmHonap(honap));
    return haviKiadasok.getBody().getData();
  }
  /*
  @GetMapping(value = "")
  public List<HavKiadTerv> findAll() {
    final ResponseEntity<Response<List<HavKiadTerv>>> havKiadTervek = this.processRequest(o -> havKiadTrevService.findAll());
    return havKiadTervek.getBody().getData();
  }
  
  @PostMapping(value = "")
  public ResponseEntity<Object> createHavKiadTerv(@RequestBody final HavKiadTerv havKiadTerv) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> havKiadTrevService.create(havKiadTerv));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof HavKiadTervAlreadyExitException) {
      throw (HavKiadTervAlreadyExitException)retData;
    }
    return new ResponseEntity<>("Havi kiadás terv is created successfully", HttpStatus.CREATED);
  }
  
  @PutMapping(value = "")
  public ResponseEntity<Object> updateBefektFajta(@RequestBody final HavKiadTerv havKiadTerv) {
    this.processRequest(o -> havKiadTrevService.update(havKiadTerv));
    return new ResponseEntity<>("Havi kiadás terv is updated successfully", HttpStatus.OK);
  }
  */
  /*
  @GetMapping(value = "kivalasztashoz")
  public List<BefektFajta> findAllKivalasztashoz() {
    final ResponseEntity<Response<List<BefektFajta>>> befektFajtak = this.processRequest(
        o -> befektFajtaService.findAllKivalasztashoz());
    return befektFajtak.getBody().getData();
  }
  
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Object> deleteBefektFajta(@PathVariable("id") final String id) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektFajtaService.delete(id));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof BefektFajtaCannotDeleteException) {
      throw (BefektFajtaCannotDeleteException)retData;
    }
    return new ResponseEntity<>("Befektetés fajta is deleted successfully", HttpStatus.OK);
  }
  */
}
