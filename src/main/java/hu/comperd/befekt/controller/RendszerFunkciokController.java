package hu.comperd.befekt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import hu.comperd.befekt.dto.EvvaltInfo;
import hu.comperd.befekt.etc.Response;
import hu.comperd.befekt.services.RendszerFunkciokServiceImpl;
import io.swagger.annotations.Api;

@RestController
@Api(tags = "Rendszer Funkciók")
@RequestMapping(value = "/rendszerfunkciok")
public class RendszerFunkciokController extends BaseController {
  @Autowired
  RendszerFunkciokServiceImpl rendszerFunkciokService = null;

  @GetMapping(value = "/evvaltinfo")
  public List<EvvaltInfo> getEvvaltInfo() {
    final ResponseEntity<Response<List<EvvaltInfo>>> konyvelesiEvek = this.processRequest(
        o -> this.rendszerFunkciokService.getEvvaltInfo());
    return konyvelesiEvek.getBody().getData();
  }

  @PostMapping(value = "/evvalt/{newEv}")
  public ResponseEntity<Object> doEvvalt(@PathVariable("newEv") final String newEv) {
    this.processRequest(o -> this.rendszerFunkciokService.doEvvaltas(newEv));
    return new ResponseEntity<>("Új könyvelési év létrehozása sikeres!", HttpStatus.CREATED);
  }

  @PostMapping(value = "/evpotlas/{ev}")
  public ResponseEntity<Object> doEvPotlas(@PathVariable("ev") final String ev) {
    this.processRequest(o -> this.rendszerFunkciokService.doEvPotlas(ev));
    return new ResponseEntity<>("Könyvelési év adatainak pótlása sikeres!", HttpStatus.CREATED);
  }
}
