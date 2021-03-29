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
import hu.comperd.befekt.collections.*;
import hu.comperd.befekt.dto.Szamla;
import hu.comperd.befekt.etc.DomainCsoportok;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.exceptions.SzamlaAlreadyExitException;
import hu.comperd.befekt.exceptions.SzamlaCannotDeleteException;
import hu.comperd.befekt.repositories.*;

@Service
public class SzamlaServiceImpl {
  private static final Logger    logger = LoggerFactory.getLogger(SzamlaServiceImpl.class);

  @Autowired
  private SzamlaRepository       repository;
  @Autowired
  private DevizanemRepository    devrepo;
  @Autowired
  private SzamlavezetoRepository szvrepo;
  @Autowired
  private DomainRepository       domrepo;
  @Autowired
  private BevetelRepository      bevrepo;
  @Autowired
  private BefektFajtaRepository  befrepo;
  @Autowired
  private KiadasRepository       kiarepo;
  @Autowired
  private HavKiadTervRepository  hktrepo;
  @Autowired
  private TranszferRepository    trarepo;

  public List<Szamla> findAll(final String pSzaSzlatip) {
    final List<SzamlaCol> szamlaCols = this.repository.findBySzaSzlatipOrderBySzaMegnevAsc(pSzaSzlatip);
    final List<Szamla> szamlas = new ArrayList<>();
    for (final SzamlaCol szamlaCol : szamlaCols) {
      final Szamla szamla = new Szamla();
      szamla.setId(szamlaCol.getId());
      szamla.setSzaKod(szamlaCol.getSzaKod());
      szamla.setSzaMegnev(szamlaCol.getSzaMegnev());
      szamla.setSzaSzlatip(szamlaCol.getSzaSzlatip());
      szamla.setSzaSzlatipNev(
          domrepo.findByDomCsoportKodAndDomKod(DomainCsoportok.SZLATIP, szamlaCol.getSzaSzlatip()).getDomMegnev());
      szamla.setSzaPenzint(szamlaCol.getSzaPenzint());
      szamla.setSzaPenzintNev(szvrepo.findBySzvKod(szamlaCol.getSzaPenzint()).getSzvMegnev());
      szamla.setSzaDeviza(szamlaCol.getSzaDeviza());
      szamla.setSzaDevizaNev(devrepo.findByDevKod(szamlaCol.getSzaDeviza()).getDevMegnev());
      szamlas.add(szamla);
    }
    return szamlas;
  }

  public List<Szamla> findAll() {
    final List<SzamlaCol> szamlaCols = this.repository.findAllByOrderBySzaKod();
    final List<Szamla> szamlas = new ArrayList<>();
    for (final SzamlaCol szamlaCol : szamlaCols) {
      final Szamla szamla = new Szamla();
      szamla.setId(szamlaCol.getId());
      szamla.setSzaKod(szamlaCol.getSzaKod());
      szamla.setSzaMegnev(szamlaCol.getSzaMegnev());
      szamla.setSzaSzlatip(szamlaCol.getSzaSzlatip());
      szamla.setSzaSzlatipNev(
          domrepo.findByDomCsoportKodAndDomKod(DomainCsoportok.SZLATIP, szamlaCol.getSzaSzlatip()).getDomMegnev());
      szamla.setSzaPenzint(szamlaCol.getSzaPenzint());
      szamla.setSzaPenzintNev(szvrepo.findBySzvKod(szamlaCol.getSzaPenzint()).getSzvMegnev());
      szamla.setSzaDeviza(szamlaCol.getSzaDeviza());
      szamla.setSzaDevizaNev(devrepo.findByDevKod(szamlaCol.getSzaDeviza()).getDevMegnev());
      szamla.setSzaMddat(szamlaCol.getSzaMddat());
      szamlas.add(szamla);
    }
    return szamlas;
  }

  public Object create(final Szamla szamla) {
    final SzamlaCol szamlaCol = new SzamlaCol(szamla);
    final SzamlaCol szamlaColOld = this.repository.findBySzaKod(szamlaCol.getSzaKod());
    if (szamlaColOld != null) {
      return new SzamlaAlreadyExitException(szamlaColOld.getSzaKod());
    }
    this.repository.save(szamlaCol);
    logger.info("Created Record: {}", szamlaCol);
    return null;
  }

  public Object update(final Szamla szamla) {
    final Optional<SzamlaCol> szamlaObj = this.repository.findById(szamla.getId());
    if (szamlaObj.isPresent()) {
      final SzamlaCol szamlaCol = szamlaObj.get();
      if (szamlaCol.getSzaMddat().toInstant().equals(szamla.getSzaMddat().toInstant())) {
        szamlaCol.setSzaKod(szamla.getSzaKod());
        szamlaCol.setSzaMegnev(szamla.getSzaMegnev());
        szamlaCol.setSzaSzlatip(szamla.getSzaSzlatip());
        szamlaCol.setSzaPenzint(szamla.getSzaPenzint());
        szamlaCol.setSzaDeviza(szamla.getSzaDeviza());
        szamlaCol.setSzaMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.repository.save(szamlaCol);
        logger.info("Updated Record: {}", szamlaCol);
      } else {
        return new MegvaltozottTartalomException("Számla", "módosítás");
      }
    }
    return null;
  }

  public Object delete(final String id, final String mddat) {
    final Optional<SzamlaCol> szamlaObj = this.repository.findById(id);
    if (szamlaObj.isPresent()) {
      final SzamlaCol szamlaCol = szamlaObj.get();
      List<TranszferCol> transzferek = trarepo.findAllByTraHova(szamlaCol.getSzaKod());
      if (!transzferek.isEmpty()) {
        return new SzamlaCannotDeleteException(szamlaCol.getSzaKod() + " (Hova)", transzferek.get(0));
      }
      transzferek = trarepo.findAllByTraHonnan(szamlaCol.getSzaKod());
      if (!transzferek.isEmpty()) {
        return new SzamlaCannotDeleteException(szamlaCol.getSzaKod() + " (Honnan)", transzferek.get(0));
      }
      final List<HavKiadTervCol> havKiadTervek = hktrepo.findAllByhktSzamla(szamlaCol.getSzaKod());
      if (!havKiadTervek.isEmpty()) {
        return new SzamlaCannotDeleteException(szamlaCol.getSzaKod(), havKiadTervek.get(0));
      }
      final List<KiadasCol> kiadasok = kiarepo.findByKiaSzamla(szamlaCol.getSzaKod());
      if (!kiadasok.isEmpty()) {
        return new SzamlaCannotDeleteException(szamlaCol.getSzaKod(), kiadasok.get(0));
      }
      final List<BefektFajtaCol> befektFajtak = befrepo.findByBffSzamla(szamlaCol.getSzaKod());
      if (!befektFajtak.isEmpty()) {
        return new SzamlaCannotDeleteException(szamlaCol.getSzaKod(), befektFajtak.get(0));
      }
      final List<BevetelCol> bevetelek = bevrepo.findByBevSzamla(szamlaCol.getSzaKod());
      if (!bevetelek.isEmpty()) {
        return new SzamlaCannotDeleteException(szamlaCol.getSzaKod(), bevetelek.get(0));
      }
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (szamlaCol.getSzaMddat().equals(pMddat)) {
        this.repository.deleteById(id);
        logger.info("Deleted Record: {}", szamlaCol);
      } else {
        return new MegvaltozottTartalomException("Számla", "törlés");
      }
    }
    return null;
  }
}
