package ru.spbau.mit.vishnyakov;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class MyZipFileTest {
    @Test
    public void unzip_simple_regex() throws Exception {
        String directory = System.getProperty("user.dir") + "/src/main/resources";

        MyZipFile file = new MyZipFile(System.getProperty("user.dir") + "/src/main/resources", "a.*");

        file.unzip();

        File file1 = new File(directory, "aaa");
        assertTrue(file1.exists());

        File file2 = new File(directory, "acccdd");
        assertTrue(file2.exists());

        File file3 = new File(directory, "bbb");
        assertFalse(file3.exists());

        File file4 = new File(directory, "ccc");
        assertFalse(file4.exists());

        File file5 = new File(directory, "baaan");
        assertFalse(file5.exists());

        file1.delete();
        file2.delete();
    }

    @Test
    public void unzip_complex_regex() throws Exception {
        String directory = System.getProperty("user.dir") + "/src/main/resources";

        MyZipFile file = new MyZipFile(System.getProperty("user.dir") + "/src/main/resources", "[b-z]aaa.+");

        file.unzip();

        File file1 = new File(directory, "aaa");
        assertFalse(file1.exists());

        File file2 = new File(directory, "acccdd");
        assertFalse(file2.exists());

        File file3 = new File(directory, "bbb");
        assertFalse(file3.exists());

        File file4 = new File(directory, "ccc");
        assertFalse(file4.exists());

        File file5 = new File(directory, "baaan");
        assertTrue(file5.exists());

        file5.delete();
    }

}