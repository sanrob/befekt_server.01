package hu.comperd.befekt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hu.comperd.befekt.dto.AltKiadSzamlankent;
import hu.comperd.befekt.dto.HavKiadTeny;
import hu.comperd.befekt.dto.HonapZarasa;
import hu.comperd.befekt.etc.Response;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.exceptions.VanKonyveltGeneraltKiadasException;
import hu.comperd.befekt.services.HavKiadTenyServiceImpl;
import io.swagger.annotations.Api;

@RestController
@Api(tags = "Havi kiadás tény adatok")
@RequestMapping(value = "/havkiadteny")
public class HavKiadTenyServiceController extends BaseController {
  @Autowired
  HavKiadTenyServiceImpl havKiadTenyService = null;

  @GetMapping(value = "/eddigikiadások/{honap}")
  public List<HavKiadTeny> findHaviKiadasok(@PathVariable("honap") final String honap) {
    final ResponseEntity<Response<List<HavKiadTeny>>> haviKiadasok = this.processRequest(
        o -> this.havKiadTenyService.findAllByHkmHonap(honap));
    return haviKiadasok.getBody().getData();
  }

  @PutMapping(value = "")
  public ResponseEntity<Object> createOrUpdateHavKiadTeny(@RequestBody final HavKiadTeny havKiadTeny) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> this.havKiadTenyService.createOrUpdate(havKiadTeny));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Havi kiadás tény is created/updated successfully", HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}/{mddat}")
  public ResponseEntity<Object> deleteHavKiadTeny(@PathVariable("id") final String id, @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> this.havKiadTenyService.delete(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Havi Kiadás Tény is deleted successfully", HttpStatus.OK);
  }

  @GetMapping(value = "/szamlankent/{honap}")
  public List<AltKiadSzamlankent> getSzamlankentiOsszesen(@PathVariable("honap") final String honap) {
    final ResponseEntity<Response<List<AltKiadSzamlankent>>> szamlankentiOsszesenek = this.processRequest(
        o -> this.havKiadTenyService.findSzamlankentiOsszesen(honap));
    return szamlankentiOsszesenek.getBody().getData();
  }

  @GetMapping(value = "/zarasadatok/{honap}")
  public HonapZarasa getEgyHonapZarasAdata(@PathVariable("honap") final String honap) {
    final ResponseEntity<Response<HonapZarasa>> egyHonapAdata = this.processRequest(
        o -> this.havKiadTenyService.getEgyHonapZarasAdata(honap));
    return egyHonapAdata.getBody().getData();
  }

  @PutMapping(value = "/altkiadgener/{honap}")
  public ResponseEntity<Object> kiadasTetelGenaralas(@PathVariable("honap") final String honap) {
    this.processRequest(o -> this.havKiadTenyService.kiadasTetelGenaralas(honap));
    return new ResponseEntity<>("Általános kiadás tételek generálása successfully", HttpStatus.OK);
  }

  @DeleteMapping(value = "/altkiadtorles/{honap}")
  public ResponseEntity<Object> kiadasTetelTorles(@PathVariable("honap") final String honap) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> this.havKiadTenyService.kiadasTetelTorles(honap));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof VanKonyveltGeneraltKiadasException) {
      throw (VanKonyveltGeneraltKiadasException)retData;
    }
    return new ResponseEntity<>("Általános kiadás tételek törlése successfully", HttpStatus.OK);
  }
  /*
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
  */
  /*
  @GetMapping(value = "kivalasztashoz")
  public List<BefektFajta> findAllKivalasztashoz() {
    final ResponseEntity<Response<List<BefektFajta>>> befektFajtak = this.processRequest(
        o -> befektFajtaService.findAllKivalasztashoz());
    return befektFajtak.getBody().getData();
  }
  */
}
