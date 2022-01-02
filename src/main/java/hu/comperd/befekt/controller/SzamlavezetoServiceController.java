package hu.comperd.befekt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hu.comperd.befekt.dto.Szamlavezeto;
import hu.comperd.befekt.etc.Response;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.exceptions.SzamlavezetoAlreadyExitException;
import hu.comperd.befekt.exceptions.SzamlavezetoCannotDeleteException;
import hu.comperd.befekt.services.SzamlavezetoServiceImpl;
import io.swagger.annotations.Api;

@RestController
@Api(tags = "Számlavezetők")
@RequestMapping(value = "/szamlavezetok")
public class SzamlavezetoServiceController extends BaseController {
  @Autowired
  SzamlavezetoServiceImpl szamlavezetoService = null;

  @GetMapping(value = "")
  public List<Szamlavezeto> findAll() {
    final ResponseEntity<Response<List<Szamlavezeto>>> szamlavezetok = this.processRequest(o -> szamlavezetoService.findAll());
    return szamlavezetok.getBody().getData();
  }

  @PostMapping(value = "")
  public ResponseEntity<Object> createSzamlavezeto(@RequestBody final Szamlavezeto szamlavezeto) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> szamlavezetoService.create(szamlavezeto));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof SzamlavezetoAlreadyExitException) {
      throw (SzamlavezetoAlreadyExitException)retData;
    }
    return new ResponseEntity<>("Szamlavezeto is created successfully", HttpStatus.CREATED);
  }

  @PutMapping(value = "/update")
  public ResponseEntity<Object> updateSzamlavezeto(@RequestBody final Szamlavezeto szamlavezeto) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> szamlavezetoService.update(szamlavezeto));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Szamlavezeto is updated successfully", HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete/{id}/{mddat}")
  public ResponseEntity<Object> deleteSzamlavezeto(@PathVariable("id") final String id,
                                                   @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> szamlavezetoService.delete(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof SzamlavezetoCannotDeleteException) {
      throw (SzamlavezetoCannotDeleteException)retData;
    } else if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Szamlavezeto is deleted successfully", HttpStatus.OK);
  }
}
