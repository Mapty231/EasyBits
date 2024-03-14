package me.tye.easybits;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HexTest {

@Test
public void toHexFull() {
  BitHolder bits = BitHolder.fromBitString("0000 0001 0010 0011 0100 0101 0110 0111 1000 1001 1010 1011 1100 1101 1110 1111");
  String hexString = bits.toHexString();
  Assertions.assertEquals("01 23 45 67 89 AB CD EF", hexString);
}

@Test
public void toHexMissingBits() {
  BitHolder bits = BitHolder.fromBitString("1100 01");
  String hexString = bits.toHexString();
  Assertions.assertEquals("C4", hexString);
}

@Test
public void toHexEmpty() {
  BitHolder bits = BitHolder.fromBitString("");
  String hexString = bits.toHexString();
  Assertions.assertEquals("", hexString);
}

@Test
public void fromHex() {
  BitHolder bits = BitHolder.fromHexString("01 23 45 67 89 AB CD EF");
  String stringBits = bits.toString();
  Assertions.assertEquals(stringBits, "00000001 00100011 01000101 01100111 10001001 10101011 11001101 11101111");
}

@Test
public void fromEmptyHex() {
  BitHolder bits = BitHolder.fromHexString("");
  String stringBits = bits.toString();
  Assertions.assertEquals("", stringBits);
}

@Test
public void fromInvalidHex() {
  Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> BitHolder.fromHexString("AB FC G1"));
}
}