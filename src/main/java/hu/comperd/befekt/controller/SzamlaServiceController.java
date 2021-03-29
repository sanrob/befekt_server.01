package hu.comperd.befekt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hu.comperd.befekt.dto.Szamla;
import hu.comperd.befekt.etc.Response;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.exceptions.SzamlaAlreadyExitException;
import hu.comperd.befekt.exceptions.SzamlaCannotDeleteException;
import hu.comperd.befekt.services.SzamlaServiceImpl;

@RestController
@RequestMapping(value = "/szamlas")
public class SzamlaServiceController extends BaseController {
  @Autowired
  SzamlaServiceImpl szamlaService = null;

  @GetMapping(value = "/{szlatip}")
  public List<Szamla> findAll(@PathVariable("szlatip") final String szlatip) {
    final ResponseEntity<Response<List<Szamla>>> szamlas = this.processRequest(o -> szamlaService.findAll(szlatip));
    return szamlas.getBody().getData();
  }

  @GetMapping(value = "")
  public List<Szamla> findAll() {
    final ResponseEntity<Response<List<Szamla>>> szamlas = this.processRequest(o -> szamlaService.findAll());
    return szamlas.getBody().getData();
  }

  @PostMapping(value = "")
  public ResponseEntity<Object> createSzamla(@RequestBody final Szamla szamla) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> szamlaService.create(szamla));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof SzamlaAlreadyExitException) {
      throw (SzamlaAlreadyExitException)retData;
    }
    return new ResponseEntity<>("Szamla is created successfully", HttpStatus.CREATED);
  }

  @PutMapping(value = "/update")
  public ResponseEntity<Object> updateSzamla(@RequestBody final Szamla szamla) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> szamlaService.update(szamla));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Szamla is updated successfully", HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete/{id}/{mddat}")
  public ResponseEntity<Object> deleteSzamla(@PathVariable("id") final String id,
                                             @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> szamlaService.delete(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof SzamlaCannotDeleteException) {
      throw (SzamlaCannotDeleteException)retData;
    } else if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Szamla is deleted successfully", HttpStatus.OK);
  }
}
