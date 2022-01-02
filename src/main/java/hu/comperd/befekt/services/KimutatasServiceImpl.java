package hu.comperd.befekt.services;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import hu.comperd.befekt.collections.*;
import hu.comperd.befekt.dto.BefektetesEgyenleg;
import hu.comperd.befekt.dto.SzamlaEgyenleg;
import hu.comperd.befekt.etc.DomainCsoportok;
import hu.comperd.befekt.etc.DomainErtekek;
import hu.comperd.befekt.repositories.*;

@Service
public class KimutatasServiceImpl {
  @Autowired
  private BefektEgyenlegCustomRepo     befEgyenRepo;
  @Autowired
  private SzamlaOsszesenRepository     szlaOsszRepo;
  @Autowired
  private SzamlaRepository             szlaRepo;
  @Autowired
  private DomainRepository             domrepo;
  @Autowired
  private SzamlavezetoRepository       szvrepo;
  @Autowired
  private DevizanemRepository          devrepo;
  @Autowired
  private BefektFajtaRepository        bffrepo;
  @Autowired
  private BefektetesArfolyamCustomRepo befArfCustomRepo;
  @Autowired
  private NyitasZarasParokRepository   parokRepo;
  @Autowired
  private BefektZarasRepository        befZarasRepo;
  @Autowired
  private DevizaArfolyamCustomRepo     devArfCustomRepo;

  public List<SzamlaEgyenleg> getSzamlakHaviEgyenleg(final String pHonap) {
    final LocalDate honapVege = this.getHonapUtolsoNapja(pHonap);
    final Collection<SzamlaOsszesenCol> osszesenTetelek = this.findBySzakodAndNotAfterForgDat(honapVege);
    final List<SzamlaEgyenleg> haviEgyenlegek = new ArrayList<>();
    for (final SzamlaOsszesenCol osszesenTetel : osszesenTetelek) {
      if (Math.round(osszesenTetel.getSzoZaro() * 100) == 0) {
        continue;
      }
      final SzamlaEgyenleg haviEgyenleg = new SzamlaEgyenleg();
      haviEgyenleg.setSzeSzaKod(osszesenTetel.getSzoSzaAzon());
      final SzamlaCol szamla = this.szlaRepo.findBySzaKod(osszesenTetel.getSzoSzaAzon());
      haviEgyenleg.setSzeSzaMegnev(szamla.getSzaMegnev());
      haviEgyenleg.setSzeSzaSzlatip(szamla.getSzaSzlatip());
      haviEgyenleg.setSzeSzaSzlatipNev(
          this.domrepo.findByDomCsoportKodAndDomKod(DomainCsoportok.SZLATIP, szamla.getSzaSzlatip()).getDomMegnev());
      haviEgyenleg.setSzeSzaPenzint(szamla.getSzaPenzint());
      haviEgyenleg.setSzeSzaPenzintNev(this.szvrepo.findBySzvKod(szamla.getSzaPenzint()).getSzvMegnev());
      haviEgyenleg.setSzeSzaDeviza(szamla.getSzaDeviza());
      haviEgyenleg.setSzeSzaDevizaNev(this.devrepo.findByDevKod(szamla.getSzaDeviza()).getDevMegnev());
      haviEgyenleg.setSzeEgyenleg(osszesenTetel.getSzoZaro());
      haviEgyenleg.setSzeArfolyam(1);
      if (DomainErtekek.ALAP_DEVIZA.equals(szamla.getSzaDeviza())) {
        haviEgyenleg.setSzeArfolyam(1);
      } else {
        final DevizaArfolyamCol devizaArfolyamCol = this.devArfCustomRepo.findMirolDevKodAndMireDevKodNapiArfolyam(
            szamla.getSzaDeviza(), DomainErtekek.ALAP_DEVIZA, honapVege);
        haviEgyenleg.setSzeArfolyam(devizaArfolyamCol == null ? 0 : devizaArfolyamCol.getDeaArfolyam());
      }
      haviEgyenleg.setSzeArfolyamErtek(haviEgyenleg.getSzeEgyenleg() * haviEgyenleg.getSzeArfolyam());
      haviEgyenlegek.add(haviEgyenleg);
    }
    return haviEgyenlegek;
  }

