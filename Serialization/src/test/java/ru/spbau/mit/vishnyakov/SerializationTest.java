package ru.spbau.mit.vishnyakov;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.*;

public class SerializationTest {

    public static final int TESTCOUNT = 100;

    Random random;

    ByteArrayOutputStream outputStream;

    /**
     * Generates random string.
     * @return result of generation.
     */

    public static String generateString() {
        return UUID.randomUUID().toString();
    }

    @Before
    public void init() {
        random = new Random();
        outputStream = new ByteArrayOutputStream();
    }

    @Test
    public void serializeIntTest() throws Exception {
        int[] array = new int[TESTCOUNT];

        for (int i = 0; i < TESTCOUNT; i++) {
            array[i] = random.nextInt();
            Serialization.serializeInt(array[i], outputStream);
        }

        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        for (int i = 0; i < TESTCOUNT; i++) {
            assertEquals(array[i], Serialization.deserializeInt(inputStream));
        }
    }

    @Test
    public void serializeLongTest() throws Exception {
        long[] array = new long[TESTCOUNT];

        for (int i = 0; i < TESTCOUNT; i++) {
            array[i] = random.nextLong();
            Serialization.serializeLong(array[i], outputStream);
        }

        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        for (int i = 0; i < TESTCOUNT; i++) {
            assertEquals(array[i], Serialization.deserializeLong(inputStream));
        }
    }

    @Test
    public void serializeByteTest() throws Exception {
        byte[] array = new byte[TESTCOUNT];

        for (int i = 0; i < TESTCOUNT; i++) {
            array[i] = (byte) (random.nextInt());
            Serialization.serializeByte(array[i], outputStream);
        }

        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        for (int i = 0; i < TESTCOUNT; i++) {
            assertEquals(array[i], (byte) Serialization.deserializeByte(inputStream));
        }
    }

    @Test
    public void serializeCharTest() throws Exception {
        char[] array = new char[TESTCOUNT];

        for (int i = 0; i < TESTCOUNT; i++) {
            array[i] = (char) random.nextInt(Character.MAX_VALUE);
            Serialization.serializeChar(array[i], outputStream);
        }

        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        for (int i = 0; i < TESTCOUNT; i++) {
            assertEquals(array[i], Serialization.deserializeChar(inputStream));
        }
    }

    @Test
    public void serializeShortTest() throws Exception {
        short[] array = new short[TESTCOUNT];

        for (int i = 0; i < TESTCOUNT; i++) {
            array[i] = (short) (random.nextInt());
            Serialization.serializeShort(array[i], outputStream);
        }

        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        for (int i = 0; i < TESTCOUNT; i++) {
            assertEquals(array[i], Serialization.deserializeShort(inputStream));
        }
    }

    @Test
    public void serializeDoubleTest() throws Exception {
        double[] array = new double[TESTCOUNT];

        for (int i = 0; i < TESTCOUNT; i++) {
            array[i] = random.nextDouble();
            Serialization.serializeDouble(array[i], outputStream);
        }

        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        for (int i = 0; i < TESTCOUNT; i++) {
            assertTrue(array[i] == Serialization.deserializeDouble(inputStream));
        }
    }

    @Test
    public void serializeFloatTest() throws Exception {
        float[] array = new float[TESTCOUNT];

        for (int i = 0; i < TESTCOUNT; i++) {
            array[i] = random.nextFloat();
            Serialization.serializeFloat(array[i], outputStream);
        }

        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        for (int i = 0; i < TESTCOUNT; i++) {
            assertTrue(array[i] == Serialization.deserializeFloat(inputStream));
        }
    }

    @Test
    public void serializeBooleanTest() throws Exception {
        boolean[] array = new boolean[TESTCOUNT];

        for (int i = 0; i < TESTCOUNT; i++) {
            array[i] = random.nextBoolean();
            Serialization.serializeBoolean(array[i], outputStream);
        }

        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        for (int i = 0; i < TESTCOUNT; i++) {
            assertEquals(array[i], Serialization.deserializeBoolean(inputStream));
        }
    }

    @Test
    public void serializeStringTest() throws Exception {
        String[] array = new String[TESTCOUNT];

        for (int i = 0; i < TESTCOUNT; i++) {
            array[i] = generateString();
            Serialization.serializeString(array[i], outputStream);
        }

        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        for (int i = 0; i < TESTCOUNT; i++) {
            assertEquals(array[i], Serialization.deserializeString(inputStream));
        }
    }

    @Test
    public void test() throws IOException, IllegalAccessException, InstantiationException {
        short shortField = (short) random.nextInt();
        int intField = random.nextInt();
        long longField = random.nextLong();
        byte byteField = (byte) random.nextInt();
        boolean booleanField = random.nextBoolean();
        float floatField = random.nextFloat();
        double doubleField = random.nextDouble();
        String stringField = generateString();

        TestClass t = new TestClass(shortField, intField, longField, byteField, booleanField, floatField, doubleField, stringField);
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        Serialization.serialize(t, os);

        InputStream is = new ByteArrayInputStream(os.toByteArray());
        TestClass res = Serialization.deserialize(is, TestClass.class);

        assertEquals(t, res);
    }
}