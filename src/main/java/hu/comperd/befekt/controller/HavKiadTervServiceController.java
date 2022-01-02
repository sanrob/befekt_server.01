package hu.comperd.befekt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hu.comperd.befekt.dto.HavKiadTerv;
import hu.comperd.befekt.etc.Response;
import hu.comperd.befekt.exceptions.HavKiadTervAlreadyExitException;
import hu.comperd.befekt.exceptions.HavKiadTervCannotDeleteException;
import hu.comperd.befekt.exceptions.HavKiadTervCannotModifyException;
import hu.comperd.befekt.services.HavKiadTervServiceImpl;
import io.swagger.annotations.Api;

@RestController
@Api(tags = "Havi kiadás terv adatok")
@RequestMapping(value = "/havkiadtervek")
public class HavKiadTervServiceController extends BaseController {
  @Autowired
  HavKiadTervServiceImpl havKiadTervService = null;

  @GetMapping(value = "/{csakAktivak}")
  public List<HavKiadTerv> findAll(@PathVariable("csakAktivak") final boolean csakAktivak) {
    final ResponseEntity<Response<List<HavKiadTerv>>> havKiadTervek = this
        .processRequest(o -> havKiadTervService.findAll(csakAktivak));
    return havKiadTervek.getBody().getData();
  }

  @PostMapping(value = "")
  public ResponseEntity<Object> createHavKiadTerv(@RequestBody final HavKiadTerv havKiadTerv) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> havKiadTervService.create(havKiadTerv));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof HavKiadTervAlreadyExitException) {
      throw (HavKiadTervAlreadyExitException)retData;
    }
    return new ResponseEntity<>("Havi kiadás terv is created successfully", HttpStatus.CREATED);
  }

  @PutMapping(value = "")
  public ResponseEntity<Object> updateHavKiadTerv(@RequestBody final HavKiadTerv havKiadTerv) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> havKiadTervService.update(havKiadTerv));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof HavKiadTervCannotModifyException) {
      throw (HavKiadTervCannotModifyException)retData;
    }
    return new ResponseEntity<>("Havi kiadás terv is updated successfully", HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Object> deleteHavKiadTerv(@PathVariable("id") final String id) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> this.havKiadTervService.delete(id));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof HavKiadTervCannotDeleteException) {
      throw (HavKiadTervCannotDeleteException)retData;
    }
    return new ResponseEntity<>("Havi kiadás tervezet is deleted successfully", HttpStatus.OK);
  }
}
