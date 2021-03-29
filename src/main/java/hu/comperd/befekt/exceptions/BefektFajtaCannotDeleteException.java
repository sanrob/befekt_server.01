package hu.comperd.befekt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class BefektFajtaCannotDeleteException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private final String      bffKod;

  public BefektFajtaCannotDeleteException(final String pBffKod) {
    super(pBffKod);
    this.bffKod = pBffKod;
  }

  public String getBffKod() {
    return this.bffKod;
  }
}
