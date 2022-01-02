package hu.comperd.befekt.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hu.comperd.befekt.collections.BizonylatSorszamCol;
import hu.comperd.befekt.collections.DomainCol;
import hu.comperd.befekt.collections.HonapZarasaCol;
import hu.comperd.befekt.dto.Domain;
import hu.comperd.befekt.dto.KonyvelesiEv;
import hu.comperd.befekt.dto.SystemParams;
import hu.comperd.befekt.repositories.AlapAdatokRepository;
import hu.comperd.befekt.repositories.BizonylatSorszamRepository;
import hu.comperd.befekt.repositories.DomainRepository;
import hu.comperd.befekt.repositories.HonapZarasaRepository;
import hu.comperd.befekt.repositories.SystemParamsRepository;

@Service
public class AlapAdatokServiceImpl {

  @Autowired
  private AlapAdatokRepository       konyvEvREpo;
  @Autowired
  private BizonylatSorszamRepository bizSorszRepo;
  @Autowired
  private DomainRepository           domainRepo;
  @Autowired
  private SystemParamsRepository     sysParRepo;
  @Autowired
  private HonapZarasaRepository      honZarRepo;

  public List<KonyvelesiEv> findAll() {
    return this.konyvEvREpo.findAllByOrderByKonEvDesc().stream().parallel().map(KonyvelesiEv::new).collect(Collectors.toList());
  }

  public synchronized int getNextBizSorsz(final String bizTipus, final int bizEv) {
    int ret = 0;
    final BizonylatSorszamCol bizonylatSorszamCol = this.bizSorszRepo.findOneByBizTipusAndBizEv(bizTipus, bizEv);
    if (bizonylatSorszamCol != null) {
      bizonylatSorszamCol.setBizSorszam(bizonylatSorszamCol.getBizSorszam() + 1);
      ret = bizonylatSorszamCol.getBizSorszam();
      this.bizSorszRepo.save(bizonylatSorszamCol);
    }
    return ret;
  }

  public List<Domain> findAllDomainByCsoport(final String csoport) {
    final List<DomainCol> domainCols = this.domainRepo.findByDomCsoportKodOrderByDomKodAsc(csoport);
    final List<Domain> domains = new ArrayList<>();
    for (final DomainCol domainCol : domainCols) {
      final Domain domain = new Domain();
      domain.setId(domainCol.getId());
      domain.setDomCsoportKod(domainCol.getDomCsoportKod());
      domain.setDomKod(domainCol.getDomKod());
      domain.setDomMegnev(domainCol.getDomMegnev());
      domains.add(domain);
    }
    return domains;
  }

  public SystemParams findSystemParams() {
    return this.sysParRepo.findAll().stream().map(SystemParams::new).findFirst().get();
  }

  public boolean isIdoszakLezart(final LocalDate datum) {
    final HonapZarasaCol honapZarasaCol = this.honZarRepo.findOneByHozHonap(datum.toString().substring(0, 7));
    return honapZarasaCol == null ? true : honapZarasaCol.isHozZarasJel();
  }
}
