package hu.comperd.befekt.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import hu.comperd.befekt.collections.BizonylatSorszamCol;

public interface BizonylatSorszamRepository extends MongoRepository<BizonylatSorszamCol, String>  {

	public List<BizonylatSorszamCol> findOneByBizTipusAndBizEv(String bizTipus, int bizEv);
}
