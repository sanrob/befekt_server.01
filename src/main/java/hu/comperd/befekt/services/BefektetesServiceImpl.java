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
import hu.comperd.befekt.dto.Befektetes;
import hu.comperd.befekt.dto.HataridosElszamolas;
import hu.comperd.befekt.dto.Kamat;
import hu.comperd.befekt.dto.Osztalek;
import hu.comperd.befekt.etc.DomainCsoportok;
import hu.comperd.befekt.etc.DomainErtekek;
import hu.comperd.befekt.etc.SzamlaKonyvTmp;
import hu.comperd.befekt.exceptions.KonyvelesiIdoszakLezartException;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.repositories.*;
import hu.comperd.befekt.util.Util;

@Service
public class BefektetesServiceImpl {
  private static final Logger           logger            = LoggerFactory.getLogger(BefektetesServiceImpl.class);

  @Autowired
  private BefektetesRepository          repository;
  @Autowired
  private BefektFajtaRepository         befFajtaRepo;
  @Autowired
  private final AlapAdatokServiceImpl   alapAdatokService = null;
  @Autowired
  private SzamlaKonyveles               szamlaKonyveles;
  @Autowired
  private SzamlaForgalomRepository      szlaForgRepo;
  @Autowired
  private OsztalekRepository            osztalekRepo;
  @Autowired
  private SzamlaRepository              szarepo;
  @Autowired
  private HataridosElszamolasRepository hatElszRepo;
  @Autowired
  private DomainRepository              domrepo;
  @Autowired
  private SzamlaRepository              szlaRepo;
  @Autowired
  private KamatRepository               kamatRepo;

  public List<Befektetes> findAll(final String konyvEv) {
    final List<BefektetesCol> befektetesCols = this.repository.findAllByBefEvOrderByBefDatumDesc(Integer.parseInt(konyvEv));
    final List<Befektetes> befektetesek = new ArrayList<>();
    for (final BefektetesCol befektetesCol : befektetesCols) {
      final Befektetes befektetes = new Befektetes();
      befektetes.setId(befektetesCol.getId());
      befektetes.setBefAzon(befektetesCol.getBefAzon());
      befektetes.setBefDatum(befektetesCol.getBefDatum());
      befektetes.setBefBffKod(befektetesCol.getBefBffKod());
      final BefektFajtaCol befektFajtaCol = this.befFajtaRepo.findByBffKod(befektetesCol.getBefBffKod());
      befektetes.setBefBffKodNev(befektFajtaCol.getBffMegnev());
      befektetes.setBefBffTip(befektFajtaCol.getBffTip());
      befektetes.setBefBffHozam(befektFajtaCol.getBffHozam());
      befektetes.setBefDarab(befektetesCol.getBefDarab());
      befektetes.setBefArfolyam(befektetesCol.getBefArfolyam());
      befektetes.setBefErtek(befektetesCol.getBefErtek());
      befektetes.setBefJutSzaz(befektetesCol.getBefJutSzaz());
      befektetes.setBefJutErtek(befektetesCol.getBefJutErtek());
      befektetes.setBefBekerErtek(befektetesCol.getBefBekerErtek());
      befektetes.setBefElszSzla(befektetesCol.getBefElszSzla());
      befektetes.setBefElszSzlaNev(this.szlaRepo.findBySzaKod(befektetesCol.getBefElszSzla()).getSzaMegnev());
      befektetes.setBefJutSzla(befektetesCol.getBefJutSzla());
      befektetes.setBefJutSzlaNev(this.szlaRepo.findBySzaKod(befektetesCol.getBefJutSzla()).getSzaMegnev());
      befektetes.setBefKonyvDat(befektetesCol.getBefKonyvDat());
      befektetes.setBefKonyvelve(befektetesCol.isBefKonyvelve());
      befektetes.setBefLezDat(befektetesCol.getBefLezDat());
      befektetes.setBefParDarab(befektetesCol.getBefParDarab());
      befektetes.setBefMddat(befektetesCol.getBefMddat());
      befektetes.setBefKamOsztElsz(
          !this.osztalekRepo.findAllByOszBefektetesIdOrderByOszDatumDescOszAzonDesc(befektetes.getId()).isEmpty());
      befektetesek.add(befektetes);
    }
    return befektetesek;
  }

  public Object create(final Befektetes befektetes) {
    if (alapAdatokService.isIdoszakLezart(befektetes.getBefKonyvDat())) {
      return new KonyvelesiIdoszakLezartException(befektetes.getBefKonyvDat().toString().substring(0, 7),
          "pozíció nyitás fölvitele");
    }
    final BefektetesCol befektetesCol = new BefektetesCol(befektetes);
    final int sorszam = this.alapAdatokService.getNextBizSorsz(DomainErtekek.BIZTIP_VETEL, befektetes.getBefDatum().getYear());
    befektetesCol.setBefAzon(
        DomainErtekek.BIZTIP_VETEL + befektetesCol.getBefDatum().getYear() + Util.padl(Integer.toString(sorszam), 4, '0'));
    this.repository.save(befektetesCol);
    logger.info("Created Record: {}", befektetesCol);
    return null;
  }

