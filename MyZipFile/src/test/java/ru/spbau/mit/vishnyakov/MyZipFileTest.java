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
        assertEquals(true, check1);

        File file2 = new File(directory, "acccdd");
        boolean check2 = file2.exists();
        assertEquals(true, check2);

        File file3 = new File(directory, "bbb");
        boolean check3 = file3.exists();
        assertEquals(false, check3);

        File file4 = new File(directory, "ccc");
        boolean check4 = file4.exists();
        assertEquals(false, check4);

        File file5 = new File(directory, "baaan");
        boolean check5 = file5.exists();
        assertEquals(false, check5);

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
        assertEquals(false, check1);

        File file2 = new File(directory, "acccdd");
        boolean check2 = file2.exists();
        assertEquals(false, check2);

        File file3 = new File(directory, "bbb");
        boolean check3 = file3.exists();
        assertEquals(false, check3);

        File file4 = new File(directory, "ccc");
        boolean check4 = file4.exists();
        assertEquals(false, check4);

        File file5 = new File(directory, "baaan");
        boolean check5 = file5.exists();
        assertEquals(true, check5);

        file5.delete();
    }

}