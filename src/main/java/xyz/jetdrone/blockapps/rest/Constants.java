package xyz.jetdrone.blockapps.rest;

import io.vertx.codegen.annotations.VertxGen;

import java.math.BigDecimal;

import static java.math.BigDecimal.TEN;

@VertxGen
public enum Constants {

  ETHER(18),
  FINNEY(15),
  SZABO(12),
  GWEI(9),
  MWEI(6),
  KWEI(3);

  private final BigDecimal value;

  Constants(int exp) {
    value = TEN.pow(exp);
  }

  public BigDecimal value() {
    return value;
  }

  public static String formatWei(long wei) {
    return formatWei(BigDecimal.valueOf(wei));
  }

  public static String formatWei(double wei) {
    return formatWei(BigDecimal.valueOf(wei));
  }

  private static String formatWei(BigDecimal b) {
    // get the absolute value
    final BigDecimal absb = b.abs();

    if (absb.compareTo(ETHER.value) >= 0) {
      return b.divide(ETHER.value).toPlainString() + " Ether";
    }
    if (absb.compareTo(FINNEY.value) >= 0) {
      return b.divide(FINNEY.value).toPlainString() + " Finney";
    }
    if (absb.compareTo(SZABO.value) >= 0) {
      return b.divide(SZABO.value).toPlainString() + " Szabo";
    }
    if (absb.compareTo(GWEI.value) >= 0) {
      return b.divide(GWEI.value).toPlainString() + " GWei";
    }
    if (absb.compareTo(MWEI.value) >= 0) {
      return b.divide(MWEI.value).toPlainString() + " Wei";
    }
    if (absb.compareTo(KWEI.value) >= 0) {
      return b.divide(KWEI.value).toPlainString() + " KWei";
    }

    return b.toPlainString() + " Wei";
  }
}
