package hu.comperd.befekt.services;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hu.comperd.befekt.collections.BefektetesArfolyamCol;
import hu.comperd.befekt.dto.BefektetesArfolyam;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.repositories.BefektetesArfolyamRepository;

@Service
public class BefektetesArfolyamServiceImpl {
  private static final Logger          logger = LoggerFactory.getLogger(BefektetesArfolyamServiceImpl.class);
  @Autowired
  private BefektetesArfolyamRepository repository;

  public List<BefektetesArfolyam> findAll(final String beaBefAzon) {
    final List<BefektetesArfolyamCol> befektetesArfolyamCols = this.repository.findByBeaBefAzonOrderByBeaArfDatumDesc(beaBefAzon);
    final List<BefektetesArfolyam> befektetesArfolyamok = new ArrayList<>();
    for (final BefektetesArfolyamCol befektetesArfolyamCol : befektetesArfolyamCols) {
      final BefektetesArfolyam befektetesArfolyam = new BefektetesArfolyam();
      befektetesArfolyam.setId(befektetesArfolyamCol.getId());
      befektetesArfolyam.setBeaBefAzon(befektetesArfolyamCol.getBeaBefAzon());
      befektetesArfolyam.setBeaArfDatum(befektetesArfolyamCol.getBeaArfDatum());
      befektetesArfolyam.setBeaArfolyam(befektetesArfolyamCol.getBeaArfolyam());
      befektetesArfolyam.setBeaMddat(befektetesArfolyamCol.getBeaMddat());
      befektetesArfolyamok.add(befektetesArfolyam);
    }
    return befektetesArfolyamok;
  }

  public Object create(final BefektetesArfolyam befektetesArfolyam) {
    BefektetesArfolyamCol befektetesArfolyamCol = this.repository.findByBeaBefAzonAndBeaArfDatum(
        befektetesArfolyam.getBeaBefAzon(), befektetesArfolyam.getBeaArfDatum());
    boolean created = true;
    if (befektetesArfolyamCol == null) {
      befektetesArfolyamCol = new BefektetesArfolyamCol(befektetesArfolyam);
    } else {
      befektetesArfolyamCol.setBeaArfolyam(befektetesArfolyam.getBeaArfolyam());
      befektetesArfolyamCol.setBeaMddat(ZonedDateTime.now(ZoneId.systemDefault()));
      created = false;
    }
    this.repository.save(befektetesArfolyamCol);
    logger.info((created ? "Created" : "Mofified") + " Record: {}", befektetesArfolyamCol);
    return null;
  }

  public Object update(final BefektetesArfolyam befektetesArfolyam) {
    final Optional<BefektetesArfolyamCol> befektetesArfolyamObj = this.repository.findById(befektetesArfolyam.getId());
    if (befektetesArfolyamObj.isPresent()) {
      final BefektetesArfolyamCol befektetesArfolyamCol = befektetesArfolyamObj.get();
      if (befektetesArfolyamCol.getBeaMddat().toInstant().equals(befektetesArfolyam.getBeaMddat().toInstant())) {
        befektetesArfolyamCol.setBeaArfolyam(befektetesArfolyam.getBeaArfolyam());
        befektetesArfolyamCol.setBeaMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.repository.save(befektetesArfolyamCol);
        logger.info("Updated Record: {}", befektetesArfolyamCol);
      } else {
        return new MegvaltozottTartalomException("Befektetés árfolyam", "módosítás");
      }
    }
    return null;
  }

  public Object delete(final String id, final String mddat) {
    final Optional<BefektetesArfolyamCol> befektetesArfolyamObj = this.repository.findById(id);
    if (befektetesArfolyamObj.isPresent()) {
      final BefektetesArfolyamCol befektetesArfolyamCol = befektetesArfolyamObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (befektetesArfolyamCol.getBeaMddat().equals(pMddat)) {
        this.repository.deleteById(id);
        logger.info("Deleted Record: {}", befektetesArfolyamCol);
      } else {
        return new MegvaltozottTartalomException("Befektetés árfolyam", "törlés");
      }
    }
    return null;
  }
}