  public Object update(final Befektetes befektetes) {
    if (alapAdatokService.isIdoszakLezart(befektetes.getBefKonyvDat())) {
      return new KonyvelesiIdoszakLezartException(befektetes.getBefKonyvDat().toString().substring(0, 7),
          "pozíció nyitás módosítása");
    }
    final Optional<BefektetesCol> befektetesObj = this.repository.findById(befektetes.getId());
    if (befektetesObj.isPresent()) {
      final BefektetesCol befekteteslCol = befektetesObj.get();
      if (befekteteslCol.getBefMddat().toInstant().equals(befektetes.getBefMddat().toInstant())) {
        befekteteslCol.setBefDatum(befektetes.getBefDatum());
        befekteteslCol.setBefBffKod(befektetes.getBefBffKod());
        befekteteslCol.setBefDarab(befektetes.getBefDarab());
        befekteteslCol.setBefArfolyam(befektetes.getBefArfolyam());
        befekteteslCol.setBefErtek(befektetes.getBefErtek());
        befekteteslCol.setBefJutSzaz(befektetes.getBefJutSzaz());
        befekteteslCol.setBefJutErtek(befektetes.getBefJutErtek());
        befekteteslCol.setBefBekerErtek(befektetes.getBefBekerErtek());
        befekteteslCol.setBefElszSzla(befektetes.getBefElszSzla());
        befekteteslCol.setBefJutSzla(befektetes.getBefJutSzla());
        befekteteslCol.setBefKonyvDat(befektetes.getBefKonyvDat());
        befekteteslCol.setBefMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.repository.save(befekteteslCol);
        logger.info("Updated Record: {}", befekteteslCol);
      } else {
        return new MegvaltozottTartalomException("Befektetés", "módosítás");
      }
    }
    return null;
  }

  public Object delete(final String id, final String mddat) {
    final Optional<BefektetesCol> befektetesObj = this.repository.findById(id);
    if (befektetesObj.isPresent()) {
      final BefektetesCol befektetesCol = befektetesObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (befektetesCol.getBefMddat().equals(pMddat)) {
        this.repository.deleteById(id);
        logger.info("Deleted Record: {}", befektetesCol);
      } else {
        return new MegvaltozottTartalomException("Befektetés", "törlés");
      }
    }
    return null;
  }

  public Object szamlaForgGen(final String id, final String mddat) {
    final Optional<BefektetesCol> befektetesObj = this.repository.findById(id);
    if (befektetesObj.isPresent()) {
      final BefektetesCol befektetesCol = befektetesObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (befektetesCol.getBefMddat().equals(pMddat)) {
        final BefektFajtaCol befektFajtaCol = befFajtaRepo.findByBffKod(befektetesCol.getBefBffKod());
        SzamlaKonyvTmp szamlaKonyv;
        if (Math.round(befektetesCol.getBefErtek() * 100) > 0) {
          szamlaKonyv = new SzamlaKonyvTmp();
          szamlaKonyv.setSzfForgDat(befektetesCol.getBefKonyvDat());
          szamlaKonyv.setSzfSzaAzon(befektetesCol.getBefElszSzla());
          szamlaKonyv.setSzfSzoveg("Vételi pozíció - " + befektFajtaCol.getBffMegnev());
          szamlaKonyv.setSzfTipus(DomainErtekek.SZLAFORGTIP_BN);
          szamlaKonyv.setSzfHivBizTip(DomainErtekek.BIZTIP_VETEL);
          szamlaKonyv.setSzfHivBizAzon(befektetesCol.getBefAzon());
          final String tkjel = DomainErtekek.BEFFAJTA_ELADASI.equals(befektFajtaCol.getBffTip())
              ? DomainErtekek.TKJEL_TARTOZIK
              : DomainErtekek.TKJEL_KOVETEL;
          szamlaKonyv.setSzfTKJel(tkjel);
          szamlaKonyv.setSzfOsszeg(befektetesCol.getBefErtek());
          this.szamlaKonyveles.konyveles(szamlaKonyv);
        }
        if (Math.round(befektetesCol.getBefJutErtek() * 100) > 0) {
          szamlaKonyv = new SzamlaKonyvTmp();
          szamlaKonyv.setSzfForgDat(befektetesCol.getBefKonyvDat());
          szamlaKonyv.setSzfSzaAzon(befektetesCol.getBefJutSzla());
          szamlaKonyv.setSzfSzoveg("Vételi pozíció - " + befektFajtaCol.getBffMegnev());
          szamlaKonyv.setSzfTipus(DomainErtekek.SZLAFORGTIP_JU);
          szamlaKonyv.setSzfHivBizTip(DomainErtekek.BIZTIP_VETEL);
          szamlaKonyv.setSzfHivBizAzon(befektetesCol.getBefAzon());
          szamlaKonyv.setSzfTKJel(DomainErtekek.TKJEL_KOVETEL);
          szamlaKonyv.setSzfOsszeg(befektetesCol.getBefJutErtek());
          this.szamlaKonyveles.konyveles(szamlaKonyv);
        }
        befektetesCol.setBefKonyvelve(true);
        befektetesCol.setBefMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.repository.save(befektetesCol);
        logger.info("Számlakönyvelt Record: {}", befektetesCol);
      } else {
        return new MegvaltozottTartalomException("Befektetés", "számlakönyvelés");
      }
    }
    return null;
  }

