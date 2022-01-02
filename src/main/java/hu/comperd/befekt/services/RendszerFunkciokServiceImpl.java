package hu.comperd.befekt.services;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hu.comperd.befekt.collections.BizonylatSorszamCol;
import hu.comperd.befekt.collections.HonapZarasaCol;
import hu.comperd.befekt.collections.KonyvelesiEvCol;
import hu.comperd.befekt.dto.EvvaltInfo;
import hu.comperd.befekt.dto.KonyvelesiEv;
import hu.comperd.befekt.dto.SystemParams;
import hu.comperd.befekt.repositories.AlapAdatokRepository;
import hu.comperd.befekt.repositories.BizonylatSorszamRepository;
import hu.comperd.befekt.repositories.DomainRepository;
import hu.comperd.befekt.repositories.HonapZarasaRepository;

@Service
public class RendszerFunkciokServiceImpl {

  @Autowired
  private AlapAdatokRepository       konyvEvREpo;
  @Autowired
  private BizonylatSorszamRepository bizSorszRepo;
  @Autowired
  private DomainRepository           domainRepo;
  @Autowired
  private HonapZarasaRepository      honapZarRepo;
  @Autowired
  private AlapAdatokServiceImpl      alapAdatokService;

  public List<EvvaltInfo> getEvvaltInfo() {
    final List<EvvaltInfo> evvaltInfo = this.konyvEvREpo.findAllByOrderByKonEvDesc().stream().parallel()
        .map(EvvaltInfo::new).map(this::setEvvaltInfo).collect(Collectors.toList());
    return evvaltInfo;
  }

  private EvvaltInfo setEvvaltInfo(final EvvaltInfo evvaltInfo) {
    evvaltInfo.setBizSorszGen(true);
    evvaltInfo.setKonyvHonapGen(true);
    this.checkForEv(evvaltInfo);
    return evvaltInfo;
  }

  private void checkForEv(final EvvaltInfo evvaltInfo) {
    this.domainRepo.findByDomCsoportKodOrderByDomKodAsc("BIZTIP")
        .forEach(b -> checkForEvAndBiztip(b.getDomKod(), evvaltInfo));
    Arrays.asList(new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"})
        .forEach(h -> checkForEvAndHonap(evvaltInfo, h));
  }

  private void checkForEvAndBiztip(final String bizTipus, final EvvaltInfo evvaltInfo) {
    if (evvaltInfo.isBizSorszGen()) {
      final BizonylatSorszamCol bizonylatSorszamCol = this.bizSorszRepo.findOneByBizTipusAndBizEv(
          bizTipus, Integer.parseInt(evvaltInfo.getKonyvEv()));
      if (bizonylatSorszamCol == null) {
        evvaltInfo.setBizSorszGen(false);
      }
    }
  }

  private void checkForEvAndHonap(final EvvaltInfo pEvvaltInfo, final String pHonap) {
    if (pEvvaltInfo.isKonyvHonapGen()) {
      final HonapZarasaCol honapZarasaCol = this.honapZarRepo.findOneByHozHonap(
          pEvvaltInfo.getKonyvEv() + "-" + pHonap);
      if (honapZarasaCol == null) {
        final SystemParams systemParams = alapAdatokService.findSystemParams();
        if (!(systemParams.getSysStartDate().compareTo(LocalDate.parse(pEvvaltInfo.getKonyvEv() + "-" + pHonap + "-01")) > 0)) {
          pEvvaltInfo.setKonyvHonapGen(false);
        }
      }
    }
  }

  public KonyvelesiEv doEvvaltas(final String newEv) {
    KonyvelesiEvCol konyvelesiEvCol = this.konyvEvREpo.findByKonEv(newEv);
    if (konyvelesiEvCol == null) {
      konyvelesiEvCol = new KonyvelesiEvCol(newEv);
      this.konyvEvREpo.save(konyvelesiEvCol);
      this.setOtherDatas(newEv);
    }
    return new KonyvelesiEv(konyvelesiEvCol);
  }

  private void setOtherDatas(final String ev) {
    this.domainRepo.findByDomCsoportKodOrderByDomKodAsc("BIZTIP").forEach(b -> addNewBizSorsz(b.getDomKod(), Integer.parseInt(ev)));
    Arrays.asList(new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"})
        .forEach(h -> addNewBHonapzaras(ev, h));
  }

  private void addNewBizSorsz(final String bizTipus, final int konyvEv) {
    BizonylatSorszamCol bizonylatSorszamCol = this.bizSorszRepo.findOneByBizTipusAndBizEv(bizTipus, konyvEv);
    if (bizonylatSorszamCol == null) {
      bizonylatSorszamCol = new BizonylatSorszamCol(bizTipus, konyvEv);
      this.bizSorszRepo.save(bizonylatSorszamCol);
    }
  }

  private void addNewBHonapzaras(final String pEv, final String pHonap) {
    HonapZarasaCol honapZarasaCol = this.honapZarRepo.findOneByHozHonap(pEv + "-" + pHonap);
    if (honapZarasaCol == null) {
      final SystemParams systemParams = alapAdatokService.findSystemParams();
      if (!(systemParams.getSysStartDate().compareTo(LocalDate.parse(pEv + "-" + pHonap + "-01")) > 0)) {
        honapZarasaCol = new HonapZarasaCol(pEv + "-" + pHonap);
        this.honapZarRepo.save(honapZarasaCol);
      }
    }
  }

  public EvvaltInfo doEvPotlas(final String pEv) {
    this.setOtherDatas(pEv);
    return null;
  }
}
