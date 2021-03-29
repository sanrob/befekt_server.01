package hu.comperd.befekt.repositories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import hu.comperd.befekt.collections.BefektetesCol;

@Repository
public class BefektEgyenlegCustomRepoImpl implements BefektEgyenlegCustomRepo {

  private final MongoTemplate mongoTemplate;

  @Autowired
  public BefektEgyenlegCustomRepoImpl(final MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public List<BefektetesCol> findAllTetelNyitottAdottNapon(final LocalDate pLekDatum) {
    final List<AggregationOperation> list = new ArrayList<>();
    final Criteria konyvDatumElotti = Criteria.where("befKonyvDat").lte(pLekDatum);
    final Criteria konyvDatumUres = Criteria.where("befKonyvDat").is(null);
    final Criteria bevDatumElotti = Criteria.where("befDatum").lte(pLekDatum);
    //    final Criteria befDatumSzerintJo = new Criteria().andOperator(konyvDatumUres, bevDatumElotti);
    final Criteria befDatumSzerintJo = bevDatumElotti;
    //    final Criteria datumElottKeletkezett = new Criteria().orOperator(konyvDatumElotti, befDatumSzerintJo);
    final Criteria datumElottKeletkezett = befDatumSzerintJo;
    final Criteria nincsLezarva = Criteria.where("befLezDat").is(null);
    final Criteria lezarasDatumUtani = Criteria.where("befLezDat").gt(pLekDatum);
    final Criteria datumUtaniLezaras = new Criteria().orOperator(nincsLezarva, lezarasDatumUtani);
    final Criteria erintettBefektetesek = new Criteria().andOperator(datumElottKeletkezett, datumUtaniLezaras);
    list.add(Aggregation.match(erintettBefektetesek));
    list.add(Aggregation.sort(Sort.Direction.ASC, "befBffKod"));
    final TypedAggregation<BefektetesCol> agg = Aggregation.newAggregation(BefektetesCol.class, list);
    return mongoTemplate.aggregate(agg, BefektetesCol.class, BefektetesCol.class).getMappedResults();
  }
}
