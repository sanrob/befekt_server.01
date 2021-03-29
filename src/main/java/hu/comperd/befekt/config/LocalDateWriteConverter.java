package hu.comperd.befekt.config;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.springframework.core.convert.converter.Converter;

public class LocalDateWriteConverter implements Converter<LocalDate, Date> {

  @Override
  public Date convert(final LocalDate localDate) {
    return Date.from(localDate.atStartOfDay(ZoneOffset.UTC).toInstant());
  }
}
