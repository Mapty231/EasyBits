package me.tye.easybits;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.SpinnerUI;

public class GetTest {

@Test
public void bit() {
  BitHolder bitHolder = BitHolder.fromBitString("0");
  Assertions.assertFalse(bitHolder.get(0));
}

@Test
public void Byte() {
  BitHolder bitHolder = BitHolder.fromBitString("00010100");
  Assertions.assertEquals(20, bitHolder.getByte(0));
}

@Test
public void Short() {
  BitHolder bitHolder = BitHolder.fromBitString("0010000101011110");
  Assertions.assertEquals(8542, bitHolder.getShort(0));
}

@Test
public void Char() {
  BitHolder bitHolder = BitHolder.fromBitString("0000000011101010");
  Assertions.assertEquals('ê', bitHolder.getChar(0));
}

@Test
public void Int() {
  BitHolder bitHolder = BitHolder.fromBitString("00101110110001001101101011010100");
  Assertions.assertEquals(784653012, bitHolder.getInt(0));
}

@Test
public void Long() {
  BitHolder bitHolder = BitHolder.fromBitString("0000110001011011100010100010001100100101101111101110101100010010");
  Assertions.assertEquals(890457234897562386L, bitHolder.getLong(0));
}

@Test
public void Float() {
  BitHolder bitHolder = BitHolder.fromBitString("01000100010100001000011111011110");
  Assertions.assertEquals(834.1229248046875, bitHolder.getFloat(0));
}

@Test
public void Double() {
  BitHolder bitHolder = BitHolder.fromBitString("0100000110010011111000111010001010000100100111010001101001100100");
  Assertions.assertEquals(8.342134515342098E7, bitHolder.getDouble(0));
}


@Test
public void bitArray() {
  BitHolder bitHolder = BitHolder.fromBitString("0110");
  Assertions.assertArrayEquals(new boolean[]{false, true, true, false}, bitHolder.get(0, 3));
}

@Test
public void ByteArray() {
  BitHolder bitHolder = BitHolder.fromBitString("000111100111111101000110");
  Assertions.assertArrayEquals(new byte[]{30, 127, 70}, bitHolder.getByteArray(0, 3));
}

@Test
public void ShortArray() {
  BitHolder bitHolder = BitHolder.fromBitString("000101110000010100100111000100001101111101111100");
  Assertions.assertArrayEquals(new short[]{5893, 10000, -8324}, bitHolder.getShortArray(0, 3));
}

@Test
public void CharArray() {
  BitHolder bitHolder = BitHolder.fromBitString("000000001101111100000000101110110000000011111110");
  Assertions.assertArrayEquals(new char[]{'ß', '»', 'þ'}, bitHolder.getCharArray(0, 3));
}


@Test
public void bitStart() {
  BitHolder bits = BitHolder.fromBitString("010");
  boolean bit = bits.get(0);
  Assertions.assertFalse(bit);
}

@Test
public void bitMid() {
  BitHolder bits = BitHolder.fromBitString("010");
  boolean bit = bits.get(1);
  Assertions.assertTrue(bit);
}

@Test
public void bitEnd() {
  BitHolder bits = BitHolder.fromBitString("010");
  boolean bit = bits.get(2);
  Assertions.assertFalse(bit);
}

@Test
public void byteStart() {
  BitHolder bits = BitHolder.from(new byte[]{30, 0, 70});
  byte aByte = bits.getByte(0);
  Assertions.assertEquals((byte) 30, aByte);
}

@Test
public void byteMid() {
  BitHolder bits = BitHolder.from(new byte[]{30, 0, 70});
  byte aByte = bits.getByte(8);
  Assertions.assertEquals((byte) 0, aByte);
}

@Test
public void byteEnd() {
  BitHolder bits = BitHolder.from(new byte[]{30, 0, 70});
  byte aByte = bits.getByte(16);
  Assertions.assertEquals((byte) 70, aByte);
}


@Test
public void bitArrayStart() {
  BitHolder bits = BitHolder.fromBitString("001101011110");
  boolean[] booleans = bits.get(0, 3);
  Assertions.assertArrayEquals(new boolean[]{false, false, true, true}, booleans);
}

@Test
public void bitArrayMid() {
  BitHolder bits = BitHolder.fromBitString("001101011110");
  boolean[] booleans = bits.get(4, 7);
  Assertions.assertArrayEquals(new boolean[]{false, true, false, true}, booleans);
}

@Test
public void bitArrayEnd() {
  BitHolder bits = BitHolder.fromBitString("001101011110");
  boolean[] booleans = bits.get(8, 11);
  Assertions.assertArrayEquals(BitHolder.fromBitString("1110").toBoolArray(), booleans);
}


@Test
public void byteArrayStart() {
  BitHolder bits = BitHolder.from(new byte[]{57, 90, -90, 43, -9});
  byte[] byteArray = bits.getByteArray(0, 2);
  Assertions.assertArrayEquals(new byte[]{57, 90}, byteArray);
}

@Test
public void byteArrayMid() {
  BitHolder bits = BitHolder.from(new byte[]{57, 90, -90, 43, -9});
  byte[] byteArray = bits.getByteArray(8, 3);
  Assertions.assertArrayEquals(new byte[]{90, -90, 43}, byteArray);
}

@Test
public void byteArrayEnd() {
  BitHolder bits = BitHolder.from(new byte[]{57, 90, -90, 43, -9});
  byte[] byteArray = bits.getByteArray(16, 3);
  Assertions.assertArrayEquals(new byte[]{-90, 43, -9}, byteArray);
}


@Test
public void bitIndexOver() {
  BitHolder bits = BitHolder.fromBitString("0110");
  Assertions.assertThrowsExactly(IndexOutOfBoundsException.class, () -> bits.get(90));
}

@Test
public void bitIndexMinus() {
  BitHolder bits = BitHolder.fromBitString("0110");
  Assertions.assertThrowsExactly(IndexOutOfBoundsException.class, () -> bits.get(-2));
}

@Test
public void byteIndexOver() {
  BitHolder bits = BitHolder.fromBitString("00001111 11110000");
  Assertions.assertThrowsExactly(IndexOutOfBoundsException.class, () -> bits.getByte(222));
}

@Test
public void byteIndexMinus() {
  BitHolder bits = BitHolder.fromBitString("00001111 11110000");
  Assertions.assertThrowsExactly(IndexOutOfBoundsException.class, () -> bits.getByte(-1));
}
}
