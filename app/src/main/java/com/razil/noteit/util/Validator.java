package com.razil.noteit.util;

public class Validator {

  /**
   * Checks if the strings are null or empty.
   *
   * @param strings strings to validate
   * @return true if all the strings are either null or empty. False otherwise.
   */
  public static boolean isNullOrEmpty(String... strings) {
    if (strings == null || strings.length == 0) {
      return true;
    }

    for (String string : strings) {
      if (string.trim().isEmpty()) {
        return true;
      }
    }

    return false;
  }
}
