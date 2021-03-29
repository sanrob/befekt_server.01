package hu.comperd.befekt.config;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.core.convert.converter.Converter;

public class LocalDateReadConverter implements Converter<Date, LocalDate> {

  @Override
  public LocalDate convert(final Date date) {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }
}
