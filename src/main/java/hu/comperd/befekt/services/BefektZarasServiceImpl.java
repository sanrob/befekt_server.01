package hu.comperd.befekt.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hu.comperd.befekt.collections.*;
import hu.comperd.befekt.dto.BefektZaras;
import hu.comperd.befekt.dto.HozamBeallito;
import hu.comperd.befekt.dto.NyitasZarasParok;
import hu.comperd.befekt.etc.DomainErtekek;
import hu.comperd.befekt.etc.SzamlaKonyvTmp;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.repositories.*;
import hu.comperd.befekt.util.Util;

@Service
public class BefektZarasServiceImpl {
  private static final Logger         logger            = LoggerFactory.getLogger(BefektZarasServiceImpl.class);

  @Autowired
  private BefektZarasRepository       repository;
  @Autowired
  private BefektFajtaRepository       befFajtaRepo;
  @Autowired
  private final AlapAdatokServiceImpl alapAdatokService = null;
  @Autowired
  private BefektetesCustomRepo        befektCustomRepo;
  @Autowired
  private NyitasZarasParokRepository  nyitasZarasParokRepo;
  @Autowired
  private BefektetesRepository        befektRepo;
  @Autowired
  private SzamlaKonyveles             szamlaKonyveles;
  @Autowired
  private SzamlaForgalomRepository    szlaForgRepo;
  @Autowired
  private HozamBeallitoRepository     hozamBeallRepo;
  @Autowired
  private SzamlaRepository            szarepo;

  public List<BefektZaras> findAll(final String konyvEv) {
    final List<BefektZarasCol> befektZarasCols = this.repository.findAllByBezEvOrderByBezDatumDesc(Integer.parseInt(konyvEv));
    final List<BefektZaras> befektZarasok = new ArrayList<>();
    for (final BefektZarasCol befektZarasCol : befektZarasCols) {
      final BefektZaras befektZaras = new BefektZaras();
      befektZaras.setId(befektZarasCol.getId());
      befektZaras.setBezAzon(befektZarasCol.getBezAzon());
      befektZaras.setBezDatum(befektZarasCol.getBezDatum());
      befektZaras.setBezBffKod(befektZarasCol.getBezBffKod());
      befektZaras.setBezBffKodNev(befFajtaRepo.findByBffKod(befektZarasCol.getBezBffKod()).getBffMegnev());
      befektZaras.setBezDarab(befektZarasCol.getBezDarab());
      befektZaras.setBezArfolyam(befektZarasCol.getBezArfolyam());
      befektZaras.setBezErtek(befektZarasCol.getBezErtek());
      befektZaras.setBezJutSzaz(befektZarasCol.getBezJutSzaz());
      befektZaras.setBezJutErtek(befektZarasCol.getBezJutErtek());
      befektZaras.setBezRealErtek(befektZarasCol.getBezRealErtek());
      befektZaras.setBezElszSzla(befektZarasCol.getBezElszSzla());
      befektZaras.setBezElszSzlaNev(this.szarepo.findBySzaKod(befektZarasCol.getBezElszSzla()).getSzaMegnev());
      befektZaras.setBezJutSzla(befektZarasCol.getBezJutSzla());
      befektZaras.setBezJutSzlaNev(this.szarepo.findBySzaKod(befektZarasCol.getBezJutSzla()).getSzaMegnev());
      befektZaras.setBezKonyvDat(befektZarasCol.getBezKonyvDat());
      befektZaras.setBezKonyvelve(befektZarasCol.isBezKonyvelve());
      befektZaras.setBezParDarab(befektZarasCol.getBezParDarab());
      befektZaras.setBezMddat(befektZarasCol.getBezMddat());
      befektZarasok.add(befektZaras);
    }
    return befektZarasok;
  }

  public Object create(final BefektZaras befektZaras) {
    final BefektZarasCol befektZarasCol = new BefektZarasCol(befektZaras);
    final int sorszam = this.alapAdatokService.getNextBizSorsz(DomainErtekek.BIZTIP_ZARAS, befektZaras.getBezDatum().getYear());
    befektZarasCol.setBezAzon(
        DomainErtekek.BIZTIP_ZARAS + befektZaras.getBezDatum().getYear() + Util.padl(Integer.toString(sorszam), 4, '0'));
    this.repository.save(befektZarasCol);
    logger.info("Created Record: {}", befektZarasCol);
    return null;
  }