  public Collection<SzamlaOsszesenCol> findBySzakodAndNotAfterForgDat(final LocalDate pForgdat) {
    final List<SzamlaOsszesenCol> szamlaOsszesenek = this.szlaOsszRepo.findAllByOrderBySzoSzaAzonAsc();
    final SortedMap<String, SzamlaOsszesenCol> joTetelek = new TreeMap<>();
    for (final SzamlaOsszesenCol szamlaOsszesen : szamlaOsszesenek) {
      if (szamlaOsszesen.getSzoForgDat().isAfter(pForgdat)) {
        continue;
      }
      SzamlaOsszesenCol regiTetel = joTetelek.get(szamlaOsszesen.getSzoSzaAzon());
      if (regiTetel == null || regiTetel.getSzoForgDat().isBefore(szamlaOsszesen.getSzoForgDat())) {
        regiTetel = szamlaOsszesen;
        joTetelek.put(regiTetel.getSzoSzaAzon(), regiTetel);
      }
    }
    final List<AggregationOperation> list = new ArrayList<>();
    list.add(Aggregation.match(
        Criteria.where("szoForgDat").lte(Date.from(pForgdat.atStartOfDay(ZoneOffset.UTC).toInstant()))));
    /*
    list.add(Aggregation.group("szoSzaAzon").max("szoForgDat").as("aa"));
    final Aggregation agg = Aggregation.newAggregation(list);
    final List<SzamlaOsszesenCol> datumElottiek = mongoTemplate.aggregate(agg, SzamlaOsszesenCol.class, SzamlaOsszesenCol.class)
        .getMappedResults();
    final TypedAggregation<SzamlaOsszesenCol> agg2 = Aggregation.newAggregation(SzamlaOsszesenCol.class, list);
    final List<SzamlaOsszesenCol> datumElottiek2 = mongoTemplate.aggregate(agg2, SzamlaOsszesenCol.class, SzamlaOsszesenCol.class)
        .getMappedResults();
    */
    return joTetelek.values();
  }

  private LocalDate getHonapUtolsoNapja(final String honap) {
    LocalDate elozonap = LocalDate.parse(honap + "-01");
    while (true) {
      boolean folyt = false;
      final LocalDate kovNap = elozonap.plusDays(1);
      if (elozonap.getYear() == kovNap.getYear() && elozonap.getMonthValue() == kovNap.getMonthValue()) {
        elozonap = kovNap;
        folyt = true;
      }
      if (!folyt) {
        break;
      }
    }
    return elozonap;
  }

