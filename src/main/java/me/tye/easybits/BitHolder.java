package me.tye.easybits;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Objects;

import static me.tye.easybits.ErrorMessages.*;
import static me.tye.easybits.Primitive.*;

/**
 This class is the base wrapper class for BitSet. It contains various methods that make working with the BitSet in java much easier. */
@SuppressWarnings ("unused") // This class contains many methods that are intend for use in projects that depend on easybits.
public class BitHolder implements Iterable<Boolean>, Serializable {

/**
 Used for serializing the class. */
private static final long serialVersionUID = 3236178498501294050L;

/**
 The amount of bits this BitHolder has indexed. */
private int size = 0;
/**
 The underlying {@link BitSet} that holds the bits being stored. */
private final @NotNull BitSet bitSet;


/**
 Constructs a new bit holder with a starting size of 16 bits. */
public BitHolder() {
  this.bitSet = new BitSet(15);
}

/**
 Constructs a new bit holder with the given starting size.<br>
 The bits will be initialized to 0.
 @param startingSize The starting size of the BitHolder. */
public BitHolder(int startingSize) {
  this.size = startingSize;
  this.bitSet = new BitSet(startingSize);
}

/**
 Constructs a new bit holder with the given starting bits.
 @param bits The bit values to instantiate the BitHolder with. */
public BitHolder(boolean[] bits) {
  this(bits.length);
  set(0, bits);
}

// Cloning

/**
 Clones the given instance of the BitHolder as either a shallow or deep clone.
 @param toClone   The instance of BitHolder to return a clone of.
 @param deepClone True if the given instance should de cloned deeply, false if the given instance should be cloned shallowly.
 @throws NullPointerException If toClone is null. */
@SuppressWarnings ("RedundantIfStatement")// Makes sure the clone is created using pass by value.
public BitHolder(@NotNull BitHolder toClone, boolean deepClone) throws NullPointerException {
  nullCheck(toClone);

  if (deepClone) {

    this.bitSet = new BitSet();
    for (boolean bit : toClone) {
      if (bit) {add(true);}
      else {add(false);}
    }

    this.setSize(toClone.size());

  }
  else { // Shallow clone

    this.bitSet = toClone.bitSet;
    this.size = toClone.size;

  }
}


/**
 Adds a bit to the end of the BitHolder.
 @param bit The bit value to write. */
public void add(boolean bit) {
  setSize(size() + 1);
  set(highestIndex(), bit);
}

/**
 Adds multiple bits to the end of the BitHolder.
 @param bits The value of the bits to write. */
public void add(boolean[] bits) {
  int originalSize = size();
  int newSize = originalSize + bits.length;
  setSize(newSize);

  for (int i = 0; i < bits.length; i++) {
    set(i + originalSize, bits[i]);
  }
}

/**
 Adds multiple bits to the end of the BitHolder.
 @param bits The value of the bits to write.
 @throws NullPointerException If bits is null. */
public void add(@NotNull BitHolder bits) throws NullPointerException {
  nullCheck(bits);

  int originalSize = size();
  int newSize = originalSize + bits.size();
  setSize(newSize);

  for (int i = 0; i < bits.size(); i++) {
    set(i + originalSize, bits.get(i));
  }
}


/**
 Sets the bit at the given index to the given bit value.
 @param index The given index.
 @param bit   The given bit value.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index is bigger than the highest index. */
public void set(int index, boolean bit) throws IndexOutOfBoundsException {
  isInBounds(index);
  bitSet.set(index, bit);
}

/**
 Sets the bits at the given index to the given bit values.
 @param index The given index.
 @param bits  The given bit values.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index is bigger than the highest index. */
public void set(int index, boolean[] bits) throws IndexOutOfBoundsException {
  if (bits.length == 0) return; // Handles edge condition

  isInBounds(index);
  isInBounds(index + bits.length - 1);

  for (int i = 0; i < bits.length; i++) {
    bitSet.set(index + i, bits[i]);
  }
}

/**
 Sets the bits at the given index to the given bit values.
 @param index The given index.
 @param bits  The given bit values.
 @throws NullPointerException      If bits are null.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus the given bits length is bigger than the highest index. */
public void set(int index, @NotNull BitHolder bits) throws NullPointerException, IndexOutOfBoundsException {
  nullCheck(bits);
  isInBounds(index);
  isInBounds(index + bits.highestIndex());

  for (int i = 0; i < bits.size(); i++) {
    this.set(index + i, bits.get(i));
  }
}


/**
 Removes one bit from the end of the BitHolder.
 If the BitHolder has a size of 0 this method will return false & have no effect on the BitHolder. */
public boolean remove() {
  if (size() == 0) return false;

  boolean bit = get(highestIndex());
  setSize(size() - 1);
  return bit;
}

/**
 Removes the bit at the given index. Shifts any subsequent elements to the left.<br>
 @param index The index to remove the bit at.
 @return The value of the bit that was removed.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index is bigger than the highest index. */
public boolean remove(int index) throws IndexOutOfBoundsException {
  return remove(index, index).get(0);
}


/**
 Removes the bits between the given startIndex & the given endIndex. Shifts any subsequent elements to the left.<br>
 The endIndex must be higher than the start index.
 @param startIndex The index to start removing the bits at (this index will also be removed).
 @param endIndex   The index to stop removing the bits at (this index will also be removed).
 @return The bits that were removed as BitHolder.
 @throws IllegalArgumentException  If the endIndex is bigger than the startIndex.
 @throws IndexOutOfBoundsException If either of the given indexes is negative or if they are bigger than the highest index. */
public @NotNull BitHolder remove(int startIndex, int endIndex) throws IllegalArgumentException, IndexOutOfBoundsException {
  if (startIndex > endIndex) throw new IllegalArgumentException(startBiggerThanEndIndex(startIndex, endIndex));

  isInBounds(startIndex);
  isInBounds(endIndex);

  // Edge case coverage. This is because one is added to the endIndex later.
  if (endIndex == highestIndex()) {
    BitHolder removedBits = subBits(startIndex, highestIndex());
    setSize(size() - (size() - startIndex));
    return removedBits;
  }

  BitHolder removedBits = subBits(startIndex, endIndex);
  boolean[] subsequentBits = get(endIndex + 1, highestIndex());
  set(startIndex, subsequentBits);
  setSize(startIndex + subsequentBits.length);

  return removedBits;
}


/**
 @param index The given index.
 @return The bit value at the given index within the BitHolder.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index is bigger than the highest index. */
public boolean get(int index) throws IndexOutOfBoundsException {
  isInBounds(index);
  return bitSet.get(index);
}

/**
 If you want to get a subsection of the BitHolder as a BitHolder use {@link #subBits(int, int)}.
 @param startIndex Must be lower.
 @param endIndex   Must be higher.
 @return The bits between the startIndex (inclusive) & the endIndex (inclusive) as a boolean array.
 @throws IllegalArgumentException  If the endIndex is bigger than the startIndex.
 @throws IndexOutOfBoundsException If either of the given indexes is negative or if they are bigger than the highest index. */
public boolean[] get(int startIndex, int endIndex) throws IllegalArgumentException, IndexOutOfBoundsException {
  if (startIndex > endIndex) throw new IllegalArgumentException(startBiggerThanEndIndex(startIndex, endIndex));

  isInBounds(startIndex);
  isInBounds(endIndex);

  boolean[] bits = new boolean[endIndex - startIndex + 1];

  for (int i = 0; startIndex + i <= endIndex; i++) {
    bits[i] = get(startIndex + i);
  }

  return bits;
}


/**
 Inserts one bit at the given index in the BitHolder. The bit at the index & any subsequent bits will be shifted right (have their index increased by one).
 @param index The index inside the BitHolder to insert the bit at.
 @param bit   The bit to insert into the BitHolder at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index is bigger than the highest index. */
public void insert(int index, boolean bit) throws IndexOutOfBoundsException {
  insert(index, new boolean[]{bit});
}

/**
 Inserts an array of bits at the given index in the BitHolder. The bit at the index & any subsequent bits will be shifted right.
 @param index The index inside the BitHolder to insert the bits at.
 @param bits  The bits to insert into the BitHolder at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index is bigger than the highest index. */
public void insert(int index, boolean[] bits) throws IndexOutOfBoundsException {
  insert(index, BitHolder.from(bits));
}

/**
 Inserts an array of bits at the given index in the BitHolder. The bit at the index & any subsequent bits will be shifted right.
 @param index The index inside the BitHolder to insert the bits at.
 @param bits  The bits to insert into the BitHolder at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index is bigger than the highest index. */
public void insert(int index, BitHolder bits) throws IndexOutOfBoundsException {
  BitHolder bitsToShift = remove(index, highestIndex());
  add(bits);
  add(bitsToShift);
}

/**
 Creates a clone of this BitHolder with the bits shifted to the left (decreasing the index) by the given amount.<br>
 This method does not wrap around bits that exit the bounds of the array.<br>
 Any new bits added will be initially set to false.
 @param amount The amount to shift the bits by.
 @return A modified BitHolder with the bits shifted by the given amount.
 @throws IllegalArgumentException If the given amount is negative. */
public @NotNull BitHolder shiftLeft(int amount) throws IllegalArgumentException {
  if (amount < 0) {throw new IllegalArgumentException(amountBellowZero(amount));}

  BitHolder bitHolderClone = new BitHolder(this, true);

  if (amount == 0) {
    return bitHolderClone;
  }

  if (amount > size()) {
    bitHolderClone.allLow();
    return bitHolderClone;
  }

  bitHolderClone.remove(0, amount - 1);
  bitHolderClone.add(new boolean[amount]);
  return bitHolderClone;
}

/**
 Creates a clone of this BitHolder with the bits shifted to the right (increasing the index) by the given amount.<br>
 This method does not wrap around bits that exit the bounds of the array. <br>
 Any new bits added will be initially set to false.
 @param amount The amount to shift the bits by.
 @return A modified BitHolder with the bits shifted by the given amount.
 @throws IllegalArgumentException If the given amount is negative. */
public @NotNull BitHolder shiftRight(int amount) throws IllegalArgumentException {
  if (amount < 0) {throw new IllegalArgumentException(amountBellowZero(amount));}

  BitHolder bitHolderClone = new BitHolder(this, true);

  if (amount == 0) {
    return bitHolderClone;
  }

  if (amount > highestIndex()) {
    bitHolderClone.allLow();
    return bitHolderClone;
  }

  boolean[] shiftedContent = new boolean[size()];
  System.arraycopy(toBoolArray(), 0, shiftedContent, amount, size() - amount); // Copy the remaining bits to a new bool array offset by the shift amount.
  bitHolderClone.set(0, shiftedContent); // Replace the contents of this with the new bool array.
  return bitHolderClone;
}


/**
 Sets all the bits in this BitHolder to high (true). */
public void allHigh() {
  boolean[] newContent = new boolean[size()];
  Arrays.fill(newContent, true);
  set(0, newContent);
}

/**
 Sets all the bits in this BitHolder to low (false). */
public void allLow() {
  set(0, new boolean[size()]);
}

/**
 @return True if all the bits in the BitHolder are high (true); */
public boolean areAllHigh() {
  return bitSet.cardinality() >= size();
}

/**
 @return True if all the bits in the BitHolder are low (false); */
public boolean areAllLow() {
  // This method can't check the cardinality of the bitSet as bits that exist above of the tracked "size"
  // in the set could interfere with the result of that method.
  for (boolean bit : this) {
    if (bit) {return false;}
  }

  return true;
}


/**
 @param index The index to gets the bits after.
 @return The bits between the index (inclusive) & the highest index as a BitHolder.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index is bigger than the highest index. */
public @NotNull BitHolder subBits(int index) throws IndexOutOfBoundsException {
  isInBounds(index);
  return subBits(index, highestIndex());
}

/**
 If you want to get a subsection of the BitHolder as a boolean array use {@link #get(int, int)}.
 @param startIndex Must be lower.
 @param endIndex   Must be higher.
 @return The bits between the startIndex (inclusive) & the endIndex (inclusive) as a BitHolder.
 @throws IllegalArgumentException  If the endIndex is bigger than the startIndex.
 @throws IndexOutOfBoundsException If either of the given indexes is negative or if they are bigger than the highest index. */
public @NotNull BitHolder subBits(int startIndex, int endIndex) throws IllegalArgumentException, IndexOutOfBoundsException {
  BitHolder bitHolder = new BitHolder(this, true); // Creates a deep clone of the object.
  boolean[] subBits = bitHolder.get(startIndex, endIndex);
  return new BitHolder(subBits);
}


// Logic operations //

/**
 Performs the NOT logic operation on this BitHolder.
 @return A new BitHolder containing the result of the NOT logic operation on the calling BitHolder. */
public @NotNull BitHolder NOT() {
  if (size() == 0) {
    return new BitHolder(); // Handles edge case of a logic operation on an empty BitHolder.
  }

  BitHolder clone = new BitHolder(this, true);
  clone.bitSet.flip(0, size());
  return clone;
}

/**
 Performs the AND logic operation on this BitHolder.
 @param bitsToAND The BitHolder to preform the AND operation with.
 @return A new BitHolder containing the result of the AND logic operation on the calling BitHolder.
 @throws NullPointerException If the given BitHolder was null. */
public @NotNull BitHolder AND(@NotNull BitHolder bitsToAND) throws NullPointerException {
  nullCheck(bitsToAND);
  if (size() == 0) {
    return new BitHolder(); // Handles edge case of a logic operation on an empty BitHolder.
  }

  bitsToAND.setSize(this.size()); // Ensures that the bitHolders are the same size for consistency.

  BitHolder clone = new BitHolder(this, true);
  clone.bitSet.and(bitsToAND.bitSet);
  return clone;
}

/**
 Performs the NAND logic operation on this BitHolder.
 @param bitsToNAND The BitHolder to preform the NAND operation with.
 @return A new BitHolder containing the result of the NAND logic operation on the calling BitHolder.
 @throws NullPointerException If the given BitHolder was null. */
public @NotNull BitHolder NAND(@NotNull BitHolder bitsToNAND) throws NullPointerException {
  nullCheck(bitsToNAND);
  if (size() == 0) {
    return new BitHolder(); // Handles edge case of a logic operation on an empty BitHolder.
  }

  bitsToNAND.setSize(this.size()); // Ensures that the bitHolders are the same size for consistency.

  BitHolder clone = new BitHolder(this, true);
  clone.bitSet.and(bitsToNAND.bitSet);
  return clone.NOT();
}

/**
 Performs the OR logic operation on this BitHolder.
 @param bitsToOR The BitHolder to preform the OR operation with.
 @return A new BitHolder containing the result of the OR logic operation on the calling BitHolder.
 @throws NullPointerException If the given BitHolder was null. */
public @NotNull BitHolder OR(@NotNull BitHolder bitsToOR) throws NullPointerException {
  nullCheck(bitsToOR);
  if (size() == 0) {
    return new BitHolder(); // Handles edge case of a logic operation on an empty BitHolder.
  }

  bitsToOR.setSize(this.size()); // Ensures that the bitHolders are the same size for consistency.

  BitHolder clone = new BitHolder(this, true);
  clone.bitSet.or(bitsToOR.bitSet);
  return clone;
}

/**
 Performs the NOR logic operation on this BitHolder.
 @param bitsToNOR The BitHolder to preform the NOR operation with.
 @return A new BitHolder containing the result of the NOR logic operation on the calling BitHolder.
 @throws NullPointerException If the given BitHolder was null. */
public @NotNull BitHolder NOR(@NotNull BitHolder bitsToNOR) throws NullPointerException {
  nullCheck(bitsToNOR);
  if (size() == 0) {
    return new BitHolder(); // Handles edge case of a logic operation on an empty BitHolder.
  }

  bitsToNOR.setSize(this.size()); // Ensures that the bitHolders are the same size for consistency.

  BitHolder clone = new BitHolder(this, true);
  clone.bitSet.or(bitsToNOR.bitSet);
  return clone.NOT();
}

/**
 Performs the XOR logic operation on this BitHolder.
 @param bitsToXOR The BitHolder to preform the XOR operation with.
 @return A new BitHolder containing the result of the XOR logic operation on the calling BitHolder.
 @throws NullPointerException If the given BitHolder was null. */
public @NotNull BitHolder XOR(@NotNull BitHolder bitsToXOR) throws NullPointerException {
  nullCheck(bitsToXOR);
  if (size() == 0) {
    return new BitHolder(); // Handles edge case of a logic operation on an empty BitHolder.
  }

  bitsToXOR.setSize(this.size()); // Ensures that the bitHolders are the same size for consistency.

  BitHolder clone = new BitHolder(this, true);
  clone.bitSet.xor(bitsToXOR.bitSet);
  return clone;
}

/**
 Performs the XNOR logic operation on this BitHolder.
 @param bitsToXNOR The BitHolder to preform the XNOR operation with.
 @return A new BitHolder containing the result of the XNOR logic operation on the calling BitHolder.
 @throws NullPointerException If the given BitHolder was null. */
public @NotNull BitHolder XNOR(@NotNull BitHolder bitsToXNOR) throws NullPointerException {
  nullCheck(bitsToXNOR);
  if (size() == 0) {
    return new BitHolder(); // Handles edge case of a logic operation on an empty BitHolder.
  }

  bitsToXNOR.setSize(this.size()); // Ensures that the bitHolders are the same size for consistency.

  BitHolder clone = new BitHolder(this, true);
  clone.bitSet.xor(bitsToXNOR.bitSet);
  return clone.NOT();
}


/**
 @return This size of BitHolder. */
public int size() {
  return size;
}

/**
 Sets the size of the bits that are contained within the BitHolder. The size is the {@link #highestIndex()} + 1<br>
 Any bits that exist within the inner bitSet that are above the highestIndex wil be false.<br>
 If any new bits are created they will default to false.
 @param newSize The new size of the BitHolder. This value must be 0 or above.
 @throws IllegalArgumentException If the given size was negative. */
private void setSize(int newSize) throws IllegalArgumentException {
  if (newSize < 0) {throw new IllegalArgumentException(amountBellowZero(newSize));}

  if (newSize > size()) {
    this.size = newSize;
    return;
  }

  bitSet.set(newSize, size() + 1, false); // Resets all out of bounds bits to false.
  this.size = newSize;
}

/**
 If the {@link #size()} of the BitHolder is 0, then this method will also return 0.
 @return The highest index of an element within the BitHolder. */
public int highestIndex() {
  return size() == 0 ? 0 : size() - 1; // Returns 0 if the size is 0 instead of returning -1.
}

/**
 Sets the size of the bits that are contained within the BitHolder based on the highestIndex. The highest index is the size – 1. <br>
 Any bits that exist within the inner bitSet that are above the highestIndex wil be false.<br>
 If any new bits are created they will default to false.
 @param newHighestIndex The new highestIndex of the BitHolder. This value must be 0 or above.
 @throws IllegalArgumentException If the given index was negative. */
public void setHighestIndex(int newHighestIndex) {
  setSize(newHighestIndex + 1);
}

/**
 @return True if the BitHolder has a size of 0. False otherwise. */
public boolean isEmpty() {
  return size() <= 0;
}

/**
 Removes all the bits the BitHolder contains.<br>
 If you instead want to set all the bits to low see {@link #allLow()} */
public void clear() {
  allLow();
  setSize(0);
}


// String handling

/**
 If you want to get a BitHolder from a string use {@link #fromBitString(String)}.
 @return An output of the bits this BitHolder contains. */
@Override
public @NotNull String toString() {
  StringBuilder bitString = new StringBuilder();

  for (int i = 0; i < size(); i++) {
    boolean bit = get(i);

    if (i % 8 == 0) {bitString.append(' ');}

    // formats it into 1s & 0s
    if (bit) {
      bitString.append('1');
    }
    else {
      bitString.append('0');
    }
  }

  return bitString.toString().trim();
}

/**
 @return A hex string that represents the bits stored in this holder. */
public @NotNull String toHexString() {
  StringBuilder hexString = new StringBuilder();
  int bitsToPad = size() % 4;
  setSize(size() + bitsToPad);

  for (int i = 0; i < size() - 3; i += 4) {
    if (i % 8 == 0) hexString.append(' '); // Appends a space after every byte.

    boolean[] nibble = get(i, i + 3);
    hexString.append(Hex.nibbleToHex(nibble));
  }

  return hexString.toString().trim();
}

/**
 Creates a new BitHolder from the given string of bits.<br>
 The given string should only contain "1"'s or "0"'s. However, tabs, spaces, & line breaks are allowed. Any other characters will throw the IllegalArgumentException<br>
 To get a bit string from a BitHolder in this format use {@link #toString()}.<br>
 @param bitString The string to parse into the BitHolder.
 @return The BitHolder parsed from the given string of bits.
 @throws IllegalArgumentException If the given string contained a character that wasn't "1", "0", tab, space, or a line break.
 @throws NullPointerException     If the given bitString was null. */
public static @NotNull BitHolder fromBitString(@NotNull String bitString) throws IllegalArgumentException, NullPointerException {
  nullCheck(bitString);

  BitHolder bits = new BitHolder();
  char[] charArray = bitString.toCharArray();

  for (char bit : charArray) {
    switch (bit) {
    // Ignores whitespaces & returns.
    case ' ':
    case '\t':
    case '\r':
    case '\n': {
      continue;
    }

    // Converts the chars to boolean values.
    case '0': {
      bits.add(false);
      continue;
    }
    case '1': {
      bits.add(true);
      continue;
    }

    // Invalid chars will case an exception.
    default: {
      throw new IllegalArgumentException(containedInvalidChars(bit));
    }
    }
  }

  return bits;
}


/**
 Concatenates this BitHolder with the given holders. They are concatenated in the order they were given.<br>
 This method has no effect on the instance it was called on.
 @param holders An array of BitHolders to concatenate together.
 @return The BitHolder that is result of the concatenation.
 @throws NullPointerException If any of the given BitHolders are null. */
public @NotNull BitHolder concat(@NotNull BitHolder... holders) {
  nullCheck(holders);
  for (BitHolder holder : holders) {
    nullCheck(holder);
  }

  BitHolder holder = new BitHolder(this, true);
  holder.add(holder.toBoolArray());

  for (BitHolder otherHolder : holders) {
    holder.add(otherHolder.toBoolArray());
  }

  return holder;
}


// Input sanitization

/**
 Tests if the given index is inside the range of the BitHolder.
 @param index The given index to check.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index is bigger than the highest index. */
private void isInBounds(int index) throws IndexOutOfBoundsException {
  if (index < 0) {throw new IndexOutOfBoundsException(amountBellowZero(index));}
  if (highestIndex() < index) {throw new IndexOutOfBoundsException(amountLargerThanIndexed(highestIndex(), index));}
}

/**
 @throws NullPointerException If the given argument is null. */
@Contract (value="null -> fail; !null -> _")
private static void nullCheck(Object object) throws NullPointerException {
  if (object == null) {throw new NullPointerException(isNull());}
}


// Byte streams

/**
 Converts the given {@link ByteArrayOutputStream} into a BitHolder.
 @param byteOutputStream The given byte stream to convert.
 @return A BitHolder filled with the bytes in the stream.
 @throws NullPointerException If the byteOutputStream was null. */
public static @NotNull BitHolder fromByteStream(@NotNull ByteArrayOutputStream byteOutputStream) {
  nullCheck(byteOutputStream);

  byte[] byteArray = byteOutputStream.toByteArray();
  return Primitive.toBitHolder(byteArray);
}

/**
 Converts the BitHolder into a byte stream.
 @return A {@link ByteArrayInputStream} that contains the content of the BitHolder. */
public @NotNull ByteArrayInputStream toByteStream() {
  byte[] byteArray = Primitive.toPrimitive(this, byte[].class);
  return new ByteArrayInputStream(byteArray);
}


// Working with primitives. //

// Returning arrays.

/**
 @return A boolean array that represents the bits contained by this BitHolder. */
public boolean[] toBoolArray() {
  boolean[] content = new boolean[size()];

  for (int i = 0; i < size(); i++) {
    boolean bit = get(i);
    content[i] = bit;
  }

  return content;
}

/**
 @return A byte array that represents the bits contained by this BitHolder. */
public byte[] toByteArray() {
  // Pads the BitHolder
  int amountOfBitsToPad = size() % BYTE.bitSize;
  setSize(size() + amountOfBitsToPad);

  int byteSize = size() / 8;
  byte[] bytes = new byte[byteSize];

  // Iterates over the bytes in the created array
  for (int byteInArray = 0; byteInArray < byteSize; byteInArray++) {
    byte byteBeingModified = 0;

    // Iterates over the bits within a byte
    for (int internalByteIndex = 0; internalByteIndex < 8; internalByteIndex++) {
      // Finds the correlating bit in the BitHolder
      boolean bit = get((byteInArray * 8) + internalByteIndex);
      if (!bit) {continue;}

      byte bitToSet = (byte) (128 >> internalByteIndex); // Sets the bit in the correct position within the byte
      byteBeingModified = (byte) (byteBeingModified | bitToSet); // "Adds" the bit to the byte
    }

    bytes[byteInArray] = byteBeingModified; //Sets the byte in the array to the correct value.
  }

  setSize(size() - amountOfBitsToPad); // Removes the padding bits to keep this method pure.
  return bytes;
}


// Primitive constructors.

/**
 Constructs a new BitHolder with the given starting bit.
 @param bit The bits to populate the holder with. */
public static @NotNull BitHolder from(boolean bit) {
  BitHolder bits = new BitHolder();
  bits.add(bit);
  return bits;
}

/**
 Constructs a new BitHolder with the given starting byte.
 @param byte_ The byte to populate the holder with. */
public static @NotNull BitHolder from(byte byte_) {
  BitHolder bits = new BitHolder();
  bits.add(byte_);
  return bits;
}

/**
 Constructs a new BitHolder with the given starting short.
 @param short_ The short to populate the holder with. */
public static @NotNull BitHolder from(short short_) {
  BitHolder bits = new BitHolder();
  bits.add(short_);
  return bits;
}

/**
 Constructs a new BitHolder with the given starting char.
 @param char_ The char to populate the holder with. */
public static @NotNull BitHolder from(char char_) {
  BitHolder bits = new BitHolder();
  bits.add(char_);
  return bits;
}

/**
 Constructs a new BitHolder with the given starting int.
 @param int_ The int to populate the holder with. */
public static @NotNull BitHolder from(int int_) {
  BitHolder bits = new BitHolder();
  bits.add(int_);
  return bits;
}

/**
 Constructs a new BitHolder with the given starting long.
 @param long_ The long to populate the holder with. */
public static @NotNull BitHolder from(long long_) {
  BitHolder bits = new BitHolder();
  bits.add(long_);
  return bits;
}

/**
 Constructs a new BitHolder with the given starting float.
 @param float_ The float to populate the holder with. */
public static @NotNull BitHolder from(float float_) {
  BitHolder bits = new BitHolder();
  bits.add(float_);
  return bits;
}

/**
 Constructs a new BitHolder with the given starting double.
 @param double_ The double to populate the holder with. */
public static @NotNull BitHolder from(double double_) {
  BitHolder bits = new BitHolder();
  bits.add(double_);
  return bits;
}

/**
 Constructs a new BitHolder with the given starting bits.
 @param bits The bits to populate the holder with. */
public static @NotNull BitHolder from(boolean[] bits) {
  BitHolder bitHolder = new BitHolder();
  bitHolder.add(bits);
  return bitHolder;
}

/**
 Constructs a new BitHolder with the given starting bytes.
 @param bytes The bytes to populate the holder with. */
public static @NotNull BitHolder from(byte[] bytes) {
  BitHolder bits = new BitHolder();
  bits.add(bytes);
  return bits;
}

/**
 Constructs a new BitHolder with the given starting shorts.
 @param shorts The shorts to populate the holder with. */
public static @NotNull BitHolder from(short[] shorts) {
  BitHolder bits = new BitHolder();
  bits.add(shorts);
  return bits;
}

/**
 Constructs a new BitHolder with the given starting chars.
 @param chars The chars to populate the holder with. */
public static @NotNull BitHolder from(char[] chars) {
  BitHolder bits = new BitHolder();
  bits.add(chars);
  return bits;
}

/**
 Constructs a new BitHolder with the given starting ints.
 @param ints The ints to populate the holder with. */
public static @NotNull BitHolder from(int[] ints) {
  BitHolder bits = new BitHolder();
  bits.add(ints);
  return bits;
}

/**
 Constructs a new BitHolder with the given starting longs.
 @param longs The longs to populate the holder with. */
public static @NotNull BitHolder from(long[] longs) {
  BitHolder bits = new BitHolder();
  bits.add(longs);
  return bits;
}

/**
 Constructs a new BitHolder with the given starting floats.
 @param floats The floats to populate the holder with. */
public static @NotNull BitHolder from(float[] floats) {
  BitHolder bits = new BitHolder();
  bits.add(floats);
  return bits;
}

/**
 Constructs a new BitHolder with the given starting doubles.
 @param doubles The doubles to populate the holder with. */
public static @NotNull BitHolder from(double[] doubles) {
  BitHolder bits = new BitHolder();
  bits.add(doubles);
  return bits;
}


// Adding primitives.

/**
 Adds the bits from a primitive or primitive array to this BitHolder.
 @param primitive The primitive to add.
 @throws NullPointerException     If the given object is null.
 @throws IllegalArgumentException If the given argument wasn't a primitive. */
public void add(@NotNull Object primitive) throws NullPointerException, IllegalArgumentException {
  nullCheck(primitive);

  add(Primitive.toBitHolder(primitive));
}

/**
 Adds the bits from the byte to this BitHolder.
 @param byte_ The given byte. */
public void add(byte byte_) {
  add(Primitive.toBitHolder(byte_));
}

/**
 Adds the bits from the short to this BitHolder.
 @param short_ The given short. */
public void add(short short_) {
  add(Primitive.toBitHolder(short_));
}

/**
 Adds the bits from the char to this BitHolder.
 @param char_ The given char. */
public void add(char char_) {
  add(Primitive.toBitHolder(char_));
}

/**
 Adds the bits from the int to this BitHolder.
 @param int_ The given int. */
public void add(int int_) {
  add(Primitive.toBitHolder(int_));
}

/**
 Adds the bits from the long to this BitHolder.
 @param long_ The given long. */
public void add(long long_) {
  add(Primitive.toBitHolder(long_));
}

/**
 Adds the bits from the float to this BitHolder.
 @param float_ The given float. */
public void add(float float_) {
  add(Primitive.toBitHolder(float_));
}

/**
 Adds the bits from the double to this BitHolder.
 @param double_ The given double. */
public void add(double double_) {
  add(Primitive.toBitHolder(double_));
}

/**
 Adds the bits from the byte array to this BitHolder.
 @param bytes The given byte array. */
public void add(byte[] bytes) {
  add(Primitive.toBitHolder(bytes));
}

/**
 Adds the bits from the short array to this BitHolder.
 @param shorts The given short array. */
public void add(short[] shorts) {
  add(Primitive.toBitHolder(shorts));
}

/**
 Adds the bits from the chars array to this BitHolder.
 @param chars The given chars array. */
public void add(char[] chars) {
  add(Primitive.toBitHolder(chars));
}

/**
 Adds the bits from the int array to this BitHolder.
 @param ints The given int array. */
public void add(int[] ints) {
  add(Primitive.toBitHolder(ints));
}

/**
 Adds the bits from the long array to this BitHolder.
 @param longs The given long array. */
public void add(long[] longs) {
  add(Primitive.toBitHolder(longs));
}

/**
 Adds the bits from the float array to this BitHolder.
 @param floats The given float array. */
public void add(float[] floats) {
  add(Primitive.toBitHolder(floats));
}

/**
 Adds the bits from the double array to this BitHolder.
 @param doubles The given double array. */
public void add(double[] doubles) {
  add(Primitive.toBitHolder(doubles));
}


// Setting primitives.

/**
 Sets the bits from a primitive or primitive array in this BitHolder at the given index.
 @param index     The index to set the bits at.
 @param primitive The primitive value to set the bits to.
 @throws NullPointerException      If the given object is null.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus the given primitive size is bigger than the highest index. */
public void set(int index, @NotNull Object primitive) throws NullPointerException, IllegalArgumentException {
  nullCheck(primitive);

  set(index, Primitive.toBitHolder(primitive));
}

/**
 Sets the bits at the given index to the bits of the given byte.<br>
 A byte has 8 bits.
 @param index The index to set the bits at.
 @param byte_ The byte to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus the given primitive size is bigger than the highest index. */
public void set(int index, byte byte_) throws IndexOutOfBoundsException {
  set(index, Primitive.toBitHolder(byte_));
}

/**
 Sets the bits at the given index to the bits of the given short.<br>
 A short has 16 bits.
 @param index  The index to set the bits at.
 @param short_ The short to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus the given primitive size is bigger than the highest index. */
public void set(int index, short short_) throws IndexOutOfBoundsException {
  set(index, Primitive.toBitHolder(short_));
}

/**
 Sets the bits at the given index to the bits of the given char.<br>
 A char has 16 bits.
 @param index The index to set the bits at.
 @param char_ The char to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus the given primitive size is bigger than the highest index. */
public void set(int index, char char_) throws IndexOutOfBoundsException {
  set(index, Primitive.toBitHolder(char_));
}

/**
 Sets the bits at the given index to the bits of the given int.<br>
 An int has 32 bits.
 @param index The index to set the bits at.
 @param int_  The int to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus the given primitive size is bigger than the highest index. */
public void set(int index, int int_) throws IndexOutOfBoundsException {
  set(index, Primitive.toBitHolder(int_));
}

/**
 Sets the bits at the given index to the bits of the given long.<br>
 A long has 64 bits.
 @param index The index to set the bits at.
 @param long_ The long to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus the given primitive size is bigger than the highest index. */
public void set(int index, long long_) throws IndexOutOfBoundsException {
  set(index, Primitive.toBitHolder(long_));
}

/**
 Sets the bits at the given index to the bits of the given float.<br>
 A float has 32 bits.
 @param index  The index to set the bits at.
 @param float_ The float to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus the given primitive size is bigger than the highest index. */
public void set(int index, float float_) throws IndexOutOfBoundsException {
  set(index, Primitive.toBitHolder(float_));
}

/**
 Sets the bits at the given index to the bits of the given double.<br>
 A double has 64 bits.
 @param index   The index to set the bits at.
 @param double_ The double to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus the given primitive size is bigger than the highest index. */
public void set(int index, double double_) throws IndexOutOfBoundsException {
  set(index, Primitive.toBitHolder(double_));
}


/**
 Sets the bits at the given index to the bits of the given byte array.<br>
 A byte has 8 bits.
 @param index The index to set the bits at.
 @param bytes The bytes to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus primitive array bits are bigger than the highest index. */
public void set(int index, byte[] bytes) throws IndexOutOfBoundsException {
  set(index, Primitive.toBitHolder(bytes));
}

/**
 Sets the bits at the given index to the bits of the given short array.<br>
 A short has 16 bits.
 @param index  The index to set the bits at.
 @param shorts The shorts to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus primitive array bits are bigger than the highest index. */
public void set(int index, short[] shorts) throws IndexOutOfBoundsException {
  set(index, Primitive.toBitHolder(shorts));
}

/**
 Sets the bits at the given index to the bits of the given char array.<br>
 A chars has 16 bits.
 @param index The index to set the bits at.
 @param chars The chars to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus primitive array bits are bigger than the highest index. */
public void set(int index, char[] chars) throws IndexOutOfBoundsException {
  set(index, Primitive.toBitHolder(chars));
}

/**
 Sets the bits at the given index to the bits of the given int array.<br>
 A ints has 32 bits.
 @param index The index to set the bits at.
 @param ints  The ints to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus primitive array bits are bigger than the highest index. */
public void set(int index, int[] ints) throws IndexOutOfBoundsException {
  set(index, Primitive.toBitHolder(ints));
}

/**
 Sets the bits at the given index to the bits of the given long array.<br>
 A long has 64 bits.
 @param index The index to set the bits at.
 @param longs The longs to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus primitive array bits are bigger than the highest index. */
public void set(int index, long[] longs) throws IndexOutOfBoundsException {
  set(index, Primitive.toBitHolder(longs));
}

/**
 Sets the bits at the given index to the bits of the given float array.<br>
 A float has 32 bits.
 @param index  The index to set the bits at.
 @param floats The floats to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus primitive array bits are bigger than the highest index. */
public void set(int index, float[] floats) throws IndexOutOfBoundsException {
  set(index, Primitive.toBitHolder(floats));
}

/**
 Sets the bits at the given index to the bits of the given double array.<br>
 A double has 64 bits.
 @param index   The index to set the bits at.
 @param doubles The doubles to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus primitive array bits are bigger than the highest index. */
public void set(int index, double[] doubles) throws IndexOutOfBoundsException {
  set(index, Primitive.toBitHolder(doubles));
}


// Getting primitives.

/**
 Gets a primitive (determined by the given class) from the BitHolder at the given index.
 @param index          The index to get the primitive from in the BitHolder.
 @param primitiveClass The primitive class to get the bits as.
 @param <T>            The primitive class to get the bits as.
 @return The bits at the given index from the BitHolder as a primitive.
 @throws NullPointerException      If the primitiveClass is null.
 @throws IllegalArgumentException  If the given primitive class isn't a primitive, primitive array or a wrapper for either.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the primitive bit length is bigger than the highest index. */
public <T> T get(int index, @NotNull Class<T> primitiveClass) throws NullPointerException, IllegalArgumentException, IndexOutOfBoundsException {
  nullCheck(primitiveClass);

  if (!isPrimitive(primitiveClass)) {throw new IllegalArgumentException(notPrimitive(primitiveClass.getName()));}
  isInBounds(index);

  int bitSize = fromClass(primitiveClass).getIndexSize();
  return Primitive.toPrimitive(subBits(index, index + bitSize), primitiveClass);
}

/**
 Gets a byte from the BitHolder at the given index.<br>
 A byte has 8 bits.
 @param index The index to get the byte at.
 @return A byte representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length is bigger than the highest index of the BitHolder. */
public byte getByte(int index) throws IndexOutOfBoundsException {
  return Primitive.toPrimitive(subBits(index, index + BYTE.getIndexSize()), byte.class);
}

/**
 Gets a short from the BitHolder at the given index.<br>
 A short has 16 bits.
 @param index The index to get the short at.
 @return A short representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length is bigger than the highest index of the BitHolder. */
public short getShort(int index) throws IndexOutOfBoundsException {
  return Primitive.toPrimitive(subBits(index, index + SHORT.getIndexSize()), short.class);
}

/**
 Gets a char from the BitHolder at the given index.<br>
 A char has 16 bits.
 @param index The index to get the char at.
 @return A char representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length is bigger than the highest index of the BitHolder. */
public char getChar(int index) throws IndexOutOfBoundsException {
  return Primitive.toPrimitive(subBits(index, index + CHAR.getIndexSize()), char.class);
}

/**
 Gets an int from the BitHolder at the given index.<br>
 A int has 32 bits.
 @param index The index to get the int at.
 @return A int representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length is bigger than the highest index of the BitHolder. */
public int getInt(int index) throws IndexOutOfBoundsException {
  return Primitive.toPrimitive(subBits(index, index + INT.getIndexSize()), int.class);
}

/**
 Gets a long from the BitHolder at the given index.<br>
 A long has 64 bits.
 @param index The index to get the long at.
 @return A long representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length is bigger than the highest index of the BitHolder. */
public long getLong(int index) throws IndexOutOfBoundsException {
  return Primitive.toPrimitive(subBits(index, index + LONG.getIndexSize()), long.class);
}

/**
 Gets a float from the BitHolder at the given index.<br>
 A float has 32 bits.
 @param index The index to get the float at.
 @return A float representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length is bigger than the highest index of the BitHolder. */
public float getFloat(int index) throws IndexOutOfBoundsException {
  return Primitive.toPrimitive(subBits(index, index + FLOAT.getIndexSize()), float.class);
}

/**
 Gets a double from the BitHolder at the given index.<br>
 A double has 64 bits.
 @param index The index to get the double at.
 @return A double representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length is bigger than the highest index of the BitHolder. */
public double getDouble(int index) throws IndexOutOfBoundsException {
  return Primitive.toPrimitive(subBits(index, index + DOUBLE.getIndexSize()), double.class);
}

/**
 Gets a byte array from the BitHolder at the given index.<br>
 A byte has 8 bits.
 @param bitHolderIndex The index to get the byte array at.
 @param arrayLength    The length of the byte array to get.
 @return A byte array representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length of the array is bigger than the highest index of the BitHolder. */
public byte[] getByteArray(int bitHolderIndex, int arrayLength) throws IndexOutOfBoundsException {
  int arrayBitLength = arrayLength * BYTE.bitSize;
  return Primitive.toPrimitive(subBits(bitHolderIndex, bitHolderIndex + arrayBitLength - 1), byte[].class);
}

/**
 Gets a short array from the BitHolder at the given index.<br>
 A short has 16 bits.
 @param bitHolderIndex The index to get the short array at.
 @param arrayLength    The length of the short array to get.
 @return A short array representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length of the array is bigger than the highest index of the BitHolder. */
public short[] getShortArray(int bitHolderIndex, int arrayLength) throws IndexOutOfBoundsException {
  int arrayBitLength = arrayLength * SHORT.bitSize;
  return Primitive.toPrimitive(subBits(bitHolderIndex, bitHolderIndex + arrayBitLength - 1), short[].class);
}

/**
 Gets a char array from the BitHolder at the given index.<br>
 A char has 16 bits.
 @param bitHolderIndex The index to get the char array at.
 @param arrayLength    The length of the char array to get.
 @return A char array representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length of the array is bigger than the highest index of the BitHolder. */
public char[] getCharArray(int bitHolderIndex, int arrayLength) throws IndexOutOfBoundsException {
  int arrayBitLength = arrayLength * CHAR.bitSize;
  return Primitive.toPrimitive(subBits(bitHolderIndex, bitHolderIndex + arrayBitLength - 1), char[].class);
}

/**
 Gets a int array from the BitHolder at the given index.<br>
 A int has 32 bits.
 @param bitHolderIndex The index to get the int array at.
 @param arrayLength    The length of the int array to get.
 @return A int array representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length of the array is bigger than the highest index of the BitHolder. */
public int[] getIntArray(int bitHolderIndex, int arrayLength) throws IndexOutOfBoundsException {
  int arrayBitLength = arrayLength * INT.bitSize;
  return Primitive.toPrimitive(subBits(bitHolderIndex, bitHolderIndex + arrayBitLength - 1), int[].class);
}

/**
 Gets a long array from the BitHolder at the given index.<br>
 A long has 64 bits.
 @param bitHolderIndex The index to get the long array at.
 @param arrayLength    The length of the long array to get.
 @return A long array representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length of the array is bigger than the highest index of the BitHolder. */
public long[] getLongArray(int bitHolderIndex, int arrayLength) throws IndexOutOfBoundsException {
  int arrayBitLength = arrayLength * LONG.bitSize;
  return Primitive.toPrimitive(subBits(bitHolderIndex, bitHolderIndex + arrayBitLength - 1), long[].class);
}

/**
 Gets a float array from the BitHolder at the given index.<br>
 A float has 32 bits.
 @param bitHolderIndex The index to get the float array at.
 @param arrayLength    The length of the float array to get.
 @return A float array representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length of the array is bigger than the highest index of the BitHolder. */
public float[] getFloatArray(int bitHolderIndex, int arrayLength) throws IndexOutOfBoundsException {
  int arrayBitLength = arrayLength * FLOAT.bitSize;
  return Primitive.toPrimitive(subBits(bitHolderIndex, bitHolderIndex + arrayBitLength - 1), float[].class);
}

/**
 Gets a double array from the BitHolder at the given index.<br>
 A double has 64 bits.
 @param bitHolderIndex The index to get the double array at.
 @param arrayLength    The length of the double array to get.
 @return A double array representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length of the array is bigger than the highest index of the BitHolder. */
public double[] getDoubleArray(int bitHolderIndex, int arrayLength) throws IndexOutOfBoundsException {
  int arrayBitLength = arrayLength * DOUBLE.bitSize;
  return Primitive.toPrimitive(subBits(bitHolderIndex, bitHolderIndex + arrayBitLength - 1), double[].class);
}


// Object overrides

@Override
public boolean equals(@Nullable Object obj) {
  if (this == obj) {return true;}
  if (obj == null || getClass() != obj.getClass()) {return false;}

  BitHolder bits = (BitHolder) obj;
  return this.size == bits.size && Objects.equals(this.toString(), bits.toString());
}

@Override
public int hashCode() {
  return Objects.hash(this.size, bitSet);
}


// Iterator override

/**
 Iterates over the bits in this BitHolder.
 @return An {@link Iterator} that iterates over the bits that the BitHolder contains. */
@Override
public @NotNull Iterator<Boolean> iterator() {
  return new Iterator<Boolean>() {
    private int index = -1;

    @Override
    public boolean hasNext() {
      return index < highestIndex();
    }

    @Override
    public Boolean next() {
      index++;
      return bitSet.get(index);
    }
  };
}
}