  public Object update(final BefektZaras befektZaras) {
    final Optional<BefektZarasCol> befektZarasObj = this.repository.findById(befektZaras.getId());
    if (befektZarasObj.isPresent()) {
      final BefektZarasCol befektZarasCol = befektZarasObj.get();
      if (befektZarasCol.getBezMddat().toInstant().equals(befektZaras.getBezMddat().toInstant())) {
        befektZarasCol.setBezDatum(befektZaras.getBezDatum());
        befektZarasCol.setBezBffKod(befektZaras.getBezBffKod());
        befektZarasCol.setBezDarab(befektZaras.getBezDarab());
        befektZarasCol.setBezArfolyam(befektZaras.getBezArfolyam());
        befektZarasCol.setBezErtek(befektZaras.getBezErtek());
        befektZarasCol.setBezJutSzaz(befektZaras.getBezJutSzaz());
        befektZarasCol.setBezJutErtek(befektZaras.getBezJutErtek());
        befektZarasCol.setBezRealErtek(befektZaras.getBezRealErtek());
        befektZarasCol.setBezElszSzla(befektZaras.getBezElszSzla());
        befektZarasCol.setBezJutSzla(befektZaras.getBezJutSzla());
        befektZarasCol.setBezKonyvDat(befektZaras.getBezKonyvDat());
        befektZarasCol.setBezMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.repository.save(befektZarasCol);
        logger.info("Updated Record: {}", befektZarasCol);
      } else {
        return new MegvaltozottTartalomException("Befektetés zárás", "módosítás");
      }
    }
    return null;
  }

  public Object delete(final String id, final String mddat) {
    final Optional<BefektZarasCol> befektZarasObj = this.repository.findById(id);
    if (befektZarasObj.isPresent()) {
      final BefektZarasCol befektZarasCol = befektZarasObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (befektZarasCol.getBezMddat().equals(pMddat)) {
        this.repository.deleteById(id);
        logger.info("Deleted Record: {}", befektZarasCol);
      } else {
        return new MegvaltozottTartalomException("Befektetés zárás", "törlés");
      }
    }
    return null;
  }

  public Object szamlaForgGen(final String id, final String mddat) {
    final Optional<BefektZarasCol> befektZarasObj = this.repository.findById(id);
    if (befektZarasObj.isPresent()) {
      final BefektZarasCol befektZarasCol = befektZarasObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (befektZarasCol.getBezMddat().equals(pMddat)) {
        SzamlaKonyvTmp szamlaKonyv = new SzamlaKonyvTmp();
        szamlaKonyv.setSzfForgDat(befektZarasCol.getBezKonyvDat());
        final BefektFajtaCol befektFajtaCol = befFajtaRepo.findByBffKod(befektZarasCol.getBezBffKod());
        szamlaKonyv.setSzfSzaAzon(befektZarasCol.getBezElszSzla());
        szamlaKonyv.setSzfSzoveg("Zárási pozíció - " + befektFajtaCol.getBffMegnev());
        szamlaKonyv.setSzfTipus(DomainErtekek.SZLAFORGTIP_BZ);
        szamlaKonyv.setSzfHivBizTip(DomainErtekek.BIZTIP_ZARAS);
        szamlaKonyv.setSzfHivBizAzon(befektZarasCol.getBezAzon());
        final String tkjel = DomainErtekek.BEFFAJTA_ELADASI.equals(befektFajtaCol.getBffTip())
            ? DomainErtekek.TKJEL_KOVETEL
            : DomainErtekek.TKJEL_TARTOZIK;
        szamlaKonyv.setSzfTKJel(tkjel);
        szamlaKonyv.setSzfOsszeg(befektZarasCol.getBezErtek());
        this.szamlaKonyveles.konyveles(szamlaKonyv);
        if (Math.round(befektZarasCol.getBezJutErtek() * 100) > 0) {
          szamlaKonyv = new SzamlaKonyvTmp();
          szamlaKonyv.setSzfForgDat(befektZarasCol.getBezKonyvDat());
          szamlaKonyv.setSzfSzaAzon(befektZarasCol.getBezJutSzla());
          szamlaKonyv.setSzfSzoveg("Zárási pozíció - " + befektFajtaCol.getBffMegnev());
          szamlaKonyv.setSzfTipus(DomainErtekek.SZLAFORGTIP_JU);
          szamlaKonyv.setSzfHivBizTip(DomainErtekek.BIZTIP_ZARAS);
          szamlaKonyv.setSzfHivBizAzon(befektZarasCol.getBezAzon());
          szamlaKonyv.setSzfTKJel(DomainErtekek.TKJEL_KOVETEL);
          szamlaKonyv.setSzfOsszeg(befektZarasCol.getBezJutErtek());
          this.szamlaKonyveles.konyveles(szamlaKonyv);
        }
        befektZarasCol.setBezKonyvelve(true);
        befektZarasCol.setBezMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.repository.save(befektZarasCol);
        logger.info("Számlakönyvelt Record: {}", befektZarasCol);
      } else {
        return new MegvaltozottTartalomException("Befektetés zárás", "számlakönyvelés");
      }
    }
    return null;
  }

