package me.tye.easybits;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChangingAllBitsTest {

@Test
public void allLow() {
  BitHolder bits = BitHolder.fromBitString("01011010 11110000");
  bits.allLow();
  Assertions.assertEquals(BitHolder.fromBitString("00000000 00000000"), bits);
  Assertions.assertTrue(bits.areAllLow());
}

@Test
public void allLowEmpty() {
  BitHolder bits = BitHolder.fromBitString("");
  bits.allLow();
  Assertions.assertEquals(BitHolder.fromBitString(""), bits);
}


@Test
public void allHigh() {
  BitHolder bits = BitHolder.fromBitString("01011010 11110000");
  bits.allHigh();
  Assertions.assertEquals(BitHolder.fromBitString("11111111 11111111"), bits);
  Assertions.assertTrue(bits.areAllHigh());
}

@Test
public void allHighEmpty() {
  BitHolder bits = BitHolder.fromBitString("");
  bits.allLow();
  Assertions.assertEquals(BitHolder.fromBitString(""), bits);
}
}
