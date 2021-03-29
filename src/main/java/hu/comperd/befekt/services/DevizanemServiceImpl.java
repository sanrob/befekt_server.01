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
import hu.comperd.befekt.collections.DevizanemCol;
import hu.comperd.befekt.collections.SzamlaCol;
import hu.comperd.befekt.dto.Devizanem;
import hu.comperd.befekt.exceptions.DevizanemAlreadyExitException;
import hu.comperd.befekt.exceptions.DevizanemCannotDeleteException;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.repositories.DevizanemRepository;
import hu.comperd.befekt.repositories.SzamlaRepository;

@Service
public class DevizanemServiceImpl {
  private static final Logger logger = LoggerFactory.getLogger(DevizanemServiceImpl.class);

  @Autowired
  private DevizanemRepository repository;
  @Autowired
  private SzamlaRepository    szlaRepo;

  public List<Devizanem> findAll() {
    final List<DevizanemCol> devizanemCols = this.repository.findAllByOrderByDevKodAsc();
    final List<Devizanem> devizanemek = new ArrayList<>();
    for (final DevizanemCol devizanemCol : devizanemCols) {
      final Devizanem devizanem = new Devizanem();
      devizanem.setId(devizanemCol.getId());
      devizanem.setDevKod(devizanemCol.getDevKod());
      devizanem.setDevMegnev(devizanemCol.getDevMegnev());
      devizanem.setDevMddat(devizanemCol.getDevMddat());
      devizanemek.add(devizanem);
    }
    return devizanemek;
  }

  public Object create(final Devizanem devizanem) {
    final DevizanemCol devizanemCol = new DevizanemCol(devizanem);
    final DevizanemCol devizanemColOld = this.repository.findByDevKod(devizanemCol.getDevKod());
    if (devizanemColOld != null) {
      return new DevizanemAlreadyExitException(devizanemColOld.getDevKod());
    }
    this.repository.save(devizanemCol);
    logger.info("Created Record: {}", devizanemCol);
    return null;
  }

  public Object update(final Devizanem devizanem) {
    final Optional<DevizanemCol> devizanemObj = this.repository.findById(devizanem.getId());
    if (devizanemObj.isPresent()) {
      final DevizanemCol devizanemCol = devizanemObj.get();
      if (devizanemCol.getDevMddat().toInstant().equals(devizanem.getDevMddat().toInstant())) {
        devizanemCol.setDevKod(devizanem.getDevKod());
        devizanemCol.setDevMegnev(devizanem.getDevMegnev());
        devizanemCol.setDevMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.repository.save(devizanemCol);
        logger.info("Updated Record: {}", devizanemCol);
      } else {
        return new MegvaltozottTartalomException("Devizanem", "módosítás");
      }
    }
    return null;
  }

  public Object delete(final String id, final String mddat) {
    final Optional<DevizanemCol> devizanemObj = this.repository.findById(id);
    if (devizanemObj.isPresent()) {
      final DevizanemCol devizanemCol = devizanemObj.get();
      final List<SzamlaCol> szamlak = szlaRepo.findBySzaDeviza(devizanemCol.getDevKod());
      if (!szamlak.isEmpty()) {
        return new DevizanemCannotDeleteException(devizanemCol.getDevKod());
      }
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (devizanemCol.getDevMddat().equals(pMddat)) {
        this.repository.deleteById(id);
        logger.info("Deleted Record: {}", devizanemCol);
      } else {
        return new MegvaltozottTartalomException("Devizanem", "törlés");
      }
    }
    return null;
  }
}
