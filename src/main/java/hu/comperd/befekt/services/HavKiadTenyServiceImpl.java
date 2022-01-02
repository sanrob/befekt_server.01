package hu.comperd.befekt.services;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hu.comperd.befekt.collections.HavKiadTenyCol;
import hu.comperd.befekt.collections.HavKiadTervCol;
import hu.comperd.befekt.collections.HonapZarasaCol;
import hu.comperd.befekt.collections.KiadasCol;
import hu.comperd.befekt.dto.AltKiadSzamlankent;
import hu.comperd.befekt.dto.HavKiadTeny;
import hu.comperd.befekt.dto.HonapZarasa;
import hu.comperd.befekt.dto.Kiadas;
import hu.comperd.befekt.etc.AltKiadSzamlankentTmp;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.exceptions.VanKonyveltGeneraltKiadasException;
import hu.comperd.befekt.repositories.*;
import hu.comperd.befekt.util.Util;

@Service
public class HavKiadTenyServiceImpl {
  private static final Logger    logger = LoggerFactory.getLogger(HavKiadTenyServiceImpl.class);
  @Autowired
  private HavKiadTenyRepository  repository;
  @Autowired
  private HavKiadTervRepository  tervRepo;
  @Autowired
  private SzamlaRepository       szarepo;
  @Autowired
  private SzamlavezetoRepository szlaVezRepo;
  @Autowired
  private HonapZarasaRepository  honZarRepo;
  @Autowired
  KiadasServiceImpl              kiadasService;
  @Autowired
  private AlapAdatokServiceImpl  alapAdatService;
  @Autowired
  KiadasRepository               kiadasRepo;

  public static AltKiadSzamlankentTmp add(final AltKiadSzamlankentTmp mihez, final AltKiadSzamlankentTmp mit) {
    mihez.setTmpTervOsszeg(mihez.getTmpTervOsszeg() + mit.getTmpTervOsszeg());
    mihez.setTmpTenyOsszeg(mihez.getTmpTenyOsszeg() + mit.getTmpTenyOsszeg());
    return mihez;
  }

  public static HavKiadTeny add(final HavKiadTeny mihez, final HavKiadTeny mit) {
    mihez.setHkmTervOsszeg(mihez.getHkmTervOsszeg() + mit.getHkmTervOsszeg());
    mihez.setHkmTenyOsszeg(mihez.getHkmTenyOsszeg() + mit.getHkmTenyOsszeg());
    return mihez;
  }

  public List<HavKiadTeny> findAllByHkmHonap(final String hkmHonap) {
    final Map<String, HavKiadTeny> havKiadTMegval = this.repository.findAllByHkmHonap(hkmHonap)
        .stream().parallel().map(HavKiadTeny::new).map(kiadTeny -> kiadTeny.setDatas(this.tervRepo, this.szarepo))
        .collect(Collectors.toMap(kiadTeny -> kiadTeny.getHkmMegnev(), kiadTeny -> kiadTeny));
    final List<HavKiadTeny> havKiadTNew = this.tervRepo.findAll().stream()
        .filter(terv -> (!havKiadTMegval.containsKey(terv.getHktMegnev())) && this.isKiadasErvenyes(terv, hkmHonap))
        .map(HavKiadTeny::new)
        .map(kiadTeny -> kiadTeny.setDatas(this.tervRepo, this.szarepo, hkmHonap)).collect(Collectors.toList());
    havKiadTNew.addAll(havKiadTMegval.values());
    havKiadTNew.addAll(havKiadTNew.stream()
        .collect(Collectors.toMap(osszesenKey -> "x", HavKiadTeny::new, HavKiadTenyServiceImpl::add))
        .entrySet().stream().map(HavKiadTeny::new).map(osszesen -> osszesen.setDatas())
        .collect(Collectors.toList()));
    return havKiadTNew;
  }

  private boolean isKiadasErvenyes(final HavKiadTervCol terv, final String honap) {
    boolean ret = false;
    if (terv.getHktDatumTol().compareTo(honap) <= 0) {
      if (Util.isEmpty(terv.getHktDatumIg()) || terv.getHktDatumIg().compareTo(honap) >= 0) {
        ret = true;
      }
    }
    return ret;
  }

