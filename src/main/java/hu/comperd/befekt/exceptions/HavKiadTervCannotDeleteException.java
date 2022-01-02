package hu.comperd.befekt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class HavKiadTervCannotDeleteException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private final String      hktMegnev;

  public HavKiadTervCannotDeleteException(final String pHktMegnev) {
    super(pHktMegnev);
    this.hktMegnev = pHktMegnev;
  }

  public String getHktMegnev() {
    return hktMegnev;
  }
}
