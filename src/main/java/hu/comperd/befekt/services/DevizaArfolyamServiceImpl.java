package hu.comperd.befekt.services;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hu.comperd.befekt.collections.DevizaArfolyamCol;
import hu.comperd.befekt.dto.DevizaArfolyam;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.repositories.DevizaArfolyamRepository;

@Service
public class DevizaArfolyamServiceImpl {
  private static final Logger      logger = LoggerFactory.getLogger(DevizaArfolyamServiceImpl.class);
  @Autowired
  private DevizaArfolyamRepository repository;

  public List<DevizaArfolyam> findAll(final String deaMirolDevKodAnd, final String deaMireDevKodAnd) {
    return this.repository.findByDeaMirolDevKodAndDeaMireDevKodOrderByDeaArfDatumDesc(deaMirolDevKodAnd, deaMireDevKodAnd)
        .stream().map(DevizaArfolyam::new).collect(Collectors.toList());
  }

  public Object create(final DevizaArfolyam devizaArfolyam) {
    DevizaArfolyamCol devizaArfolyamCol = this.repository.findByDeaMirolDevKodAndDeaMireDevKodAndDeaArfDatum(
        devizaArfolyam.getDeaMirolDevKod(), devizaArfolyam.getDeaMireDevKod(), devizaArfolyam.getDeaArfDatum());
    boolean created = true;
    if (devizaArfolyamCol == null) {
      devizaArfolyamCol = new DevizaArfolyamCol(devizaArfolyam);
    } else {
      devizaArfolyamCol.setDeaArfolyam(devizaArfolyam.getDeaArfolyam());
      devizaArfolyamCol.setDeaMddat(ZonedDateTime.now(ZoneId.systemDefault()));
      created = false;
    }
    this.repository.save(devizaArfolyamCol);
    logger.info((created ? "Created" : "Modified") + " Record: {}", devizaArfolyamCol);
    return null;
  }

  public Object update(final DevizaArfolyam devizaArfolyam) {
    final Optional<DevizaArfolyamCol> devizaArfolyamObj = this.repository.findById(devizaArfolyam.getId());
    if (devizaArfolyamObj.isPresent()) {
      final DevizaArfolyamCol devizaArfolyamCol = devizaArfolyamObj.get();
      if (devizaArfolyamCol.getDeaMddat().toInstant().equals(devizaArfolyam.getDeaMddat().toInstant())) {
        devizaArfolyamCol.setDeaArfolyam(devizaArfolyam.getDeaArfolyam());
        devizaArfolyamCol.setDeaMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.repository.save(devizaArfolyamCol);
        logger.info("Updated Record: {}", devizaArfolyamCol);
      } else {
        return new MegvaltozottTartalomException("Deviza árfolyam", "módosítás");
      }
    }
    return null;
  }

  public Object delete(final String id, final String mddat) {
    final Optional<DevizaArfolyamCol> devizaArfolyamObj = this.repository.findById(id);
    if (devizaArfolyamObj.isPresent()) {
      final DevizaArfolyamCol devizaArfolyamCol = devizaArfolyamObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (devizaArfolyamCol.getDeaMddat().equals(pMddat)) {
        this.repository.deleteById(id);
        logger.info("Deleted Record: {}", devizaArfolyamCol);
      } else {
        return new MegvaltozottTartalomException("Deviza árfolyam", "törlés");
      }
    }
    return null;
  }
}
