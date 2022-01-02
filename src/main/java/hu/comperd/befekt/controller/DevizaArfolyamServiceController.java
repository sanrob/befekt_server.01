package hu.comperd.befekt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hu.comperd.befekt.dto.DevizaArfolyam;
import hu.comperd.befekt.etc.Response;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.services.DevizaArfolyamServiceImpl;
import io.swagger.annotations.Api;

@RestController
@Api(tags = "Deviza árfolyamok")
@RequestMapping(value = "/devizaarfolyam")
public class DevizaArfolyamServiceController extends BaseController {
  @Autowired
  DevizaArfolyamServiceImpl devizaArfolyamService = null;

  @GetMapping(value = "/{deaMirolDevKod}/{deaMireDevKod}")
  public List<DevizaArfolyam> findAll(@PathVariable("deaMirolDevKod") final String deaMirolDevKod,
                                      @PathVariable("deaMireDevKod") final String deaMireDevKod) {
    final ResponseEntity<Response<List<DevizaArfolyam>>> devizaArfolyamok = this.processRequest(
        o -> devizaArfolyamService.findAll(deaMirolDevKod, deaMireDevKod));
    return devizaArfolyamok.getBody().getData();
  }

  @PostMapping(value = "")
  public ResponseEntity<Object> createDevizaArfolyam(@RequestBody final DevizaArfolyam devizaArfolyam) {
    this.processRequest(o -> devizaArfolyamService.create(devizaArfolyam));
    return new ResponseEntity<>("Deviza árfolyam is created successfully", HttpStatus.CREATED);
  }

  @PutMapping(value = "/update")
  public ResponseEntity<Object> updateDevizaArfolyam(@RequestBody final DevizaArfolyam devizaArfolyam) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> this.devizaArfolyamService.update(devizaArfolyam));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Deviza árfrolyam is updated successfully", HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete/{id}/{mddat}")
  public ResponseEntity<Object> deleteDevizaArfolyam(@PathVariable("id") final String id,
                                                     @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> this.devizaArfolyamService.delete(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Deviza árfolyamO is deleted successfully", HttpStatus.OK);
  }
}
