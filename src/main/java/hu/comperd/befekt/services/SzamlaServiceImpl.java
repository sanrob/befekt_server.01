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
import hu.comperd.befekt.dto.Szamla;
import hu.comperd.befekt.etc.DomainCsoportok;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.exceptions.SzamlaAlreadyExitException;
import hu.comperd.befekt.exceptions.SzamlaCannotDeleteException;
import hu.comperd.befekt.repositories.*;

@Service
public class SzamlaServiceImpl {
  private static final Logger           logger = LoggerFactory.getLogger(SzamlaServiceImpl.class);

  @Autowired
  private SzamlaRepository              repository;
  @Autowired
  private DevizanemRepository           devrepo;
  @Autowired
  private SzamlavezetoRepository        szvrepo;
  @Autowired
  private DomainRepository              domrepo;
  @Autowired
  private BevetelRepository             bevrepo;
  @Autowired
  private BefektFajtaRepository         befrepo;
  @Autowired
  private KiadasRepository              kiarepo;
  @Autowired
  private HavKiadTervRepository         hktrepo;
  @Autowired
  private TranszferRepository           trarepo;
  @Autowired
  private BefektZarasRepository         befZarRepo;
  @Autowired
  private BefektetesRepository          befektRepo;
  @Autowired
  private HataridosElszamolasRepository hatElszRepo;
  @Autowired
  private HozamBeallitoRepository       hozBeallRepo;
  @Autowired
  private OsztalekRepository            osztalekRepo;

  public List<Szamla> findAll(final String pSzaSzlatip) {
    final List<SzamlaCol> szamlaCols = this.repository.findBySzaSzlatipOrderBySzaMegnevAsc(pSzaSzlatip);
    final List<Szamla> szamlas = new ArrayList<>();
    for (final SzamlaCol szamlaCol : szamlaCols) {
      final Szamla szamla = new Szamla();
      szamla.setId(szamlaCol.getId());
      szamla.setSzaKod(szamlaCol.getSzaKod());
      szamla.setSzaMegnev(szamlaCol.getSzaMegnev());
      szamla.setSzaSzlatip(szamlaCol.getSzaSzlatip());
      szamla.setSzaSzlatipNev(
          domrepo.findByDomCsoportKodAndDomKod(DomainCsoportok.SZLATIP, szamlaCol.getSzaSzlatip()).getDomMegnev());
      szamla.setSzaPenzint(szamlaCol.getSzaPenzint());
      szamla.setSzaPenzintNev(szvrepo.findBySzvKod(szamlaCol.getSzaPenzint()).getSzvMegnev());
      szamla.setSzaDeviza(szamlaCol.getSzaDeviza());
      szamla.setSzaDevizaNev(devrepo.findByDevKod(szamlaCol.getSzaDeviza()).getDevMegnev());
      szamlas.add(szamla);
    }
    return szamlas;
  }

  public List<Szamla> findAll() {
    final List<SzamlaCol> szamlaCols = this.repository.findAllByOrderBySzaKod();
    final List<Szamla> szamlas = new ArrayList<>();
    for (final SzamlaCol szamlaCol : szamlaCols) {
      final Szamla szamla = new Szamla();
      szamla.setId(szamlaCol.getId());
      szamla.setSzaKod(szamlaCol.getSzaKod());
      szamla.setSzaMegnev(szamlaCol.getSzaMegnev());
      szamla.setSzaSzlatip(szamlaCol.getSzaSzlatip());
      szamla.setSzaSzlatipNev(
          domrepo.findByDomCsoportKodAndDomKod(DomainCsoportok.SZLATIP, szamlaCol.getSzaSzlatip()).getDomMegnev());
      szamla.setSzaPenzint(szamlaCol.getSzaPenzint());
      szamla.setSzaPenzintNev(szvrepo.findBySzvKod(szamlaCol.getSzaPenzint()).getSzvMegnev());
      szamla.setSzaDeviza(szamlaCol.getSzaDeviza());
      szamla.setSzaDevizaNev(devrepo.findByDevKod(szamlaCol.getSzaDeviza()).getDevMegnev());
      szamla.setSzaMddat(szamlaCol.getSzaMddat());
      szamlas.add(szamla);
    }
    return szamlas;
  }

