package hu.comperd.befekt.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "hu.comperd.befekt.repositories")
public class MongoConfig extends AbstractMongoClientConfiguration {

  private final List<Converter<?, ?>> converters = new ArrayList<>();

  @Override
  protected String getDatabaseName() {
    return "befekt";
  }

  @Override
  public MongoCustomConversions customConversions() {
    converters.add(new ZonedDateTimeReadConverter());
    converters.add(new ZonedDateTimeWriteConverter());
    converters.add(new LocalDateReadConverter());
    converters.add(new LocalDateWriteConverter());
    return new MongoCustomConversions(converters);
  }
}