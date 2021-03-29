package hu.comperd.befekt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hu.comperd.befekt.dto.BefektetesArfolyam;
import hu.comperd.befekt.etc.Response;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.services.BefektetesArfolyamServiceImpl;

@RestController
@RequestMapping(value = "/befektetesarfolyam")
public class BefektetesArfolyamServiceController extends BaseController {
  @Autowired
  BefektetesArfolyamServiceImpl befektArfolyamService = null;

  @GetMapping(value = "/{beaBefAzon}")
  public List<BefektetesArfolyam> findAll(@PathVariable("beaBefAzon") final String beaBefAzon) {
    final ResponseEntity<Response<List<BefektetesArfolyam>>> befektArfolyamok = this.processRequest(
        o -> befektArfolyamService.findAll(beaBefAzon));
    return befektArfolyamok.getBody().getData();
  }

  @PostMapping(value = "")
  public ResponseEntity<Object> createBefektetesArfolyam(@RequestBody final BefektetesArfolyam befektetesArfolyam) {
    this.processRequest(o -> befektArfolyamService.create(befektetesArfolyam));
    return new ResponseEntity<>("Befektetés árfolyam is created successfully", HttpStatus.CREATED);
  }

  @PutMapping(value = "/update")
  public ResponseEntity<Object> updateBefektetesArfolyam(@RequestBody final BefektetesArfolyam befektArfolyam) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektArfolyamService.update(befektArfolyam));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Befektetés árfrolyam is updated successfully", HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete/{id}/{mddat}")
  public ResponseEntity<Object> deleteBefektetesArfolyam(@PathVariable("id") final String id,
                                                         @PathVariable("mddat") final String mddat) {
    final ResponseEntity<Response<Object>> retObj = this.processRequest(o -> befektArfolyamService.delete(id, mddat));
    final Object retData = retObj.getBody().getData();
    if (retData instanceof MegvaltozottTartalomException) {
      throw (MegvaltozottTartalomException)retData;
    }
    return new ResponseEntity<>("Befektetés árfolyamO is deleted successfully", HttpStatus.OK);
  }
}
