package me.tye.easybits;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InsertTest {


@Test
public void insertStart() {
  BitHolder bits = BitHolder.fromBitString("010101");
  bits.insert(0, true);
  Assertions.assertEquals(BitHolder.fromBitString("1010101"), bits);
}

@Test
public void insertMiddle() {
  BitHolder bits = BitHolder.fromBitString("010101");
  bits.insert(2, true);
  Assertions.assertEquals(BitHolder.fromBitString("0110101"), bits);
}

@Test
public void insertEnd() {
  BitHolder bits = BitHolder.fromBitString("010101");
  bits.insert(5, false);
  Assertions.assertEquals(BitHolder.fromBitString("0101001"), bits);
}

@Test
public void insertMultiStart() {
  BitHolder bits = BitHolder.fromBitString("11110011");
  bits.insert(0, BitHolder.fromBitString("0000"));
  Assertions.assertEquals(BitHolder.fromBitString("000011110011"), bits);
}

@Test
public void insertMultiMid() {
  BitHolder bits = BitHolder.fromBitString("11110011");
  bits.insert(4, BitHolder.fromBitString("0000"));
  Assertions.assertEquals(BitHolder.fromBitString("111100000011"), bits);
}

@Test
public void insertMultiEnd() {
  BitHolder bits = BitHolder.fromBitString("11110011");
  bits.insert(bits.highestIndex(), BitHolder.fromBitString("0000"));
  Assertions.assertEquals(BitHolder.fromBitString("111100100001"), bits);
}

@Test
public void insertEmpty() {
  BitHolder bits = new BitHolder(4);
  bits.insert(0, new boolean[0]);
  Assertions.assertEquals(new BitHolder(4), bits);
}

}
