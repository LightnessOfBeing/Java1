package ru.spbau.mit.vishnyakov;

public final class TestClass {
    short shortField;
    int intField;
    long longField;
    byte byteField;
    boolean booleanField;
    float floatField;
    double doubleField;
    String stringField;

    TestClass(short s, int i, long l, byte b, boolean bo, float f, double d, String st) {
        shortField = s;
        intField = i;
        longField = l;
        byteField = b;
        booleanField = bo;
        floatField = f;
        doubleField = d;
        stringField = st;
    }

    public TestClass(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TestClass)) {
            return false;
        }

        TestClass other = (TestClass) o;

        if (shortField != other.shortField) {
            return false;
        }
        if (intField != other.intField) {
            return false;
        }
        if (longField != other.longField) {
            return false;
        }
        if (byteField != other.byteField) {
            return false;
        }
        if (booleanField != other.booleanField) {
            return false;
        }
        if (Double.compare(doubleField, other.doubleField) != 0) {
            return false;
        }
        if (Float.compare(floatField, other.floatField) != 0) {
            return false;
        }
        if (stringField == null && other.stringField != null || stringField != null && other.stringField == null) {
            return false;
        }
        return stringField == null && other.stringField == null || stringField.equals(other.stringField);
    }

    @Override
    public int hashCode() {
        return intField ^ (int) longField;
    }
}
