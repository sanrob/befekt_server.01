package hu.comperd.befekt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class DevizanemAlreadyExitException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private final String      devKod;

  public DevizanemAlreadyExitException(final String pDevKod) {
    super(pDevKod);
    this.devKod = pDevKod;
  }

  public String getDevKod() {
    return this.devKod;
  }
}