  public Object createOrUpdate(final HavKiadTeny havKiadTeny) {
    final HavKiadTenyCol havKiadTenyCol = new HavKiadTenyCol(havKiadTeny);
    if (!Util.isEmpty(havKiadTenyCol.getId())) {
      final HavKiadTenyCol havKiadTenyColOld = this.repository.findById(havKiadTenyCol.getId()).get();
      havKiadTenyCol.setHkmTenyOsszeg(havKiadTenyCol.getHkmTenyOsszeg() + havKiadTenyColOld.getHkmTenyOsszeg());
      if (!havKiadTenyColOld.getHkmMddat().toInstant().equals(havKiadTeny.getHkmMddat().toInstant())) {
        return new MegvaltozottTartalomException("Havi tényleges kiadás", "felvitel/módosítás");
      }
    }
    this.repository.save(havKiadTenyCol);
    logger.info(Util.isEmpty(havKiadTenyCol.getId()) ? "Created Record: {}" : "Updated Record: {}", havKiadTenyCol);
    return null;
  }

  public Object delete(final String id, final String mddat) {
    if (!Util.isEmpty(id)) {
      final Optional<HavKiadTenyCol> havKiadTenyObj = this.repository.findById(id);
      if (havKiadTenyObj.isPresent()) {
        final HavKiadTenyCol havKiadTenyCol = havKiadTenyObj.get();
        final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
        if (havKiadTenyCol.getHkmMddat().equals(pMddat)) {
          this.repository.deleteById(id);
          logger.info("Deleted Record: {}", havKiadTenyCol);
        } else {
          return new MegvaltozottTartalomException("Havi Kiadás Tény", "törlés");
        }
      }
    }
    return null;
  }

  public List<AltKiadSzamlankent> findSzamlankentiOsszesen(final String hkmHonap) {
    final List<AltKiadSzamlankent> altKiad = this.repository.findAllByHkmHonap(hkmHonap)
        .stream().parallel().map(AltKiadSzamlankentTmp::new).map(kiadTmp -> kiadTmp.setDatas(this.tervRepo))
        .collect(Collectors.toMap(
            kiadTmp -> kiadTmp.getTmpSzaKod(), kiadTmp -> kiadTmp, HavKiadTenyServiceImpl::add))
        .entrySet().stream().map(AltKiadSzamlankent::new).map(szamlankent -> szamlankent.setDatas(szarepo, szlaVezRepo))
        .collect(Collectors.toList());
    altKiad.addAll(
        altKiad.stream()
            .collect(Collectors.toMap(altKiadKey -> "x", AltKiadSzamlankentTmp::new, HavKiadTenyServiceImpl::add))
            .entrySet().stream().map(AltKiadSzamlankent::new).map(osszesen -> osszesen.setDatasOsszesen())
            .collect(Collectors.toList()));
    return altKiad;
  }

  public HonapZarasa getEgyHonapZarasAdata(final String hkmHonap) {
    return new HonapZarasa(this.honZarRepo.findOneByHozHonap(hkmHonap));
  }

  public Object kiadasTetelGenaralas(final String honap) {
    final String azonositok = this.findSzamlankentiOsszesen(honap).stream()
        .filter(x -> x.getAksSzaKod() != null).map(Kiadas::new)
        .map(kiadTmp -> kiadTmp.setDatas(honap, alapAdatService.findSystemParams()))
        .map(kiadasService::create).map(x -> x instanceof KiadasCol ? ((KiadasCol)x).getKiaAzon() : "")
        .collect(Collectors.joining(","));
    final HonapZarasaCol honapzaras = this.honZarRepo.findOneByHozHonap(honap);
    honapzaras.setHozAltKiadGeneralva(true);
    honapzaras.setHozAltKiadAzonositok(azonositok);
    this.honZarRepo.save(honapzaras);
    return null;
  }

  public Object kiadasTetelTorles(final String honap) {
    final HonapZarasaCol honapzaras = this.honZarRepo.findOneByHozHonap(honap);
    final List<KiadasCol> kiadastetelek = Arrays.stream(honapzaras.getHozAltKiadAzonositok().split(","))
        .map(x -> this.kiadasRepo.findByKiaAzon(x)).collect(Collectors.toList());
    final long konyveltDb = kiadastetelek.stream().filter(x -> x.isKiaSzlaKonyv()).count();
    if (konyveltDb > 0) {
      return new VanKonyveltGeneraltKiadasException(honap);
    }
    kiadastetelek.forEach(this.kiadasRepo::delete);
    honapzaras.setHozAltKiadGeneralva(false);
    honapzaras.setHozAltKiadAzonositok(null);
    this.honZarRepo.save(honapzaras);
    return null;
  }
}