  public Object szamlaForgTorl(final String id, final String mddat) {
    final Optional<BefektZarasCol> befektZarasObj = this.repository.findById(id);
    if (befektZarasObj.isPresent()) {
      final BefektZarasCol befektZarasCol = befektZarasObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (befektZarasCol.getBezMddat().equals(pMddat)) {
        final List<SzamlaForgalomCol> szlaForgTetelek = this.szlaForgRepo.findAllBySzfHivBizTipAndSzfHivBizAzon(
            DomainErtekek.BIZTIP_ZARAS, befektZarasCol.getBezAzon());
        for (final SzamlaForgalomCol szlaForgTetel : szlaForgTetelek) {
          this.szamlaKonyveles.szamlaOsszesenKonyveles(szlaForgTetel.getSzfSzaKod(), szlaForgTetel.getSzfForgDat(),
              szlaForgTetel.getSzfTKJel(), -1 * szlaForgTetel.getSzfOsszeg());
          this.szlaForgRepo.delete(szlaForgTetel);
        }
        befektZarasCol.setBezKonyvelve(false);
        befektZarasCol.setBezMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.repository.save(befektZarasCol);
        logger.info("Számlakönyvelés törölve Record: {}", befektZarasCol);
      } else {
        return new MegvaltozottTartalomException("Befektetés zárás", "számlakönyvelés törlése");
      }
    }
    return null;
  }

  public List<NyitasZarasParok> findAllParok(final String parZarAzon) {
    final List<NyitasZarasParokCol> nyitasZarasParokCols = this.nyitasZarasParokRepo.findByParZarAzon(parZarAzon);
    final List<NyitasZarasParok> nyitasZarasParokList = new ArrayList<>();
    final BefektZarasCol befektZarasCol = this.repository.findByBezAzon(parZarAzon);
    final Set<String> eddigiParok = new TreeSet<>();
    for (final NyitasZarasParokCol nyitasZarasParokCol : nyitasZarasParokCols) {
      final NyitasZarasParok nyitasZarasPar = new NyitasZarasParok();
      nyitasZarasPar.setId(nyitasZarasParokCol.getId());
      final BefektetesCol befektetesCol = this.befektRepo.findByBefAzon(nyitasZarasParokCol.getParNyitAzon());
      nyitasZarasPar.setParNyitAzon(nyitasZarasParokCol.getParNyitAzon());
      nyitasZarasPar.setParNyitDarab(befektetesCol.getBefDarab());
      nyitasZarasPar.setParParositott(befektetesCol.getBefParDarab());
      nyitasZarasPar.setParZarAzon(nyitasZarasParokCol.getParZarAzon());
      nyitasZarasPar.setParDarab(nyitasZarasParokCol.getParDarab());
      nyitasZarasPar.setParNyitDatum(befektetesCol.getBefDatum());
      nyitasZarasPar.setParZarDatum(nyitasZarasParokCol.getParZarDatum());
      nyitasZarasPar.setParMddat(nyitasZarasParokCol.getParMddat());
      eddigiParok.add(nyitasZarasParokCol.getParNyitAzon());
      nyitasZarasParokList.add(nyitasZarasPar);
    }
    final List<BefektetesCol> lehetsegesParok = this.befektCustomRepo.findAllByBffKodDatumElottNyitott(
        befektZarasCol.getBezBffKod(), befektZarasCol.getBezDatum());
    for (final BefektetesCol befektetesCol : lehetsegesParok) {
      if (eddigiParok.contains(befektetesCol.getBefAzon())
          || Math.round((befektetesCol.getBefDarab() - befektetesCol.getBefParDarab()) * 100) / 100 == 0) {
        continue;
      }
      final NyitasZarasParok nyitasZarasPar = new NyitasZarasParok();
      nyitasZarasPar.setParNyitAzon(befektetesCol.getBefAzon());
      nyitasZarasPar.setParNyitDarab(befektetesCol.getBefDarab());
      nyitasZarasPar.setParParositott(befektetesCol.getBefParDarab());
      nyitasZarasPar.setParNyitDatum(befektetesCol.getBefDatum());
      nyitasZarasParokList.add(nyitasZarasPar);
    }
    return nyitasZarasParokList;
  }

