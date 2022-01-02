package hu.comperd.befekt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class VanKonyveletlenTetelException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private final String      tranzakcio;

  public VanKonyveletlenTetelException(final String pTranzakcio) {
    this.tranzakcio = pTranzakcio;
  }

  public String getTranzakcio() {
    return this.tranzakcio;
  }
}
