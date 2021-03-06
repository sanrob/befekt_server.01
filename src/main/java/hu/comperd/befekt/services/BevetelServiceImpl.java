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
import hu.comperd.befekt.collections.BevetelCol;
import hu.comperd.befekt.collections.SzamlaForgalomCol;
import hu.comperd.befekt.dto.Bevetel;
import hu.comperd.befekt.etc.DomainErtekek;
import hu.comperd.befekt.etc.SzamlaKonyvTmp;
import hu.comperd.befekt.exceptions.KonyvelesiIdoszakLezartException;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.exceptions.ParositottSzamlaforgalmiTetelException;
import hu.comperd.befekt.repositories.BevetelRepository;
import hu.comperd.befekt.repositories.SzamlaForgalomRepository;
import hu.comperd.befekt.repositories.SzamlaRepository;
import hu.comperd.befekt.util.Util;

@Service
public class BevetelServiceImpl {
  private static final Logger      logger = LoggerFactory.getLogger(BevetelServiceImpl.class);

  @Autowired
  private BevetelRepository        repository;
  @Autowired
  private SzamlaRepository         szarepo;
  @Autowired
  private AlapAdatokServiceImpl    alapAdatokService;
  @Autowired
  private SzamlaKonyveles          szamlaKonyveles;
  @Autowired
  private SzamlaForgalomRepository szlaForgRepo;

  public List<Bevetel> findAll(final String konyvEv) {
    final List<BevetelCol> bevetelCols = this.repository.findAllByBevEvOrderByBevDatumDesc(Integer.parseInt(konyvEv));
    final List<Bevetel> bevetels = new ArrayList<>();
    for (final BevetelCol bevetelCol : bevetelCols) {
      final Bevetel bevetel = new Bevetel();
      bevetel.setId(bevetelCol.getId());
      bevetel.setBevDatum(bevetelCol.getBevDatum());
      bevetel.setBevAzon(bevetelCol.getBevAzon());
      bevetel.setBevSzoveg(bevetelCol.getBevSzoveg());
      bevetel.setBevOsszeg(bevetelCol.getBevOsszeg());
      bevetel.setBevSzamla(bevetelCol.getBevSzamla());
      bevetel.setBevSzamlaNev(szarepo.findBySzaKod(bevetelCol.getBevSzamla()).getSzaMegnev());
      bevetel.setBevSzlaKonyv(bevetelCol.isBevSzlaKonyv());
      bevetel.setBevMddat(bevetelCol.getBevMddat());
      bevetels.add(bevetel);
    }
    return bevetels;
  }

  public Object create(final Bevetel bevetel) {
    if (alapAdatokService.isIdoszakLezart(bevetel.getBevDatum())) {
      return new KonyvelesiIdoszakLezartException(bevetel.getBevDatum().toString().substring(0, 7), "bev??tel f??lvitele");
    }
    final BevetelCol bevetelCol = new BevetelCol(bevetel);
    final int sorszam = this.alapAdatokService.getNextBizSorsz(DomainErtekek.BIZTIP_BEVETEL, bevetel.getBevDatum().getYear());
    bevetelCol.setBevAzon(
        DomainErtekek.BIZTIP_BEVETEL + bevetel.getBevDatum().getYear() + Util.padl(Integer.toString(sorszam), 4, '0'));
    this.repository.save(bevetelCol);
    logger.info("Created Record: {}", bevetelCol);
    return null;
  }

  public Object update(final Bevetel bevetel) {
    if (alapAdatokService.isIdoszakLezart(bevetel.getBevDatum())) {
      return new KonyvelesiIdoszakLezartException(bevetel.getBevDatum().toString().substring(0, 7), "bev??tel m??dos??t??sa");
    }
    final Optional<BevetelCol> bevetelObj = this.repository.findById(bevetel.getId());
    if (bevetelObj.isPresent()) {
      final BevetelCol bevetelCol = bevetelObj.get();
      if (bevetelCol.getBevMddat().toInstant().equals(bevetel.getBevMddat().toInstant())) {
        bevetelCol.setBevSzoveg(bevetel.getBevSzoveg());
        bevetelCol.setBevDatum(bevetel.getBevDatum());
        bevetelCol.setBevOsszeg(bevetel.getBevOsszeg());
        bevetelCol.setBevSzamla(bevetel.getBevSzamla());
        bevetelCol.setBevMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.repository.save(bevetelCol);
        logger.info("Updated Record: {}", bevetelCol);
      } else {
        return new MegvaltozottTartalomException("Bev??tel", "m??dos??t??s");
      }
    }
    return null;
  }