  public List<BefektetesEgyenleg> getBefektetesekNapiEgyenleg(final LocalDate lekNap) {
    final Collection<BefektetesCol> osszesenTetelek = this.befEgyenRepo.findAllTetelNyitottAdottNapon(lekNap);
    final SortedMap<String, BefektetesEgyenleg> nyitottBefektetesek = new TreeMap<>();
    for (final BefektetesCol erintettTetel : osszesenTetelek) {
      BefektetesEgyenleg nyitottBefektetes = nyitottBefektetesek.get(erintettTetel.getBefBffKod());
      if (nyitottBefektetes == null) {
        nyitottBefektetes = new BefektetesEgyenleg();
        nyitottBefektetes.setBeeBffKod(erintettTetel.getBefBffKod());
        final BefektFajtaCol befektFajta = this.bffrepo.findByBffKod(erintettTetel.getBefBffKod());
        nyitottBefektetes.setBeeBffKodNev(befektFajta.getBffMegnev());
        final BefektetesArfolyamCol befektetesArfolyam = this.befArfCustomRepo.findBefKodNapiArfolyam(
            erintettTetel.getBefBffKod(), lekNap);
        final double arfolyam = "V".equals(befektFajta.getBffNyitElsz())
            ? erintettTetel.getBefArfolyam()
            : (befektetesArfolyam == null ? 0 : befektetesArfolyam.getBeaArfolyam());
        final LocalDate arfDatum = "V".equals(befektFajta.getBffNyitElsz())
            ? erintettTetel.getBefDatum()
            : (befektetesArfolyam == null ? null : befektetesArfolyam.getBeaArfDatum());
        nyitottBefektetes.setBeeArfDatum(arfDatum);
        nyitottBefektetes.setBeeArfolyam(arfolyam);
        final SzamlaCol szamla = this.szlaRepo.findBySzaKod(befektFajta.getBffSzamla());
        nyitottBefektetes.setBeeSzaDeviza(szamla.getSzaDeviza());
        nyitottBefektetes.setBeeSzaDevizaNev(this.devrepo.findByDevKod(szamla.getSzaDeviza()).getDevMegnev());
        nyitottBefektetes.setBeeDarab(0);
        if (DomainErtekek.ALAP_DEVIZA.equals(szamla.getSzaDeviza())) {
          nyitottBefektetes.setBeeDevArfo(1);
        } else {
          final DevizaArfolyamCol devizaArfolyamCol = this.devArfCustomRepo.findMirolDevKodAndMireDevKodNapiArfolyam(
              szamla.getSzaDeviza(), DomainErtekek.ALAP_DEVIZA, lekNap);
          nyitottBefektetes.setBeeDevArfo(devizaArfolyamCol == null ? 0 : devizaArfolyamCol.getDeaArfolyam());
        }
        nyitottBefektetesek.put(erintettTetel.getBefBffKod(), nyitottBefektetes);
      }
      final double marEladott = this.getAdottNaponMerNemMeglevoDarab(erintettTetel, lekNap);
      nyitottBefektetes.setBeeDarab(nyitottBefektetes.getBeeDarab() + erintettTetel.getBefDarab() - marEladott);
      final double arfErtek = Math.round(
          (erintettTetel.getBefDarab() - marEladott) * nyitottBefektetes.getBeeArfolyam() * 100D) / 100D;
      nyitottBefektetes.setBeeArfErtek(nyitottBefektetes.getBeeArfErtek() + arfErtek);
      nyitottBefektetes.setBeeAlapdevErtek(
          nyitottBefektetes.getBeeAlapdevErtek() + Math.round(arfErtek * nyitottBefektetes.getBeeDevArfo()));
    }
    final List<BefektetesEgyenleg> tetelLista = new ArrayList<>(nyitottBefektetesek.values());
    final List<BefektetesEgyenleg> osszesJoTetel = new ArrayList<>();
    final BefektetesEgyenleg osszesen = new BefektetesEgyenleg();
    osszesJoTetel.add(osszesen);
    osszesen.setBeeBffKodNev("ÖSSZESEN:");
    osszesen.setBeeAlapdevErtek(0);
    for (final BefektetesEgyenleg tetel : tetelLista) {
      if ((double)(Math.round(tetel.getBeeDarab() * 100) / 100) != 0) {
        osszesJoTetel.add(tetel);
      }
      osszesen.setBeeAlapdevErtek(osszesen.getBeeAlapdevErtek() + tetel.getBeeAlapdevErtek());
    }
    return osszesJoTetel;
  }

  private double getAdottNaponMerNemMeglevoDarab(final BefektetesCol befektetesCol, final LocalDate lekNap) {
    double ret = 0;
    final List<NyitasZarasParokCol> nyitasZarasParokCols = this.parokRepo.findByParNyitAzon(befektetesCol.getBefAzon());
    for (final NyitasZarasParokCol nyitasZarasParokCol : nyitasZarasParokCols) {
      final BefektZarasCol befektZarasCol = this.befZarasRepo.findByBezAzon(nyitasZarasParokCol.getParZarAzon());
      if (befektZarasCol != null) {
        //        final LocalDate hasDate = befektZarasCol.getBezKonyvDat() == null ? befektZarasCol.getBezDatum()
        //            : befektZarasCol.getBezKonyvDat();
        final LocalDate hasDate = befektZarasCol.getBezDatum();
        if (lekNap.compareTo(hasDate) >= 0) {
          ret += nyitasZarasParokCol.getParDarab();
        }
      }
    }
    return ret;
  }
}
