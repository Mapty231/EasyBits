package me.tye.easybits;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShiftTest {

@Test
public void shiftLeftZero() {
  BitHolder bits = BitHolder.fromBitString("1001011");
  BitHolder shifted = bits.shiftLeft(0);
  Assertions.assertEquals(BitHolder.fromBitString("1001011"), shifted);
}

@Test
public void shiftLeftMid() {
  BitHolder bits = BitHolder.fromBitString("1001011");
  BitHolder shifted = bits.shiftLeft(3);
  Assertions.assertEquals(BitHolder.fromBitString("1011000"), shifted);
}

@Test
public void shiftLeftOver() {
  BitHolder bits = BitHolder.fromBitString("1001011");
  BitHolder shifted = bits.shiftLeft(20);
  Assertions.assertEquals(BitHolder.fromBitString("0000000"), shifted);
}

@Test
public void shiftLeftMinus() {
  BitHolder bits = BitHolder.fromBitString("1001011");
  Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> bits.shiftLeft(-2));
}


@Test
public void shiftRightZero() {
  BitHolder bits = BitHolder.fromBitString("1001011");
  BitHolder shifted = bits.shiftRight(0);
  Assertions.assertEquals(BitHolder.fromBitString("1001011"), shifted);
}

@Test
public void shiftRightMid() {
  BitHolder bits = BitHolder.fromBitString("1001011");
  BitHolder shifted = bits.shiftRight(3);
  Assertions.assertEquals(BitHolder.fromBitString("0001001"), shifted);
}

@Test
public void shiftRightOver() {
  BitHolder bits = BitHolder.fromBitString("1001011");
  BitHolder shifted = bits.shiftRight(38);
  Assertions.assertEquals(BitHolder.fromBitString("0000000"), shifted);
}

@Test
public void shiftRightMinus() {
  BitHolder bits = BitHolder.fromBitString("1001011");
  Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> bits.shiftRight(-2));
}

}
