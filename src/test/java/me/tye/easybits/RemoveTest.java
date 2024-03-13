package me.tye.easybits;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RemoveTest {

@Test
public void removeLast() {
  BitHolder bits = BitHolder.fromBitString("01011");
  boolean removed = bits.remove();
  Assertions.assertTrue(removed);
  Assertions.assertEquals(BitHolder.fromBitString("0101"), bits);
}

@Test
public void removeFirst() {
  BitHolder bits = BitHolder.fromBitString("01011");
  boolean removed = bits.remove(0);
  Assertions.assertFalse(removed);
  Assertions.assertEquals(BitHolder.fromBitString("1011"), bits);
}

@Test
public void removeMid() {
  BitHolder bits = BitHolder.fromBitString("01011");
  boolean removed = bits.remove(2);
  Assertions.assertFalse(removed);
  Assertions.assertEquals(BitHolder.fromBitString("0111"), bits);
}

@Test
public void removeIndexLast() {
  BitHolder bits = BitHolder.fromBitString("01011");
  boolean removed = bits.remove(4);
  Assertions.assertTrue(removed);
  Assertions.assertEquals(BitHolder.fromBitString("0101"), bits);
}

@Test
public void removeMultipleStart() {
  BitHolder bits = BitHolder.fromBitString("0101110011101");
  BitHolder removed = bits.remove(0, 2);
  Assertions.assertEquals(BitHolder.fromBitString("010"), removed);
  Assertions.assertEquals(BitHolder.fromBitString("1110011101"), bits);
}

@Test
public void removeMultipleMid() {
  BitHolder bits = BitHolder.fromBitString("0101110011101");
  BitHolder removed = bits.remove(3, 7);
  Assertions.assertEquals(BitHolder.fromBitString("11100"), removed);
  Assertions.assertEquals(BitHolder.fromBitString("01011101"), bits);
}

@Test
public void removeMultipleEnd() {
  BitHolder bits = BitHolder.fromBitString("0101110011101");
  BitHolder removed = bits.remove(8, 12);
  Assertions.assertEquals(BitHolder.fromBitString("11101"), removed);
  Assertions.assertEquals(BitHolder.fromBitString("01011100"), bits);
}


@Test
public void removeIllegalArgument() {
  BitHolder bits = BitHolder.fromBitString("01011");
  Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> bits.remove(3, 2));
}

@Test
public void removeIndexNegative() {
  BitHolder bits = BitHolder.fromBitString("01011");
  Assertions.assertThrowsExactly(IndexOutOfBoundsException.class, () -> bits.remove(-3, 2));
}

@Test
public void removeIndexHigher() {
  BitHolder bits = BitHolder.fromBitString("01011");
  Assertions.assertThrowsExactly(IndexOutOfBoundsException.class, () -> bits.remove(1, 10));
}
}
