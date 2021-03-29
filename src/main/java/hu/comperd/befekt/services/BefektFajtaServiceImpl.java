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
import hu.comperd.befekt.collections.BefektFajtaCol;
import hu.comperd.befekt.collections.BefektetesCol;
import hu.comperd.befekt.dto.BefektFajta;
import hu.comperd.befekt.etc.DomainCsoportok;
import hu.comperd.befekt.exceptions.BefektFajtaAlreadyExitException;
import hu.comperd.befekt.exceptions.BefektFajtaCannotDeleteException;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.repositories.BefektFajtaRepository;
import hu.comperd.befekt.repositories.BefektetesRepository;
import hu.comperd.befekt.repositories.DomainRepository;
import hu.comperd.befekt.repositories.SzamlaRepository;

@Service
public class BefektFajtaServiceImpl {
  private static final Logger   logger = LoggerFactory.getLogger(BevetelServiceImpl.class);
  @Autowired
  private BefektFajtaRepository repository;
  @Autowired
  private SzamlaRepository      szarepo;
  @Autowired
  private DomainRepository      domrepo;
  @Autowired
  private BefektetesRepository  befektrepo;

  public List<BefektFajta> findAll() {
    final List<BefektFajtaCol> befektFajtaCols = this.repository.findAllByOrderByBffKod();
    final List<BefektFajta> befektFajtak = new ArrayList<>();
    for (final BefektFajtaCol befektFajtaCol : befektFajtaCols) {
      final BefektFajta befektFajta = new BefektFajta();
      befektFajta.setId(befektFajtaCol.getId());
      befektFajta.setBffKod(befektFajtaCol.getBffKod());
      befektFajta.setBffMegnev(befektFajtaCol.getBffMegnev());
      befektFajta.setBffTip(befektFajtaCol.getBffTip());
      befektFajta.setBffTipNev(
          domrepo.findByDomCsoportKodAndDomKod(DomainCsoportok.BEFFAJTTIP, befektFajtaCol.getBffTip()).getDomMegnev());
      befektFajta.setBffSzamla(befektFajtaCol.getBffSzamla());
      befektFajta.setBffSzamlaNev(szarepo.findBySzaKod(befektFajtaCol.getBffSzamla()).getSzaMegnev());
      befektFajta.setBffJutSzla(befektFajtaCol.getBffJutSzla());
      befektFajta.setBffJutSzlaNev(szarepo.findBySzaKod(befektFajtaCol.getBffJutSzla()).getSzaMegnev());
      befektFajta.setBffEgyseg(befektFajtaCol.getBffEgyseg());
      befektFajta.setBffNyitElsz(befektFajtaCol.getBffNyitElsz());
      befektFajta.setBffNyitElszNev(
          domrepo.findByDomCsoportKodAndDomKod(DomainCsoportok.BEFELSZAM, befektFajtaCol.getBffNyitElsz()).getDomMegnev());
      befektFajta.setBffMddat(befektFajtaCol.getBffMddat());
      befektFajtak.add(befektFajta);
    }
    return befektFajtak;
  }

  public List<BefektFajta> findAllKivalasztashoz() {
    final List<BefektFajtaCol> befektFajtaCols = this.repository.findAllByOrderByBffKodDesc();
    final List<BefektFajta> befektFajtak = new ArrayList<>();
    for (final BefektFajtaCol befektFajtaCol : befektFajtaCols) {
      final BefektFajta befektFajta = new BefektFajta();
      befektFajta.setBffKod(befektFajtaCol.getBffKod());
      befektFajta.setBffMegnev(befektFajtaCol.getBffMegnev() + "(" + befektFajtaCol.getBffKod() + ")");
      befektFajtak.add(befektFajta);
    }
    return befektFajtak;
  }

  public Object create(final BefektFajta befektFajta) {
    final BefektFajtaCol befektFajtaCol = new BefektFajtaCol(befektFajta);
    final BefektFajtaCol befektFajtaColOld = this.repository.findByBffKod(befektFajtaCol.getBffKod());
    if (befektFajtaColOld != null) {
      return new BefektFajtaAlreadyExitException(befektFajtaColOld.getBffKod());
    }
    this.repository.save(befektFajtaCol);
    logger.info("Created Record: {}", befektFajtaCol);
    return null;
  }

  public Object update(final BefektFajta befektFajta) {
    final Optional<BefektFajtaCol> befektFajtaObj = this.repository.findById(befektFajta.getId());
    if (befektFajtaObj.isPresent()) {
      final BefektFajtaCol befektFajtaCol = befektFajtaObj.get();
      if (befektFajtaCol.getBffMddat().toInstant().equals(befektFajta.getBffMddat().toInstant())) {
        befektFajtaCol.setBffKod(befektFajta.getBffKod());
        befektFajtaCol.setBffMegnev(befektFajta.getBffMegnev());
        befektFajtaCol.setBffTip(befektFajta.getBffTip());
        befektFajtaCol.setBffSzamla(befektFajta.getBffSzamla());
        befektFajtaCol.setBffJutSzla(befektFajta.getBffJutSzla());
        befektFajtaCol.setBffEgyseg(befektFajta.getBffEgyseg());
        befektFajtaCol.setBffNyitElsz(befektFajta.getBffNyitElsz());
        befektFajtaCol.setBffMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.repository.save(befektFajtaCol);
        logger.info("Updated Record: {}", befektFajtaCol);
      } else {
        return new MegvaltozottTartalomException("Befektetés fajta", "módosítás");
      }
    }
    return null;
  }

  public Object delete(final String id, final String mddat) {
    final Optional<BefektFajtaCol> befektFajtaObj = this.repository.findById(id);
    if (befektFajtaObj.isPresent()) {
      final BefektFajtaCol befektFajtaCol = befektFajtaObj.get();
      final List<BefektetesCol> befektetesCol = befektrepo.findByBefBffKod(befektFajtaCol.getBffKod());
      if (!befektetesCol.isEmpty()) {
        return new BefektFajtaCannotDeleteException(befektFajtaCol.getBffKod());
      }
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (befektFajtaCol.getBffMddat().equals(pMddat)) {
        this.repository.deleteById(id);
        logger.info("Deleted Record: {}", befektFajtaCol);
      } else {
        return new MegvaltozottTartalomException("Befektetés fajta", "törlés");
      }
    }
    return null;
  }
}
