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
import hu.comperd.befekt.collections.SzamlaCol;
import hu.comperd.befekt.collections.SzamlavezetoCol;
import hu.comperd.befekt.dto.Szamlavezeto;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.exceptions.SzamlavezetoAlreadyExitException;
import hu.comperd.befekt.exceptions.SzamlavezetoCannotDeleteException;
import hu.comperd.befekt.repositories.SzamlaRepository;
import hu.comperd.befekt.repositories.SzamlavezetoRepository;

@Service
public class SzamlavezetoServiceImpl {
  private static final Logger    logger = LoggerFactory.getLogger(SzamlavezetoServiceImpl.class);

  @Autowired
  private SzamlavezetoRepository repository;
  @Autowired
  private SzamlaRepository       szlaRepo;

  public List<Szamlavezeto> findAll() {
    final List<SzamlavezetoCol> szamlavezetoCols = this.repository.findAllByOrderBySzvKodAsc();
    final List<Szamlavezeto> szamlavezetok = new ArrayList<>();
    for (final SzamlavezetoCol szamlavezetoCol : szamlavezetoCols) {
      final Szamlavezeto szamlavezeto = new Szamlavezeto();
      szamlavezeto.setId(szamlavezetoCol.getId());
      szamlavezeto.setSzvKod(szamlavezetoCol.getSzvKod());
      szamlavezeto.setSzvMegnev(szamlavezetoCol.getSzvMegnev());
      szamlavezeto.setSzvMddat(szamlavezetoCol.getSzvMddat());
      szamlavezetok.add(szamlavezeto);
    }
    return szamlavezetok;
  }

  public Object create(final Szamlavezeto szamlavezeto) {
    final SzamlavezetoCol szamlavezetoCol = new SzamlavezetoCol(szamlavezeto);
    final SzamlavezetoCol szamlavezetoColOld = this.repository.findBySzvKod(szamlavezeto.getSzvKod());
    if (szamlavezetoColOld != null) {
      return new SzamlavezetoAlreadyExitException(szamlavezetoColOld.getSzvKod());
    }
    this.repository.save(szamlavezetoCol);
    logger.info("Created Record: {}", szamlavezetoCol);
    return null;
  }

  public Object update(final Szamlavezeto szamlavezeto) {
    final Optional<SzamlavezetoCol> szamlavezetoObj = this.repository.findById(szamlavezeto.getId());
    if (szamlavezetoObj.isPresent()) {
      final SzamlavezetoCol szamlavezetoCol = szamlavezetoObj.get();
      if (szamlavezetoCol.getSzvMddat().toInstant().equals(szamlavezeto.getSzvMddat().toInstant())) {
        szamlavezetoCol.setSzvKod(szamlavezeto.getSzvKod());
        szamlavezetoCol.setSzvMegnev(szamlavezeto.getSzvMegnev());
        szamlavezetoCol.setSzvMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.repository.save(szamlavezetoCol);
        logger.info("Updated Record: {}", szamlavezetoCol);
      } else {
        return new MegvaltozottTartalomException("Számlavezető", "módosítás");
      }
    }
    return null;
  }

  public Object delete(final String id, final String mddat) {
    final Optional<SzamlavezetoCol> szamlavezetoObj = this.repository.findById(id);
    if (szamlavezetoObj.isPresent()) {
      final SzamlavezetoCol szamlavezetoCol = szamlavezetoObj.get();
      final List<SzamlaCol> szamlak = szlaRepo.findBySzaPenzint(szamlavezetoCol.getSzvKod());
      if (!szamlak.isEmpty()) {
        return new SzamlavezetoCannotDeleteException(szamlavezetoCol.getSzvKod());
      }
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (szamlavezetoCol.getSzvMddat().equals(pMddat)) {
        this.repository.deleteById(id);
        logger.info("Deleted Record: {}", szamlavezetoCol);
      } else {
        return new MegvaltozottTartalomException("Számlavezető", "törlés");
      }
    }
    return null;
  }
}