  public Object createParositas(final NyitasZarasParok nyitasZarasPar) {
    final NyitasZarasParokCol nyitasZarasParokCol = new NyitasZarasParokCol(nyitasZarasPar);
    double corrDarab = 0;
    if (nyitasZarasParokCol.getId() != null) {
      final Optional<NyitasZarasParokCol> oldParObj = this.nyitasZarasParokRepo.findById(nyitasZarasParokCol.getId());
      if (oldParObj.isPresent()) {
        final NyitasZarasParokCol oldParCol = oldParObj.get();
        corrDarab = oldParCol.getParDarab();
      }
    }
    final double beallDarab = nyitasZarasParokCol.getParDarab() - corrDarab;
    final BefektetesCol nyitoTetel = this.befektRepo.findByBefAzon(nyitasZarasParokCol.getParNyitAzon());
    final BefektZarasCol zaroTetel = this.repository.findByBezAzon(nyitasZarasParokCol.getParZarAzon());
    nyitoTetel.setBefParDarab(nyitoTetel.getBefParDarab() + beallDarab);
    nyitoTetel.setBefLezDat(Math.round((nyitoTetel.getBefDarab() - nyitoTetel.getBefParDarab()) * 100) == 0
        ? zaroTetel.getBezKonyvDat()
        : null);
    zaroTetel.setBezParDarab(zaroTetel.getBezParDarab() + beallDarab);
    nyitasZarasParokCol.setParMddat(ZonedDateTime.now(ZoneId.systemDefault()));
    if (Math.round(nyitasZarasParokCol.getParDarab() * 100) == 0) {
      if (nyitasZarasParokCol.getId() != null) {
        this.nyitasZarasParokRepo.delete(nyitasZarasParokCol);
      }
    } else {
      this.nyitasZarasParokRepo.save(nyitasZarasParokCol);
    }
    nyitoTetel.setBefMddat(ZonedDateTime.now(ZoneId.systemDefault()));
    this.befektRepo.save(nyitoTetel);
    zaroTetel.setBezMddat(ZonedDateTime.now(ZoneId.systemDefault()));
    this.repository.save(zaroTetel);
    return null;
  }

