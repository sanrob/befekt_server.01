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
import hu.comperd.befekt.collections.SzamlaForgalomCol;
import hu.comperd.befekt.collections.TranszferCol;
import hu.comperd.befekt.dto.Transzfer;
import hu.comperd.befekt.etc.DomainCsoportok;
import hu.comperd.befekt.etc.DomainErtekek;
import hu.comperd.befekt.etc.SzamlaKonyvTmp;
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
    final List<TranszferCol> transzferCols = this.repository.findAllByTraEvOrderByTraDatumDesc(Integer.parseInt(konyvEv));
    final List<Transzfer> transzfers = new ArrayList<>();
    for (final TranszferCol transzferCol : transzferCols) {
      final Transzfer transzfer = new Transzfer();
      transzfer.setId(transzferCol.getId());
      transzfer.setTraAzon(transzferCol.getTraAzon() == null ? "" : transzferCol.getTraAzon());
      transzfer.setTraHonnan(transzferCol.getTraHonnan());
      transzfer.setTraHonnanNev(szarepo.findBySzaKod(transzferCol.getTraHonnan()).getSzaMegnev());
      transzfer.setTraHova(transzferCol.getTraHova());
      transzfer.setTraHovaNev(szarepo.findBySzaKod(transzferCol.getTraHova()).getSzaMegnev());
      transzfer.setTraLeiras(transzferCol.getTraLeiras());
      transzfer.setTraDatum(transzferCol.getTraDatum());
      transzfer.setTraTerheles(transzferCol.getTraTerheles());
      transzfer.setTraArfolyam(transzferCol.getTraArfolyam());
      transzfer.setTraJovairas(transzferCol.getTraJovairas());
      transzfer.setTraJutalek(transzferCol.getTraJutalek());
      transzfer.setTraJutKonyv(transzferCol.getTraJutKonyv());
      transzfer.setTraJutKonyvNev(
          domrepo.findByDomCsoportKodAndDomKod(DomainCsoportok.TRAJUTKON, transzferCol.getTraJutKonyv()).getDomMegnev());
      transzfer.setTraSzamlaKonyv(transzferCol.isTraSzamlaKonyv());
      transzfer.setTraMddat(transzferCol.getTraMddat());
      transzfers.add(transzfer);
    }
    return transzfers;
  }

  public Object create(final Transzfer transzfer) {
    final TranszferCol transzferCol = new TranszferCol(transzfer);
    final int sorszam = this.alapAdatokService.getNextBizSorsz(DomainErtekek.BIZTIP_TRANSZFER, transzfer.getTraDatum().getYear());
    transzferCol.setTraAzon(
        DomainErtekek.BIZTIP_TRANSZFER + transzfer.getTraDatum().getYear() + Util.padl(Integer.toString(sorszam), 4, '0'));
    this.repository.save(transzferCol);
    logger.info("Created Record: {}", transzferCol);
    return null;
  }

  public Object update(final Transzfer transzfer) {
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
        return new MegvaltozottTartalomException("Transzfer", "módosítás");
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
        return new MegvaltozottTartalomException("Transzfer", "törlés");
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
        logger.info("Számlakönyvelt Record: {}", transzferCol);
      } else {
        return new MegvaltozottTartalomException("Transzfer", "számlakönyvelés");
      }
    }
    return null;
  }

  public Object szamlaForgTorl(final String id, final String mddat) {
    final Optional<TranszferCol> transzferObj = this.repository.findById(id);
    if (transzferObj.isPresent()) {
      final TranszferCol transzferCol = transzferObj.get();
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (transzferCol.getTraMddat().equals(pMddat)) {
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
          logger.info("Számlakönyvelés törölve Record: {}", transzferCol);
        } else {
          return new ParositottSzamlaforgalmiTetelException(parosAzon);
        }
      } else {
        return new MegvaltozottTartalomException("Transzfer", "számlakönyvelés törlése");
      }
    }
    return null;
  }
}
