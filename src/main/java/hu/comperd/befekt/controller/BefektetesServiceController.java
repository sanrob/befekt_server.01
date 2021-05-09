package hu.comperd.befekt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hu.comperd.befekt.dto.Befektetes;
import hu.comperd.befekt.dto.HataridosElszamolas;
import hu.comperd.befekt.dto.Osztalek;
import hu.comperd.befekt.etc.Response;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.services.BefektetesServiceImpl;

@RestController
@RequestMapping(value = "/befektetesek")
public class BefektetesServiceController extends BaseController {
  @Autowired
  BefektetesServiceImpl befektetesService = null;

  @GetMapping(value = "/{konyvEv}")
  public List<Befektetes> findAll(@PathVariable("konyvEv") final String konyvEv) {
    final ResponseEntity<Response<List<Befektetes>>> befektetesek = this.processRequest(o -> befektetesService.findAll(konyvEv));
    return befektetesek.getBody().getData();
  }

  @PostMapping(value = "")
  public ResponseEntity<Object> createBefektetes(@RequestBody final Befektetes befektetes) {
    this.processRequest(o -> befektetesService.create(befektetes));
    return new ResponseEntity<>("Befektetes is created successfully", HttpStatus.CREATED);
  }

  @PutMapping(value = "/update")
  public ResponseEntity<Object> updateBefektetes(@RequestBody final Befektetes befektetes) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektetesService.update(befektetes));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Befektetes is updated successfully", HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete/{id}/{mddat}")
  public ResponseEntity<Object> deleteBefektetes(@PathVariable("id") final String id,
                                                 @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektetesService.delete(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Befektetes is deleted successfully", HttpStatus.OK);
  }

  @PutMapping(value = "/szamlakonyveles/{id}/{mddat}")
  public ResponseEntity<Object> szlakonyvBefektetes(@PathVariable("id") final String id,
                                                    @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektetesService.szamlaForgGen(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Befektetés tétel számlakönyvelése sikeres!", HttpStatus.OK);
  }

  @PutMapping(value = "/szamlakonytorles/{id}/{mddat}")
  public ResponseEntity<Object> szlakonyvTorlesBefektetes(@PathVariable("id") final String id,
                                                          @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektetesService.szamlaForgTorl(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Befektetés tétel számlakönyvelése törlése sikeres!", HttpStatus.OK);
  }

  @GetMapping(value = "/osztalekbeallito/{befektId}")
  public Osztalek findHozamBeallito(@PathVariable("befektId") final String befektId) {
    final ResponseEntity<Response<Osztalek>> osztalekBeallito = this.processRequest(
        o -> this.befektetesService.findOsztalekBeallito(befektId));
    return osztalekBeallito.getBody().getData();
  }

  @PutMapping(value = "/saveosztalek")
  public ResponseEntity<Object> osztalekTarolas(@RequestBody final Osztalek osztalek) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektetesService.osztalekTarolas(osztalek));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Osztalek is saved successfully", HttpStatus.OK);
  }

  @PutMapping(value = "/osztalekszlakonyveles/{id}/{mddat}")
  public ResponseEntity<Object> szlakonyvOsztalek(@PathVariable("id") final String id,
                                                  @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektetesService.osztalekSzamlaForgGen(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Osztalék tétel számlakönyvelése sikeres!", HttpStatus.OK);
  }

  @PutMapping(value = "/osztalekszlakonytorles/{id}/{mddat}")
  public ResponseEntity<Object> szlakonyvTorlesOsztalek(@PathVariable("id") final String id,
                                                        @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektetesService.osztalekSzamlaForgTorl(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Osztalek számlakönyvelése törlése sikeres!", HttpStatus.OK);
  }

  @GetMapping(value = "/hataridoselszamolas/{befektAzon}")
  public List<HataridosElszamolas> findAllhatElsz(@PathVariable("befektAzon") final String befektAzon) {
    final ResponseEntity<Response<List<HataridosElszamolas>>> hataridosElszamolasok = this.processRequest(
        o -> this.befektetesService.findAllHatElsz(befektAzon));
    return hataridosElszamolasok.getBody().getData();
  }

  @PutMapping(value = "/savehatelsz")
  public ResponseEntity<Object> hatElszTarolas(@RequestBody final HataridosElszamolas hataridosElszamolas) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektetesService.createHatElsz(hataridosElszamolas));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Hataridős elszámolás is saved successfully", HttpStatus.OK);
  }

  @DeleteMapping(value = "/deleteHatelsz/{id}/{mddat}")
  public ResponseEntity<Object> deleteHatelsz(@PathVariable("id") final String id,
                                              @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektetesService.deleteHatElsz(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Határidős elszámolás is deleted successfully", HttpStatus.OK);
  }

  @PutMapping(value = "/hatelszszlakonyveles/{id}/{mddat}")
  public ResponseEntity<Object> szlakonyvHatElsz(@PathVariable("id") final String id,
                                                 @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektetesService.hatElszSzamlaForgGen(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Határidős elszámolás tétel számlakönyvelése sikeres!", HttpStatus.OK);
  }

  @PutMapping(value = "/hatelszszlakonytorles/{id}/{mddat}")
  public ResponseEntity<Object> szlakonyvTorlesHatelsz(@PathVariable("id") final String id,
                                                       @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektetesService.hatElszSzamlaForgTorl(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Határidős elszámolás számlakönyvelésének törlése sikeres!", HttpStatus.OK);
  }
}
