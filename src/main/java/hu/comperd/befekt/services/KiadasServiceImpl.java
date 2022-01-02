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
import hu.comperd.befekt.collections.KiadasCol;
import hu.comperd.befekt.collections.SzamlaForgalomCol;
import hu.comperd.befekt.dto.Kiadas;
import hu.comperd.befekt.etc.DomainCsoportok;
import hu.comperd.befekt.etc.DomainErtekek;
import hu.comperd.befekt.etc.SzamlaKonyvTmp;
import hu.comperd.befekt.exceptions.KonyvelesiIdoszakLezartException;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.exceptions.ParositottSzamlaforgalmiTetelException;
import hu.comperd.befekt.repositories.DomainRepository;
import hu.comperd.befekt.repositories.KiadasRepository;
import hu.comperd.befekt.repositories.SzamlaForgalomRepository;
import hu.comperd.befekt.repositories.SzamlaRepository;
import hu.comperd.befekt.util.Util;

@Service
public class KiadasServiceImpl {
  private static final Logger      logger = LoggerFactory.getLogger(KiadasServiceImpl.class);

  @Autowired
  private KiadasRepository         repository;
  @Autowired
  private SzamlaRepository         szarepo;
  @Autowired
  private DomainRepository         domrepo;
  @Autowired
  private AlapAdatokServiceImpl    alapAdatokService;
  @Autowired
  private SzamlaKonyveles          szamlaKonyveles;
  @Autowired
  private SzamlaForgalomRepository szlaForgRepo;

  public List<Kiadas> findAll(final String konyvEv) {
    final List<KiadasCol> kiadasCols = this.repository.findAllByKiaEvOrderByKiaDatumDesc(Integer.parseInt(konyvEv));
    final List<Kiadas> kiadasok = new ArrayList<>();
    for (final KiadasCol kiadasCol : kiadasCols) {
      final Kiadas kiadas = new Kiadas();
      kiadas.setId(kiadasCol.getId());
      kiadas.setKiaDatum(kiadasCol.getKiaDatum());
      kiadas.setKiaAzon(kiadasCol.getKiaAzon());
      kiadas.setKiaSzoveg(kiadasCol.getKiaSzoveg());
      kiadas.setKiaOsszeg(kiadasCol.getKiaOsszeg());
      kiadas.setKiaSzamla(kiadasCol.getKiaSzamla());
      kiadas.setKiaSzamlaNev(this.szarepo.findBySzaKod(kiadasCol.getKiaSzamla()).getSzaMegnev());
      kiadas.setKiaTipus(kiadasCol.getKiaTipus());
      kiadas.setKiaTipusNev(
          this.domrepo.findByDomCsoportKodAndDomKod(DomainCsoportok.KIADTIPUS, kiadasCol.getKiaTipus()).getDomMegnev());
      kiadas.setKiaSzlaKonyv(kiadasCol.isKiaSzlaKonyv());
      kiadas.setKiaMddat(kiadasCol.getKiaMddat());
      kiadasok.add(kiadas);
    }
    return kiadasok;
  }

  public Object create(final Kiadas kiadas) {
    if (alapAdatokService.isIdoszakLezart(kiadas.getKiaDatum())) {
      return new KonyvelesiIdoszakLezartException(kiadas.getKiaDatum().toString().substring(0, 7),
          "kiadás fölvitele");
    }
    final KiadasCol kiadasCol = new KiadasCol(kiadas);
    final int sorszam = this.alapAdatokService.getNextBizSorsz(DomainErtekek.BIZTIP_KIADAS, kiadas.getKiaDatum().getYear());
    kiadasCol.setKiaAzon(
        DomainErtekek.BIZTIP_KIADAS + kiadas.getKiaDatum().getYear() + Util.padl(Integer.toString(sorszam), 4, '0'));
    final KiadasCol savedKiadas = this.repository.save(kiadasCol);
    logger.info("Created Record: {}", savedKiadas);
    return savedKiadas;
  }

  public Object update(final Kiadas kiadas) {
    if (alapAdatokService.isIdoszakLezart(kiadas.getKiaDatum())) {
      return new KonyvelesiIdoszakLezartException(kiadas.getKiaDatum().toString().substring(0, 7),
          "kiadás módosítása");
    }
    final Optional<KiadasCol> kiadasObj = this.repository.findById(kiadas.getId());
    if (kiadasObj.isPresent()) {
      final KiadasCol kiadasCol = kiadasObj.get();
      if (kiadasCol.getKiaMddat().toInstant().equals(kiadas.getKiaMddat().toInstant())) {
        kiadasCol.setKiaDatum(kiadas.getKiaDatum());
        kiadasCol.setKiaSzoveg(kiadas.getKiaSzoveg());
        kiadasCol.setKiaOsszeg(kiadas.getKiaOsszeg());
        kiadasCol.setKiaSzamla(kiadas.getKiaSzamla());
        kiadasCol.setKiaTipus(kiadas.getKiaTipus());
        kiadasCol.setKiaMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.repository.save(kiadasCol);
        logger.info("Updated Record: {}", kiadasCol);
      } else {
        return new MegvaltozottTartalomException("Kiadás", "módosítás");
      }
    }
    return null;
  }

