package ru.spbau.mit.vishnyakov;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static java.lang.Math.max;

/**
 * Class that implements unzip method.
 */

public class MyZipFile {

    /**
     * Path to the folder.
     */

    @NotNull
    private final String path;

    /**
     * Regular expression which we want to match with filenames;
     */

    @NotNull
    private final String regExpression;

    /**
     * Size of buffer.
     */

    private final int BUFFER = 1024;

    /**
     * Constructor.
     * @param pathName represents path to folder.
     * @param regex represents regular expression.
     */

    public MyZipFile(@NotNull String pathName, @NotNull String regex){
        path = pathName;
        regExpression = regex;
    }

    /**
     * Method that returns list of zip archives which are contained in path folder.
     * @return list of zip archives which are contained in path folder.
     */

    @NotNull
    private ArrayList<String> getListOfFiles(){
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        ArrayList<String> list = new ArrayList<>();

        assert listOfFiles != null;
        for (File listOfFile : listOfFiles) {
            int sz = listOfFile.getName().length();
            String subString = listOfFile.getName().substring(max(sz - 4, 0), sz);
            if (listOfFile.isFile() && subString.equals(".zip")) {
                list.add(listOfFile.getName());
            }
        }

        return list;
    }

    /**
     * Implements unzipping operation.
     * The method receives zipEntry and checks
     * if element of that entry is a file and its name matches the regular expression.
     */

    public void unzip(){
        ArrayList<String> listOfArchives = getListOfFiles();

        for (String s : listOfArchives) {
            String filename = path + "/" + s;
            File file = new File(filename);
            if (!file.exists() || !file.canRead()) {
                System.out.println("File cannot be read");
                return;
            }

            try {
                ZipFile zip = new ZipFile(filename);
                Enumeration entries = zip.entries();

                while (entries.hasMoreElements()) {
                    ZipEntry entry = (ZipEntry) entries.nextElement();

                    if (!entry.isDirectory() && entry.getName().matches(regExpression)) {
                        write(zip.getInputStream(entry),
                                new BufferedOutputStream(new FileOutputStream(
                                        new File(file.getParent(), entry.getName()))));
                    }
                }

                zip.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Writes content to unzipped file.
     * @param in - stream that we want to read from.
     * @param out - stream that we want to write to.
     * @throws IOException
     */

    @SuppressWarnings("JavaDoc")
    private void write(@NotNull InputStream in, @NotNull OutputStream out) throws IOException {
        byte[] buffer = new byte[BUFFER];
        int len;
        while ((len = in.read(buffer)) >= 0) {
            out.write(buffer, 0, len);
        }
        out.close();
        in.close();
    }
}
