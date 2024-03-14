package me.tye.easybits;

import java.util.Arrays;

import static me.tye.easybits.ErrorMessages.expectedHexValue;
import static me.tye.easybits.ErrorMessages.expectedNibbleValue;

/**
 This class is a utility class to convert between hex & numeric values. */
public class Hex {

/**
 This class is a utility class & shouldn't be instantiated. */
private Hex() {}

/**
 @param nibble A boolean array of length 4 that represents a hex value.
 @return The hex character that represent the given value.
 @throws IllegalArgumentException If the given boolean array isn't a length of 4. */
protected static char nibbleToHex(boolean[] nibble) throws IllegalArgumentException {
  if (Arrays.equals(nibble, new boolean[]{false, false, false, false})) {
    return '0';
  }
  else if (Arrays.equals(nibble, new boolean[]{false, false, false, true})) {
    return '1';
  }
  else if (Arrays.equals(nibble, new boolean[]{false, false, true, false})) {
    return '2';
  }
  else if (Arrays.equals(nibble, new boolean[]{false, false, true, true})) {
    return '3';
  }
  else if (Arrays.equals(nibble, new boolean[]{false, true, false, false})) {
    return '4';
  }
  else if (Arrays.equals(nibble, new boolean[]{false, true, false, true})) {
    return '5';
  }
  else if (Arrays.equals(nibble, new boolean[]{false, true, true, false})) {
    return '6';
  }
  else if (Arrays.equals(nibble, new boolean[]{false, true, true, true})) {
    return '7';
  }
  else if (Arrays.equals(nibble, new boolean[]{true, false, false, false})) {
    return '8';
  }
  else if (Arrays.equals(nibble, new boolean[]{true, false, false, true})) {
    return '9';
  }
  else if (Arrays.equals(nibble, new boolean[]{true, false, true, false})) {
    return 'A';
  }
  else if (Arrays.equals(nibble, new boolean[]{true, false, true, true})) {
    return 'B';
  }
  else if (Arrays.equals(nibble, new boolean[]{true, true, false, false})) {
    return 'C';
  }
  else if (Arrays.equals(nibble, new boolean[]{true, true, false, true})) {
    return 'D';
  }
  else if (Arrays.equals(nibble, new boolean[]{true, true, true, false})) {
    return 'E';
  }
  else if (Arrays.equals(nibble, new boolean[]{true, true, true, true})) {
    return 'F';
  }
  throw new IllegalArgumentException(expectedNibbleValue(nibble));
}

/**
 @param hexCharacter A char that represents a hex value.
 @return The binary value of the hex char in a boolean array.
 @throws IllegalArgumentException If the given value isn't a valid hex char. */
protected static boolean[] hexToNibble(char hexCharacter) throws IllegalArgumentException {
  char upperCaseHex = Character.toUpperCase(hexCharacter); // Standardizes the input to be higher case.

  switch (upperCaseHex) {
  case '0': return new boolean[]{false, false, false, false};
  case '1': return new boolean[]{false, false, false, true};
  case '2': return new boolean[]{false, false, true, false};
  case '3': return new boolean[]{false, false, true, true};
  case '4': return new boolean[]{false, true, false, false};
  case '5': return new boolean[]{false, true, false, true};
  case '6': return new boolean[]{false, true, true, false};
  case '7': return new boolean[]{false, true, true, true};
  case '8': return new boolean[]{true, false, false, false};
  case '9': return new boolean[]{true, false, false, true};
  case 'A': return new boolean[]{true, false, true, false};
  case 'B': return new boolean[]{true, false, true, true};
  case 'C': return new boolean[]{true, true, false, false};
  case 'D': return new boolean[]{true, true, false, true};
  case 'E': return new boolean[]{true, true, true, false};
  case 'F': return new boolean[]{true, true, true, true};
  default: throw new IllegalArgumentException(expectedHexValue(upperCaseHex));
  }
}

}
