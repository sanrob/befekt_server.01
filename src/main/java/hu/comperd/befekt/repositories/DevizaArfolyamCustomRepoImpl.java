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
import hu.comperd.befekt.collections.DevizaArfolyamCol;

@Repository
public class DevizaArfolyamCustomRepoImpl implements DevizaArfolyamCustomRepo {

  private final MongoTemplate      mongoTemplate;
  @Autowired
  private DevizaArfolyamRepository devArfRepo;

  @Autowired
  public DevizaArfolyamCustomRepoImpl(final MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public DevizaArfolyamCol findMirolDevKodAndMireDevKodNapiArfolyam(final String pMirolDevKod,
                                                                    final String pMireDevKod,
                                                                    final LocalDate pLekDatum) {
    DevizaArfolyamCol ret = null;
    final Criteria devizaParok = Criteria.where("deaMirolDevKod").is(pMirolDevKod)
        .andOperator(Criteria.where("deaMireDevKod").is(pMireDevKod));
    final Criteria datumElottiek = Criteria.where("deaArfDatum").lte(Date.from(pLekDatum.atStartOfDay(ZoneOffset.UTC).toInstant()));
    final Criteria devizaParokDatumElottiArfolyamai = new Criteria().andOperator(devizaParok, datumElottiek);
    final List<AggregationOperation> list = new ArrayList<>();
    list.add(Aggregation.match(devizaParokDatumElottiArfolyamai));
    list.add(Aggregation.group("deaMirolDevKod", "deaMireDevKod").max("deaArfDatum").as("maxArfDatum"));
    final TypedAggregation<BasicDBObject> agg = Aggregation.newAggregation(BasicDBObject.class, list);
    final AggregationResults<BasicDBObject> results = mongoTemplate.aggregate(
        agg, DevizaArfolyamCol.class, BasicDBObject.class);
    final List<BasicDBObject> dbObjects = results.getMappedResults();
    if (!dbObjects.isEmpty()) {
      final Date forgDateElotti = dbObjects.get(0).getDate("maxArfDatum");
      final LocalDate arfDatum = forgDateElotti.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      ret = this.devArfRepo.findByDeaMirolDevKodAndDeaMireDevKodAndDeaArfDatum(pMirolDevKod, pMireDevKod, arfDatum);
    }
    return ret;
  }
}
