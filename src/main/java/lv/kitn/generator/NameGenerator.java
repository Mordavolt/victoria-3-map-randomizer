package lv.kitn.generator;

import java.util.Random;

public class NameGenerator {
  private static final String[] PREFIXES = {
    "Ar", "Ark", "Ba", "Bel", "Ca", "Cher", "Dar", "De", "Eas", "El", "Fa", "Far", "Ga", "Gor",
    "Ha", "Hel", "Il", "Ish", "Ja", "Jen", "Ka", "Kor", "La", "Len", "Ma", "Mik", "Na", "Nor", "O",
    "Orl", "Pa", "Pet", "Qua", "Ra", "Riv", "Sa", "Sar", "Ta", "Tver", "U", "Ufa", "Va", "Vla",
    "Wa", "Wel", "Xa", "Xan", "Ya", "Yar", "Za", "Zan"
  };

  private static final String[] SUFFIXES = {
    "ania", "ara", "burg", "da", "dor", "ford", "gia", "grad", "ica", "ka", "la", "land", "lia",
    "ma", "na", "nia", "ona", "ora", "pa", "qua", "ra", "ria", "shire", "sia", "stan", "ta", "tan",
    "tha", "tha", "tia", "ton", "va", "via", "ville"
  };

  public static String generateName(Random random) {
    String prefix = PREFIXES[random.nextInt(PREFIXES.length)];
    String suffix = SUFFIXES[random.nextInt(SUFFIXES.length)];
    return prefix + suffix;
  }

  public static String generateAdjective(String country) {
    if (country.endsWith("land")) {
      return country.substring(0, country.length() - 4) + "lish";
    } else if (country.endsWith("ia") || country.endsWith("ria")) {
      return country.substring(0, country.length() - 2) + "ian";
    } else if (country.endsWith("stan")) {
      return country.substring(0, country.length() - 4) + "i";
    } else if (country.endsWith("dor") || country.endsWith("lia")) {
      return country.substring(0, country.length() - 3) + "dorian";
    } else if (country.endsWith("tia") || country.endsWith("via") || country.endsWith("gia")) {
      return country.substring(0, country.length() - 3) + "tian";
    } else if (country.endsWith("ica")) {
      return country.substring(0, country.length() - 3) + "ican";
    } else if (country.endsWith("ara") || country.endsWith("ora") || country.endsWith("ona")) {
      return country.substring(0, country.length() - 3) + "an";
    } else if (country.endsWith("ania") || country.endsWith("nia") || country.endsWith("sia")) {
      return country.substring(0, country.length() - 2) + "n";
    } else if (country.endsWith("burg")) {
      return country.substring(0, country.length() - 4) + "burgian";
    } else if (country.endsWith("ford")
        || country.endsWith("shire")
        || country.endsWith("ton")
        || country.endsWith("ville")
        || country.endsWith("grad")) {
      return country.substring(0, country.length() - 4) + "ian";
    } else {
      return country + "ian";
    }
  }
}