  public Object szamlaForgTorl(final String id, final String mddat) {
    final Optional<BefektetesCol> befektetesObj = this.repository.findById(id);
    if (befektetesObj.isPresent()) {
      final BefektetesCol befektetesCol = befektetesObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (!befektetesCol.getBefMddat().equals(pMddat)) {
        return new MegvaltozottTartalomException("Befektetés", "számlakönyvelés törlése");
      }
      if (this.alapAdatokService.isIdoszakLezart(befektetesCol.getBefKonyvDat())) {
        return new KonyvelesiIdoszakLezartException(
            befektetesCol.getBefKonyvDat().toString().substring(0, 7), "befektetés számlakönyvelésének törlése");
      }
      final List<SzamlaForgalomCol> szlaForgTetelek = this.szlaForgRepo.findAllBySzfHivBizTipAndSzfHivBizAzon(
          DomainErtekek.BIZTIP_VETEL, befektetesCol.getBefAzon());
      for (final SzamlaForgalomCol szlaForgTetel : szlaForgTetelek) {
        this.szamlaKonyveles.szamlaOsszesenKonyveles(szlaForgTetel.getSzfSzaKod(), szlaForgTetel.getSzfForgDat(),
            szlaForgTetel.getSzfTKJel(), -1 * szlaForgTetel.getSzfOsszeg());
        this.szlaForgRepo.delete(szlaForgTetel);
      }
      befektetesCol.setBefKonyvelve(false);
      befektetesCol.setBefMddat(ZonedDateTime.now(ZoneId.systemDefault()));
      this.repository.save(befektetesCol);
      logger.info("Számlakönyvelés törölve Record: {}", befektetesCol);
    }
    return null;
  }

  public List<Osztalek> findAllOsztalek(final String befektId) {
    final List<OsztalekCol> osztalekCols = this.osztalekRepo.findAllByOszBefektetesIdOrderByOszDatumDescOszAzonDesc(befektId);
    final List<Osztalek> osztalekok = new ArrayList<>();
    for (final OsztalekCol osztalekCol : osztalekCols) {
      final Osztalek osztalek = new Osztalek();
      osztalek.setId(osztalekCol.getId());
      osztalek.setOszBefektetesId(osztalekCol.getOszBefektetesId());
      osztalek.setOszAzon(osztalekCol.getOszAzon());
      osztalek.setOszDatum(osztalekCol.getOszDatum());
      osztalek.setOszBefDarab(osztalekCol.getOszBefDarab());
      osztalek.setOszMertek(osztalekCol.getOszMertek());
      osztalek.setOszOsszeg(osztalekCol.getOszOsszeg());
      osztalek.setOszAdoSzaz(osztalekCol.getOszAdoSzaz());
      osztalek.setOszAdo(osztalekCol.getOszAdo());
      osztalek.setOszAdoSzamla(osztalekCol.getOszAdoSzamla());
      osztalek.setOszAdoSzamlaNev(this.szarepo.findBySzaKod(osztalekCol.getOszAdoSzamla()).getSzaMegnev());
      osztalek.setOszKonyvelve(osztalekCol.isOszKonyvelve());
      osztalek.setOszMddat(osztalekCol.getOszMddat());
      osztalekok.add(osztalek);
    }
    return osztalekok;
  }

  public Object osztalekTarolas(final Osztalek osztalek) {
    OsztalekCol osztalekCol;
    if (Util.isEmpty(osztalek.getId())) {
      osztalekCol = new OsztalekCol(osztalek);
      final int sorszam = this.alapAdatokService.getNextBizSorsz(DomainErtekek.BIZTIP_OSZTALEK,
          osztalekCol.getOszDatum().getYear());
      osztalekCol.setOszAzon(
          DomainErtekek.BIZTIP_OSZTALEK + osztalekCol.getOszDatum().getYear() + Util.padl(Integer.toString(sorszam), 4, '0'));
    } else {
      osztalekCol = this.osztalekRepo.findById(osztalek.getId()).get();
      if (osztalekCol.getOszMddat().toInstant().equals(osztalek.getOszMddat().toInstant())) {
        osztalekCol.setOszDatum(osztalek.getOszDatum());
        osztalekCol.setOszBefDarab(osztalek.getOszBefDarab());
        osztalekCol.setOszMertek(osztalek.getOszMertek());
        osztalekCol.setOszOsszeg(osztalek.getOszOsszeg());
        osztalekCol.setOszAdoSzaz(osztalek.getOszAdoSzaz());
        osztalekCol.setOszAdo(osztalek.getOszAdo());
        osztalekCol.setOszAdoSzamla(osztalek.getOszAdoSzamla());
        osztalekCol.setOszKonyvelve(osztalek.isOszKonyvelve());
        osztalekCol.setOszMddat(ZonedDateTime.now(ZoneId.systemDefault()));
      } else {
        return new MegvaltozottTartalomException("Osztalék elszámolás", "update");
      }
    }
    this.osztalekRepo.save(osztalekCol);
    logger.info("Created Record: {}", osztalekCol);
    return null;
  }

