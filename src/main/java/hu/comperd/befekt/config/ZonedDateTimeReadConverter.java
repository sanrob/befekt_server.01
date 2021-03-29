package hu.comperd.befekt.config;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import org.springframework.core.convert.converter.Converter;

public class ZonedDateTimeReadConverter implements Converter<Date, ZonedDateTime> {

  @Override
  public ZonedDateTime convert(final Date date) {
    return date.toInstant().atZone(ZoneOffset.UTC);
  }
}
