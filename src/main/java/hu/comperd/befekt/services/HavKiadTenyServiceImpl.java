package hu.comperd.befekt.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hu.comperd.befekt.collections.HavKiadTenyCol;
import hu.comperd.befekt.collections.HavKiadTervCol;
import hu.comperd.befekt.dto.HavKiadTeny;
import hu.comperd.befekt.repositories.HavKiadTenyRepository;
import hu.comperd.befekt.repositories.HavKiadTervRepository;
import hu.comperd.befekt.repositories.SzamlaRepository;

@Service
public class HavKiadTenyServiceImpl {
  //  private static final Logger   logger = LoggerFactory.getLogger(HavKiadTenyServiceImpl.class);
  @Autowired
  private HavKiadTenyRepository repository;
  @Autowired
  private HavKiadTervRepository tervRepo;
  @Autowired
  private SzamlaRepository      szarepo;

  public List<HavKiadTeny> findAllByHkmHonap(final String hkmHonap) {
    final List<HavKiadTenyCol> havKiadTenyCols = this.repository.findAllByHkmHonapOrderByHkmSorszamAsc(hkmHonap);
    final List<HavKiadTeny> havKiadTMegval = new ArrayList<>();
    final Set<String> marLetezo = new HashSet<>();
    for (final HavKiadTenyCol havKiadTenyCol : havKiadTenyCols) {
      final HavKiadTeny havKiadTeny = new HavKiadTeny();
      havKiadTeny.setId(havKiadTenyCol.getId());
      havKiadTeny.setHkmAzon(havKiadTenyCol.getHkmAzon());
      havKiadTeny.setHkmHonap(havKiadTenyCol.getHkmHonap());
      havKiadTeny.setHkmSorszam(havKiadTenyCol.getHkmSorszam());
      havKiadTeny.setHkmMegnev(havKiadTenyCol.getHkmMegnev());
      havKiadTeny.setHkmSzamla(havKiadTenyCol.getHkmSzamla());
      havKiadTeny.setHkmSzamlaNev(szarepo.findBySzaKod(havKiadTenyCol.getHkmSzamla()).getSzaMegnev());
      havKiadTeny.setHkmTervOsszeg(havKiadTenyCol.getHkmTervOsszeg());
      havKiadTeny.setHkmTenyOsszeg(havKiadTenyCol.getHkmTenyOsszeg());
      havKiadTeny.setHkmKonyvelve(havKiadTenyCol.isHkmKonyvelve());
      havKiadTeny.setHkmMddat(havKiadTenyCol.getHkmMddat());
      marLetezo.add(havKiadTeny.getHkmMegnev());
      havKiadTMegval.add(havKiadTeny);
    }
    final List<HavKiadTervCol> havKiadTervCols = this.tervRepo.findAll();
    for (final HavKiadTervCol havKiadTervCol : havKiadTervCols) {
      if (!marLetezo.contains(havKiadTervCol.getHktMegnev())) {
        final HavKiadTeny havKiadTeny = new HavKiadTeny();
        havKiadTeny.setHkmHonap(hkmHonap);
        havKiadTeny.setHkmSorszam(havKiadTervCol.getHktSorszam());
        havKiadTeny.setHkmMegnev(havKiadTervCol.getHktMegnev());
        havKiadTeny.setHkmSzamla(havKiadTervCol.getHktSzamla());
        havKiadTeny.setHkmSzamlaNev(szarepo.findBySzaKod(havKiadTervCol.getHktSzamla()).getSzaMegnev());
        havKiadTeny.setHkmTervOsszeg(havKiadTervCol.getHktOsszeg());
        havKiadTeny.setHkmTenyOsszeg(0);
        havKiadTeny.setHkmKonyvelve(false);
        havKiadTMegval.add(havKiadTeny);
      }
    }
    return havKiadTMegval;
  }
  /*
  public Object create(final HavKiadTerv havKiadTerv) {
    final HavKiadTervCol havKiadTervCol = new HavKiadTervCol(havKiadTerv);
    final HavKiadTervCol havKiadTervColOld = this.repository.findByHktMegnev(havKiadTervCol.getHktMegnev());
    if (havKiadTervColOld != null) {
      return new HavKiadTervAlreadyExitException(havKiadTervColOld.getHktMegnev());
    }
    this.repository.save(havKiadTervCol);
    logger.info("Created Record: {}", havKiadTervCol);
    return null;
  }

  public Object update(final HavKiadTerv havKiadTerv) {
    final HavKiadTervCol havKiadTervCol = new HavKiadTervCol(havKiadTerv);
    this.repository.save(havKiadTervCol);
    logger.info("Updated Record: {}", havKiadTervCol);
    return null;
  }
  */
  /*
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

  public Object delete(final String id) {
    final Optional<BefektFajtaCol> befektFajtaCol = this.repository.findById(id);
    if (befektFajtaCol.isPresent()) {
      final List<BefektetesCol> fefektetesCol = befektrepo.findByBefBffKod(befektFajtaCol.get().getBffKod());
      if (!fefektetesCol.isEmpty()) {
        return new BefektFajtaCannotDeleteException(befektFajtaCol.get().getBffKod());
      }
      this.repository.deleteById(id);
      logger.info("Deleted Record: {}", befektFajtaCol.get());
    }
    return null;
  }

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
  */
}
