package hu.comperd.befekt.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.TranszferCol;

public interface TranszferRepository extends MongoRepository<TranszferCol, String> {

  public List<TranszferCol> findAllByTraEvOrderByTraDatumDesc(int traEv);

  public TranszferCol findByTraAzon(String traAzon);

  public List<TranszferCol> findAllByTraHonnan(String traHonnan);

  public List<TranszferCol> findAllByTraHova(String traHova);
}