  public Object delete(final String id, final String mddat) {
    final Optional<BevetelCol> bevetelObj = this.repository.findById(id);
    if (bevetelObj.isPresent()) {
      final BevetelCol bevetelCol = bevetelObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (bevetelCol.getBevMddat().equals(pMddat)) {
        this.repository.deleteById(id);
        logger.info("Deleted Record: {}", bevetelCol);
      } else {
        return new MegvaltozottTartalomException("Bev??tel", "t??rl??s");
      }
    }
    return null;
  }

  public Object szamlaForgGen(final String id, final String mddat) {
    final Optional<BevetelCol> bevetelObj = this.repository.findById(id);
    if (bevetelObj.isPresent()) {
      final BevetelCol bevetelCol = bevetelObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (bevetelCol.getBevMddat().equals(pMddat)) {
        final SzamlaKonyvTmp szamlaKonyv = new SzamlaKonyvTmp();
        szamlaKonyv.setSzfForgDat(bevetelCol.getBevDatum());
        szamlaKonyv.setSzfSzaAzon(bevetelCol.getBevSzamla());
        szamlaKonyv.setSzfSzoveg("Bev??tel - " + bevetelCol.getBevSzoveg());
        szamlaKonyv.setSzfTipus(DomainErtekek.SZLAFORGTIP_SB);
        szamlaKonyv.setSzfHivBizTip(DomainErtekek.BIZTIP_BEVETEL);
        szamlaKonyv.setSzfHivBizAzon(bevetelCol.getBevAzon());
        szamlaKonyv.setSzfTKJel(DomainErtekek.TKJEL_TARTOZIK);
        szamlaKonyv.setSzfOsszeg(bevetelCol.getBevOsszeg());
        this.szamlaKonyveles.konyveles(szamlaKonyv);
        bevetelCol.setBevSzlaKonyv(true);
        bevetelCol.setBevMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.repository.save(bevetelCol);
        logger.info("Sz??mlak??nyvelt Record: {}", bevetelCol);
      } else {
        return new MegvaltozottTartalomException("Bev??tel", "sz??mlak??nyvel??s");
      }
    }
    return null;
  }

  public Object szamlaForgTorl(final String id, final String mddat) {
    final Optional<BevetelCol> bevetelObj = this.repository.findById(id);
    if (bevetelObj.isPresent()) {
      final BevetelCol bevetelCol = bevetelObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (!bevetelCol.getBevMddat().equals(pMddat)) {
        return new MegvaltozottTartalomException("Bev??tel", "sz??mlak??nyvel??s t??rl??se");
      }
      if (this.alapAdatokService.isIdoszakLezart(bevetelCol.getBevDatum())) {
        return new KonyvelesiIdoszakLezartException(
            bevetelCol.getBevDatum().toString().substring(0, 7), "bev??tel sz??mlak??nyvel??s??nek t??rl??se");
      }
      final List<SzamlaForgalomCol> szlaForgTetelek = this.szlaForgRepo.findAllBySzfHivBizTipAndSzfHivBizAzon(
          DomainErtekek.BIZTIP_BEVETEL, bevetelCol.getBevAzon());
      String parosAzon = null;
      for (final SzamlaForgalomCol szlaForgTetel : szlaForgTetelek) {
        if (Math.round(szlaForgTetel.getSzfParos() * 100) != 0) {
          parosAzon = szlaForgTetel.getSzfAzon();
        }
      }
      if (parosAzon != null) {
        return new ParositottSzamlaforgalmiTetelException(parosAzon);
      }
      for (final SzamlaForgalomCol szlaForgTetel : szlaForgTetelek) {
        this.szamlaKonyveles.szamlaOsszesenKonyveles(szlaForgTetel.getSzfSzaKod(), szlaForgTetel.getSzfForgDat(),
            szlaForgTetel.getSzfTKJel(), -1 * szlaForgTetel.getSzfOsszeg());
        this.szlaForgRepo.delete(szlaForgTetel);
      }
      bevetelCol.setBevSzlaKonyv(false);
      bevetelCol.setBevMddat(ZonedDateTime.now(ZoneId.systemDefault()));
      this.repository.save(bevetelCol);
      logger.info("Sz??mlak??nyvel??s t??r??lve Record: {}", bevetelCol);
    }
    return null;
  }
}
