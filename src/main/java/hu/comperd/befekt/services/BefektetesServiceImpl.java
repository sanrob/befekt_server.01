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
import hu.comperd.befekt.collections.HataridosElszamolasCol;
import hu.comperd.befekt.collections.OsztalekCol;
import hu.comperd.befekt.collections.SzamlaForgalomCol;
import hu.comperd.befekt.dto.Befektetes;
import hu.comperd.befekt.dto.HataridosElszamolas;
import hu.comperd.befekt.dto.Osztalek;
import hu.comperd.befekt.etc.DomainCsoportok;
import hu.comperd.befekt.etc.DomainErtekek;
import hu.comperd.befekt.etc.SzamlaKonyvTmp;
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
  AlapAdatokServiceImpl                 alapAdatokService = null;
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

  public List<Befektetes> findAll(final String konyvEv) {
    final List<BefektetesCol> befektetesCols = this.repository.findAllByBefEvOrderByBefDatumDesc(Integer.parseInt(konyvEv));
    final List<Befektetes> befektetesek = new ArrayList<>();
    for (final BefektetesCol befektetesCol : befektetesCols) {
      final Befektetes befektetes = new Befektetes();
      befektetes.setId(befektetesCol.getId());
      befektetes.setBefAzon(befektetesCol.getBefAzon());
      befektetes.setBefDatum(befektetesCol.getBefDatum());
      befektetes.setBefBffKod(befektetesCol.getBefBffKod());
      befektetes.setBefBffKodNev(befFajtaRepo.findByBffKod(befektetesCol.getBefBffKod()).getBffMegnev());
      befektetes.setBefDarab(befektetesCol.getBefDarab());
      befektetes.setBefArfolyam(befektetesCol.getBefArfolyam());
      befektetes.setBefErtek(befektetesCol.getBefErtek());
      befektetes.setBefJutSzaz(befektetesCol.getBefJutSzaz());
      befektetes.setBefJutErtek(befektetesCol.getBefJutErtek());
      befektetes.setBefBekerErtek(befektetesCol.getBefBekerErtek());
      befektetes.setBefKonyvDat(befektetesCol.getBefKonyvDat());
      befektetes.setBefKonyvelve(befektetesCol.isBefKonyvelve());
      befektetes.setBefLezDat(befektetesCol.getBefLezDat());
      befektetes.setBefParDarab(befektetesCol.getBefParDarab());
      befektetes.setBefMddat(befektetesCol.getBefMddat());
      befektetesek.add(befektetes);
    }
    return befektetesek;
  }

  public Object create(final Befektetes befektetes) {
    final BefektetesCol befektetesCol = new BefektetesCol(befektetes);
    final int sorszam = this.alapAdatokService.getNextBizSorsz(DomainErtekek.BIZTIP_VETEL, befektetes.getBefDatum().getYear());
    befektetesCol.setBefAzon(
        DomainErtekek.BIZTIP_VETEL + befektetesCol.getBefDatum().getYear() + Util.padl(Integer.toString(sorszam), 4, '0'));
    this.repository.save(befektetesCol);
    logger.info("Created Record: {}", befektetesCol);
    return null;
  }

  public Object update(final Befektetes befektetes) {
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
        SzamlaKonyvTmp szamlaKonyv = new SzamlaKonyvTmp();
        szamlaKonyv.setSzfForgDat(befektetesCol.getBefKonyvDat());
        final BefektFajtaCol befektFajtaCol = befFajtaRepo.findByBffKod(befektetesCol.getBefBffKod());
        szamlaKonyv.setSzfSzaAzon(befektFajtaCol.getBffSzamla());
        szamlaKonyv.setSzfSzoveg("Vételi pozíció - " + befektFajtaCol.getBffMegnev());
        szamlaKonyv.setSzfTipus(DomainErtekek.SZLAFORGTIP_BN);
        szamlaKonyv.setSzfHivBizTip(DomainErtekek.BIZTIP_VETEL);
        szamlaKonyv.setSzfHivBizAzon(befektetesCol.getBefAzon());
        szamlaKonyv.setSzfTKJel(DomainErtekek.TKJEL_KOVETEL);
        szamlaKonyv.setSzfOsszeg(befektetesCol.getBefErtek());
        this.szamlaKonyveles.konyveles(szamlaKonyv);
        if (Math.round(befektetesCol.getBefJutErtek() * 100) / 100 > 0) {
          szamlaKonyv = new SzamlaKonyvTmp();
          szamlaKonyv.setSzfForgDat(befektetesCol.getBefKonyvDat());
          szamlaKonyv.setSzfSzaAzon(befektFajtaCol.getBffJutSzla());
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
      if (befektetesCol.getBefMddat().equals(pMddat)) {
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
      } else {
        return new MegvaltozottTartalomException("Befektetés", "számlakönyvelés törlése");
      }
    }
    return null;
  }

  public Osztalek findOsztalekBeallito(final String befektId) {
    Osztalek osztalek = null;
    final OsztalekCol osztalekCol = this.osztalekRepo.findByOszBefektetesId(befektId);
    if (osztalekCol == null) {
      final Optional<BefektetesCol> befektetesObj = this.repository.findById(befektId);
      if (befektetesObj.isPresent()) {
        final BefektetesCol befektetesCol = befektetesObj.get();
        osztalek = new Osztalek();
        osztalek.setOszBefektetesId(befektetesCol.getId());
        osztalek.setOszBefDarab(befektetesCol.getBefDarab() - befektetesCol.getBefParDarab());
      }
    } else {
      osztalek = new Osztalek();
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
    }
    return osztalek;
  }

  public Object osztalekTarolas(final Osztalek osztalek) {
    final OsztalekCol osztalekCol = new OsztalekCol(osztalek);
    if (osztalekCol.getId() == null || osztalekCol.getId().length() == 0) {
      final int sorszam = this.alapAdatokService.getNextBizSorsz(DomainErtekek.BIZTIP_OSZTALEK,
          osztalekCol.getOszDatum().getYear());
      osztalekCol.setOszAzon(
          DomainErtekek.BIZTIP_OSZTALEK + osztalekCol.getOszDatum().getYear() + Util.padl(Integer.toString(sorszam), 4, '0'));
    }
    osztalekCol.setOszMddat(ZonedDateTime.now(ZoneId.systemDefault()));
    this.osztalekRepo.save(osztalekCol);
    logger.info("Created Record: {}", osztalekCol);
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
          final BefektFajtaCol befektFajtaCol = befFajtaRepo.findByBffKod(befektetesCol.getBefBffKod());
          szamla = befektFajtaCol.getBffSzamla();
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
    final List<HataridosElszamolasCol> hataridosElszamolasCols = this.hatElszRepo.findAllByHatNyitoId(befektId);
    final BefektetesCol befektetesCol = this.repository.findById(befektId).get();
    final double darab = befektetesCol.getBefDarab() - befektetesCol.getBefParDarab();
    double eloarf = befektetesCol.getBefArfolyam();
    final List<HataridosElszamolas> hataridosElszamolasok = new ArrayList<>();
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
      if ("yyy".contentEquals(hataridosElszamolas.getHatNyitoAzon())) {
        hataridosElszamolas.setHatNyitoAzon(befektetesCol.getBefAzon());
        hataridosElszamolas.setHatDarab(darab);
        hataridosElszamolas.setHatElozoArf(eloarf);
      }
      eloarf = hataridosElszamolas.getHatElszArf();
      hataridosElszamolasok.add(hataridosElszamolas);
    }
    return hataridosElszamolasok;
  }

  public Object createHatElsz(final HataridosElszamolas hataridosElszamolas) {
    if (Util.isEmpty(hataridosElszamolas.getId())) {
      final BefektetesCol befektetesCol = this.repository.findById(hataridosElszamolas.getHatNyitoId()).get();
      hataridosElszamolas.setHatNyitoAzon(befektetesCol.getBefAzon());
      final BefektFajtaCol befektFajtaCol = this.befFajtaRepo.findByBffKod(befektetesCol.getBefBffKod());
      hataridosElszamolas.setHatTipus(befektFajtaCol.getBffTip());
    }
    final HataridosElszamolasCol hataridosElszamolasCol = new HataridosElszamolasCol(hataridosElszamolas);
    final int sorszam = this.alapAdatokService.getNextBizSorsz(
        DomainErtekek.BIZTIP_HATARIDO, hataridosElszamolas.getHatElszDatum().getYear());
    hataridosElszamolasCol.setHatAzon(
        DomainErtekek.BIZTIP_HATARIDO + hataridosElszamolas.getHatElszDatum().getYear()
            + Util.padl(Integer.toString(sorszam), 4, '0'));
    this.hatElszRepo.save(hataridosElszamolasCol);
    logger.info("Created Record: {}", hataridosElszamolasCol);
    return null;
  }

  public Object hatElszSzamlaForgGen(final String id, final String mddat) {
    final Optional<HataridosElszamolasCol> hatElszObj = this.hatElszRepo.findById(id);
    if (hatElszObj.isPresent()) {
      final HataridosElszamolasCol hatElszCol = hatElszObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (hatElszCol.getHatMddat().equals(pMddat)) {
        SzamlaKonyvTmp szamlaKonyv = new SzamlaKonyvTmp();
        szamlaKonyv.setSzfForgDat(hatElszCol.getHatElszDatum());
        szamlaKonyv.setSzfSzaAzon(hatElszCol.getHatHatSzamla());
        szamlaKonyv.setSzfSzoveg("Határidős elszámolás");
        szamlaKonyv.setSzfTipus(DomainErtekek.SZLAFORGTIP_HI);
        szamlaKonyv.setSzfHivBizTip(DomainErtekek.BIZTIP_HATARIDO);
        szamlaKonyv.setSzfHivBizAzon(hatElszCol.getHatAzon());
        String tkjel = Math.round(hatElszCol.getHatElszOsszeg() * 100) > 0
            ? DomainErtekek.TKJEL_KOVETEL
            : DomainErtekek.TKJEL_TARTOZIK;
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
        tkjel = Math.round(hatElszCol.getHatElszOsszeg() * 100) > 0
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
}