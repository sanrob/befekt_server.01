package hu.comperd.befekt.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import hu.comperd.befekt.collections.SzamlaForgalomCol;
import hu.comperd.befekt.collections.SzamlaOsszesenCol;
import hu.comperd.befekt.etc.DomainErtekek;
import hu.comperd.befekt.etc.SzamlaKonyvTmp;
import hu.comperd.befekt.repositories.SzamlaForgalomRepository;
import hu.comperd.befekt.repositories.SzamlaOsszesenCustomRepo;
import hu.comperd.befekt.repositories.SzamlaOsszesenRepository;
import hu.comperd.befekt.util.Util;

@Service
public class SzamlaKonyveles {
  private static final Logger logger = LoggerFactory.getLogger(SzamlaKonyveles.class);

  @Autowired
  SzamlaForgalomRepository    szfForgRepo;
  @Autowired
  SzamlaOsszesenCustomRepo    szfOsszCustomRepo;
  @Autowired
  SzamlaOsszesenRepository    szfOsszRepo;
  @Autowired
  AlapAdatokServiceImpl       alapAdatokService;
  @Autowired
  MongoTemplate               template;

  public void konyveles(final SzamlaKonyvTmp szamlaKonyv) {
    final SzamlaForgalomCol szamlaForgalomCol = new SzamlaForgalomCol();
    final int ev = szamlaKonyv.getSzfForgDat().getYear();
    final int sorszam = this.alapAdatokService.getNextBizSorsz(DomainErtekek.BIZTIP_PENZTAR, ev);
    szamlaForgalomCol.setSzfAzon(
        DomainErtekek.BIZTIP_PENZTAR + szamlaKonyv.getSzfForgDat().getYear() + Util.padl(Integer.toString(sorszam), 4, '0'));
    szamlaForgalomCol.setSzfSzaKod(szamlaKonyv.getSzfSzaAzon());
    szamlaForgalomCol.setSzfSzoveg(szamlaKonyv.getSzfSzoveg());
    szamlaForgalomCol.setSzfForgDat(szamlaKonyv.getSzfForgDat());
    szamlaForgalomCol.setSzfTipus(szamlaKonyv.getSzfTipus());
    szamlaForgalomCol.setSzfHivBizTip(szamlaKonyv.getSzfHivBizTip());
    szamlaForgalomCol.setSzfHivBizAzon(szamlaKonyv.getSzfHivBizAzon());
    szamlaForgalomCol.setSzfTKJel(szamlaKonyv.getSzfTKJel());
    szamlaForgalomCol.setSzfOsszeg(szamlaKonyv.getSzfOsszeg());
    szamlaForgalomCol.setSzfParos(0);
    szamlaForgalomCol.setSzfMddat(ZonedDateTime.now(ZoneId.systemDefault()));
    this.szfForgRepo.save(szamlaForgalomCol);
    logger.info("Created Record: {}", szamlaForgalomCol);
    this.szamlaOsszesenKonyveles(
        szamlaKonyv.getSzfSzaAzon(), szamlaKonyv.getSzfForgDat(), szamlaKonyv.getSzfTKJel(), szamlaKonyv.getSzfOsszeg());
  }

  private SzamlaOsszesenCol getTarolandoRecord(final SzamlaOsszesenCol szamlaOsszesenCol,
                                               final String szamla,
                                               final LocalDate datum,
                                               final double ujNyito,
                                               final String tkjel,
                                               final double osszeg) {
    SzamlaOsszesenCol ret;
    if (szamlaOsszesenCol == null) {
      ret = new SzamlaOsszesenCol();
      ret.setSzoSzaAzon(szamla);
      ret.setSzoForgDat(datum);
      ret.setSzoNyito(ujNyito);
      if ("T".equals(tkjel)) {
        ret.setSzoTartozik(osszeg);
      } else {
        ret.setSzoKovetel(osszeg);
      }
    } else {
      ret = szamlaOsszesenCol;
      ret.setSzoNyito(ujNyito);
      if ("T".equals(tkjel)) {
        ret.setSzoTartozik(ret.getSzoTartozik() + osszeg);
      } else {
        ret.setSzoKovetel(ret.getSzoKovetel() + osszeg);
      }
    }
    ret.setSzoZaro(ret.getSzoNyito() + ret.getSzoTartozik() - ret.getSzoKovetel());
    ret.setSzoMddat(ZonedDateTime.now(ZoneId.systemDefault()));
    return ret;
  }

  public void szamlaOsszesenKonyveles(final String szamla, final LocalDate forgDatum, final String tkJel, final double osszeg) {
    List<SzamlaOsszesenCol> szamlaOsszesenek = this.szfOsszCustomRepo.findAllBySzakodAndNotBeforeForgDat(szamla, forgDatum);
    final SzamlaOsszesenCol tarolando;
    boolean elsoTorles = false;
    if (szamlaOsszesenek.isEmpty()) {
      tarolando = this.getTarolandoRecord(null, szamla, forgDatum, 0, tkJel, osszeg);
    } else {
      final SzamlaOsszesenCol elsoRec = szamlaOsszesenek.get(0);
      if (elsoRec.getSzoForgDat().isBefore(forgDatum)) {
        tarolando = this.getTarolandoRecord(null, szamla, forgDatum, elsoRec.getSzoZaro(), tkJel, osszeg);
        elsoTorles = true;
      } else if (elsoRec.getSzoForgDat().isEqual(forgDatum)) {
        tarolando = this.getTarolandoRecord(elsoRec, szamla, forgDatum, elsoRec.getSzoNyito(), tkJel, osszeg);
        elsoTorles = true;
      } else {
        tarolando = this.getTarolandoRecord(null, szamla, forgDatum, 0, tkJel, osszeg);
      }
    }
    if (Math.round(tarolando.getSzoTartozik() * 100) == 0 && Math.round(tarolando.getSzoKovetel() * 100) == 0) {
      if (tarolando.get_id() != null) {
        this.szfOsszRepo.delete(tarolando);
        logger.info("Deleted Record: {}", tarolando);
      }
    } else {
      this.szfOsszRepo.save(tarolando);
      logger.info("Created Record: {}", tarolando);
    }
    double ujNyito = tarolando.getSzoZaro();
    if (elsoTorles) {
      szamlaOsszesenek = new ArrayList<>(szamlaOsszesenek); //UnsupportedOperationException
      szamlaOsszesenek.remove(0);
    }
    for (final SzamlaOsszesenCol modositando : szamlaOsszesenek) {
      final SzamlaOsszesenCol modositott = this.getTarolandoRecord(modositando, szamla, forgDatum, ujNyito, tkJel, 0);
      if (Math.round(modositott.getSzoTartozik() * 100) == 0 && Math.round(modositott.getSzoKovetel() * 100) == 0) {
        this.szfOsszRepo.delete(modositott);
        logger.info("Deleted Record: {}", modositott);
      } else {
        this.szfOsszRepo.save(modositott);
        logger.info("Modified Record: {}", modositott);
      }
      ujNyito = modositott.getSzoZaro();
    }
  }
}
