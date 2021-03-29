package hu.comperd.befekt.config;

import java.time.ZonedDateTime;
import java.util.Date;
import org.springframework.core.convert.converter.Converter;

public class ZonedDateTimeWriteConverter implements Converter<ZonedDateTime, Date> {

  @Override
  public Date convert(final ZonedDateTime zonedDateTime) {
    return Date.from(zonedDateTime.toInstant());
  }
}
