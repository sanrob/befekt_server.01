package hu.comperd.befekt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class ParositottSzamlaforgalmiTetelException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private final String      szlaForgAzon;

  public ParositottSzamlaforgalmiTetelException(final String pSzlaForgAzon) {
    super(pSzlaForgAzon + " számlaforgalmi tétel párosítva lett!");
    this.szlaForgAzon = pSzlaForgAzon;
  }

  public String getSzlaForgAzon() {
    return this.szlaForgAzon;
  }
}
