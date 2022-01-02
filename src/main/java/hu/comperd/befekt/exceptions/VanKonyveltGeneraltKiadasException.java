package hu.comperd.befekt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class VanKonyveltGeneraltKiadasException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private final String      honap;

  public VanKonyveltGeneraltKiadasException(final String pHonap) {
    this.honap = pHonap;
  }

  public String getHonap() {
    return this.honap;
  }
}