  public HozamBeallito findHozamBeallito(final String parAzon) {
    HozamBeallito hozamBeallito = null;
    final HozamBeallitoCol hozamBeallitoCol = this.hozamBeallRepo.findByHobParositasId(parAzon);
    if (hozamBeallitoCol == null) {
      final Optional<NyitasZarasParokCol> nyitasZarasParokObj = this.nyitasZarasParokRepo.findById(parAzon);
      if (nyitasZarasParokObj.isPresent()) {
        final NyitasZarasParokCol nyitasZarasParokCol = nyitasZarasParokObj.get();
        final BefektetesCol befektetesCol = this.befektRepo.findByBefAzon(nyitasZarasParokCol.getParNyitAzon());
        final BefektZarasCol befektZarasCol = this.repository.findByBezAzon(nyitasZarasParokCol.getParZarAzon());
        hozamBeallito = new HozamBeallito();
        hozamBeallito.setHobParositasId(nyitasZarasParokCol.getId());
        final double bruttoHozam = Math.round(
            nyitasZarasParokCol.getParDarab() * (befektZarasCol.getBezArfolyam() - befektetesCol.getBefArfolyam()));
        hozamBeallito.setHobBruttoHozam(bruttoHozam);
        final double nyitasiJutalek = Math.round(
            (nyitasZarasParokCol.getParDarab() / befektetesCol.getBefDarab()) * befektetesCol.getBefJutErtek());
        hozamBeallito.setHobNyitoJutalek(nyitasiJutalek);
        final double zarasiJutalek = Math.round(
            (nyitasZarasParokCol.getParDarab() / befektZarasCol.getBezDarab()) * befektZarasCol.getBezJutErtek());
        hozamBeallito.setHobZaroJutalek(zarasiJutalek);
        final double nettoHozam = hozamBeallito.getHobBruttoHozam()
            - (hozamBeallito.getHobBruttoHozam() < 0
                ? 0
                : (hozamBeallito.getHobNyitoJutalek() + hozamBeallito.getHobZaroJutalek()));
        hozamBeallito.setHobNettoHozam(nettoHozam);
        final LocalDate hobZarDatum = Math.round(nettoHozam * 100) < 0
            ? befektZarasCol.getBezDatum()
            : befektZarasCol.getBezKonyvDat();
        hozamBeallito.setHobZarDatum(hobZarDatum);
      }
    } else {
      hozamBeallito = new HozamBeallito();
      hozamBeallito.setId(hozamBeallitoCol.getId());
      hozamBeallito.setHobParositasId(hozamBeallitoCol.getHobParositasId());
      hozamBeallito.setHobAzon(hozamBeallitoCol.getHobAzon());
      hozamBeallito.setHobZarDatum(hozamBeallitoCol.getHobZarDatum());
      hozamBeallito.setHobBruttoHozam(hozamBeallitoCol.getHobBruttoHozam());
      hozamBeallito.setHobNyitoJutalek(hozamBeallitoCol.getHobNyitoJutalek());
      hozamBeallito.setHobZaroJutalek(hozamBeallitoCol.getHobZaroJutalek());
      hozamBeallito.setHobNettoHozam(hozamBeallitoCol.getHobNettoHozam());
      hozamBeallito.setHobAdoSzaz(hozamBeallitoCol.getHobAdoSzaz());
      hozamBeallito.setHobAdo(hozamBeallitoCol.getHobAdo());
      hozamBeallito.setHobTartSzamla(hozamBeallitoCol.getHobTartSzamla());
      hozamBeallito.setHobTartSzamlaNev(this.szarepo.findBySzaKod(hozamBeallitoCol.getHobTartSzamla()).getSzaMegnev());
      hozamBeallito.setHobKovSzamla(hozamBeallitoCol.getHobKovSzamla());
      hozamBeallito.setHobKovSzamlaNev(this.szarepo.findBySzaKod(hozamBeallitoCol.getHobKovSzamla()).getSzaMegnev());
      hozamBeallito.setHobKonyvelve(hozamBeallitoCol.isHobKonyvelve());
      hozamBeallito.setHobMddat(hozamBeallitoCol.getHobMddat());
    }
    return hozamBeallito;
  }

  public Object hozamTarolas(final HozamBeallito hozamBeallito) {
    final HozamBeallitoCol hozamBeallitoCol = new HozamBeallitoCol(hozamBeallito);
    if (hozamBeallitoCol.getId() == null || hozamBeallitoCol.getId().length() == 0) {
      final int sorszam = this.alapAdatokService.getNextBizSorsz(DomainErtekek.BIZTIP_HOZAM,
          hozamBeallitoCol.getHobZarDatum().getYear());
      hozamBeallitoCol.setHobAzon(
          DomainErtekek.BIZTIP_HOZAM + hozamBeallitoCol.getHobZarDatum().getYear() + Util.padl(Integer.toString(sorszam), 4, '0'));
      hozamBeallitoCol.setHobEv(hozamBeallitoCol.getHobZarDatum().getYear());
    } else {
      final Optional<HozamBeallitoCol> hozamBeallitoOldObj = this.hozamBeallRepo.findById(hozamBeallitoCol.getId());
      if (hozamBeallitoOldObj.isPresent()) {
        final HozamBeallitoCol hozamBeallitoOldCol = hozamBeallitoOldObj.get();
        if (!hozamBeallitoCol.getHobMddat().toInstant().equals(hozamBeallitoOldCol.getHobMddat().toInstant())) {
          return new MegvaltozottTartalomException("HozamBeallito", "hozam tárolás");
        }
      }
    }
    hozamBeallitoCol.setHobMddat(ZonedDateTime.now(ZoneId.systemDefault()));
    this.hozamBeallRepo.save(hozamBeallitoCol);
    logger.info("Created Record: {}", hozamBeallitoCol);
    return null;
  }

