package me.tye.easybits;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LogicOperationsTest {

@Test
public void not() {
  BitHolder bits = BitHolder.fromBitString("01001");
  BitHolder not = bits.NOT();
  Assertions.assertEquals(BitHolder.fromBitString("10110"), not);
}

@Test
public void notButEmpty() {
  BitHolder bits = BitHolder.fromBitString("");
  BitHolder not = bits.NOT();
  Assertions.assertEquals(BitHolder.fromBitString(""), not);
}

@Test
public void and() {
  BitHolder bits = BitHolder.fromBitString("01001111");
  BitHolder and = bits.AND(BitHolder.fromBitString("00111111"));
  Assertions.assertEquals(BitHolder.fromBitString("00001111"), and);
}

@Test
public void andButEmpty() {
  BitHolder bits = BitHolder.fromBitString("");
  BitHolder and = bits.AND(BitHolder.fromBitString(""));
  Assertions.assertEquals(BitHolder.fromBitString(""), and);
}

@Test
public void nand() {
  BitHolder bits = BitHolder.fromBitString("01001111");
  BitHolder and = bits.NAND(BitHolder.fromBitString("00111111"));
  Assertions.assertEquals(BitHolder.fromBitString("11110000"), and);
}

@Test
public void or() {
  BitHolder bits = BitHolder.fromBitString("01001111");
  BitHolder and = bits.OR(BitHolder.fromBitString("00111111"));
  Assertions.assertEquals(BitHolder.fromBitString("01111111"), and);
}

@Test
public void nor() {
  BitHolder bits = BitHolder.fromBitString("01001111");
  BitHolder and = bits.NOR(BitHolder.fromBitString("00011111"));
  Assertions.assertEquals(BitHolder.fromBitString("10100000"), and);
}

@Test
public void xor() {
  BitHolder bits = BitHolder.fromBitString("01001111");
  BitHolder and = bits.XOR(BitHolder.fromBitString("00011111"));
  Assertions.assertEquals(BitHolder.fromBitString("01010000"), and);
}

@Test
public void xnor() {
  BitHolder bits = BitHolder.fromBitString("01001111");
  BitHolder and = bits.XNOR(BitHolder.fromBitString("00011111"));
  Assertions.assertEquals(BitHolder.fromBitString("10101111"), and);
}

}