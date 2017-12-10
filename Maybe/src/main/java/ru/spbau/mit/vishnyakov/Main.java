package ru.spbau.mit.vishnyakov;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

@SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
public class Main {

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

    @NotNull
    public static ArrayList<String> readAndSquare(@NotNull String path1, @NotNull String path2) throws IOException, MyException {
        File input = new File(path1);
        File output = new File(path2);
        Maybe<Integer> m;
        output.createNewFile();
        ArrayList<String> written = new ArrayList<>();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path2)); Scanner sIn = new Scanner(input)) {
            while (sIn.hasNext()) {
                m = Main.readNumber(sIn);
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
        output.delete();
        return written;
    }
}
