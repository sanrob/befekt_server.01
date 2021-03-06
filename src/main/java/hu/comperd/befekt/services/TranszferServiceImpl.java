package hu.comperd.befekt.services;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hu.comperd.befekt.collections.SzamlaForgalomCol;
import hu.comperd.befekt.collections.TranszferCol;
import hu.comperd.befekt.dto.Transzfer;
import hu.comperd.befekt.etc.DomainErtekek;
import hu.comperd.befekt.etc.SzamlaKonyvTmp;
import hu.comperd.befekt.exceptions.KonyvelesiIdoszakLezartException;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.exceptions.ParositottSzamlaforgalmiTetelException;
import hu.comperd.befekt.repositories.DomainRepository;
import hu.comperd.befekt.repositories.SzamlaForgalomRepository;
import hu.comperd.befekt.repositories.SzamlaRepository;
import hu.comperd.befekt.repositories.TranszferRepository;
import hu.comperd.befekt.util.Util;

@Service
public class TranszferServiceImpl {
  private static final Logger      logger            = LoggerFactory.getLogger(TranszferServiceImpl.class);

  @Autowired
  private TranszferRepository      repository;
  @Autowired
  private SzamlaRepository         szarepo;
  @Autowired
  AlapAdatokServiceImpl            alapAdatokService = null;
  @Autowired
  private SzamlaKonyveles          szamlaKonyveles;
  @Autowired
  private SzamlaForgalomRepository szlaForgRepo;
  @Autowired
  private DomainRepository         domrepo;

  public List<Transzfer> findAll(final String konyvEv) {
    return this.repository.findAllByTraEvOrderByTraDatumDesc(Integer.parseInt(konyvEv))
        .stream().parallel().map(Transzfer::new).map(transzfer -> transzfer.setDatas(this.szarepo, this.domrepo))
        .collect(Collectors.toList());
  }

  public Object create(final Transzfer transzfer) {
    if (alapAdatokService.isIdoszakLezart(transzfer.getTraDatum())) {
      return new KonyvelesiIdoszakLezartException(transzfer.getTraDatum().toString().substring(0, 7), "transzfer f??lvitele");
    }
    final TranszferCol transzferCol = new TranszferCol(transzfer);
    final int sorszam = this.alapAdatokService.getNextBizSorsz(DomainErtekek.BIZTIP_TRANSZFER, transzfer.getTraDatum().getYear());
    transzferCol.setTraAzon(
        DomainErtekek.BIZTIP_TRANSZFER + transzfer.getTraDatum().getYear() + Util.padl(Integer.toString(sorszam), 4, '0'));
    this.repository.save(transzferCol);
    logger.info("Created Record: {}", transzferCol);
    return null;
  }

  public Object update(final Transzfer transzfer) {
    if (alapAdatokService.isIdoszakLezart(transzfer.getTraDatum())) {
      return new KonyvelesiIdoszakLezartException(transzfer.getTraDatum().toString().substring(0, 7), "transzfer m??dos??t??sa");
    }
    final Optional<TranszferCol> transzferObj = this.repository.findById(transzfer.getId());
    if (transzferObj.isPresent()) {
      final TranszferCol transzferCol = transzferObj.get();
      if (transzferCol.getTraMddat().toInstant().equals(transzfer.getTraMddat().toInstant())) {
        transzferCol.setTraHonnan(transzfer.getTraHonnan());
        transzferCol.setTraHova(transzfer.getTraHova());
        transzferCol.setTraLeiras(transzfer.getTraLeiras());
        transzferCol.setTraDatum(transzfer.getTraDatum());
        transzferCol.setTraTerheles(transzfer.getTraTerheles());
        transzferCol.setTraArfolyam(transzfer.getTraArfolyam());
        transzferCol.setTraJovairas(transzfer.getTraJovairas());
        transzferCol.setTraJutalek(transzfer.getTraJutalek());
        transzferCol.setTraJutKonyv(transzfer.getTraJutKonyv());
        transzferCol.setTraMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.repository.save(transzferCol);
        logger.info("Updated Record: {}", transzferCol);
      } else {
        return new MegvaltozottTartalomException("Transzfer", "m??dos??t??s");
      }
    }
    return null;
  }

  public Object delete(final String id, final String mddat) {
    final Optional<TranszferCol> transzferObj = this.repository.findById(id);
    if (transzferObj.isPresent()) {
      final TranszferCol transzferCol = transzferObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (transzferCol.getTraMddat().equals(pMddat)) {
        this.repository.deleteById(id);
        logger.info("Deleted Record: {}", transzferCol);
      } else {
        return new MegvaltozottTartalomException("Transzfer", "t??rl??s");
      }
    }
    return null;
  }