  public Object hozamSzamlaForgGen(final String id, final String mddat) {
    final Optional<HozamBeallitoCol> hozamBeallitoObj = this.hozamBeallRepo.findById(id);
    if (hozamBeallitoObj.isPresent()) {
      final HozamBeallitoCol hozamBeallitoCol = hozamBeallitoObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (hozamBeallitoCol.getHobMddat().equals(pMddat)) {
        SzamlaKonyvTmp szamlaKonyv = new SzamlaKonyvTmp();
        szamlaKonyv.setSzfForgDat(hozamBeallitoCol.getHobZarDatum());
        szamlaKonyv.setSzfSzaAzon(hozamBeallitoCol.getHobKovSzamla());
        szamlaKonyv.setSzfSzoveg("Hozam");
        szamlaKonyv.setSzfTipus(DomainErtekek.SZLAFORGTIP_HO);
        szamlaKonyv.setSzfHivBizTip(DomainErtekek.BIZTIP_HOZAM);
        szamlaKonyv.setSzfHivBizAzon(hozamBeallitoCol.getHobAzon());
        szamlaKonyv.setSzfTKJel(DomainErtekek.TKJEL_KOVETEL);
        szamlaKonyv.setSzfOsszeg(Math.abs(hozamBeallitoCol.getHobAdo()));
        this.szamlaKonyveles.konyveles(szamlaKonyv);
        szamlaKonyv = new SzamlaKonyvTmp();
        szamlaKonyv.setSzfForgDat(hozamBeallitoCol.getHobZarDatum());
        szamlaKonyv.setSzfSzaAzon(hozamBeallitoCol.getHobTartSzamla());
        szamlaKonyv.setSzfSzoveg("Hozam");
        szamlaKonyv.setSzfTipus(DomainErtekek.SZLAFORGTIP_HO);
        szamlaKonyv.setSzfHivBizTip(DomainErtekek.BIZTIP_HOZAM);
        szamlaKonyv.setSzfHivBizAzon(hozamBeallitoCol.getHobAzon());
        szamlaKonyv.setSzfTKJel(DomainErtekek.TKJEL_TARTOZIK);
        szamlaKonyv.setSzfOsszeg(Math.abs(hozamBeallitoCol.getHobAdo()));
        this.szamlaKonyveles.konyveles(szamlaKonyv);
        hozamBeallitoCol.setHobKonyvelve(true);
        hozamBeallitoCol.setHobMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.hozamBeallRepo.save(hozamBeallitoCol);
        logger.info("Számlakönyvelt Record: {}", hozamBeallitoCol);
      } else {
        return new MegvaltozottTartalomException("Hozam", "számlakönyvelés");
      }
    }
    return null;
  }

  public Object hozamSzamlaForgTorl(final String id, final String mddat) {
    final Optional<HozamBeallitoCol> hozamBeallitoObj = this.hozamBeallRepo.findById(id);
    if (hozamBeallitoObj.isPresent()) {
      final HozamBeallitoCol hozamBeallitoCol = hozamBeallitoObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (hozamBeallitoCol.getHobMddat().equals(pMddat)) {
        final List<SzamlaForgalomCol> szlaForgTetelek = this.szlaForgRepo.findAllBySzfHivBizTipAndSzfHivBizAzon(
            DomainErtekek.BIZTIP_HOZAM, hozamBeallitoCol.getHobAzon());
        for (final SzamlaForgalomCol szlaForgTetel : szlaForgTetelek) {
          this.szamlaKonyveles.szamlaOsszesenKonyveles(szlaForgTetel.getSzfSzaKod(), szlaForgTetel.getSzfForgDat(),
              szlaForgTetel.getSzfTKJel(), -1 * szlaForgTetel.getSzfOsszeg());
          this.szlaForgRepo.delete(szlaForgTetel);
        }
        hozamBeallitoCol.setHobKonyvelve(false);
        hozamBeallitoCol.setHobMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.hozamBeallRepo.save(hozamBeallitoCol);
        logger.info("Számlakönyvelés törölve Record: {}", hozamBeallitoCol);
      } else {
        return new MegvaltozottTartalomException("Hozam beállítás", "számlakönyvelés törlése");
      }
    }
    return null;
  }
}