  public Object create(final Szamla szamla) {
    final SzamlaCol szamlaCol = new SzamlaCol(szamla);
    final SzamlaCol szamlaColOld = this.repository.findBySzaKod(szamlaCol.getSzaKod());
    if (szamlaColOld != null) {
      return new SzamlaAlreadyExitException(szamlaColOld.getSzaKod());
    }
    this.repository.save(szamlaCol);
    logger.info("Created Record: {}", szamlaCol);
    return null;
  }

  public Object update(final Szamla szamla) {
    final Optional<SzamlaCol> szamlaObj = this.repository.findById(szamla.getId());
    if (szamlaObj.isPresent()) {
      final SzamlaCol szamlaCol = szamlaObj.get();
      if (szamlaCol.getSzaMddat().toInstant().equals(szamla.getSzaMddat().toInstant())) {
        szamlaCol.setSzaKod(szamla.getSzaKod());
        szamlaCol.setSzaMegnev(szamla.getSzaMegnev());
        szamlaCol.setSzaSzlatip(szamla.getSzaSzlatip());
        szamlaCol.setSzaPenzint(szamla.getSzaPenzint());
        szamlaCol.setSzaDeviza(szamla.getSzaDeviza());
        szamlaCol.setSzaMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.repository.save(szamlaCol);
        logger.info("Updated Record: {}", szamlaCol);
      } else {
        return new MegvaltozottTartalomException("Számla", "módosítás");
      }
    }
    return null;
  }