  public Object osztalekTorles(final String id, final String mddat) {
    final Optional<OsztalekCol> osztalekObj = this.osztalekRepo.findById(id);
    if (osztalekObj.isPresent()) {
      final OsztalekCol osztalekCol = osztalekObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (osztalekCol.getOszMddat().equals(pMddat)) {
        this.osztalekRepo.deleteById(id);
        logger.info("Deleted Record: {}", osztalekCol);
      } else {
        return new MegvaltozottTartalomException("Osztalék", "törlés");
      }
    }
    return null;
  }

  public Object osztalekSzamlaForgGen(final String id, final String mddat) {
    final Optional<OsztalekCol> osztalekObj = this.osztalekRepo.findById(id);
    if (osztalekObj.isPresent()) {
      final OsztalekCol osztalekCol = osztalekObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (osztalekCol.getOszMddat().equals(pMddat)) {
        String szamla = null;
        final Optional<BefektetesCol> befektetesObj = this.repository.findById(osztalekCol.getOszBefektetesId());
        if (befektetesObj.isPresent()) {
          final BefektetesCol befektetesCol = befektetesObj.get();
          szamla = befektetesCol.getBefElszSzla();
        }
        SzamlaKonyvTmp szamlaKonyv = new SzamlaKonyvTmp();
        szamlaKonyv.setSzfForgDat(osztalekCol.getOszDatum());
        szamlaKonyv.setSzfSzaAzon(szamla);
        szamlaKonyv.setSzfSzoveg("Osztalek");
        szamlaKonyv.setSzfTipus(DomainErtekek.SZLAFORGTIP_OS);
        szamlaKonyv.setSzfHivBizTip(DomainErtekek.BIZTIP_OSZTALEK);
        szamlaKonyv.setSzfHivBizAzon(osztalekCol.getOszAzon());
        szamlaKonyv.setSzfTKJel(DomainErtekek.TKJEL_TARTOZIK);
        szamlaKonyv.setSzfOsszeg(Math.abs(osztalekCol.getOszOsszeg()));
        this.szamlaKonyveles.konyveles(szamlaKonyv);
        if (Math.round(osztalekCol.getOszAdo() * 100) != 0) {
          szamlaKonyv = new SzamlaKonyvTmp();
          szamlaKonyv.setSzfForgDat(osztalekCol.getOszDatum());
          szamlaKonyv.setSzfSzaAzon(szamla);
          szamlaKonyv.setSzfSzoveg("Osztalek");
          szamlaKonyv.setSzfTipus(DomainErtekek.SZLAFORGTIP_OS);
          szamlaKonyv.setSzfHivBizTip(DomainErtekek.BIZTIP_OSZTALEK);
          szamlaKonyv.setSzfHivBizAzon(osztalekCol.getOszAzon());
          szamlaKonyv.setSzfTKJel(DomainErtekek.TKJEL_KOVETEL);
          szamlaKonyv.setSzfOsszeg(Math.abs(osztalekCol.getOszAdo()));
          this.szamlaKonyveles.konyveles(szamlaKonyv);
          szamlaKonyv = new SzamlaKonyvTmp();
          szamlaKonyv.setSzfForgDat(osztalekCol.getOszDatum());
          szamlaKonyv.setSzfSzaAzon(osztalekCol.getOszAdoSzamla());
          szamlaKonyv.setSzfSzoveg("Osztalek");
          szamlaKonyv.setSzfTipus(DomainErtekek.SZLAFORGTIP_OS);
          szamlaKonyv.setSzfHivBizTip(DomainErtekek.BIZTIP_OSZTALEK);
          szamlaKonyv.setSzfHivBizAzon(osztalekCol.getOszAzon());
          szamlaKonyv.setSzfTKJel(DomainErtekek.TKJEL_TARTOZIK);
          szamlaKonyv.setSzfOsszeg(Math.abs(osztalekCol.getOszAdo()));
          this.szamlaKonyveles.konyveles(szamlaKonyv);
        }
        osztalekCol.setOszKonyvelve(true);
        osztalekCol.setOszMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.osztalekRepo.save(osztalekCol);
        logger.info("Számlakönyvelt Record: {}", osztalekCol);
      } else {
        return new MegvaltozottTartalomException("Osztalek", "számlakönyvelés");
      }
    }
    return null;
  }

  public Object osztalekSzamlaForgTorl(final String id, final String mddat) {
    final Optional<OsztalekCol> osztalekObj = this.osztalekRepo.findById(id);
    if (osztalekObj.isPresent()) {
      final OsztalekCol osztalekCol = osztalekObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (osztalekCol.getOszMddat().equals(pMddat)) {
        final List<SzamlaForgalomCol> szlaForgTetelek = this.szlaForgRepo.findAllBySzfHivBizTipAndSzfHivBizAzon(
            DomainErtekek.BIZTIP_OSZTALEK, osztalekCol.getOszAzon());
        for (final SzamlaForgalomCol szlaForgTetel : szlaForgTetelek) {
          this.szamlaKonyveles.szamlaOsszesenKonyveles(szlaForgTetel.getSzfSzaKod(), szlaForgTetel.getSzfForgDat(),
              szlaForgTetel.getSzfTKJel(), -1 * szlaForgTetel.getSzfOsszeg());
          this.szlaForgRepo.delete(szlaForgTetel);
        }
        osztalekCol.setOszKonyvelve(false);
        osztalekCol.setOszMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.osztalekRepo.save(osztalekCol);
        logger.info("Számlakönyvelés törölve Record: {}", osztalekCol);
      } else {
        return new MegvaltozottTartalomException("Osztalek", "számlakönyvelés törlése");
      }
    }
    return null;
  }

