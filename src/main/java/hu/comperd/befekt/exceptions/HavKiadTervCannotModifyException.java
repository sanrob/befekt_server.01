package hu.comperd.befekt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class HavKiadTervCannotModifyException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private final String      hktMegnev;
  private final String      hktDatumTol;
  private final String      hktDatumIg;

  public HavKiadTervCannotModifyException(final String pHktMegnev, final String pHktDatumTol, final String pHktDatumIg) {
    super(pHktMegnev);
    this.hktMegnev = pHktMegnev;
    this.hktDatumTol = pHktDatumTol;
    this.hktDatumIg = pHktDatumIg;
  }

  public String getHktMegnev() {
    return this.hktMegnev;
  }

  public String getHktDatumTol() {
    return this.hktDatumTol;
  }

  public String getHktDatumIg() {
    return this.hktDatumIg;
  }
}
