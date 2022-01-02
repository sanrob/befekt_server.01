package hu.comperd.befekt.services;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hu.comperd.befekt.collections.HonapZarasaCol;
import hu.comperd.befekt.dto.HonapZarasa;
import hu.comperd.befekt.exceptions.HonapNemLezarhatoException;
import hu.comperd.befekt.exceptions.MegvaltozottTartalomException;
import hu.comperd.befekt.exceptions.VanKonyveletlenTetelException;
import hu.comperd.befekt.repositories.*;

@Service
public class HonapZarasaServiceImpl {
  private static final Logger   logger = LoggerFactory.getLogger(HavKiadTenyServiceImpl.class);
  @Autowired
  private HonapZarasaRepository honZarRepo;
  @Autowired
  private HonapZarasaCustomRepo repository;
  @Autowired
  private BevetelRepository     bevetelRepo;
  @Autowired
  private TranszferRepository   transzferRepo;
  @Autowired
  private BefektetesRepository  befektRepo;
  @Autowired
  private BefektZarasRepository befZarasRepo;
  @Autowired
  private KiadasRepository      kiadasRepo;

  public List<HonapZarasa> findAll(final String hozEv) {
    return this.repository.findAllByHozEv(hozEv).stream().map(HonapZarasa::new).collect(Collectors.toList());
  }

  public Object zarasNyitas(final HonapZarasa honapZarasa) {
    final Optional<HonapZarasaCol> honapZarasaObj = this.honZarRepo.findById(honapZarasa.getId());
    if (honapZarasaObj.isPresent()) {
      final HonapZarasaCol honapZarasaCol = honapZarasaObj.get();
      if (honapZarasaCol.getHozMddat().withZoneSameInstant(ZoneId.of("Europe/Budapest")).toLocalDateTime()
          .equals(honapZarasa.getHozMddat())) {
        if (honapZarasaCol.isHozZarasJel()) {
          final String nextMonth = this.addOrSubtractMonth(honapZarasa.getHozHonap(), 1);
          final HonapZarasaCol nextObj = this.honZarRepo.findOneByHozHonap(nextMonth);
          if (nextObj != null) {
            if (nextObj.isHozZarasJel()) {
              return new HonapNemLezarhatoException(false);
            }
          }
        } else {
          final String nextMonth = this.addOrSubtractMonth(honapZarasa.getHozHonap(), -1);
          final HonapZarasaCol nextObj = this.honZarRepo.findOneByHozHonap(nextMonth);
          if (nextObj != null) {
            if (!nextObj.isHozZarasJel()) {
              return new HonapNemLezarhatoException(true);
            }
          }
          long konyveletlen = this.bevetelRepo.findAllByBevEvAndBevSzlaKonyv(
              Integer.parseInt(honapZarasa.getHozHonap().substring(0, 4)), false)
              .stream().filter(bev -> bev.getBevDatum().toString().substring(0, 7).equals(honapZarasa.getHozHonap())).count();
          if (konyveletlen > 0) {
            return new VanKonyveletlenTetelException("Bevétel");
          }
          konyveletlen = this.transzferRepo.findAllByTraEvAndTraSzamlaKonyv(
              Integer.parseInt(honapZarasa.getHozHonap().substring(0, 4)), false)
              .stream().filter(tra -> tra.getTraDatum().toString().substring(0, 7).equals(honapZarasa.getHozHonap())).count();
          if (konyveletlen > 0) {
            return new VanKonyveletlenTetelException("Transzfer");
          }
          konyveletlen = this.befektRepo.findAllByBefEvAndBefKonyvelve(
              Integer.parseInt(honapZarasa.getHozHonap().substring(0, 4)), false)
              .stream().filter(bef -> bef.getBefKonyvDat().toString().substring(0, 7).equals(honapZarasa.getHozHonap())).count();
          if (konyveletlen > 0) {
            return new VanKonyveletlenTetelException("Befektetés");
          }
          konyveletlen = this.befZarasRepo.findAllByBezEvAndBezKonyvelve(
              Integer.parseInt(honapZarasa.getHozHonap().substring(0, 4)), false)
              .stream().filter(bez -> bez.getBezKonyvDat().toString().substring(0, 7).equals(honapZarasa.getHozHonap())).count();
          if (konyveletlen > 0) {
            return new VanKonyveletlenTetelException("Befektetés zárás");
          }
          konyveletlen = this.kiadasRepo.findAllByKiaEvAndKiaSzlaKonyv(
              Integer.parseInt(honapZarasa.getHozHonap().substring(0, 4)), false)
              .stream().filter(kia -> kia.getKiaDatum().toString().substring(0, 7).equals(honapZarasa.getHozHonap())).count();
          if (konyveletlen > 0) {
            return new VanKonyveletlenTetelException("Kiadás tétel");
          }
        }
        honapZarasaCol.setHozZarasJel(!honapZarasaCol.isHozZarasJel());
        honapZarasaCol.setHozMddat(ZonedDateTime.now(ZoneId.systemDefault()));
        this.honZarRepo.save(honapZarasaCol);
        logger.info("Updated Record: {}", honapZarasaCol);
      } else {
        return new MegvaltozottTartalomException("Hónap zárása", "zárás/nyitás");
      }
    }
    return null;
  }

  private String addOrSubtractMonth(final String currMonth, final int tag) {
    final int work = Integer.parseInt(currMonth.substring(0, 4)) * 12 + Integer.parseInt(currMonth.substring(5, 7)) + tag;
    int ev = work / 12;
    int ho = work % 12;
    if (ho == 0) {
      ev--;
      ho = 12;
    }
    return Integer.toString(ev) + "-" + StringUtils.leftPad(Integer.toString(ho), 2, '0');
  }
}
