package lv.kitn.generator;

import java.util.Random;

public class NameGenerator {
  private static final String[] PREFIXES = {
    "Ar", "Ba", "Ca", "De", "El", "Fa", "Ga", "Ha", "Il", "Ja", "Ka", "La", "Ma", "Na", "O", "Pa",
    "Qua", "Ra", "Sa", "Ta", "U", "Va", "Wa", "Xa", "Ya", "Za", "Ark", "Bel", "Cher", "Dar", "Eas",
    "Far", "Gor", "Hel", "Ish", "Jen", "Kor", "Len", "Mik", "Nor", "Orl", "Pet", "Qua", "Riv",
    "Sar", "Tver", "Ufa", "Vla", "Wel", "Xan", "Yar", "Zan"
  };

  private static final String[] SUFFIXES = {
    "land", "nia", "ria", "stan", "dor", "lia", "tia", "via", "gia", "ica", "ara", "ora", "ona",
    "ria", "sia", "tan", "qua", "da", "na", "la", "pa", "ma", "tha", "land", "ania", "burg", "ford",
    "shire", "ton", "ville", "grad", "stan", "ka", "nia", "lia", "sia", "va", "ra", "ta", "qua",
    "na", "la", "pa", "ma", "tha"
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
