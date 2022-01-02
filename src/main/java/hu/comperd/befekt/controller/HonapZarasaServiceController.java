package hu.comperd.befekt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hu.comperd.befekt.dto.HonapZarasa;
import hu.comperd.befekt.etc.Response;
import hu.comperd.befekt.exceptions.HonapNemLezarhatoException;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.exceptions.VanKonyveletlenTetelException;
import hu.comperd.befekt.services.HonapZarasaServiceImpl;
import io.swagger.annotations.Api;

@RestController
@Api(tags = "Hónap zárása")
@RequestMapping(value = "/rendszerfunkciok/honapzarasa")
public class HonapZarasaServiceController extends BaseController {
  @Autowired
  HonapZarasaServiceImpl honZarService = null;

  @GetMapping(value = "/{honEv}")
  public List<HonapZarasa> findAll(@PathVariable("honEv") final String honEv) {
    final ResponseEntity<Response<List<HonapZarasa>>> honapok = this.processRequest(o -> this.honZarService.findAll(honEv));
    return honapok.getBody().getData();
  }

  @PutMapping(value = "/zarasnyitas")
  public ResponseEntity<Object> zarasNyitas(@RequestBody final HonapZarasa honapZarasa) {
    System.out.println("Megjött a kérdés!");
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> this.honZarService.zarasNyitas(honapZarasa));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    } else if (retData instanceof HonapNemLezarhatoException) {
      throw (HonapNemLezarhatoException)retData;
    } else if (retData instanceof VanKonyveletlenTetelException) {
      throw (VanKonyveletlenTetelException)retData;
    }
    return new ResponseEntity<>("HonapZarasa is updated successfully", HttpStatus.OK);
  }
  /*
  @PostMapping(value = "")
  public ResponseEntity<Object> createBevetel(@RequestBody final Bevetel bevetel) {
    this.processRequest(o -> bevetelService.create(bevetel));
    return new ResponseEntity<>("Bevetel is created successfully", HttpStatus.CREATED);
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

  @DeleteMapping(value = "/szamlakonyveles/{id}/{mddat}")
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
    }
    return new ResponseEntity<>("Bevétel tétel számlakönyvelése törlése sikeres!", HttpStatus.OK);
  }
  */
}
