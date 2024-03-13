package me.tye.easybits;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;

import static me.tye.easybits.ErrorMessages.notPrimitive;

/**
 This class has methods that allow for the easy conversion between primitives & the {@link BitHolder} class. */
public enum Primitive {
  BOOLEAN(1, boolean.class, Boolean.class),
  BYTE(8, byte.class, Byte.class),
  SHORT(16, short.class, Short.class),
  CHAR(16, char.class, Character.class),
  INT(32, int.class, Integer.class),
  FLOAT(32, float.class, Float.class),
  LONG(64, long.class, Long.class),
  DOUBLE(64, double.class, Double.class),
  ;


/**
 Contains how many bits is used to store each primitive. For primitive arrays it's how many bits are used to store a single instance of the primitive the array represents. */
public final int bitSize;
/**
 Contains the class the enum represents. */
public final Class<?> primitiveClass;
/**
 Contains the wrapper class for the primitive that this enum represents. */
public final Class<?> wrapperClass;

/**
 Instantiates an enum that represents a primitive.
 @param bitSize        The amount of bits that make up the primitive.
 @param primitiveClass The class of the primitive.
 @param wrapperClass   The wrapper class of the primitive. */
Primitive(int bitSize, @NotNull Class<?> primitiveClass, @NotNull Class<?> wrapperClass) {
  this.bitSize = bitSize;
  this.primitiveClass = primitiveClass;
  this.wrapperClass = wrapperClass;
}

/**
 @return The size of the primitive minus 1. */
public int getIndexSize() {
  return bitSize - 1;
}

/**
 Gets the enum that represents the given primitive class or primitive array class.
 @param clazz The given primitive class.
 @return The enum that represents the given primitive class or primitive array class.
 @throws IllegalArgumentException If the given class wasn't a primitive class or primitive array class. */
public static @NotNull Primitive fromClass(@NotNull Class<?> clazz) throws IllegalArgumentException {
  if (clazz.isArray()) clazz = clazz.getComponentType(); // Converts arrays to component types.

  for (Primitive value : Primitive.values()) {
    if (value.primitiveClass.equals(clazz) || value.wrapperClass.equals(clazz)) return value;
  }

  throw new IllegalArgumentException(notPrimitive(clazz.getName()));
}

/**
 Checks if the given class is a primitive class or primitive array class.
 @param clazz The given class.
 @return True if the class represents a primitive class or primitive array class. */
public static boolean isPrimitive(@NotNull Class<?> clazz) {
  if (clazz.isArray() && clazz.getComponentType().isPrimitive()) return true;
  if (!clazz.isArray() && clazz.isPrimitive()) return true;

  if (clazz.isArray()) clazz = clazz.getComponentType(); // Converts arrays to component types.

  // Checks for primitive wrappers.
  if (clazz.equals(Boolean.class) ||
      clazz.equals(Byte.class) ||
      clazz.equals(Short.class) ||
      clazz.equals(Character.class) ||
      clazz.equals(Integer.class) ||
      clazz.equals(Long.class) ||
      clazz.equals(Float.class) ||
      clazz.equals(Double.class)
  ) return true;

  return false;
}

/**
 Converts a primitive to a BitHolder representing the bits that make up the primitive.
 @param primitive The primitive object to convert to a BitHolder.
 @return The bits that make up the primitive represented as a BitHolder.
 @throws IllegalArgumentException If an object that isn't a primitive or primitive array was passed into the method. */
public static @NotNull BitHolder toBitHolder(@NotNull Object primitive) throws IllegalArgumentException {
  Class<?> primitiveClass = primitive.getClass();

  // Object must be a primitive or primitive array.
  if (!isPrimitive(primitiveClass)) throw new IllegalArgumentException(notPrimitive(primitiveClass.getName()));

  // Converts any primitive into a primitive array.
  if (!primitiveClass.isArray()) {
    Object array = Array.newInstance(primitiveClass, 1);
    Array.set(array, 0, primitive);
    primitive = array;
  }

  Primitive primitiveType = fromClass(primitiveClass);
  int length = Array.getLength(primitive);
  boolean[] bits = new boolean[length * primitiveType.bitSize];

  for (int primitiveArrayIndex = 0; primitiveArrayIndex < length; primitiveArrayIndex++) {
    Object primitiveInstance = Array.get(primitive, primitiveArrayIndex);

    switch (primitiveType) {
    case BOOLEAN: {
      for (int i = 0; i < primitiveType.bitSize; i++) {
        bits[primitiveArrayIndex] = (boolean) primitiveInstance;
      }
      break;
    }

    case BYTE: {
      for (int i = 0; i < primitiveType.bitSize; i++) {
        // Shifts the int value so that the current bit being evaluated is in the least significant position.
        byte shifted = (byte) (((byte) primitiveInstance) << i);
        shifted = (byte) (shifted >>> primitiveType.bitSize - 1);

        int bitIndex = i + (primitiveArrayIndex * primitiveType.bitSize); // Offsets the inserted bits depending on the current array iteration.
        boolean bitValue = (shifted & 0x1) == 1; // Determines if the bit was high or low.

        bits[bitIndex] = bitValue;
      }
      break;
    }
    case SHORT: {
      for (int i = 0; i < primitiveType.bitSize; i++) {
        // Shifts the int value so that the current bit being evaluated is in the least significant position.
        short shifted = (short) (((short) primitiveInstance) << i);
        shifted = (short) (shifted >>> primitiveType.bitSize - 1);

        int bitIndex = i + (primitiveArrayIndex * primitiveType.bitSize); // Offsets the inserted bits depending on the current array iteration.
        boolean bitValue = (shifted & 0x1) == 1; // Determines if the bit was high or low.

        bits[bitIndex] = bitValue;
      }
      break;
    }
    case CHAR: {
      for (int i = 0; i < primitiveType.bitSize; i++) {
        // Shifts the int value so that the current bit being evaluated is in the least significant position.
        char shifted = (char) (((char) primitiveInstance) << i);
        shifted = (char) (shifted >>> primitiveType.bitSize - 1);

        int bitIndex = i + (primitiveArrayIndex * primitiveType.bitSize); // Offsets the inserted bits depending on the current array iteration.
        boolean bitValue = (shifted & 0x1) == 1; // Determines if the bit was high or low.

        bits[bitIndex] = bitValue;
      }
      break;
    }

    case FLOAT: {
      // Converts the float value to an int.
      primitiveInstance = Float.floatToRawIntBits((float) primitiveInstance);
      // Falls into int handling.
    }
    case INT: {
      for (int i = 0; i < primitiveType.bitSize; i++) {
        // Shifts the int value so that the current bit being evaluated is in the least significant position.
        int shifted = (((int) primitiveInstance) << i);
        shifted = (shifted >>> primitiveType.bitSize-1);

        int bitIndex = i + (primitiveArrayIndex * primitiveType.bitSize); // Offsets the inserted bits depending on the current array iteration.
        boolean bitValue = (shifted & 0x1) == 1; // Determines if the bit was high or low.

        bits[bitIndex] = bitValue;
      }
      break;
    }

    case DOUBLE: {
      // Converts the double value to a long.
      primitiveInstance = Double.doubleToRawLongBits((double) primitiveInstance);
      // Falls into long handling
    }
    case LONG: {
      for (int i = 0; i < primitiveType.bitSize; i++) {
        // Shifts the int value so that the current bit being evaluated is in the least significant position.
        long shifted = (((long) primitiveInstance) << i);
        shifted = (shifted >>> primitiveType.bitSize-1);

        int bitIndex = i + (primitiveArrayIndex * primitiveType.bitSize); // Offsets the inserted bits depending on the current array iteration.
        boolean bitValue = (shifted & 0x1) == 1; // Determines if the bit was high or low.

        bits[bitIndex] = bitValue;
      }
      break;
    }
    }

  }

  return new BitHolder(bits);
}


/**
 Converts a BitHolder to the given primitive.<br>
 If there are too few bits to make up a primitive extra padding bits will be added to the end of the BitHolder.<br>
 If there are more bits than make up the primitive bits with a higher index will be removed (this doesn't apply to arrays).<br>
 @param bits           The BitHolder to convert.
 @param primitiveClass The primitive class to convert the BitHolder to.
 @param <T>            The primitive class the BitHolder will be returned as.
 @return The BitHolder as a primitive.
 @throws IllegalArgumentException If the given class isn't a primitive or primitive array. */
public static <T> @NotNull T toPrimitive(@NotNull BitHolder bits, @NotNull Class<T> primitiveClass) throws IllegalArgumentException {
  Primitive primitive = fromClass(primitiveClass);

  // Clazz must be a primitive or primitive array.
  if (!isPrimitive(primitiveClass))
    throw new IllegalArgumentException(notPrimitive(primitiveClass.getName()));

  // If there are fewer bits than make up the primitive add padding bits.
  if (bits.size() < primitive.bitSize) {
    int increaseBy = primitive.bitSize - bits.size();
    bits.add(new boolean[increaseBy]);
  }

  // If there are more bits than make up the primitive remove the extra bits.
  // The bits with a higher index will be removed first.
  if (!primitiveClass.isArray() && bits.size() > primitive.bitSize) {
    bits = bits.shiftLeft(bits.size() - primitive.bitSize);
    bits.remove(primitive.bitSize, bits.size());
  }

  int length = 1;

  // If the primitive is an array.
  // Determines how many elements will be in the returned primitive array.
  if (primitiveClass.isArray()) {
    length = bits.highestIndex() / primitive.bitSize;

    // Rounds the value up if there is a remainder
    if (bits.highestIndex() % primitive.bitSize != 0) {
      length++;
    }
  }

  // Returns early if it's a boolean type since they are easy to parse.
  if (primitive == BOOLEAN) {
    if (primitiveClass.isArray()) {
      return (T) bits.toBoolArray();
    } else {
      return (T) (Boolean) bits.get(0);
    }
  }

  Object primitiveArray = Array.newInstance(primitive.primitiveClass, length);

  for (int i = 0; i < length; i++) {
    long primitiveAsLongBits = getPrimitiveAsLongBits(bits, i, primitive);

    // Puts the primitive value into the array.
    switch (primitive) {
    case BYTE: Array.setByte(primitiveArray, i, (byte) primitiveAsLongBits); break;
    case SHORT: Array.setShort(primitiveArray, i, (short) primitiveAsLongBits); break;
    case CHAR: Array.setChar(primitiveArray, i, (char) primitiveAsLongBits); break;
    case INT: Array.setInt(primitiveArray, i, (int) primitiveAsLongBits); break;
    case FLOAT: Array.setFloat(primitiveArray, i, Float.intBitsToFloat((int) primitiveAsLongBits)); break;
    case LONG: Array.setLong(primitiveArray, i, primitiveAsLongBits); break;
    case DOUBLE: Array.setDouble(primitiveArray, i, Double.longBitsToDouble(primitiveAsLongBits)); break;
    }

  }

  if (primitiveClass.isArray()) return (T) primitiveArray;
  return (T) Array.get(primitiveArray, 0);
}

/**
 Gets the bits from the primitive at index i within the array & stores them in a long.
 * @param bits The bits of the primitive.
 * @param i The index of the primitive within the array.
 * @param primitive The type of primitive that is being parsed.
 * @return The bits from the primitive at index i in a long.
 */
private static long getPrimitiveAsLongBits(@NotNull BitHolder bits, int i, @NotNull Primitive primitive) {
  // Gets the boolean array that represents the primitive bits
  int primitiveIndex = (i + 1) * primitive.bitSize;
  primitiveIndex--; // Subtracts one to get the index.
  boolean[] primitiveBits = bits.get(i * primitive.bitSize, primitiveIndex);

  // Creates a temporary var to hold the primitive value.
  long primitiveHolder = 0;

  // Iterates over the high bits of the primitive.
  for (int primitiveBitIndex = 0; primitiveBitIndex < primitive.bitSize; primitiveBitIndex++) {
    boolean bit = primitiveBits[primitiveBitIndex];
    if (!bit) continue;

    // "Adds" the primitive bit to the temporary var value at the correct offset.
    primitiveHolder = (primitiveHolder | 1L << (primitive.bitSize - primitiveBitIndex - 1));
  }

  return primitiveHolder;
}

}
