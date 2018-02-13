package ru.spbau.mit.vishnyakov;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

/**
 * This class contains additional methods that read numbers from file, square it and write to another file.
 */

@SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
public class Main {

    /**
     * Reads a single number.
     * @param s that we will use to read the input.
     * @return Maybe object, which entity depends on the input.
     */

    @Nullable
    public static Maybe<Integer> readNumber(@NotNull Scanner s) {
        String st = s.nextLine();
        Maybe<Integer> m;
        try {
            m = Maybe.just(parseInt(st));
        } catch (NumberFormatException ex) {
            m =  Maybe.nothing();
        }
        return m;
    }

    /**
     * Reads a file and saves squared number to another file.
     * @param sourcePath where input file is located.
     * @param destinationPath where the result file will be located.
     * @return
     */

    @NotNull
    public static String[] readAndSquare(@NotNull String sourcePath, @NotNull String destinationPath) throws IOException, MaybeException {
        File input = new File(sourcePath);
        File output = new File(destinationPath);
        Maybe<Integer> m;
        output.createNewFile();
        ArrayList<String> written = new ArrayList<>();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destinationPath));
             Scanner inputScanner = new Scanner(input)) {
            while (inputScanner.hasNext()) {
                m = Main.readNumber(inputScanner);
                if (m.isPresent()) {
                    writer.write(m.map(x -> x * x).get().toString() + "\n");
                    written.add(m.map(x -> x * x).get().toString());
                }
                else {
                    writer.write("null\n");
                    written.add("null");
                }
            }
        }
        String [] everything;
        try (BufferedReader br = new BufferedReader(new FileReader(destinationPath))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString().split("\\s");
        }
        return everything;
    }
}
