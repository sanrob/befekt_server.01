package hu.comperd.befekt.services;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hu.comperd.befekt.collections.HavKiadTervCol;
import hu.comperd.befekt.dto.HavKiadTerv;
import hu.comperd.befekt.exceptions.HavKiadTervAlreadyExitException;
import hu.comperd.befekt.repositories.HavKiadTervRepository;
import hu.comperd.befekt.repositories.SzamlaRepository;

@Service
public class HavKiadTervServiceImpl {
  private static final Logger   logger = LoggerFactory.getLogger(HavKiadTervServiceImpl.class);
  @Autowired
  private HavKiadTervRepository repository;
  @Autowired
  private SzamlaRepository      szarepo;

  public List<HavKiadTerv> findAll() {
    final List<HavKiadTervCol> havKiadTervCols = this.repository.findAllByOrderByHktSorszamAsc();
    final List<HavKiadTerv> havKiadTervek = new ArrayList<>();
    for (final HavKiadTervCol havKiadTervCol : havKiadTervCols) {
      final HavKiadTerv havKiadTerv = new HavKiadTerv();
      havKiadTerv.setId(havKiadTervCol.getId());
      havKiadTerv.setHktSorszam(havKiadTervCol.getHktSorszam());
      havKiadTerv.setHktMegnev(havKiadTervCol.getHktMegnev());
      havKiadTerv.setHktSzamla(havKiadTervCol.getHktSzamla());
      havKiadTerv.setHktSzamlaNev(szarepo.findBySzaKod(havKiadTerv.getHktSzamla()).getSzaMegnev());
      havKiadTerv.setHktOsszeg(havKiadTervCol.getHktOsszeg());
      havKiadTerv.setHktDatumTol(havKiadTervCol.getHktDatumTol());
      havKiadTerv.setHktDatumIg(havKiadTervCol.getHktDatumIg());
      havKiadTerv.setHktMddat(havKiadTervCol.getHktMddat());
      havKiadTervek.add(havKiadTerv);
    }
    return havKiadTervek;
  }

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