  public Object szamlaForgGen(final String id, final String mddat) {
    final Optional<TranszferCol> transzferObj = this.repository.findById(id);
    if (transzferObj.isPresent()) {
      final TranszferCol transzferCol = transzferObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (transzferCol.getTraMddat().equals(pMddat)) {
        SzamlaKonyvTmp szamlaKonyv = new SzamlaKonyvTmp();
        szamlaKonyv.setSzfForgDat(transzferCol.getTraDatum());
        szamlaKonyv.setSzfSzaAzon(transzferCol.getTraHonnan());
        szamlaKonyv.setSzfSzoveg("Transzfer - " + transzferCol.getTraLeiras());
        szamlaKonyv.setSzfTipus(DomainErtekek.SZLAFORGTIP_ST);
        szamlaKonyv.setSzfHivBizTip(DomainErtekek.BIZTIP_TRANSZFER);
        szamlaKonyv.setSzfHivBizAzon(transzferCol.getTraAzon());
        szamlaKonyv.setSzfTKJel(DomainErtekek.TKJEL_KOVETEL);
        szamlaKonyv.setSzfOsszeg(transzferCol.getTraTerheles());
        this.szamlaKonyveles.konyveles(szamlaKonyv);
        szamlaKonyv = new SzamlaKonyvTmp();
        szamlaKonyv.setSzfForgDat(transzferCol.getTraDatum());
        szamlaKonyv.setSzfSzaAzon(transzferCol.getTraHova());
        szamlaKonyv.setSzfSzoveg("Transzfer - " + transzferCol.getTraLeiras());
        szamlaKonyv.setSzfTipus(DomainErtekek.SZLAFORGTIP_ST);
        szamlaKonyv.setSzfHivBizTip(DomainErtekek.BIZTIP_TRANSZFER);
        szamlaKonyv.setSzfHivBizAzon(transzferCol.getTraAzon());
        szamlaKonyv.setSzfTKJel(DomainErtekek.TKJEL_TARTOZIK);
        szamlaKonyv.setSzfOsszeg(transzferCol.getTraJovairas());
        this.szamlaKonyveles.konyveles(szamlaKonyv);
        if (Math.round(transzferCol.getTraJutalek() * 100) / 100 != 0) {
          szamlaKonyv = new SzamlaKonyvTmp();
          szamlaKonyv.setSzfForgDat(transzferCol.getTraDatum());
          final String szamla = DomainErtekek.TRAJUTKON_HONNAN.equals(transzferCol.getTraJutKonyv())
              ? transzferCol.getTraHonnan()
              : transzferCol.getTraHova();
          szamlaKonyv.setSzfSzaAzon(szamla);
          szamlaKonyv.setSzfSzoveg("Transzfer - " + transzferCol.getTraLeiras());
          szamlaKonyv.setSzfTipus(DomainErtekek.SZLAFORGTIP_JU);
          szamlaKonyv.setSzfHivBizTip(DomainErtekek.BIZTIP_TRANSZFER);
          szamlaKonyv.setSzfHivBizAzon(transzferCol.getTraAzon());
          szamlaKonyv.setSzfTKJel(DomainErtekek.TKJEL_KOVETEL);
          szamlaKonyv.setSzfOsszeg(transzferCol.getTraJutalek());
          this.szamlaKonyveles.konyveles(szamlaKonyv);
        }
        transzferCol.setTraSzamlaKonyv(true);
        transzferCol.setTraMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.repository.save(transzferCol);
        logger.info("Sz??mlak??nyvelt Record: {}", transzferCol);
      } else {
        return new MegvaltozottTartalomException("Transzfer", "sz??mlak??nyvel??s");
      }
    }
    return null;
  }

  public Object szamlaForgTorl(final String id, final String mddat) {
    final Optional<TranszferCol> transzferObj = this.repository.findById(id);
    if (transzferObj.isPresent()) {
      final TranszferCol transzferCol = transzferObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (!transzferCol.getTraMddat().equals(pMddat)) {
        return new MegvaltozottTartalomException("Transzfer", "sz??mlak??nyvel??s t??rl??se");
      }
      if (this.alapAdatokService.isIdoszakLezart(transzferCol.getTraDatum())) {
        return new KonyvelesiIdoszakLezartException(
            transzferCol.getTraDatum().toString().substring(0, 7), "transzfer sz??mlak??nyvel??s??nek t??rl??se");
      }
      final List<SzamlaForgalomCol> szlaForgTetelek = this.szlaForgRepo.findAllBySzfHivBizTipAndSzfHivBizAzon(
          DomainErtekek.BIZTIP_TRANSZFER, transzferCol.getTraAzon());
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
        transzferCol.setTraSzamlaKonyv(false);
        transzferCol.setTraMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.repository.save(transzferCol);
        logger.info("Sz??mlak??nyvel??s t??r??lve Record: {}", transzferCol);
      } else {
        return new ParositottSzamlaforgalmiTetelException(parosAzon);
      }
    }
    return null;
  }
}