  public Object delete(final String id, final String mddat) {
    final Optional<SzamlaCol> szamlaObj = this.repository.findById(id);
    if (szamlaObj.isPresent()) {
      final SzamlaCol szamlaCol = szamlaObj.get();
      final Optional<TranszferCol> transzferekTraHova = this.trarepo.findAllByTraHova(szamlaCol.getSzaKod()).stream().findFirst();
      if (transzferekTraHova.isPresent()) {
        return new SzamlaCannotDeleteException(szamlaCol.getSzaKod() + " (Hova)", transzferekTraHova.get());
      }
      final Optional<TranszferCol> transzferekTraHonnan = this.trarepo.findAllByTraHonnan(szamlaCol.getSzaKod()).stream()
          .findFirst();
      if (transzferekTraHonnan.isPresent()) {
        return new SzamlaCannotDeleteException(szamlaCol.getSzaKod() + " (Honnan)", transzferekTraHonnan.get());
      }
      final Optional<OsztalekCol> osztalekok = this.osztalekRepo.findAllByOszAdoSzamla(szamlaCol.getSzaKod()).stream().findFirst();
      if (osztalekok.isPresent()) {
        return new SzamlaCannotDeleteException(szamlaCol.getSzaKod(), osztalekok.get());
      }
      final Optional<KiadasCol> kiadasok = this.kiarepo.findByKiaSzamla(szamlaCol.getSzaKod()).stream().findFirst();
      if (kiadasok.isPresent()) {
        return new SzamlaCannotDeleteException(szamlaCol.getSzaKod(), kiadasok.get());
      }
      final Optional<HozamBeallitoCol> hozBeallTartSzamla = this.hozBeallRepo.findAllByHobTartSzamla(szamlaCol.getSzaKod())
          .stream().findFirst();
      if (hozBeallTartSzamla.isPresent()) {
        return new SzamlaCannotDeleteException(szamlaCol.getSzaKod() + "(HobTartSzamla)", hozBeallTartSzamla.get());
      }
      final Optional<HozamBeallitoCol> hozBeallKovSzamla = this.hozBeallRepo.findAllByHobKovSzamla(szamlaCol.getSzaKod())
          .stream().findFirst();
      if (hozBeallKovSzamla.isPresent()) {
        return new SzamlaCannotDeleteException(szamlaCol.getSzaKod() + "(HobKovSzamla)", hozBeallKovSzamla.get());
      }
      final Optional<HavKiadTervCol> havKiadTervek = this.hktrepo.findAllByHktSzamla(szamlaCol.getSzaKod()).stream().findFirst();
      if (havKiadTervek.isPresent()) {
        return new SzamlaCannotDeleteException(szamlaCol.getSzaKod(), havKiadTervek.get());
      }
      final Optional<HataridosElszamolasCol> hatElszHatHatSzamla = this.hatElszRepo.findAllByHatHatSzamla(szamlaCol.getSzaKod())
          .stream().findFirst();
      if (hatElszHatHatSzamla.isPresent()) {
        return new SzamlaCannotDeleteException(szamlaCol.getSzaKod() + "(HatHatSzamla)", hatElszHatHatSzamla.get());
      }
      final Optional<HataridosElszamolasCol> hatElszHatElszSzamla = this.hatElszRepo.findAllByHatElszSzamla(szamlaCol.getSzaKod())
          .stream().findFirst();
      if (hatElszHatElszSzamla.isPresent()) {
        return new SzamlaCannotDeleteException(szamlaCol.getSzaKod() + "(HatElszSzamla)", hatElszHatElszSzamla.get());
      }
      final Optional<BevetelCol> bevetelekSzamla = this.bevrepo.findByBevSzamla(szamlaCol.getSzaKod()).stream().findFirst();
      if (bevetelekSzamla.isPresent()) {
        return new SzamlaCannotDeleteException(szamlaCol.getSzaKod(), bevetelekSzamla.get());
      }
      final Optional<BefektetesCol> befektetesElszSzla = this.befektRepo.findAllByBefElszSzla(szamlaCol.getSzaKod()).stream()
          .findFirst();
      if (befektetesElszSzla.isPresent()) {
        return new SzamlaCannotDeleteException(szamlaCol.getSzaKod() + "(BefElszSzla)", befektetesElszSzla.get());
      }
      final Optional<BefektetesCol> befektetesJutSzla = this.befektRepo.findAllByBefJutSzla(szamlaCol.getSzaKod()).stream()
          .findFirst();
      if (befektetesJutSzla.isPresent()) {
        return new SzamlaCannotDeleteException(szamlaCol.getSzaKod() + "(BefJutSzla)", befektetesJutSzla.get());
      }
      final Optional<BefektZarasCol> befektZarasElszSzla = this.befZarRepo.findAllByBezElszSzla(szamlaCol.getSzaKod()).stream()
          .findFirst();
      if (befektZarasElszSzla.isPresent()) {
        return new SzamlaCannotDeleteException(szamlaCol.getSzaKod() + "(BezElszSzla)", befektZarasElszSzla.get());
      }
      final Optional<BefektZarasCol> befektZarasJutSzla = this.befZarRepo.findAllByBezJutSzla(szamlaCol.getSzaKod()).stream()
          .findFirst();
      if (befektZarasJutSzla.isPresent()) {
        return new SzamlaCannotDeleteException(szamlaCol.getSzaKod() + "(BezJutSzla)", befektZarasJutSzla.get());
      }
      final Optional<BefektFajtaCol> befektFajtakSzamla = befrepo.findByBffSzamla(szamlaCol.getSzaKod()).stream().findFirst();
      if (befektFajtakSzamla.isPresent()) {
        return new SzamlaCannotDeleteException(szamlaCol.getSzaKod() + "(BffSzamla)", befektFajtakSzamla.get());
      }
      final Optional<BefektFajtaCol> befektFajtakJutSzla = befrepo.findByBffJutSzla(szamlaCol.getSzaKod()).stream().findFirst();
      if (befektFajtakJutSzla.isPresent()) {
        return new SzamlaCannotDeleteException(szamlaCol.getSzaKod() + "(BffJutSzla)", befektFajtakJutSzla.get());
      }
      final ZonedDateTime pMddat = ZonedDateTime.parse(mddat);
      if (szamlaCol.getSzaMddat().equals(pMddat)) {
        this.repository.deleteById(id);
        logger.info("Deleted Record: {}", szamlaCol);
      } else {
        return new MegvaltozottTartalomException("Számla", "törlés");
      }
    }
    return null;
  }
}
