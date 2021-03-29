package hu.comperd.befekt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class SzamlaCannotDeleteException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private final String      anyCode;
  private final Object      anyObject;

  public SzamlaCannotDeleteException(final String pAnyCode, final Object pAnyObject) {
    super(pAnyCode);
    this.anyCode = pAnyCode;
    this.anyObject = pAnyObject;
  }

  public String getAnyCode() {
    return this.anyCode;
  }

  public Object getAnyObject() {
    return this.anyObject;
  }
}
