package com.razil.noteit.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ValidatorTest {

  @Test
  public void isNullOrEmpty_returnsTrue() {
    String str = "";
    assertThat(Validator.isNullOrEmpty(str), is(true));
  }

  @Test
  public void isNullOrEmpty_returnFalse() {
    String str = "Non empty string";
    assertThat(Validator.isNullOrEmpty(str), is(false));
  }
}