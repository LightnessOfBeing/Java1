package ru.spbau.mit.vishnyakov;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.*;
import java.nio.ByteBuffer;

/**
 * Class which serializes and deserializes
 * objects fields via Java Reflection API.
 */

public class Serialization {

    /**
     * Serializes objects fields.
     * @param o object which fields we want to serialize.
     * @param outputStream where we write information.
     */

    public static void serialize(@Nullable Object o, @NotNull OutputStream outputStream) throws IOException, IllegalAccessException {
        if (o == null) {
            outputStream.write(0);
            return;
        }
        outputStream.write(1);

        Class<?> clazz = o.getClass();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {

            Class t = field.getType();

            if (t.equals(int.class)) {
                serializeInt(field.getInt(o), outputStream);
            }
            else if (t.equals(long.class)) {
                serializeLong(field.getLong(o), outputStream);
            }
            else if (t.equals(byte.class)) {
                serializeByte(field.getByte(o), outputStream);
            }
            else if (t.equals(short.class)) {
                serializeShort(field.getShort(o), outputStream);
            }
            else if (t.equals(double.class)) {
                serializeDouble(field.getDouble(o), outputStream);
            }
            else if (t.equals(float.class))  {
                serializeFloat(field.getFloat(o), outputStream);
            }
            else if (t.equals(char.class)) {
                serializeChar(field.getChar(o), outputStream);
            }
            else if (t.equals(boolean.class)) {
                serializeBoolean(field.getBoolean(o), outputStream);
            }
            else {
                serializeString((String) field.get(o), outputStream);
            }
        }
    }

    /**
     * Serializes int.
     * @param value which we write to stream.
     * @param outputStream where we write information.
     */

    public static void serializeInt(int value, @NotNull OutputStream outputStream) throws IOException {
        outputStream.write(ByteBuffer.allocate(Integer.BYTES).putInt(value).array());
    }

    /**
     *
     * @param value which we write to stream.
     * @param outputStream where we write information.
     */

    public static void serializeLong(long value, @NotNull OutputStream outputStream) throws IOException {
        outputStream.write(ByteBuffer.allocate(Long.BYTES).putLong(value).array());
    }

    /**
     * Serializes byte.
     * @param value which we write to stream.
     * @param outputStream where we write information.
     */

    public static void serializeByte(byte value, @NotNull OutputStream outputStream) throws IOException {
        outputStream.write(value);
    }

    /**
     * Serializes short.
     * @param value which we write to stream.
     * @param outputStream where we write information.
     */

    public static void serializeShort(short value, @NotNull OutputStream outputStream) throws IOException {
        outputStream.write(ByteBuffer.allocate(Short.BYTES).putShort(value).array());
    }

    /**
     * Serializes double.
     * @param value which we write to stream.
     * @param outputStream where we write information.
     */

