package me.tye.easybits;

import java.util.Arrays;

/**
 This class contains the error messages for the easy bits software. */
public class ErrorMessages {

/**
 This class is a Utility class & not meant to be instantiated. */
private ErrorMessages() {}


protected static String amountBellowZero(Object actualValue) {
  return "Amount < 0. Amount was: " + actualValue;
}

protected static String amountLargerThanIndexed(int largestIndexed, Object actualValue) {
  return "Amount > " + largestIndexed + ". Amount was: " + actualValue;
}

protected static String notPrimitive(String clazzName) {
  return clazzName + " isn't a primitive class";
}

protected static String startBiggerThanEndIndex(int startIndex, int endIndex) {
  return "StartIndex was bigger than endIndex. endIndex: " + endIndex + " < startIndex:" + startIndex;
}

protected static String isNull() {
  return "Value == null";
}

protected static String containedInvalidBinaryChars(char invalidChar) {
  return "Expected \"1\" or \"0\". Got: \"" + invalidChar + "\"";
}


protected static String expectedNibbleValue(boolean[] nibbleValue) {
  return "Expected boolean array of length four. Actual length: " + nibbleValue.length + ". Array value: " + Arrays.toString(nibbleValue);
}

protected static String expectedHexValue(char charValue) {
  return "Expected hex char. Any of \"1, 2, 3, 4, 5, 6, 7, 8, 9, A, B, C, D, E, F\". Instead got: " + charValue;
}
}
