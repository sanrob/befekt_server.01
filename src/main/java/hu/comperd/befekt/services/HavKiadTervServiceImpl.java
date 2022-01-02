package hu.comperd.befekt.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hu.comperd.befekt.collections.HavKiadTervCol;
import hu.comperd.befekt.dto.HavKiadTerv;
import hu.comperd.befekt.exceptions.HavKiadTervAlreadyExitException;
import hu.comperd.befekt.exceptions.HavKiadTervCannotDeleteException;
import hu.comperd.befekt.exceptions.HavKiadTervCannotModifyException;
import hu.comperd.befekt.repositories.HavKiadTenyRepository;
import hu.comperd.befekt.repositories.HavKiadTervRepository;
import hu.comperd.befekt.repositories.SzamlaRepository;
import hu.comperd.befekt.util.Util;

@Service
public class HavKiadTervServiceImpl {
  private static final Logger   logger = LoggerFactory.getLogger(HavKiadTervServiceImpl.class);
  @Autowired
  private HavKiadTervRepository repository;
  @Autowired
  private SzamlaRepository      szarepo;
  @Autowired
  private HavKiadTenyRepository tenyRepo;

  public static double add(final double a, final double b) {
    return a + b;
  }

  public List<HavKiadTerv> findAll(final boolean csakAktivak) {
    final List<HavKiadTerv> osszesTerv = this.repository.findAllByOrderByHktSorszamAsc().stream().parallel()
        .filter(x -> csakAktivakFilter(csakAktivak, x))
        .map(HavKiadTerv::new).map(havKIiadTerv -> havKIiadTerv.setDatas(this.szarepo)).collect(Collectors.toList());
    osszesTerv.addAll(osszesTerv.stream()
        .collect(Collectors.toMap(osszesenKey -> "x", HavKiadTerv::getHktOsszeg, HavKiadTervServiceImpl::add))
        .entrySet().stream().map(HavKiadTerv::new).map(osszesen -> osszesen.setDatasOsszesen())
        .collect(Collectors.toList()));
    return osszesTerv;
  }

  private boolean csakAktivakFilter(final boolean csakAktivak, final HavKiadTervCol terv) {
    final LocalDate currDat = LocalDate.now();
    final String currHo = Integer.toString(currDat.getYear()) + "-" + Util.padl(Integer.toString(currDat.getMonthValue()), 2, '0');
    return (!csakAktivak) || Util.isEmpty(terv.getHktDatumIg()) || terv.getHktDatumIg().compareTo(currHo) >= 0;
  }

  public Object create(final HavKiadTerv havKiadTerv) {
    final HavKiadTervCol havKiadTervColOld = this.repository.findByHktMegnev(havKiadTerv.getHktMegnev());
    if (havKiadTervColOld != null) {
      return new HavKiadTervAlreadyExitException(havKiadTervColOld.getHktMegnev());
    }
    if ((havKiadTerv.getHktDatumIg() != null) && Util.isEmpty(havKiadTerv.getHktDatumIg())) {
      havKiadTerv.setHktDatumIg(null);
    }
    final HavKiadTervCol havKiadTervCol = new HavKiadTervCol(havKiadTerv);
    this.repository.save(havKiadTervCol);
    logger.info("Created Record: {}", havKiadTervCol);
    return null;
  }

  public Object update(final HavKiadTerv havKiadTerv) {
    final String minHonap = this.tenyRepo.findAllByHkmMegnev(havKiadTerv.getHktMegnev())
        .stream().map(x -> x.getHkmHonap()).min(String.CASE_INSENSITIVE_ORDER).orElse(null);
    final String maxHonap = this.tenyRepo.findAllByHkmMegnev(havKiadTerv.getHktMegnev())
        .stream().map(x -> x.getHkmHonap()).max(String.CASE_INSENSITIVE_ORDER).orElse(null);
    if (minHonap != null && minHonap.compareTo(havKiadTerv.getHktDatumTol()) < 0) {
      return new HavKiadTervCannotModifyException(havKiadTerv.getHktMegnev(), minHonap, null);
    } else if (maxHonap != null && (!Util.isEmpty(havKiadTerv.getHktDatumIg()))
        && maxHonap.compareTo(havKiadTerv.getHktDatumIg()) > 0) {
      return new HavKiadTervCannotModifyException(havKiadTerv.getHktMegnev(), null, maxHonap);
    }
    final HavKiadTervCol havKiadTervCol = new HavKiadTervCol(havKiadTerv);
    this.repository.save(havKiadTervCol);
    logger.info("Updated Record: {}", havKiadTervCol);
    return null;
  }

  public Object delete(final String id) {
    final Optional<HavKiadTervCol> havKiadTervCol = this.repository.findById(id);
    if (havKiadTervCol.isPresent()) {
      if (!tenyRepo.findAllByHkmMegnev(havKiadTervCol.get().getHktMegnev()).isEmpty()) {
        return new HavKiadTervCannotDeleteException(havKiadTervCol.get().getHktMegnev());
      }
      this.repository.deleteById(id);
      logger.info("Deleted Record: {}", havKiadTervCol.get());
    }
    return null;
  }
}
