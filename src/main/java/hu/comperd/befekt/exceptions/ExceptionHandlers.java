package hu.comperd.befekt.exceptions;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import hu.comperd.befekt.collections.BefektFajtaCol;
import hu.comperd.befekt.collections.BevetelCol;
import hu.comperd.befekt.collections.HavKiadTervCol;
import hu.comperd.befekt.collections.KiadasCol;
import hu.comperd.befekt.collections.TranszferCol;

@ControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

  @ExceptionHandler(DevizanemAlreadyExitException.class)
  public ResponseEntity<Object> handleDevizanemAlreadyExitException(final DevizanemAlreadyExitException ex) {
    final List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    return new ResponseEntity<>("Ez a devizanem már létezik: " + ex.getDevKod(), HttpStatus.ALREADY_REPORTED);
  }

  @ExceptionHandler(DevizanemCannotDeleteException.class)
  public ResponseEntity<Object> handleDevizanemCannotDeleteException(final DevizanemCannotDeleteException ex) {
    final List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    return new ResponseEntity<>("Ez a devizanem szerepel a SZÁMLA állományban: " + ex.getDevKod(), HttpStatus.ALREADY_REPORTED);
  }

  @ExceptionHandler(SzamlavezetoAlreadyExitException.class)
  public ResponseEntity<Object> handleSzamlavezetoAlreadyExitException(final SzamlavezetoAlreadyExitException ex) {
    final List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    return new ResponseEntity<>("Ez a számlavezető már létezik: " + ex.getSzvKod(), HttpStatus.ALREADY_REPORTED);
  }

  @ExceptionHandler(SzamlavezetoCannotDeleteException.class)
  public ResponseEntity<Object> handleSzamlavezetoCannotDeleteException(final SzamlavezetoCannotDeleteException ex) {
    final List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    return new ResponseEntity<>("Ez a számlavezető szerepel a SZÁMLA állományban: " + ex.getSzvKod(), HttpStatus.ALREADY_REPORTED);
  }

  @ExceptionHandler(SzamlaAlreadyExitException.class)
  public ResponseEntity<Object> handleSzamlaAlreadyExitException(final SzamlaAlreadyExitException ex) {
    final List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    return new ResponseEntity<>("Ez a számla már létezik: " + ex.getSzaKod(), HttpStatus.ALREADY_REPORTED);
  }

  @ExceptionHandler(SzamlaCannotDeleteException.class)
  public ResponseEntity<Object> handleSzamlaCannotDeleteException(final SzamlaCannotDeleteException ex) {
    ResponseEntity<Object> ret = null;
    final List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    if (ex.getAnyObject() instanceof TranszferCol) {
      ret = new ResponseEntity<>(
          "Ez a számla szerepel a Transzferek állományában: " + ex.getAnyCode(), HttpStatus.ALREADY_REPORTED);
    } else if (ex.getAnyObject() instanceof HavKiadTervCol) {
      ret = new ResponseEntity<>(
          "Ez a számla szerepel a Havi Kiadás Tervezet állományában: " + ex.getAnyCode(), HttpStatus.ALREADY_REPORTED);
    } else if (ex.getAnyObject() instanceof KiadasCol) {
      ret = new ResponseEntity<>(
          "Ez a számla szerepel a KIADÁS állományában: " + ex.getAnyCode(), HttpStatus.ALREADY_REPORTED);
    } else if (ex.getAnyObject() instanceof BefektFajtaCol) {
      ret = new ResponseEntity<>(
          "Ez a számla szerepel a Befektetés egyedek állományában: " + ex.getAnyCode(), HttpStatus.ALREADY_REPORTED);
    } else if (ex.getAnyObject() instanceof BevetelCol) {
      ret = new ResponseEntity<>(
          "Ez a számla szerepel a BEVÉTEL állományban: " + ex.getAnyCode(), HttpStatus.ALREADY_REPORTED);
    }
    return ret;
  }

  @ExceptionHandler(BefektFajtaAlreadyExitException.class)
  public ResponseEntity<Object> handleBefektFajtaAlreadyExitException(final BefektFajtaAlreadyExitException ex) {
    final List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    return new ResponseEntity<>("Ez a befektetés fajta már létezik: " + ex.getBffKod(), HttpStatus.ALREADY_REPORTED);
  }

  @ExceptionHandler(BefektFajtaCannotDeleteException.class)
  public ResponseEntity<Object> handleBefektFajtaCannotDeleteException(final BefektFajtaCannotDeleteException ex) {
    final List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    return new ResponseEntity<>("Ez a befektetés fajta szerepel a BEFEKTETÉS állományban: " + ex.getBffKod(),
        HttpStatus.ALREADY_REPORTED);
  }

  @ExceptionHandler(HavKiadTervAlreadyExitException.class)
  public ResponseEntity<Object> handleHavKiadTervAlreadyExitException(final HavKiadTervAlreadyExitException ex) {
    final List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    return new ResponseEntity<>("Ez a havi kiadás tervezet tétel már létezik: " + ex.getHktMegnev(), HttpStatus.ALREADY_REPORTED);
  }

  @ExceptionHandler(MegvaltozottTartalomException.class)
  public ResponseEntity<Object> handleMegvaltozottTartalomException(final MegvaltozottTartalomException ex) {
    final List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    return new ResponseEntity<>(
        "A(z) " + ex.getTranzakcio() + " adattartalma közben megváltozott, így a(z) " + ex.getSzoveg() + " nem végrehajtható!",
        HttpStatus.ALREADY_REPORTED);
  }

  @ExceptionHandler(ParositottSzamlaforgalmiTetelException.class)
  public ResponseEntity<Object> handleParositottSzamlaforgalmiTetelException(final ParositottSzamlaforgalmiTetelException ex) {
    final List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    return new ResponseEntity<>(
        "A " + ex.getSzlaForgAzon() + " számlaforgalmi tétel már párosítva lett, így a törlés nem végrehajtható!",
        HttpStatus.ALREADY_REPORTED);
  }
}
