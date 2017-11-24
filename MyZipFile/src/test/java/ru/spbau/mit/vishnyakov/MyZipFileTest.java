package ru.spbau.mit.vishnyakov;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class MyZipFileTest {
    @Test
    public void unzip_simple_regex() throws Exception {
        String directory = System.getProperty("user.dir") + "/src/main/resources";

        MyZipFile file = new MyZipFile(System.getProperty("user.dir") + "/src/main/resources", "a.*");

        file.unzip();

        File file1 = new File(directory, "aaa");
        boolean check1 = file1.exists();
        assertTrue(check1);

        File file2 = new File(directory, "acccdd");
        boolean check2 = file2.exists();
        assertTrue(check2);

        File file3 = new File(directory, "bbb");
        boolean check3 = file3.exists();
        assertFalse(check3);

        File file4 = new File(directory, "ccc");
        boolean check4 = file4.exists();
        assertFalse(check4);

        File file5 = new File(directory, "baaan");
        boolean check5 = file5.exists();
        assertFalse(check5);

        file1.delete();
        file2.delete();
    }

    @Test
    public void unzip_complex_regex() throws Exception {
        String directory = System.getProperty("user.dir") + "/src/main/resources";

        MyZipFile file = new MyZipFile(System.getProperty("user.dir") + "/src/main/resources", "[b-z]aaa.+");

        file.unzip();

        File file1 = new File(directory, "aaa");
        boolean check1 = file1.exists();
        assertFalse(check1);

        File file2 = new File(directory, "acccdd");
        boolean check2 = file2.exists();
        assertFalse(check2);

        File file3 = new File(directory, "bbb");
        boolean check3 = file3.exists();
        assertFalse(check3);

        File file4 = new File(directory, "ccc");
        boolean check4 = file4.exists();
        assertFalse(check4);

        File file5 = new File(directory, "baaan");
        boolean check5 = file5.exists();
        assertTrue(check5);

        file5.delete();
    }

}