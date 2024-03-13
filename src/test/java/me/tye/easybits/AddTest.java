package me.tye.easybits;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddTest {

@Test
public void bit() {
  BitHolder bits = new BitHolder();
  bits.add(true);
  Assertions.assertTrue(bits.get(0));
}

@Test
public void Byte() {
  BitHolder byte_ = new BitHolder();
  byte_.add((byte) 40);
  Assertions.assertEquals((byte) 40, byte_.getByte(0));
}

@Test
public void bits() {
  BitHolder bits = new BitHolder();
  bits.add(new boolean[]{true, false, false, true});
  Assertions.assertArrayEquals(new boolean[]{true, false, false, true}, bits.get(0, 3));
}

@Test
public void ByteArray() {
  BitHolder bits = new BitHolder();
  bits.add(new byte[]{80, -120, -8});
  Assertions.assertArrayEquals(new byte[]{80, -120, -8}, bits.getByteArray(0, 3));
}
}
