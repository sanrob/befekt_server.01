package hu.comperd.befekt.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hu.comperd.befekt.collections.BizonylatSorszamCol;
import hu.comperd.befekt.collections.DomainCol;
import hu.comperd.befekt.collections.KonyvelesiEvCol;
import hu.comperd.befekt.dto.Domain;
import hu.comperd.befekt.dto.KonyvelesiEv;
import hu.comperd.befekt.repositories.AlapAdatokRepository;
import hu.comperd.befekt.repositories.BizonylatSorszamRepository;
import hu.comperd.befekt.repositories.DomainRepository;

@Service
public class AlapAdatokServiceImpl {

  @Autowired
  private AlapAdatokRepository       konyvEvREpo;
  @Autowired
  private BizonylatSorszamRepository bizSorszRepo;
  @Autowired
  private DomainRepository           domainRepo;

  public List<KonyvelesiEv> findAll() {
    final List<KonyvelesiEvCol> konyvelesiEvCols = this.konyvEvREpo.findAllByOrderByKonEvDesc();
    final List<KonyvelesiEv> konyvelesiEvek = new ArrayList<>();
    for (final KonyvelesiEvCol konyvelesiEvCol : konyvelesiEvCols) {
      final KonyvelesiEv konyvelesiEv = new KonyvelesiEv();
      konyvelesiEv.setId(konyvelesiEvCol.getId());
      konyvelesiEv.setKonEv(konyvelesiEvCol.getKonEv());
      konyvelesiEvek.add(konyvelesiEv);
    }
    return konyvelesiEvek;
  }

  public synchronized int getNextBizSorsz(final String bizTipus, final int bizEv) {
    BizonylatSorszamCol bizonylatSorszamCol = null;
    int ret = 0;
    final List<BizonylatSorszamCol> bizonylatSorszamok = this.bizSorszRepo.findOneByBizTipusAndBizEv(bizTipus, bizEv);
    if (!bizonylatSorszamok.isEmpty()) {
      bizonylatSorszamCol = bizonylatSorszamok.get(0);
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
}
