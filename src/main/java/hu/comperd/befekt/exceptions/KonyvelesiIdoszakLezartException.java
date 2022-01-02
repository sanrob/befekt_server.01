package hu.comperd.befekt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class KonyvelesiIdoszakLezartException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private final String      idoszak;
  private final String      szoveg;

  public KonyvelesiIdoszakLezartException(final String pIdoszak, final String pSzoveg) {
    super(pIdoszak + " könyvelési időszak már le lett zárva");
    this.idoszak = pIdoszak;
    this.szoveg = pSzoveg;
  }

  public String getIdoszak() {
    return this.idoszak;
  }

  public String getSzoveg() {
    return this.szoveg;
  }
}
