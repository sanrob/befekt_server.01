package hu.comperd.befekt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hu.comperd.befekt.dto.Kiadas;
import hu.comperd.befekt.etc.Response;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.exceptions.ParositottSzamlaforgalmiTetelException;
import hu.comperd.befekt.services.KiadasServiceImpl;

@RestController
@RequestMapping(value = "/kiadasok")
public class KiadasServiceController extends BaseController {
  @Autowired
  KiadasServiceImpl kiadasService = null;

  @GetMapping(value = "/{konyvEv}")
  public List<Kiadas> findAll(@PathVariable("konyvEv") final String konyvEv) {
    final ResponseEntity<Response<List<Kiadas>>> kiadasok = this.processRequest(o -> kiadasService.findAll(konyvEv));
    return kiadasok.getBody().getData();
  }

  @PostMapping(value = "")
  public ResponseEntity<Object> createKiadas(@RequestBody final Kiadas kiadas) {
    this.processRequest(o -> kiadasService.create(kiadas));
    return new ResponseEntity<>("Kiadas is created successfully", HttpStatus.CREATED);
  }

  @PutMapping(value = "/update")
  public ResponseEntity<Object> updateKiadas(@RequestBody final Kiadas kiadas) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> kiadasService.update(kiadas));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Kiadás is updated successfully", HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete/{id}/{mddat}")
  public ResponseEntity<Object> deleteKiadas(@PathVariable("id") final String id,
                                             @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> kiadasService.delete(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Kiadás is deleted successfully", HttpStatus.OK);
  }

  @PutMapping(value = "/szamlakonyveles/{id}/{mddat}")
  public ResponseEntity<Object> szlakonyvKiadas(@PathVariable("id") final String id,
                                                @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> kiadasService.szamlaForgGen(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Kiadás tétel számlakönyvelése sikeres!", HttpStatus.OK);
  }

  @PutMapping(value = "/szamlakonytorles/{id}/{mddat}")
  public ResponseEntity<Object> szlakonyvTorlesKiadas(@PathVariable("id") final String id,
                                                      @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> kiadasService.szamlaForgTorl(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    } else if (retData instanceof ParositottSzamlaforgalmiTetelException) {
      throw (ParositottSzamlaforgalmiTetelException)retData;
    }
    return new ResponseEntity<>("Bevétel tétel számlakönyvelése törlése sikeres!", HttpStatus.OK);
  }
}