  public List<HataridosElszamolas> findAllHatElsz(final String befektId) {
    final List<HataridosElszamolasCol> hataridosElszamolasCols = this.hatElszRepo
        .findAllByHatNyitoIdOrderByHatElszDatumDescHatAzonDesc(befektId);
    final BefektetesCol befektetesCol = this.repository.findById(befektId).get();
    final double darab = befektetesCol.getBefDarab() - befektetesCol.getBefParDarab();
    final List<HataridosElszamolas> hataridosElszamolasok = new ArrayList<>();
    HataridosElszamolas feTetel = null;
    for (final HataridosElszamolasCol hataridosElszamolasCol : hataridosElszamolasCols) {
      final HataridosElszamolas hataridosElszamolas = new HataridosElszamolas();
      hataridosElszamolas.setId(hataridosElszamolasCol.getId());
      hataridosElszamolas.setHatAzon(hataridosElszamolasCol.getHatAzon());
      hataridosElszamolas.setHatNyitoId(hataridosElszamolasCol.getHatNyitoId());
      hataridosElszamolas.setHatNyitoAzon(hataridosElszamolasCol.getHatNyitoAzon());
      hataridosElszamolas.setHatTipus(hataridosElszamolasCol.getHatTipus());
      hataridosElszamolas.setHatTipusNev(this.domrepo.findByDomCsoportKodAndDomKod(
          DomainCsoportok.BEFFAJTTIP, hataridosElszamolasCol.getHatTipus()).getDomMegnev());
      hataridosElszamolas.setHatElszDatum(hataridosElszamolasCol.getHatElszDatum());
      hataridosElszamolas.setHatElozoArf(hataridosElszamolasCol.getHatElozoArf());
      hataridosElszamolas.setHatElszArf(hataridosElszamolasCol.getHatElszArf());
      hataridosElszamolas.setHatDarab(hataridosElszamolasCol.getHatDarab());
      hataridosElszamolas.setHatElszOsszeg(hataridosElszamolasCol.getHatElszOsszeg());
      hataridosElszamolas.setHatHatSzamla(hataridosElszamolasCol.getHatHatSzamla());
      hataridosElszamolas.setHatHatSzamlaNev(
          this.szarepo.findBySzaKod(hataridosElszamolasCol.getHatHatSzamla()).getSzaMegnev());
      hataridosElszamolas.setHatElszSzamla(hataridosElszamolasCol.getHatElszSzamla());
      hataridosElszamolas.setHatElszSzamlaNev(
          this.szarepo.findBySzaKod(hataridosElszamolasCol.getHatElszSzamla()).getSzaMegnev());
      hataridosElszamolas.setHatKonyvelve(hataridosElszamolasCol.isHatKonyvelve());
      hataridosElszamolas.setHatMddat(hataridosElszamolasCol.getHatMddat());
      hataridosElszamolas.setHatMentve(true);
      if ("yyy".contentEquals(hataridosElszamolas.getHatNyitoAzon())) {
        hataridosElszamolas.setHatNyitoAzon(befektetesCol.getBefAzon());
        hataridosElszamolas.setHatDarab(darab);
        hataridosElszamolas.setHatMentve(false);
      }
      if (feTetel != null && Math.round(feTetel.getHatElozoArf() * 100) == 0) {
        feTetel.setHatElozoArf(hataridosElszamolas.getHatElszArf());
      }
      feTetel = hataridosElszamolas;
      hataridosElszamolasok.add(hataridosElszamolas);
    }
    if (feTetel != null && Math.round(feTetel.getHatElozoArf() * 100) == 0) {
      feTetel.setHatElozoArf(befektetesCol.getBefArfolyam());
    }
    return hataridosElszamolasok;
  }

  public Object createHatElsz(final HataridosElszamolas hataridosElszamolas) {
    HataridosElszamolasCol hataridosElszamolasCol;
    if (Util.isEmpty(hataridosElszamolas.getId())) {
      hataridosElszamolasCol = new HataridosElszamolasCol(hataridosElszamolas);
      final BefektetesCol befektetesCol = this.repository.findById(hataridosElszamolas.getHatNyitoId()).get();
      hataridosElszamolasCol.setHatNyitoAzon(befektetesCol.getBefAzon());
      final BefektFajtaCol befektFajtaCol = this.befFajtaRepo.findByBffKod(befektetesCol.getBefBffKod());
      hataridosElszamolasCol.setHatTipus(befektFajtaCol.getBffTip());
      final int sorszam = this.alapAdatokService.getNextBizSorsz(
          DomainErtekek.BIZTIP_HATARIDO, hataridosElszamolasCol.getHatElszDatum().getYear());
      hataridosElszamolasCol.setHatAzon(
          DomainErtekek.BIZTIP_HATARIDO + hataridosElszamolasCol.getHatElszDatum().getYear()
              + Util.padl(Integer.toString(sorszam), 4, '0'));
    } else {
      hataridosElszamolasCol = this.hatElszRepo.findById(hataridosElszamolas.getId()).get();
      if (hataridosElszamolasCol.getHatMddat().toInstant().equals(hataridosElszamolas.getHatMddat().toInstant())) {
        hataridosElszamolasCol.setHatNyitoAzon(hataridosElszamolas.getHatNyitoAzon());
        hataridosElszamolasCol.setHatDarab(hataridosElszamolas.getHatDarab());
        hataridosElszamolasCol.setHatElozoArf(hataridosElszamolas.getHatElozoArf());
        hataridosElszamolasCol.setHatElszArf(hataridosElszamolas.getHatElszArf());
        hataridosElszamolasCol.setHatElszOsszeg(hataridosElszamolas.getHatElszOsszeg());
        hataridosElszamolasCol.setHatHatSzamla(hataridosElszamolas.getHatHatSzamla());
        hataridosElszamolasCol.setHatElszSzamla(hataridosElszamolas.getHatElszSzamla());
        hataridosElszamolasCol.setHatMddat(ZonedDateTime.now(ZoneId.systemDefault()));
      } else {
        return new MegvaltozottTartalomException("Határidős elszámolás", "update");
      }
    }
    this.hatElszRepo.save(hataridosElszamolasCol);
    logger.info("Created Record: {}", hataridosElszamolasCol);
    return null;
  }