  public Object delete(final String id, final String mddat) {
    final Optional<KiadasCol> kiadasObj = this.repository.findById(id);
    if (kiadasObj.isPresent()) {
      final KiadasCol kiadasCol = kiadasObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (kiadasCol.getKiaMddat().equals(pMddat)) {
        this.repository.deleteById(id);
        logger.info("Deleted Record: {}", kiadasCol);
      } else {
        return new MegvaltozottTartalomException("Kiadás", "törlés");
      }
    }
    return null;
  }

  public Object szamlaForgGen(final String id, final String mddat) {
    final Optional<KiadasCol> kiadasObj = this.repository.findById(id);
    if (kiadasObj.isPresent()) {
      final KiadasCol kiadasCol = kiadasObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (!kiadasCol.getKiaMddat().equals(pMddat)) {
        return new MegvaltozottTartalomException("Kiadás", "számlakönyvelés");
      }
      final SzamlaKonyvTmp szamlaKonyv = new SzamlaKonyvTmp();
      szamlaKonyv.setSzfForgDat(kiadasCol.getKiaDatum());
      szamlaKonyv.setSzfSzaAzon(kiadasCol.getKiaSzamla());
      szamlaKonyv.setSzfSzoveg("Kiadás - " + kiadasCol.getKiaSzoveg());
      szamlaKonyv.setSzfTipus(kiadasCol.getKiaTipus());
      szamlaKonyv.setSzfHivBizTip(DomainErtekek.BIZTIP_KIADAS);
      szamlaKonyv.setSzfHivBizAzon(kiadasCol.getKiaAzon());
      szamlaKonyv.setSzfTKJel(DomainErtekek.TKJEL_KOVETEL);
      szamlaKonyv.setSzfOsszeg(kiadasCol.getKiaOsszeg());
      this.szamlaKonyveles.konyveles(szamlaKonyv);
      kiadasCol.setKiaSzlaKonyv(true);
      kiadasCol.setKiaMddat(ZonedDateTime.now(ZoneId.systemDefault()));
      this.repository.save(kiadasCol);
      logger.info("Számlakönyvelt Record: {}", kiadasCol);
    }
    return null;
  }

  public Object szamlaForgTorl(final String id, final String mddat) {
    final Optional<KiadasCol> kiadasObj = this.repository.findById(id);
    if (kiadasObj.isPresent()) {
      final KiadasCol kiadasCol = kiadasObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (!kiadasCol.getKiaMddat().equals(pMddat)) {
        return new MegvaltozottTartalomException("Kiadás", "számlakönyvelés törlése");
      }
      if (this.alapAdatokService.isIdoszakLezart(kiadasCol.getKiaDatum())) {
        return new KonyvelesiIdoszakLezartException(
            kiadasCol.getKiaDatum().toString().substring(0, 7), "kiadás számlakönyvelésének törlése");
      }
      final List<SzamlaForgalomCol> szlaForgTetelek = this.szlaForgRepo.findAllBySzfHivBizTipAndSzfHivBizAzon(
          DomainErtekek.BIZTIP_KIADAS, kiadasCol.getKiaAzon());
      String parosAzon = null;
      for (final SzamlaForgalomCol szlaForgTetel : szlaForgTetelek) {
        if (Math.round(szlaForgTetel.getSzfParos() * 100) != 0) {
          parosAzon = szlaForgTetel.getSzfAzon();
        }
      }
      if (parosAzon == null) {
        for (final SzamlaForgalomCol szlaForgTetel : szlaForgTetelek) {
          this.szamlaKonyveles.szamlaOsszesenKonyveles(szlaForgTetel.getSzfSzaKod(), szlaForgTetel.getSzfForgDat(),
              szlaForgTetel.getSzfTKJel(), -1 * szlaForgTetel.getSzfOsszeg());
          this.szlaForgRepo.delete(szlaForgTetel);
        }
        kiadasCol.setKiaSzlaKonyv(false);
        kiadasCol.setKiaMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.repository.save(kiadasCol);
        logger.info("Számlakönyvelés törölve Record: {}", kiadasCol);
      } else {
        return new ParositottSzamlaforgalmiTetelException(parosAzon);
      }
    }
    return null;
  }
}
