package hu.comperd.befekt.exceptions;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import hu.comperd.befekt.collections.*;
import hu.comperd.befekt.util.Util;

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
    if (ex.getAnyObject() instanceof BefektFajtaCol) {
      ret = new ResponseEntity<>(
          "Ez a számla szerepel a Befektetés egyedek állományában: " + ex.getAnyCode(), HttpStatus.ALREADY_REPORTED);
    } else if (ex.getAnyObject() instanceof BefektZarasCol) {
      ret = new ResponseEntity<>(
          "Ez a számla szerepel a Befektetések zárása állományában: " + ex.getAnyCode(), HttpStatus.ALREADY_REPORTED);
    } else if (ex.getAnyObject() instanceof BefektetesCol) {
      ret = new ResponseEntity<>(
          "Ez a számla szerepel a Befektetések nyitása állományában: " + ex.getAnyCode(), HttpStatus.ALREADY_REPORTED);
    } else if (ex.getAnyObject() instanceof BevetelCol) {
      ret = new ResponseEntity<>(
          "Ez a számla szerepel a BEVÉTEL állományban: " + ex.getAnyCode(), HttpStatus.ALREADY_REPORTED);
    } else if (ex.getAnyObject() instanceof TranszferCol) {
      ret = new ResponseEntity<>(
          "Ez a számla szerepel a Transzferek állományában: " + ex.getAnyCode(), HttpStatus.ALREADY_REPORTED);
    } else if (ex.getAnyObject() instanceof HavKiadTervCol) {
      ret = new ResponseEntity<>(
          "Ez a számla szerepel a Havi Kiadás Tervezet állományában: " + ex.getAnyCode(), HttpStatus.ALREADY_REPORTED);
    } else if (ex.getAnyObject() instanceof KiadasCol) {
      ret = new ResponseEntity<>(
          "Ez a számla szerepel a KIADÁS állományában: " + ex.getAnyCode(), HttpStatus.ALREADY_REPORTED);
    } else if (ex.getAnyObject() instanceof HataridosElszamolasCol) {
      ret = new ResponseEntity<>(
          "Ez a számla szerepel a Határidős Elszámolások állományában: " + ex.getAnyCode(), HttpStatus.ALREADY_REPORTED);
    } else if (ex.getAnyObject() instanceof HozamBeallitoCol) {
      ret = new ResponseEntity<>(
          "Ez a számla szerepel a Hozam Beállítások állományában: " + ex.getAnyCode(), HttpStatus.ALREADY_REPORTED);
    } else if (ex.getAnyObject() instanceof OsztalekCol) {
      ret = new ResponseEntity<>(
          "Ez a számla szerepel az Osztalékok állományában: " + ex.getAnyCode(), HttpStatus.ALREADY_REPORTED);
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

  @ExceptionHandler(KonyvelesiIdoszakLezartException.class)
  public ResponseEntity<Object> handleKonyvelesiIdoszakLezartException(final KonyvelesiIdoszakLezartException ex) {
    final List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    return new ResponseEntity<>(ex.getMessage() + ", így a(z) " + ex.getSzoveg() + " funkció nem végrehajtható!",
        HttpStatus.ALREADY_REPORTED);
  }

  @ExceptionHandler(HonapNemLezarhatoException.class)
  public ResponseEntity<Object> handleHonapNemLezarhatoException(final HonapNemLezarhatoException ex) {
    final List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    return new ResponseEntity<>(ex.isZarase()
        ? "Az előző záráshoz képest csak a rákövetkező hónap zárható le!"
        : "Csak az utoljára lezárt hónap nyitható fel!",
        HttpStatus.ALREADY_REPORTED);
  }

  @ExceptionHandler(VanKonyveletlenTetelException.class)
  public ResponseEntity<Object> handleVanKonyveletlenTetelException(final VanKonyveletlenTetelException ex) {
    final List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    return new ResponseEntity<>("Az adott hónapban van könyveletlen " + ex.getTranzakcio() + ", így nem lezárható!",
        HttpStatus.ALREADY_REPORTED);
  }

  @ExceptionHandler(VanKonyveltGeneraltKiadasException.class)
  public ResponseEntity<Object> handleVanKonyveltGeneraltKiadasException(final VanKonyveltGeneraltKiadasException ex) {
    final List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    return new ResponseEntity<>(ex.getHonap() + " hónap generált tételei között van lekönyvelt, így a törlés nem megengedett",
        HttpStatus.ALREADY_REPORTED);
  }

  @ExceptionHandler(HavKiadTervCannotDeleteException.class)
  public ResponseEntity<Object> handleHavKiadTervCannotDeleteException(final HavKiadTervCannotDeleteException ex) {
    final List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    return new ResponseEntity<>("'" + ex.getHktMegnev() + "' tervezetre már van tényadat könyvelve, így nem törölhető!",
        HttpStatus.ALREADY_REPORTED);
  }

  @ExceptionHandler(HavKiadTervCannotModifyException.class)
  public ResponseEntity<Object> handleHavKiadTervCannotModifyException(final HavKiadTervCannotModifyException ex) {
    final List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    if (Util.isEmpty(ex.getHktDatumIg())) {
      return new ResponseEntity<>("Az érvényesség kezdete nem lehet nagyobb, mint a legkisebb hónap, amire már könyveltek: '"
          + ex.getHktDatumTol() + "'", HttpStatus.ALREADY_REPORTED);
    }
    return new ResponseEntity<>("Az érvényesség vége nem lehet kisebb, mint a legnagyobb hónap, amire már könyveltek: '"
        + ex.getHktDatumIg() + "'", HttpStatus.ALREADY_REPORTED);
  }
}
