package hu.comperd.befekt.repositories;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import hu.comperd.befekt.collections.BefektetesCol;

@Repository
public class BefektetesCustomRepoImpl implements BefektetesCustomRepo {

  private final MongoTemplate mongoTemplate;

  @Autowired
  public BefektetesCustomRepoImpl(final MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public List<BefektetesCol> findAllByBffKodDatumElottNyitott(final String pBffKod, final LocalDate pLekDatum) {
    final Criteria befektTetelei = Criteria.where("befBffKod").is(pBffKod);
    final Criteria datumElottiek = Criteria.where("befDatum").lte(Date.from(pLekDatum.atStartOfDay(ZoneOffset.UTC).toInstant()));
    //    final Criteria nyitottak = Criteria.where("befDarab").gt("$befParDarab");
    final Criteria befektDatumElottiNyitottTetelei = new Criteria().andOperator(befektTetelei, datumElottiek);
    final List<AggregationOperation> list = new ArrayList<>();
    list.add(Aggregation.match(befektDatumElottiNyitottTetelei));
    final TypedAggregation<BefektetesCol> agg = Aggregation.newAggregation(BefektetesCol.class, list);
    final AggregationResults<BefektetesCol> results = mongoTemplate.aggregate(
        agg, BefektetesCol.class, BefektetesCol.class);
    return results.getMappedResults();
  }
}
