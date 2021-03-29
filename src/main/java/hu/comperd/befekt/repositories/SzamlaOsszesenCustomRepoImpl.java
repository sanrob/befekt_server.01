package hu.comperd.befekt.repositories;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.mongodb.BasicDBObject;
import hu.comperd.befekt.collections.SzamlaOsszesenCol;

@Repository
public class SzamlaOsszesenCustomRepoImpl implements SzamlaOsszesenCustomRepo {

  private final MongoTemplate mongoTemplate;

  @Autowired
  public SzamlaOsszesenCustomRepoImpl(final MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public List<SzamlaOsszesenCol> findAllBySzakodAndNotBeforeForgDat(final String pSzakod, final LocalDate pForgdat) {
    final Criteria szamlaTetelei = Criteria.where("szoSzaAzon").is(pSzakod);
    final Criteria datumElottiek = Criteria.where("szoForgDat").lte(Date.from(pForgdat.atStartOfDay(ZoneOffset.UTC).toInstant()));
    final Criteria szamlaDatumElottiTetelei = new Criteria().andOperator(szamlaTetelei, datumElottiek);
    final List<AggregationOperation> list = new ArrayList<>();
    list.add(Aggregation.match(szamlaDatumElottiTetelei));
    list.add(Aggregation.group("szoSzaAzon").max("szoForgDat").as("maxForgDat"));
    final TypedAggregation<BasicDBObject> agg = Aggregation.newAggregation(BasicDBObject.class, list);
    final AggregationResults<BasicDBObject> results = mongoTemplate.aggregate(agg, SzamlaOsszesenCol.class, BasicDBObject.class);
    final List<BasicDBObject> dbObjects = results.getMappedResults();
    LocalDate tolDatum = pForgdat;
    if (!dbObjects.isEmpty()) {
      final Date forgDateElotti = dbObjects.get(0).getDate("maxForgDat");
      tolDatum = forgDateElotti.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    final Criteria nemDatumElottiek = Criteria.where("szoForgDat")
        .gte(Date.from(tolDatum.atStartOfDay(ZoneOffset.UTC).toInstant()));
    final Criteria szamlaNemDatumElottiTetelei = new Criteria().andOperator(szamlaTetelei, nemDatumElottiek);
    list.clear();
    list.add(Aggregation.match(szamlaNemDatumElottiTetelei));
    list.add(Aggregation.sort(Sort.Direction.ASC, "szoForgDat"));
    final TypedAggregation<SzamlaOsszesenCol> newAgg = Aggregation.newAggregation(SzamlaOsszesenCol.class, list);
    return mongoTemplate.aggregate(newAgg, SzamlaOsszesenCol.class, SzamlaOsszesenCol.class).getMappedResults();
  }
}
