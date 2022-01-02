package hu.comperd.befekt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class HonapNemLezarhatoException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private final boolean     zarase;

  public HonapNemLezarhatoException(final boolean pZarase) {
    this.zarase = pZarase;
  }

  public boolean isZarase() {
    return this.zarase;
  }
}
