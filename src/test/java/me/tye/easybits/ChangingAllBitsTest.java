package me.tye.easybits;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChangingAllBitsTest {

@Test
public void allLowEmpty() {
  BitHolder bits = BitHolder.fromBitString("");
  bits.allLow();
  Assertions.assertEquals(BitHolder.fromBitString(""), bits);
}


}
