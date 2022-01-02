package hu.comperd.befekt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hu.comperd.befekt.dto.Transzfer;
import hu.comperd.befekt.etc.Response;
import hu.comperd.befekt.exceptions.KonyvelesiIdoszakLezartException;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.exceptions.ParositottSzamlaforgalmiTetelException;
import hu.comperd.befekt.services.TranszferServiceImpl;
import io.swagger.annotations.Api;

@RestController
@Api(tags = "Számla transzferek")
@RequestMapping(value = "/transzferek")
public class TranszferServiceController extends BaseController {
  @Autowired
  TranszferServiceImpl transzferService = null;

  @GetMapping(value = "/{konyvEv}")
  public List<Transzfer> findAll(@PathVariable("konyvEv") final String konyvEv) {
    final ResponseEntity<Response<List<Transzfer>>> transzferek = this.processRequest(o -> transzferService.findAll(konyvEv));
    return transzferek.getBody().getData();
  }

  @PostMapping(value = "")
  public ResponseEntity<Object> createTranszfer(@RequestBody final Transzfer transzfer) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> transzferService.create(transzfer));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof KonyvelesiIdoszakLezartException) {
      throw (KonyvelesiIdoszakLezartException)retData;
    }
    return new ResponseEntity<>("Transzfer is created successfully", HttpStatus.CREATED);
  }

  @PutMapping(value = "/update")
  public ResponseEntity<Object> updateTranszfer(@RequestBody final Transzfer transzfer) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> transzferService.update(transzfer));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof KonyvelesiIdoszakLezartException) {
      throw (KonyvelesiIdoszakLezartException)retData;
    } else if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Transzfer is updated successfully", HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete/{id}/{mddat}")
  public ResponseEntity<Object> deleteTranszfer(@PathVariable("id") final String id,
                                                @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> transzferService.delete(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Transzfer is deleted successfully", HttpStatus.OK);
  }

  @PutMapping(value = "/szamlakonyveles/{id}/{mddat}")
  public ResponseEntity<Object> szlakonyvTranszfer(@PathVariable("id") final String id,
                                                   @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> transzferService.szamlaForgGen(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Transzfer tétel számlakönyvelése sikeres!", HttpStatus.OK);
  }

  @PutMapping(value = "/szamlakonytorles/{id}/{mddat}")
  public ResponseEntity<Object> szlakonyvTorlesTranszfer(@PathVariable("id") final String id,
                                                         @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> transzferService.szamlaForgTorl(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    } else if (retData instanceof ParositottSzamlaforgalmiTetelException) {
      throw (ParositottSzamlaforgalmiTetelException)retData;
    } else if (retData instanceof KonyvelesiIdoszakLezartException) {
      throw (KonyvelesiIdoszakLezartException)retData;
    }
    return new ResponseEntity<>("Transzfer tétel számlakönyvelésének törlése sikeres!", HttpStatus.OK);
  }
}