  public Object deleteHatElsz(final String id, final String mddat) {
    final Optional<HataridosElszamolasCol> hataridosElszamolasObj = this.hatElszRepo.findById(id);
    if (hataridosElszamolasObj.isPresent()) {
      final HataridosElszamolasCol hataridosElszamolasCol = hataridosElszamolasObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (hataridosElszamolasCol.getHatMddat().equals(pMddat)) {
        this.hatElszRepo.deleteById(id);
        logger.info("Deleted Record: {}", hataridosElszamolasCol);
      } else {
        return new MegvaltozottTartalomException("Határidős elszámolás", "törlés");
      }
    }
    return null;
  }

  public Object hatElszSzamlaForgGen(final String id, final String mddat) {
    final Optional<HataridosElszamolasCol> hatElszObj = this.hatElszRepo.findById(id);
    if (hatElszObj.isPresent()) {
      final HataridosElszamolasCol hatElszCol = hatElszObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (hatElszCol.getHatMddat().equals(pMddat)) {
        final boolean veteli = DomainErtekek.BEFFAJTA_VETELI.equals(hatElszCol.getHatTipus());
        final boolean arfolyamNo = Math.round(hatElszCol.getHatElszOsszeg() * 100) > 0;
        SzamlaKonyvTmp szamlaKonyv = new SzamlaKonyvTmp();
        szamlaKonyv.setSzfForgDat(hatElszCol.getHatElszDatum());
        szamlaKonyv.setSzfSzaAzon(hatElszCol.getHatHatSzamla());
        szamlaKonyv.setSzfSzoveg("Határidős elszámolás");
        szamlaKonyv.setSzfTipus(DomainErtekek.SZLAFORGTIP_HI);
        szamlaKonyv.setSzfHivBizTip(DomainErtekek.BIZTIP_HATARIDO);
        szamlaKonyv.setSzfHivBizAzon(hatElszCol.getHatAzon());
        String tkjel = veteli
            ? (arfolyamNo ? DomainErtekek.TKJEL_KOVETEL : DomainErtekek.TKJEL_TARTOZIK)
            : (arfolyamNo ? DomainErtekek.TKJEL_TARTOZIK : DomainErtekek.TKJEL_KOVETEL);
        szamlaKonyv.setSzfTKJel(tkjel);
        szamlaKonyv.setSzfOsszeg(Math.abs(hatElszCol.getHatElszOsszeg()));
        this.szamlaKonyveles.konyveles(szamlaKonyv);
        szamlaKonyv = new SzamlaKonyvTmp();
        szamlaKonyv.setSzfForgDat(hatElszCol.getHatElszDatum());
        szamlaKonyv.setSzfSzaAzon(hatElszCol.getHatElszSzamla());
        szamlaKonyv.setSzfSzoveg("Határidős elszámolás");
        szamlaKonyv.setSzfTipus(DomainErtekek.SZLAFORGTIP_HI);
        szamlaKonyv.setSzfHivBizTip(DomainErtekek.BIZTIP_HATARIDO);
        szamlaKonyv.setSzfHivBizAzon(hatElszCol.getHatAzon());
        tkjel = DomainErtekek.TKJEL_KOVETEL.equals(tkjel)
            ? DomainErtekek.TKJEL_TARTOZIK
            : DomainErtekek.TKJEL_KOVETEL;
        szamlaKonyv.setSzfTKJel(tkjel);
        szamlaKonyv.setSzfOsszeg(Math.abs(hatElszCol.getHatElszOsszeg()));
        this.szamlaKonyveles.konyveles(szamlaKonyv);
        hatElszCol.setHatKonyvelve(true);
        hatElszCol.setHatMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.hatElszRepo.save(hatElszCol);
        logger.info("Számlakönyvelt Record: {}", hatElszCol);
      } else {
        return new MegvaltozottTartalomException("Határidős elszámolás", "számlakönyvelés");
      }
    }
    return null;
  }

