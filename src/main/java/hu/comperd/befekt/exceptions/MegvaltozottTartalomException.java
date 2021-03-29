package hu.comperd.befekt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class MegvaltozottTartalomException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private final String      tranzakcio;
  private final String      szoveg;

  public MegvaltozottTartalomException(final String pTranzakcio, final String pSzoveg) {
    super(pTranzakcio + " adattartalma változott!");
    this.tranzakcio = pTranzakcio;
    this.szoveg = pSzoveg;
  }

  public String getTranzakcio() {
    return this.tranzakcio;
  }

  public String getSzoveg() {
    return this.szoveg;
  }
}
