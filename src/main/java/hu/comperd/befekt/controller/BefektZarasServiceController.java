package hu.comperd.befekt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hu.comperd.befekt.dto.BefektZaras;
import hu.comperd.befekt.dto.HozamBeallito;
import hu.comperd.befekt.dto.NyitasZarasParok;
import hu.comperd.befekt.etc.Response;
import hu.comperd.befekt.exceptions.KonyvelesiIdoszakLezartException;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.exceptions.ParositottSzamlaforgalmiTetelException;
import hu.comperd.befekt.services.BefektZarasServiceImpl;
import io.swagger.annotations.Api;

@RestController
@Api(tags = "Befektetések zárása")
@RequestMapping(value = "/befektzarasok")
public class BefektZarasServiceController extends BaseController {
  @Autowired
  BefektZarasServiceImpl befektZarasService = null;

  @GetMapping(value = "/{konyvEv}")
  public List<BefektZaras> findAll(@PathVariable("konyvEv") final String konyvEv) {
    final ResponseEntity<Response<List<BefektZaras>>> befektZarasok = this.processRequest(o -> befektZarasService.findAll(konyvEv));
    return befektZarasok.getBody().getData();
  }

  @PostMapping(value = "")
  public ResponseEntity<Object> createBefektZaras(@RequestBody final BefektZaras befektZaras) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektZarasService.create(befektZaras));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof KonyvelesiIdoszakLezartException) {
      throw (KonyvelesiIdoszakLezartException)retData;
    }
    return new ResponseEntity<>("Befektetes zárás is created successfully", HttpStatus.CREATED);
  }

  @PutMapping(value = "/update")
  public ResponseEntity<Object> updateBefektZaras(@RequestBody final BefektZaras befektZaras) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektZarasService.update(befektZaras));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof KonyvelesiIdoszakLezartException) {
      throw (KonyvelesiIdoszakLezartException)retData;
    } else if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Befektetes zárás is updated successfully", HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete/{id}/{mddat}")
  public ResponseEntity<Object> deleteBefektZaras(@PathVariable("id") final String id,
                                                  @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektZarasService.delete(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Befektetes zárás is deleted successfully", HttpStatus.OK);
  }

  @PutMapping(value = "/szamlakonyveles/{id}/{mddat}")
  public ResponseEntity<Object> szlakonyvBefektetesZaras(@PathVariable("id") final String id,
                                                         @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektZarasService.szamlaForgGen(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Befektetés zárás tétel számlakönyvelése sikeres!", HttpStatus.OK);
  }

  @PutMapping(value = "/szamlakonytorles/{id}/{mddat}")
  public ResponseEntity<Object> szlakonyvTorlesBefektetesZaras(@PathVariable("id") final String id,
                                                               @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektZarasService.szamlaForgTorl(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    } else if (retData instanceof KonyvelesiIdoszakLezartException) {
      throw (KonyvelesiIdoszakLezartException)retData;
    }
    return new ResponseEntity<>("Befektetés zárás tétel számlakönyvelése törlése sikeres!", HttpStatus.OK);
  }

  @GetMapping(value = "/parositasok/{parZarAzon}")
  public List<NyitasZarasParok> findAllParok(@PathVariable("parZarAzon") final String parZarAzon) {
    final ResponseEntity<Response<List<NyitasZarasParok>>> nyitasZarasParok = this.processRequest(
        o -> this.befektZarasService.findAllParok(parZarAzon));
    return nyitasZarasParok.getBody().getData();
  }

  @PutMapping(value = "/parositas")
  public ResponseEntity<Object> createParositas(@RequestBody final NyitasZarasParok nyitasZarasPar) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektZarasService.createParositas(nyitasZarasPar));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Befektetes zárás párosítás is updated successfully", HttpStatus.OK);
  }

  @GetMapping(value = "/hozambeallito/{parAzon}")
  public HozamBeallito findHozamBeallito(@PathVariable("parAzon") final String parAzon) {
    final ResponseEntity<Response<HozamBeallito>> hozamBeallito = this.processRequest(
        o -> this.befektZarasService.findHozamBeallito(parAzon));
    return hozamBeallito.getBody().getData();
  }

  @PutMapping(value = "/savehozam")
  public ResponseEntity<Object> hozamTarolas(@RequestBody final HozamBeallito hozamBeallito) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektZarasService.hozamTarolas(hozamBeallito));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Hozam is saved successfully", HttpStatus.OK);
  }

  @PutMapping(value = "/hozamszlakonyveles/{id}/{mddat}")
  public ResponseEntity<Object> szlakonyvHozam(@PathVariable("id") final String id,
                                               @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektZarasService.hozamSzamlaForgGen(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Hozam tétel számlakönyvelése sikeres!", HttpStatus.OK);
  }

  @PutMapping(value = "/hozamszlakonytorles/{id}/{mddat}")
  public ResponseEntity<Object> szlakonyvTorlesHozam(@PathVariable("id") final String id,
                                                     @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektZarasService.hozamSzamlaForgTorl(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    if (retData instanceof ParositottSzamlaforgalmiTetelException) {
      throw (ParositottSzamlaforgalmiTetelException)retData;
    }
    return new ResponseEntity<>("Hozam tétel számlakönyvelése törlése sikeres!", HttpStatus.OK);
  }
}