  public Object hatElszSzamlaForgTorl(final String id, final String mddat) {
    final Optional<HataridosElszamolasCol> hataridosElszamolasObj = this.hatElszRepo.findById(id);
    if (hataridosElszamolasObj.isPresent()) {
      final HataridosElszamolasCol hataridosElszamolasCol = hataridosElszamolasObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (hataridosElszamolasCol.getHatMddat().equals(pMddat)) {
        final List<SzamlaForgalomCol> szlaForgTetelek = this.szlaForgRepo.findAllBySzfHivBizTipAndSzfHivBizAzon(
            DomainErtekek.BIZTIP_HATARIDO, hataridosElszamolasCol.getHatAzon());
        for (final SzamlaForgalomCol szlaForgTetel : szlaForgTetelek) {
          this.szamlaKonyveles.szamlaOsszesenKonyveles(szlaForgTetel.getSzfSzaKod(), szlaForgTetel.getSzfForgDat(),
              szlaForgTetel.getSzfTKJel(), -1 * szlaForgTetel.getSzfOsszeg());
          this.szlaForgRepo.delete(szlaForgTetel);
        }
        hataridosElszamolasCol.setHatKonyvelve(false);
        hataridosElszamolasCol.setHatMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.hatElszRepo.save(hataridosElszamolasCol);
        logger.info("Számlakönyvelés törölve Record: {}", hataridosElszamolasCol);
      } else {
        return new MegvaltozottTartalomException("Határidős elszámolás", "számlakönyvelés törlése");
      }
    }
    return null;
  }

