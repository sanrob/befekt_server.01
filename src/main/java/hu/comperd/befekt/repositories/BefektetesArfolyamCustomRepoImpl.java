package hu.comperd.befekt.repositories;

import java.time.LocalDate;
import java.time.ZoneId;
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
import com.mongodb.BasicDBObject;
import hu.comperd.befekt.collections.BefektetesArfolyamCol;

@Repository
public class BefektetesArfolyamCustomRepoImpl implements BefektetesArfolyamCustomRepo {

  private final MongoTemplate          mongoTemplate;
  @Autowired
  private BefektetesArfolyamRepository befArfRepo;

  @Autowired
  public BefektetesArfolyamCustomRepoImpl(final MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public BefektetesArfolyamCol findBefKodNapiArfolyam(final String pBffKod, final LocalDate pLekDatum) {
    BefektetesArfolyamCol ret = null;
    final Criteria befektArfolyamai = Criteria.where("beaBefAzon").is(pBffKod);
    final Criteria datumElottiek = Criteria.where("beaArfDatum").lte(Date.from(pLekDatum.atStartOfDay(ZoneOffset.UTC).toInstant()));
    final Criteria befektDatumElottiArfolyamai = new Criteria().andOperator(befektArfolyamai, datumElottiek);
    final List<AggregationOperation> list = new ArrayList<>();
    list.add(Aggregation.match(befektDatumElottiArfolyamai));
    list.add(Aggregation.group("beaBefAzon").max("beaArfDatum").as("maxArfDatum"));
    final TypedAggregation<BasicDBObject> agg = Aggregation.newAggregation(BasicDBObject.class, list);
    final AggregationResults<BasicDBObject> results = mongoTemplate.aggregate(
        agg, BefektetesArfolyamCol.class, BasicDBObject.class);
    final List<BasicDBObject> dbObjects = results.getMappedResults();
    if (!dbObjects.isEmpty()) {
      final Date forgDateElotti = dbObjects.get(0).getDate("maxArfDatum");
      final LocalDate arfDatum = forgDateElotti.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      ret = this.befArfRepo.findByBeaBefAzonAndBeaArfDatum(pBffKod, arfDatum);
    }
    return ret;
  }
}