    public static void serializeDouble(double value, @NotNull OutputStream outputStream) throws IOException {
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(value).array());
    }

    /**
     * Serializes float.
     * @param value which we write to stream.
     * @param outputStream where we write information.
     */

    public static void serializeFloat(float value, @NotNull OutputStream outputStream) throws IOException {
        outputStream.write(ByteBuffer.allocate(Float.BYTES).putFloat(value).array());
    }

    /**
     * Serializes char.
     * @param value which we write to stream.
     * @param outputStream where we write information.
     */

    public static void serializeChar(char value, @NotNull OutputStream outputStream) throws IOException {
        outputStream.write(ByteBuffer.allocate(Character.BYTES).putChar(value).array());
    }

    /**
     * Serializes boolean.
     * @param value which we write to stream.
     * @param outputStream where we write information.
     */

    public static void serializeBoolean(boolean value, @NotNull OutputStream outputStream) throws IOException {
        outputStream.write(value ? 1 : 0);
    }

    /**
     * Serializes String.
     * @param value which we write to stream.
     * @param outputStream where we write information.
     */

    public static void serializeString(@Nullable String value, @NotNull OutputStream outputStream) throws IOException {
        if (value == null) {
            outputStream.write(0);
            return;
        }
        outputStream.write(1);
        serializeInt(value.length(), outputStream);
        outputStream.write(value.getBytes());
    }

    /**
     * Deserializes fields from stream.
     * @param inputStream from where we read information.
     * @param clazz to which our object belong.
     * @param <T>  the type of the class.
     * @return object with deserialized fields.
     */

    public static <T> T deserialize(@NotNull InputStream inputStream, @NotNull Class<T> clazz) throws IOException, IllegalAccessException, InstantiationException {
        boolean isNull = deserializeBoolean(inputStream);
        if (!isNull) {
            return null;
        }

        T o = clazz.newInstance();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            Class<?> t = field.getType();

            if (t.equals(int.class)) {
                field.setInt(o, deserializeInt(inputStream));
            }
            else if (t.equals(long.class)) {
                field.setLong(o, deserializeLong(inputStream));
            }
            else if (t.equals(byte.class)) {
                field.setByte(o, deserializeByte(inputStream));
            }
            else if (t.equals(short.class)) {
                field.setShort(o, deserializeShort(inputStream));
            }
            else if (t.equals(double.class)) {
                field.setDouble(o, deserializeDouble(inputStream));
            }
            else if (t.equals(float.class))  {
                field.setFloat(o, deserializeFloat(inputStream));
            }
            else if (t.equals(char.class)) {
                field.setChar(o, deserializeChar(inputStream));
            }
            else if (t.equals(boolean.class)) {
                field.setBoolean(o, deserializeBoolean(inputStream));
            }
            else {
                field.set(o, deserializeString(inputStream));
            }
        }
        return o;
    }

    /**
     * Deserializes int.
     * @param inputStream from where we read information.
     * @return deserialized int.
     */

    public static int deserializeInt(@NotNull InputStream inputStream) throws IOException {
        byte[] bytes = new byte[Integer.BYTES];
        if (inputStream.read(bytes) != Integer.BYTES) {
            throw new IOException();
        }
        return ByteBuffer.wrap(bytes).getInt();
    }

    /**
     * Deserializes long.
     * @param inputStream from where we read information.
     * @return deserialized long.
     */

    public static long deserializeLong(@NotNull InputStream inputStream) throws IOException {
        byte[] bytes = new byte[Long.BYTES];
        if (inputStream.read(bytes) != Long.BYTES) {
            throw new IOException();
        }
        return ByteBuffer.wrap(bytes).getLong();
    }

    /**
     * Deserializes byte.
     * @param inputStream from where we read information.
     * @return deserialized byte.
     */

    public static Byte deserializeByte(@NotNull InputStream inputStream) throws IOException {
        return (byte) inputStream.read();
    }

    /**
     * Deserializes short.
     * @param inputStream from where we read information.
     * @return deserialized short.
     */

    public static short deserializeShort(@NotNull InputStream inputStream) throws IOException {
        byte[] bytes = new byte[Short.BYTES];
        if (inputStream.read(bytes) != Short.BYTES) {
            throw new IOException();
        }
        return ByteBuffer.wrap(bytes).getShort();
    }

    /**
     * Deserializes double.
     * @param inputStream from where we read information.
     * @return deserialized double.
     */

    public static double deserializeDouble(@NotNull InputStream inputStream) throws IOException {
        byte[] bytes = new byte[Double.BYTES];
        if (inputStream.read(bytes) != Double.BYTES) {
            throw new IOException();
        }
        return ByteBuffer.wrap(bytes).getDouble();
    }

    /**
     * Deserializes float.
     * @param inputStream from where we read information.
     * @return deserialized float.
     */

    public static float deserializeFloat(@NotNull InputStream inputStream) throws IOException {
        byte[] bytes = new byte[Float.BYTES];
        if (inputStream.read(bytes) != Float.BYTES) {
            throw new IOException();
        }
        return ByteBuffer.wrap(bytes).getFloat();
    }

    /**
     * Deserializes char.
     * @param inputStream from where we read information.
     * @return deserialized char.
     */

    public static char deserializeChar(@NotNull InputStream inputStream) throws IOException {
        byte[] bytes = new byte[Character.BYTES];
        if (inputStream.read(bytes) != Character.BYTES) {
            throw new IOException();
        }
        return ByteBuffer.wrap(bytes).getChar();
    }

    /**
     * Deserializes boolean.
     * @param inputStream from where we read information.
     * @return deserialized boolean.
     */

    public static boolean deserializeBoolean(@NotNull InputStream inputStream) throws IOException {
        return inputStream.read() == 1;
    }

    /**
     * Deserializes String.
     * @param inputStream from where we read information.
     * @return deserialized string.
     */

    public static String deserializeString(@NotNull InputStream inputStream) throws IOException {
        boolean isNull = deserializeBoolean(inputStream);
        if (!isNull) {
            return null;
        }
        int len = deserializeInt(inputStream);
        byte[] bytes = new byte[len];
        if (inputStream.read(bytes) != len) {
            throw new IOException();
        }
        return new String(bytes);
    }
}