  public List<Kamat> findAllKamat(final String befektId) {
    final List<KamatCol> kamatCols = this.kamatRepo.findAllByKamBefektetesIdOrderByKamDatumDescKamAzonDesc(befektId);
    final List<Kamat> kamatok = new ArrayList<>();
    /*
    for (final OsztalekCol osztalekCol : osztalekCols) {
      final Osztalek osztalek = new Osztalek();
      osztalek.setId(osztalekCol.getId());
      osztalek.setOszBefektetesId(osztalekCol.getOszBefektetesId());
      osztalek.setOszAzon(osztalekCol.getOszAzon());
      osztalek.setOszDatum(osztalekCol.getOszDatum());
      osztalek.setOszBefDarab(osztalekCol.getOszBefDarab());
      osztalek.setOszMertek(osztalekCol.getOszMertek());
      osztalek.setOszOsszeg(osztalekCol.getOszOsszeg());
      osztalek.setOszAdoSzaz(osztalekCol.getOszAdoSzaz());
      osztalek.setOszAdo(osztalekCol.getOszAdo());
      osztalek.setOszAdoSzamla(osztalekCol.getOszAdoSzamla());
      osztalek.setOszAdoSzamlaNev(this.szarepo.findBySzaKod(osztalekCol.getOszAdoSzamla()).getSzaMegnev());
      osztalek.setOszKonyvelve(osztalekCol.isOszKonyvelve());
      osztalek.setOszMddat(osztalekCol.getOszMddat());
      osztalekok.add(osztalek);
    }
    */
    return kamatok;
  }
  /*
  public Object osztalekTarolas(final Osztalek osztalek) {
    OsztalekCol osztalekCol;
    if (Util.isEmpty(osztalek.getId())) {
      osztalekCol = new OsztalekCol(osztalek);
      final int sorszam = this.alapAdatokService.getNextBizSorsz(DomainErtekek.BIZTIP_OSZTALEK,
          osztalekCol.getOszDatum().getYear());
      osztalekCol.setOszAzon(
          DomainErtekek.BIZTIP_OSZTALEK + osztalekCol.getOszDatum().getYear() + Util.padl(Integer.toString(sorszam), 4, '0'));
    } else {
      osztalekCol = this.osztalekRepo.findById(osztalek.getId()).get();
      if (osztalekCol.getOszMddat().toInstant().equals(osztalek.getOszMddat().toInstant())) {
        osztalekCol.setOszDatum(osztalek.getOszDatum());
        osztalekCol.setOszBefDarab(osztalek.getOszBefDarab());
        osztalekCol.setOszMertek(osztalek.getOszMertek());
        osztalekCol.setOszOsszeg(osztalek.getOszOsszeg());
        osztalekCol.setOszAdoSzaz(osztalek.getOszAdoSzaz());
        osztalekCol.setOszAdo(osztalek.getOszAdo());
        osztalekCol.setOszAdoSzamla(osztalek.getOszAdoSzamla());
        osztalekCol.setOszKonyvelve(osztalek.isOszKonyvelve());
        osztalekCol.setOszMddat(ZonedDateTime.now(ZoneId.systemDefault()));
      } else {
        return new MegvaltozottTartalomException("Osztalék elszámolás", "update");
      }
    }
    this.osztalekRepo.save(osztalekCol);
    logger.info("Created Record: {}", osztalekCol);
    return null;
  }
  
  public Object osztalekTorles(final String id, final String mddat) {
    final Optional<OsztalekCol> osztalekObj = this.osztalekRepo.findById(id);
    if (osztalekObj.isPresent()) {
      final OsztalekCol osztalekCol = osztalekObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (osztalekCol.getOszMddat().equals(pMddat)) {
        this.osztalekRepo.deleteById(id);
        logger.info("Deleted Record: {}", osztalekCol);
      } else {
        return new MegvaltozottTartalomException("Osztalék", "törlés");
      }
    }
    return null;
  }
  
  public Object osztalekSzamlaForgGen(final String id, final String mddat) {
    final Optional<OsztalekCol> osztalekObj = this.osztalekRepo.findById(id);
    if (osztalekObj.isPresent()) {
      final OsztalekCol osztalekCol = osztalekObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (osztalekCol.getOszMddat().equals(pMddat)) {
        String szamla = null;
        final Optional<BefektetesCol> befektetesObj = this.repository.findById(osztalekCol.getOszBefektetesId());
        if (befektetesObj.isPresent()) {
          final BefektetesCol befektetesCol = befektetesObj.get();
          szamla = befektetesCol.getBefElszSzla();
        }
        SzamlaKonyvTmp szamlaKonyv = new SzamlaKonyvTmp();
        szamlaKonyv.setSzfForgDat(osztalekCol.getOszDatum());
        szamlaKonyv.setSzfSzaAzon(szamla);
        szamlaKonyv.setSzfSzoveg("Osztalek");
        szamlaKonyv.setSzfTipus(DomainErtekek.SZLAFORGTIP_OS);
        szamlaKonyv.setSzfHivBizTip(DomainErtekek.BIZTIP_OSZTALEK);
        szamlaKonyv.setSzfHivBizAzon(osztalekCol.getOszAzon());
        szamlaKonyv.setSzfTKJel(DomainErtekek.TKJEL_TARTOZIK);
        szamlaKonyv.setSzfOsszeg(Math.abs(osztalekCol.getOszOsszeg()));
        this.szamlaKonyveles.konyveles(szamlaKonyv);
        if (Math.round(osztalekCol.getOszAdo() * 100) != 0) {
          szamlaKonyv = new SzamlaKonyvTmp();
          szamlaKonyv.setSzfForgDat(osztalekCol.getOszDatum());
          szamlaKonyv.setSzfSzaAzon(szamla);
          szamlaKonyv.setSzfSzoveg("Osztalek");
          szamlaKonyv.setSzfTipus(DomainErtekek.SZLAFORGTIP_OS);
          szamlaKonyv.setSzfHivBizTip(DomainErtekek.BIZTIP_OSZTALEK);
          szamlaKonyv.setSzfHivBizAzon(osztalekCol.getOszAzon());
          szamlaKonyv.setSzfTKJel(DomainErtekek.TKJEL_KOVETEL);
          szamlaKonyv.setSzfOsszeg(Math.abs(osztalekCol.getOszAdo()));
          this.szamlaKonyveles.konyveles(szamlaKonyv);
          szamlaKonyv = new SzamlaKonyvTmp();
          szamlaKonyv.setSzfForgDat(osztalekCol.getOszDatum());
          szamlaKonyv.setSzfSzaAzon(osztalekCol.getOszAdoSzamla());
          szamlaKonyv.setSzfSzoveg("Osztalek");
          szamlaKonyv.setSzfTipus(DomainErtekek.SZLAFORGTIP_OS);
          szamlaKonyv.setSzfHivBizTip(DomainErtekek.BIZTIP_OSZTALEK);
          szamlaKonyv.setSzfHivBizAzon(osztalekCol.getOszAzon());
          szamlaKonyv.setSzfTKJel(DomainErtekek.TKJEL_TARTOZIK);
          szamlaKonyv.setSzfOsszeg(Math.abs(osztalekCol.getOszAdo()));
          this.szamlaKonyveles.konyveles(szamlaKonyv);
        }
        osztalekCol.setOszKonyvelve(true);
        osztalekCol.setOszMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.osztalekRepo.save(osztalekCol);
        logger.info("Számlakönyvelt Record: {}", osztalekCol);
      } else {
        return new MegvaltozottTartalomException("Osztalek", "számlakönyvelés");
      }
    }
    return null;
  }
  
  public Object osztalekSzamlaForgTorl(final String id, final String mddat) {
    final Optional<OsztalekCol> osztalekObj = this.osztalekRepo.findById(id);
    if (osztalekObj.isPresent()) {
      final OsztalekCol osztalekCol = osztalekObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (osztalekCol.getOszMddat().equals(pMddat)) {
        final List<SzamlaForgalomCol> szlaForgTetelek = this.szlaForgRepo.findAllBySzfHivBizTipAndSzfHivBizAzon(
            DomainErtekek.BIZTIP_OSZTALEK, osztalekCol.getOszAzon());
        for (final SzamlaForgalomCol szlaForgTetel : szlaForgTetelek) {
          this.szamlaKonyveles.szamlaOsszesenKonyveles(szlaForgTetel.getSzfSzaKod(), szlaForgTetel.getSzfForgDat(),
              szlaForgTetel.getSzfTKJel(), -1 * szlaForgTetel.getSzfOsszeg());
          this.szlaForgRepo.delete(szlaForgTetel);
        }
        osztalekCol.setOszKonyvelve(false);
        osztalekCol.setOszMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.osztalekRepo.save(osztalekCol);
        logger.info("Számlakönyvelés törölve Record: {}", osztalekCol);
      } else {
        return new MegvaltozottTartalomException("Osztalek", "számlakönyvelés törlése");
      }
    }
    return null;
  }
  */
}
