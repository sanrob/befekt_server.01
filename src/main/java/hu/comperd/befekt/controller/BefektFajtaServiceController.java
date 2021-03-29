package hu.comperd.befekt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hu.comperd.befekt.dto.BefektFajta;
import hu.comperd.befekt.etc.Response;
import hu.comperd.befekt.exceptions.BefektFajtaAlreadyExitException;
import hu.comperd.befekt.exceptions.BefektFajtaCannotDeleteException;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.services.BefektFajtaServiceImpl;

@RestController
@RequestMapping(value = "/befektfajtak")
public class BefektFajtaServiceController extends BaseController {
  @Autowired
  BefektFajtaServiceImpl befektFajtaService = null;

  @GetMapping(value = "")
  public List<BefektFajta> findAll() {
    final ResponseEntity<Response<List<BefektFajta>>> befektFajtak = this.processRequest(o -> befektFajtaService.findAll());
    return befektFajtak.getBody().getData();
  }

  @GetMapping(value = "kivalasztashoz")
  public List<BefektFajta> findAllKivalasztashoz() {
    final ResponseEntity<Response<List<BefektFajta>>> befektFajtak = this.processRequest(
        o -> befektFajtaService.findAllKivalasztashoz());
    return befektFajtak.getBody().getData();
  }

  @PostMapping(value = "")
  public ResponseEntity<Object> createBefektFajta(@RequestBody final BefektFajta befektFajta) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektFajtaService.create(befektFajta));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof BefektFajtaAlreadyExitException) {
      throw (BefektFajtaAlreadyExitException)retData;
    }
    return new ResponseEntity<>("Befektetés fajta is created successfully", HttpStatus.CREATED);
  }

  @PutMapping(value = "/update")
  public ResponseEntity<Object> updateBefektFajta(@RequestBody final BefektFajta befektFajta) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektFajtaService.update(befektFajta));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Befektetés fajta is updated successfully", HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete/{id}/{mddat}")
  public ResponseEntity<Object> deleteBefektFajta(@PathVariable("id") final String id,
                                                  @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektFajtaService.delete(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof BefektFajtaCannotDeleteException) {
      throw (BefektFajtaCannotDeleteException)retData;
    } else if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Befektetés fajta is deleted successfully", HttpStatus.OK);
  }
}
