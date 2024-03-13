package me.tye.easybits;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SetTest {

@Test
public void bit() {
  BitHolder bits = new BitHolder(1);
  bits.set(0, true);
  Assertions.assertTrue(bits.get(0));
}

@Test
public void Byte() {
  BitHolder bits = new BitHolder(8);
  bits.set(0, (byte) 54);
  Assertions.assertEquals((byte) 54, bits.getByte(0));
}

@Test
public void bits() {
  BitHolder bits = new BitHolder(4);
  bits.set(0, new boolean[]{false, false, true, true});
  Assertions.assertArrayEquals(new boolean[]{false, false, true, true}, bits.get(0, 3));
}

@Test
public void Bytes() {
  BitHolder bits = new BitHolder(32);
  bits.set(0, new byte[]{40, -20, 77, 10});
  Assertions.assertArrayEquals(new byte[]{40, -20, 77, 10}, bits.getByteArray(0, 4));
}

@Test
public void bitOutOfBounds() {
  BitHolder bits = new BitHolder(1);
  Assertions.assertThrowsExactly(IndexOutOfBoundsException.class, () -> bits.set(3, true));
}

@Test
public void byteOutOfBounds() {
  BitHolder bits = new BitHolder(16);
  Assertions.assertThrowsExactly(IndexOutOfBoundsException.class, () -> bits.set(24, (byte) 2));
}


@Test
public void bitArrayEmpty() {
  BitHolder bits = new BitHolder(4);
  bits.set(0, new boolean[0]);
  Assertions.assertEquals(new BitHolder(4), bits);
}

@Test
public void byteArrayEmpty() {
  BitHolder bits = new BitHolder(16);
  bits.set(0, new byte[0]);
  Assertions.assertEquals(new BitHolder(16), bits);
}
}
