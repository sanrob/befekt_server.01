package hu.comperd.befekt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hu.comperd.befekt.dto.HavKiadTerv;
import hu.comperd.befekt.etc.Response;
import hu.comperd.befekt.exceptions.HavKiadTervAlreadyExitException;
import hu.comperd.befekt.services.HavKiadTervServiceImpl;

@RestController
@RequestMapping(value = "/havkiadtervek")
public class HavKIadTervServiceController extends BaseController {
  @Autowired
  HavKiadTervServiceImpl havKiadTrevService = null;

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
