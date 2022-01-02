package hu.comperd.befekt.util;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

public class Util {

  private Util() {
    //
  }

  public static int getYear(final Date date) {
    final Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return cal.get(Calendar.YEAR);
  }

  public static boolean isEmpty(final String value) {
    return value == null ? true : value.trim().length() == 0;
  }

  public static String padl(final String mit, final int hossz, final char mivel) {
    String retstring;
    final String wmit = (isEmpty(mit) ? "" : mit).trim();
    if (hossz < 0) {
      retstring = wmit;
    } else if (hossz <= wmit.length()) {
      retstring = wmit.substring(wmit.length() - hossz, wmit.length());
    } else {
      final StringBuffer sb = new StringBuffer(hossz);
      for (int ind = 0; ind < hossz; ind++) {
        if (ind < hossz - wmit.length()) {
          sb.append(mivel);
        } else {
          sb.append(wmit.charAt(ind - (hossz - wmit.length())));
        }
      }
      retstring = new String(sb);
    }
    return retstring;
  }

  public static LocalDate getLastDayOfMonth(final String honap) {
    final LocalDate firstDay = LocalDate.of(Integer.parseInt(honap.substring(0, 4)), Integer.parseInt(honap.substring(5, 7)), 01);
    return firstDay.plusMonths(1).minusDays(1);
  }
}
