package hu.comperd.befekt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class DevizanemCannotDeleteException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private final String      devKod;

  public DevizanemCannotDeleteException(final String pDevKod) {
    super(pDevKod);
    this.devKod = pDevKod;
  }

  public String getDevKod() {
    return this.devKod;
  }
}
