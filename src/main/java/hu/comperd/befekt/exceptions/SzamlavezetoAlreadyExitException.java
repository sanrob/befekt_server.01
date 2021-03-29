package hu.comperd.befekt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class SzamlavezetoAlreadyExitException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private final String      szvKod;

  public SzamlavezetoAlreadyExitException(final String pSzvKod) {
    super(pSzvKod);
    this.szvKod = pSzvKod;
  }

  public String getSzvKod() {
    return this.szvKod;
  }
}
