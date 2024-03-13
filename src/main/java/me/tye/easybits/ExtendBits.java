package me.tye.easybits;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Iterator;

@SuppressWarnings ("unused")
public class ExtendBits implements Iterable<Boolean>, Serializable {


private static final long serialVersionUID = -7451360967466495900L;
private final @NotNull BitHolder bitHolder;

/**
 Constructs a new bit holder with a starting size of 16 bits. */
protected ExtendBits() {
  this.bitHolder = new BitHolder();
}

/**
 Constructs a new bit holder with the given starting size.
 @param startingSize The starting size of the BitHolder. */
protected ExtendBits(int startingSize) {
  bitHolder = new BitHolder(startingSize);
}

/**
 Constructs a new bit holder with the given starting bits.
 @param bits The bit values to instantiate the BitHolder with. */
protected ExtendBits(boolean[] bits) {
  bitHolder = new BitHolder(bits);
}

/**
 Clones the given instance of the BitHolder as either a shallow or deep clone.
 @param toClone   The instance of BitHolder to return a clone of.
 @param deepClone True if the given instance should de cloned deeply, false if the given instance should be cloned shallowly.
 @throws NullPointerException If toClone is null. */
protected ExtendBits(@NotNull ExtendBits toClone, boolean deepClone) throws NullPointerException {
  bitHolder = new BitHolder(toClone.bitHolder, deepClone);
}

/**
 Adds a bit to the end of the BitHolder.
 @param bit The bit value to write. */
protected void add(boolean bit) {
  bitHolder.add(bit);
}

/**
 Adds multiple bits to the end of the BitHolder.
 @param bits The value of the bits to write. */
protected void add(boolean[] bits) {
  bitHolder.add(bits);
}

/**
 Adds multiple bits to the end of the BitHolder.
 @param bits The value of the bits to write.
 @throws NullPointerException If bits is null. */
protected void add(@NotNull BitHolder bits) throws NullPointerException {
  bitHolder.add(bits);
}

/**
 Sets the bit at the given index to the given bit value.
 @param index The given index.
 @param bit   The given bit value.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index is bigger than the highest index. */
protected void set(int index, boolean bit) throws IndexOutOfBoundsException {
  bitHolder.set(index, bit);
}

/**
 Sets the bits at the given index to the given bit values.
 @param index The given index.
 @param bits  The given bit values.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index is bigger than the highest index. */
protected void set(int index, boolean[] bits) throws IndexOutOfBoundsException {
  bitHolder.set(index, bits);
}

/**
 Sets the bits at the given index to the given bit values.
 @param index The given index.
 @param bits  The given bit values.
 @throws NullPointerException      If bits are null.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus the given bits length is bigger than the highest index. */
protected void set(int index, @NotNull BitHolder bits) throws NullPointerException, IndexOutOfBoundsException {
  bitHolder.set(index, bits);
}

/**
 Removes one bit from the end of the BitHolder.
 If the BitHolder has a size of 0 this method will return false & have no effect on the BitHolder. */
protected boolean remove() {
  return bitHolder.remove();
}

/**
 Removes the bit at the given index. Shifts any subsequent elements to the left.<br>
 @param index The index to remove the bit at.
 @return The value of the bit that was removed.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index is bigger than the highest index. */
protected boolean remove(int index) throws IndexOutOfBoundsException {
  return bitHolder.remove(index);
}

/**
 Removes the bits between the given startIndex & the given endIndex. Shifts any subsequent elements to the left.<br>
 The endIndex must be higher than the start index.
 @param startIndex The index (inclusive) to start removing the bits at.
 @param endIndex   The index (inclusive) to stop removing the bits at.
 @return The bits that were removed as BitHolder.
 @throws IllegalArgumentException  If the endIndex is bigger than the startIndex.
 @throws IndexOutOfBoundsException If either of the given indexes is negative or if they are bigger than the highest index. */
protected @NotNull BitHolder remove(int startIndex, int endIndex) throws IllegalArgumentException, IndexOutOfBoundsException {
  return bitHolder.remove(startIndex, endIndex);
}

/**
 @param index The given index.
 @return The bit value at the given index within the BitHolder.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index is bigger than the highest index. */
protected boolean get(int index) throws IndexOutOfBoundsException {
  return bitHolder.get(index);
}

/**
 If you want to get a subsection of the BitHolder as a BitHolder use {@link #subBits(int, int)}.
 @param startIndex Must be lower.
 @param endIndex   Must be higher.
 @return The bits between the startIndex (inclusive) & the endIndex (inclusive) as a boolean array.
 @throws IllegalArgumentException  If the endIndex is bigger than the startIndex.
 @throws IndexOutOfBoundsException If either of the given indexes is negative or if they are bigger than the highest index. */
protected boolean[] get(int startIndex, int endIndex) throws IllegalArgumentException, IndexOutOfBoundsException {
  return bitHolder.get(startIndex, endIndex);
}

/**
 Inserts one bit at the given index in the BitHolder. The bit at the index & any subsequent bits will be shifted right (have their index increased by one).
 @param index The index inside the BitHolder to insert the bit at.
 @param bit   The bit to insert into the BitHolder at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index is bigger than the highest index. */
protected void insert(int index, boolean bit) throws IndexOutOfBoundsException {
  bitHolder.insert(index, bit);
}

/**
 Inserts an array of bits at the given index in the BitHolder. The bit at the index & any subsequent bits will be shifted right.
 @param index The index inside the BitHolder to insert the bits at.
 @param bits  The bits to insert into the BitHolder at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index is bigger than the highest index. */
protected void insert(int index, boolean[] bits) throws IndexOutOfBoundsException {
  bitHolder.insert(index, bits);
}

/**
 Shifts the bits stored in the bit holder to the left (decreasing the index) by the given amount.<br>
 This method does not wrap around bits that exit the bounds of the array.<br>
 Any new bits added will be initially set to false.
 @param amount The amount to shift the bits by.
 @throws IllegalArgumentException If the given amount is negative. */
protected void shiftLeft(int amount) throws IllegalArgumentException {
  bitHolder.shiftLeft(amount);
}

/**
 Shifts the bits stored in the bit holder to the right (increasing the index) by the given amount.<br>
 This method does not wrap around bits that exit the bounds of the array. <br>
 Any new bits added will be initially set to false.
 @param amount The amount to shift the bits by.
 @throws IllegalArgumentException If the given amount is negative. */
protected void shiftRight(int amount) throws IllegalArgumentException {
  bitHolder.shiftRight(amount);
}

/**
 Sets all the bits in this BitHolder to high (true). */
protected void allHigh() {
  bitHolder.allHigh();
}

/**
 Sets all the bits in this BitHolder to low (false). */
public void allLow() {
  bitHolder.allLow();
}

/**
 @return True if all the bits in the BitHolder are high (true); */
public boolean areAllHigh() {
  return bitHolder.areAllHigh();
}

/**
 @return True if all the bits in the BitHolder are low (false); */
public boolean areAllLow() {
  return bitHolder.areAllLow();
}

/**
 @param index The index to gets the bits after.
 @return The bits between the index (inclusive) & the highest index as a BitHolder.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index is bigger than the highest index. */
public @NotNull BitHolder subBits(int index) throws IndexOutOfBoundsException {
  return bitHolder.subBits(index);
}

/**
 If you want to get a subsection of the BitHolder as a boolean array use {@link #get(int, int)}.
 @param startIndex Must be lower.
 @param endIndex   Must be higher.
 @return The bits between the startIndex (inclusive) & the endIndex (inclusive) as a BitHolder.
 @throws IllegalArgumentException  If the endIndex is bigger than the startIndex.
 @throws IndexOutOfBoundsException If either of the given indexes is negative or if they are bigger than the highest index. */
public @NotNull BitHolder subBits(int startIndex, int endIndex) throws IllegalArgumentException, IndexOutOfBoundsException {
  return bitHolder.subBits(startIndex, endIndex);
}

/**
 Performs the NOT logic operation on this BitHolder.
 @return A new BitHolder containing the result of the NOT logic operation on the calling BitHolder. */
public @NotNull BitHolder NOT() {
  return bitHolder.NOT();
}

/**
 Performs the AND logic operation on this BitHolder.
 @param bitsToAND The BitHolder to preform the AND operation with.
 @return A new BitHolder containing the result of the AND logic operation on the calling BitHolder.
 @throws NullPointerException If the given BitHolder was null. */
public @NotNull BitHolder AND(@NotNull BitHolder bitsToAND) throws NullPointerException {
  return bitHolder.AND(bitsToAND);
}

/**
 Performs the NAND logic operation on this BitHolder.
 @param bitsToNAND The BitHolder to preform the NAND operation with.
 @return A new BitHolder containing the result of the NAND logic operation on the calling BitHolder.
 @throws NullPointerException If the given BitHolder was null. */
public @NotNull BitHolder NAND(@NotNull BitHolder bitsToNAND) throws NullPointerException {
  return bitHolder.NAND(bitsToNAND);
}

/**
 Performs the OR logic operation on this BitHolder.
 @param bitsToOR The BitHolder to preform the OR operation with.
 @return A new BitHolder containing the result of the OR logic operation on the calling BitHolder.
 @throws NullPointerException If the given BitHolder was null. */
public @NotNull BitHolder OR(@NotNull BitHolder bitsToOR) throws NullPointerException {
  return bitHolder.OR(bitsToOR);
}

/**
 Performs the NOR logic operation on this BitHolder.
 @param bitsToNOR The BitHolder to preform the NOR operation with.
 @return A new BitHolder containing the result of the NOR logic operation on the calling BitHolder.
 @throws NullPointerException If the given BitHolder was null. */
public @NotNull BitHolder NOR(@NotNull BitHolder bitsToNOR) throws NullPointerException {
  return bitHolder.NOR(bitsToNOR);
}

/**
 Performs the XOR logic operation on this BitHolder.
 @param bitsToXOR The BitHolder to preform the XOR operation with.
 @return A new BitHolder containing the result of the XOR logic operation on the calling BitHolder.
 @throws NullPointerException If the given BitHolder was null. */
public @NotNull BitHolder XOR(@NotNull BitHolder bitsToXOR) throws NullPointerException {
  return bitHolder.XOR(bitsToXOR);
}

/**
 Performs the XNOR logic operation on this BitHolder.
 @param bitsToXNOR The BitHolder to preform the XNOR operation with.
 @return A new BitHolder containing the result of the XNOR logic operation on the calling BitHolder.
 @throws NullPointerException If the given BitHolder was null. */
public @NotNull BitHolder XNOR(@NotNull BitHolder bitsToXNOR) throws NullPointerException {
  return bitHolder.XNOR(bitsToXNOR);
}

/**
 @return This size of BitHolder. */
public int size() {
  return bitHolder.size();
}

/**
 @return The highest index of an element within the BitHolder. */
public int highestIndex() {
  return bitHolder.highestIndex();
}

/**
 @return True if the BitHolder has a size of 0. False otherwise. */
public boolean isEmpty() {
  return bitHolder.isEmpty();
}

/**
 Removes all the bits the BitHolder contains.<br>
 If you instead want to set all the bits to low see {@link #allLow()} */
public void clear() {
  bitHolder.clear();
}

/**
 Iterates over the bits in this BitHolder.
 @return An {@link Iterator} that iterates over the bits that the BitHolder contains. */
public @NotNull Iterator<Boolean> iterator() {
  return bitHolder.iterator();
}

/**
 See {@link #toBitString()} for a non-formatted output, with just the raw bits present.
 @return A formatted output of the bits that this BitHolder contains. */
public @NotNull String toString() {
  return bitHolder.toString();
}

/**
 See {@link #toString()} for a formatted output. This is often easier to read.
 @return A raw output of the bits this BitHolder contains. */
public @NotNull String toBitString() {
  return bitHolder.toString();
}


public boolean equals(@Nullable Object obj) {
  return bitHolder.equals(obj);
}


public int hashCode() {
  return bitHolder.hashCode();
}

/**
 Concatenates this BitHolder with the given holders. They are concatenated in the order they were given.<br>
 This method has no effect on the instance it was called on.
 @param holders An array of BitHolders to concatenate together.
 @return The BitHolder that is result of the concatenation.
 @throws NullPointerException If any of the given BitHolders are null. */
public @NotNull BitHolder concat(@NotNull BitHolder... holders) {
  return bitHolder.concat(holders);
}

/**
 Converts the BitHolder into a byte stream.
 @return A {@link ByteArrayInputStream} that contains the content of the BitHolder. */
public @NotNull ByteArrayInputStream toByteStream() {
  return bitHolder.toByteStream();
}

/**
 @return A boolean array that represents the bits contained by this BitHolder. */
public boolean[] toBoolArray() {
  return bitHolder.toBoolArray();
}

/**
 @return A byte array that represents the bits contained by this BitHolder. */
public byte[] toByteArray() {
  return bitHolder.toByteArray();
}

/**
 Adds the bits from a primitive or primitive array to this BitHolder.
 @param primitive The primitive to add.
 @throws NullPointerException     If the given object is null.
 @throws IllegalArgumentException If the given argument wasn't a primitive. */
public void add(@NotNull Object primitive) throws NullPointerException, IllegalArgumentException {
  bitHolder.add(primitive);
}

/**
 Adds the bits from the byte to this BitHolder.@param byte_ The given byte. */
public void add(byte byte_) {
  bitHolder.add(byte_);
}

/**
 Adds the bits from the short to this BitHolder.@param short_ The given short. */
public void add(short short_) {
  bitHolder.add(short_);
}

/**
 Adds the bits from the char to this BitHolder.@param char_ The given char. */
public void add(char char_) {
  bitHolder.add(char_);
}

/**
 Adds the bits from the int to this BitHolder.@param int_ The given int. */
public void add(int int_) {
  bitHolder.add(int_);
}

/**
 Adds the bits from the long to this BitHolder.@param long_ The given long. */
public void add(long long_) {
  bitHolder.add(long_);
}

/**
 Adds the bits from the float to this BitHolder.@param float_ The given float. */
public void add(float float_) {
  bitHolder.add(float_);
}

/**
 Adds the bits from the double to this BitHolder.@param double_ The given double. */
public void add(double double_) {
  bitHolder.add(double_);
}

/**
 Adds the bits from the byte array to this BitHolder.@param bytes The given byte array. */
public void add(byte[] bytes) {
  bitHolder.add(bytes);
}

/**
 Adds the bits from the short array to this BitHolder.@param shorts The given short array. */
public void add(short[] shorts) {
  bitHolder.add(shorts);
}

/**
 Adds the bits from the chars array to this BitHolder.@param chars The given chars array. */
public void add(char[] chars) {
  bitHolder.add(chars);
}

/**
 Adds the bits from the int array to this BitHolder.@param ints The given int array. */
public void add(int[] ints) {
  bitHolder.add(ints);
}

/**
 Adds the bits from the long array to this BitHolder.@param longs The given long array. */
public void add(long[] longs) {
  bitHolder.add(longs);
}

/**
 Adds the bits from the float array to this BitHolder.@param floats The given float array. */
public void add(float[] floats) {
  bitHolder.add(floats);
}

/**
 Adds the bits from the double array to this BitHolder.@param doubles The given double array. */
public void add(double[] doubles) {
  bitHolder.add(doubles);
}

/**
 Sets the bits from a primitive or primitive array in this BitHolder at the given index.
 @param index     The index to set the bits at.
 @param primitive The primitive value to set the bits to.
 @throws NullPointerException      If the given object is null.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus the given primitive size is bigger than the highest index. */
public void set(int index, @NotNull Object primitive) throws NullPointerException, IllegalArgumentException {
  bitHolder.set(index, primitive);
}

/**
 Sets the bits at the given index to the bits of the given byte.<br>
 A byte has 8 bits.
 @param index The index to set the bits at.
 @param byte_ The byte to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus the given primitive size is bigger than the highest index. */
public void set(int index, byte byte_) throws IndexOutOfBoundsException {
  bitHolder.set(index, byte_);
}

/**
 Sets the bits at the given index to the bits of the given short.<br>
 A short has 16 bits.
 @param index  The index to set the bits at.
 @param short_ The short to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus the given primitive size is bigger than the highest index. */
public void set(int index, short short_) throws IndexOutOfBoundsException {
  bitHolder.set(index, short_);
}

/**
 Sets the bits at the given index to the bits of the given char.<br>
 A char has 16 bits.
 @param index The index to set the bits at.
 @param char_ The char to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus the given primitive size is bigger than the highest index. */
public void set(int index, char char_) throws IndexOutOfBoundsException {
  bitHolder.set(index, char_);
}

/**
 Sets the bits at the given index to the bits of the given int.<br>
 An int has 32 bits.
 @param index The index to set the bits at.
 @param int_  The int to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus the given primitive size is bigger than the highest index. */
public void set(int index, int int_) throws IndexOutOfBoundsException {
  bitHolder.set(index, int_);
}

/**
 Sets the bits at the given index to the bits of the given long.<br>
 A long has 64 bits.
 @param index The index to set the bits at.
 @param long_ The long to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus the given primitive size is bigger than the highest index. */
public void set(int index, long long_) throws IndexOutOfBoundsException {
  bitHolder.set(index, long_);
}

/**
 Sets the bits at the given index to the bits of the given float.<br>
 A float has 32 bits.
 @param index  The index to set the bits at.
 @param float_ The float to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus the given primitive size is bigger than the highest index. */
public void set(int index, float float_) throws IndexOutOfBoundsException {
  bitHolder.set(index, float_);
}

/**
 Sets the bits at the given index to the bits of the given double.<br>
 A double has 64 bits.
 @param index   The index to set the bits at.
 @param double_ The double to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus the given primitive size is bigger than the highest index. */
public void set(int index, double double_) throws IndexOutOfBoundsException {
  bitHolder.set(index, double_);
}

/**
 Sets the bits at the given index to the bits of the given byte array.<br>
 A byte has 8 bits.
 @param index The index to set the bits at.
 @param bytes The bytes to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus primitive array bits are bigger than the highest index. */
public void set(int index, byte[] bytes) throws IndexOutOfBoundsException {
  bitHolder.set(index, bytes);
}

/**
 Sets the bits at the given index to the bits of the given short array.<br>
 A short has 16 bits.
 @param index  The index to set the bits at.
 @param shorts The shorts to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus primitive array bits are bigger than the highest index. */
public void set(int index, short[] shorts) throws IndexOutOfBoundsException {
  bitHolder.set(index, shorts);
}

/**
 Sets the bits at the given index to the bits of the given char array.<br>
 A chars has 16 bits.
 @param index The index to set the bits at.
 @param chars The chars to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus primitive array bits are bigger than the highest index. */
public void set(int index, char[] chars) throws IndexOutOfBoundsException {
  bitHolder.set(index, chars);
}

/**
 Sets the bits at the given index to the bits of the given int array.<br>
 A ints has 32 bits.
 @param index The index to set the bits at.
 @param ints  The ints to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus primitive array bits are bigger than the highest index. */
public void set(int index, int[] ints) throws IndexOutOfBoundsException {
  bitHolder.set(index, ints);
}

/**
 Sets the bits at the given index to the bits of the given long array.<br>
 A long has 64 bits.
 @param index The index to set the bits at.
 @param longs The longs to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus primitive array bits are bigger than the highest index. */
public void set(int index, long[] longs) throws IndexOutOfBoundsException {
  bitHolder.set(index, longs);
}

/**
 Sets the bits at the given index to the bits of the given float array.<br>
 A float has 32 bits.
 @param index  The index to set the bits at.
 @param floats The floats to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus primitive array bits are bigger than the highest index. */
public void set(int index, float[] floats) throws IndexOutOfBoundsException {
  bitHolder.set(index, floats);
}

/**
 Sets the bits at the given index to the bits of the given double array.<br>
 A double has 64 bits.
 @param index   The index to set the bits at.
 @param doubles The doubles to set the bits to.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index plus primitive array bits are bigger than the highest index. */
public void set(int index, double[] doubles) throws IndexOutOfBoundsException {
  bitHolder.set(index, doubles);
}

/**
 Gets a primitive (determined by the given class) from the BitHolder at the given index.
 @param index          The index to get the primitive from in the BitHolder.
 @param primitiveClass The primitive class to get the bits as.
 @return The bits at the given index from the BitHolder as a primitive.
 @throws NullPointerException      If the primitiveClass is null.
 @throws IllegalArgumentException  If the given primitive class isn't a primitive, primitive array or a wrapper for either.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the primitive bit length is bigger than the highest index. */
public <T> T get(int index, @NotNull Class<T> primitiveClass) throws NullPointerException, IllegalArgumentException, IndexOutOfBoundsException {
  return bitHolder.get(index, primitiveClass);
}

/**
 Gets a byte from the BitHolder at the given index.<br>
 A byte has 8 bits.
 @param index The index to get the byte at.
 @return A byte representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length is bigger than the highest index of the BitHolder. */
public byte getByte(int index) throws IndexOutOfBoundsException {
  return bitHolder.getByte(index);
}

/**
 Gets a short from the BitHolder at the given index.<br>
 A short has 16 bits.
 @param index The index to get the short at.
 @return A short representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length is bigger than the highest index of the BitHolder. */
public short getShort(int index) throws IndexOutOfBoundsException {
  return bitHolder.getShort(index);
}

/**
 Gets a char from the BitHolder at the given index.<br>
 A char has 16 bits.
 @param index The index to get the char at.
 @return A char representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length is bigger than the highest index of the BitHolder. */
public char getChar(int index) throws IndexOutOfBoundsException {
  return bitHolder.getChar(index);
}

/**
 Gets an int from the BitHolder at the given index.<br>
 A int has 32 bits.
 @param index The index to get the int at.
 @return A int representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length is bigger than the highest index of the BitHolder. */
public int getInt(int index) throws IndexOutOfBoundsException {
  return bitHolder.getInt(index);
}

/**
 Gets a long from the BitHolder at the given index.<br>
 A long has 64 bits.
 @param index The index to get the long at.
 @return A long representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length is bigger than the highest index of the BitHolder. */
public long getLong(int index) throws IndexOutOfBoundsException {
  return bitHolder.getLong(index);
}

/**
 Gets a float from the BitHolder at the given index.<br>
 A float has 32 bits.
 @param index The index to get the float at.
 @return A float representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length is bigger than the highest index of the BitHolder. */
public float getFloat(int index) throws IndexOutOfBoundsException {
  return bitHolder.getFloat(index);
}

/**
 Gets a double from the BitHolder at the given index.<br>
 A double has 64 bits.
 @param index The index to get the double at.
 @return A double representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length is bigger than the highest index of the BitHolder. */
public double getDouble(int index) throws IndexOutOfBoundsException {
  return bitHolder.getDouble(index);
}

/**
 Gets a byte array from the BitHolder at the given index.<br>
 A byte has 8 bits.
 @param index       The index to get the byte array at.
 @param arrayLength The length of the byte array to get.
 @return A byte array representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length of the array is bigger than the highest index of the BitHolder. */
public byte[] getByteArray(int index, int arrayLength) throws IndexOutOfBoundsException {
  return bitHolder.getByteArray(index, arrayLength);
}

/**
 Gets a short array from the BitHolder at the given index.<br>
 A short has 16 bits.
 @param index       The index to get the short array at.
 @param arrayLength The length of the short array to get.
 @return A short array representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length of the array is bigger than the highest index of the BitHolder. */
public short[] getShortArray(int index, int arrayLength) throws IndexOutOfBoundsException {
  return bitHolder.getShortArray(index, arrayLength);
}

/**
 Gets a char array from the BitHolder at the given index.<br>
 A char has 16 bits.
 @param index       The index to get the char array at.
 @param arrayLength The length of the char array to get.
 @return A char array representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length of the array is bigger than the highest index of the BitHolder. */
public char[] getCharArray(int index, int arrayLength) throws IndexOutOfBoundsException {
  return bitHolder.getCharArray(index, arrayLength);
}

/**
 Gets a int array from the BitHolder at the given index.<br>
 A int has 32 bits.
 @param index       The index to get the int array at.
 @param arrayLength The length of the int array to get.
 @return A int array representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length of the array is bigger than the highest index of the BitHolder. */
public int[] getIntArray(int index, int arrayLength) throws IndexOutOfBoundsException {
  return bitHolder.getIntArray(index, arrayLength);
}

/**
 Gets a long array from the BitHolder at the given index.<br>
 A long has 64 bits.
 @param index       The index to get the long array at.
 @param arrayLength The length of the long array to get.
 @return A long array representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length of the array is bigger than the highest index of the BitHolder. */
public long[] getLongArray(int index, int arrayLength) throws IndexOutOfBoundsException {
  return bitHolder.getLongArray(index, arrayLength);
}

/**
 Gets a float array from the BitHolder at the given index.<br>
 A float has 32 bits.
 @param index       The index to get the float array at.
 @param arrayLength The length of the float array to get.
 @return A float array representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length of the array is bigger than the highest index of the BitHolder. */
public float[] getFloatArray(int index, int arrayLength) throws IndexOutOfBoundsException {
  return bitHolder.getFloatArray(index, arrayLength);
}

/**
 Gets a double array from the BitHolder at the given index.<br>
 A double has 64 bits.
 @param index       The index to get the double array at.
 @param arrayLength The length of the double array to get.
 @return A double array representing the bits at the given index.
 @throws IndexOutOfBoundsException If the given index is negative or if the given index summed with the bit length of the array is bigger than the highest index of the BitHolder. */
public double[] getDoubleArray(int index, int arrayLength) throws IndexOutOfBoundsException {
  return bitHolder.getDoubleArray(index, arrayLength);
}
}
