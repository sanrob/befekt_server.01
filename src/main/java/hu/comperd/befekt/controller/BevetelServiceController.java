package hu.comperd.befekt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hu.comperd.befekt.dto.Bevetel;
import hu.comperd.befekt.etc.Response;
import hu.comperd.befekt.exceptions.KonyvelesiIdoszakLezartException;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.exceptions.ParositottSzamlaforgalmiTetelException;
import hu.comperd.befekt.services.BevetelServiceImpl;
import io.swagger.annotations.Api;

@RestController
@Api(tags = "Bevételek")
@RequestMapping(value = "/bevetels")
public class BevetelServiceController extends BaseController {
  @Autowired
  BevetelServiceImpl bevetelService = null;

  @GetMapping(value = "/{konyvEv}")
  public List<Bevetel> findAll(@PathVariable("konyvEv") final String konyvEv) {
    final ResponseEntity<Response<List<Bevetel>>> bevetels = this.processRequest(o -> bevetelService.findAll(konyvEv));
    return bevetels.getBody().getData();
  }

  @PostMapping(value = "")
  public ResponseEntity<Object> createBevetel(@RequestBody final Bevetel bevetel) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> bevetelService.create(bevetel));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof KonyvelesiIdoszakLezartException) {
      throw (KonyvelesiIdoszakLezartException)retData;
    }
    return new ResponseEntity<>("Bevetel is created successfully", HttpStatus.CREATED);
  }

  @PutMapping(value = "/update")
  public ResponseEntity<Object> updateBevetel(@RequestBody final Bevetel bevetel) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> bevetelService.update(bevetel));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof KonyvelesiIdoszakLezartException) {
      throw (KonyvelesiIdoszakLezartException)retData;
    } else if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Bevetel is updated successfully", HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete/{id}/{mddat}")
  public ResponseEntity<Object> deleteBevetel(@PathVariable("id") final String id,
                                              @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> bevetelService.delete(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Bevetel is deleted successfully", HttpStatus.OK);
  }

  @PutMapping(value = "/szamlakonyveles/{id}/{mddat}")
  public ResponseEntity<Object> szlakonyvBevetel(@PathVariable("id") final String id,
                                                 @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> bevetelService.szamlaForgGen(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Bevetel tétel számlakönyvelése sikeres!", HttpStatus.OK);
  }

  @PutMapping(value = "/szamlakonytorles/{id}/{mddat}")
  public ResponseEntity<Object> szlakonyvTorlesBevetel(@PathVariable("id") final String id,
                                                       @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> bevetelService.szamlaForgTorl(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    } else if (retData instanceof ParositottSzamlaforgalmiTetelException) {
      throw (ParositottSzamlaforgalmiTetelException)retData;
    } else if (retData instanceof KonyvelesiIdoszakLezartException) {
      throw (KonyvelesiIdoszakLezartException)retData;
    }
    return new ResponseEntity<>("Bevétel tétel számlakönyvelése törlése sikeres!", HttpStatus.OK);
  }
}
