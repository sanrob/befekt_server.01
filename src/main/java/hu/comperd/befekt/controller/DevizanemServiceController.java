package hu.comperd.befekt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hu.comperd.befekt.dto.Devizanem;
import hu.comperd.befekt.etc.Response;
import hu.comperd.befekt.exceptions.DevizanemAlreadyExitException;
import hu.comperd.befekt.exceptions.DevizanemCannotDeleteException;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.services.DevizanemServiceImpl;
import io.swagger.annotations.Api;

@RestController
@Api(tags = "Devizanemek")
@RequestMapping(value = "/devizanemek")
public class DevizanemServiceController extends BaseController {
  @Autowired
  DevizanemServiceImpl devizanemService = null;

  @GetMapping(value = "")
  public List<Devizanem> findAll() {
    final ResponseEntity<Response<List<Devizanem>>> devizanemek = this.processRequest(o -> devizanemService.findAll());
    return devizanemek.getBody().getData();
  }

  @PostMapping(value = "")
  public ResponseEntity<Object> createDevizanem(@RequestBody final Devizanem devizanem) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> devizanemService.create(devizanem));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof DevizanemAlreadyExitException) {
      throw (DevizanemAlreadyExitException)retData;
    }
    return new ResponseEntity<>("Devizanem is created successfully", HttpStatus.CREATED);
  }

  @PutMapping(value = "/update")
  public ResponseEntity<Object> updateDevizanem(@RequestBody final Devizanem devizanem) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> devizanemService.update(devizanem));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Devizanem is updated successfully", HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete/{id}/{mddat}")
  public ResponseEntity<Object> deleteDevizanem(@PathVariable("id") final String id,
                                                @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> devizanemService.delete(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof DevizanemCannotDeleteException) {
      throw (DevizanemCannotDeleteException)retData;
    } else if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Devizanem is deleted successfully", HttpStatus.OK);
  }
}
