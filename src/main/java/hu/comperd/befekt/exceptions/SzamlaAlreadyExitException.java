package hu.comperd.befekt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class SzamlaAlreadyExitException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private final String      szaKod;

  public SzamlaAlreadyExitException(final String pSzaKod) {
    super(pSzaKod);
    this.szaKod = pSzaKod;
  }

  public String getSzaKod() {
    return this.szaKod;
  }
}
