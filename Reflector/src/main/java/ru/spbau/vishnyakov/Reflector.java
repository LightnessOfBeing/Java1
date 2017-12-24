package ru.spbau.vishnyakov;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class that gets all inner structure of other class and prints it to file.
 */

public class Reflector {

    /**
     * Represents indent.
     */

    private static final String INDENT = "    ";

    /**
     * Prints all contents of the gicen class.
     * @param writer represents writing object.
     * @param clazz which content we want to write.
     * @param recursionLevel needed to maintain proper indentation level.
     */

    public static void printClass(Writer writer, Class<?> clazz, Integer recursionLevel) throws IOException {
        printClassSignature(writer, clazz, recursionLevel);
        printFields(writer, clazz, recursionLevel);
        printConstructors(writer, clazz, recursionLevel);
        printMethods(writer, clazz, recursionLevel);
        printClasses(writer, clazz, recursionLevel);
        printIndent(writer, recursionLevel);
        writer.write("}\n\n");
    }

    /**
     * Method that prints indent an appropriate number of time.
     * @param writer represents writing object.
     * @param recursionLevel needed to maintain proper indentation level.
     */

    public static void printIndent(Writer writer, Integer recursionLevel) throws IOException {
        for (int i = 0; i < recursionLevel; i++) {
            writer.write(INDENT);
        }
    }

    /**
     * Mathod that prints structure of a given class.
     * @param clazz which we want to print.
     */

    public static void printStructure(Class<?> clazz) {
        try {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(clazz.getSimpleName() + ".java"), "utf-8"))) {
                writer.write("package " + clazz.getPackage().getName() + ";\n\n");
                printClass(writer, clazz, 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that absorbs redundant package name.
     * @param forRemove string that we want remove.
     * @param className which we want to save.
     * @return string without redundant prefixes.
     */

    public static String getOnlyClassName(String forRemove, String className) {
        return className.replace(forRemove + ".","");
    }

    /**
     * Prints superclass of a given class.
     * @param writer represents writing object.
     * @param clazz which superclass we want to print.
     * @param recursionLevel needed to maintain proper indentation level.
     */

    public static void printSuperClass(Writer writer, Class<?> clazz, Integer recursionLevel) throws IOException {
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null && !superClazz.equals(Object.class)) {
            Type t = clazz.getGenericSuperclass();
            writer.write("extends " + getOnlyClassName(superClazz.getPackage().getName(), t.getTypeName()) + " ");
        }
    }

    /**
     * Method that prints all interfaces that are implemented by this class.
     * @param writer represents writing object.
     * @param clazz which interfaces we want to print.
     * @param recursionLevel needed to maintain proper indentation level.
     */

    public static void printInterfaces(Writer writer, Class<?> clazz, Integer recursionLevel) throws IOException {
        Type[] interfaces = clazz.getInterfaces();
        if (interfaces.length != 0) {
            writer.write("implements ");
            int cnt = 0;
            while (cnt < interfaces.length - 1) {
                Class t = (Class) interfaces[cnt];
                writer.write(getOnlyClassName(t.getPackage().getName(), t.getTypeName()) + ", ");
                cnt++;
            }
            writer.write(getOnlyClassName(((Class) interfaces[cnt]).getPackage().getName(), interfaces[cnt].getTypeName()) + " ");
        }
    }

    /**
     * Print signature of a class.
     * @param writer represents writing object.
     * @param clazz which signature we want to print.
     * @param recursionLevel needed to maintain proper indentation level.
     */

    public static void printClassSignature(Writer writer, Class<?> clazz, Integer recursionLevel) throws IOException {
        String modifiers = Modifier.toString(clazz.getModifiers());

        printIndent(writer, recursionLevel);
        writer.write(modifiers);

        if (!Objects.equals(modifiers, "")) {
            writer.write(" ");
        }

        writer.write("class " + clazz.getSimpleName() + " ");

        printSuperClass(writer, clazz, recursionLevel);
        printInterfaces(writer, clazz, recursionLevel);

        writer.write("{\n\n");
    }

    /**
     * Prints all constructor of a given class.
     * @param writer represents writing object.
     * @param clazz which constructor we want to print.
     * @param recursionLevel needed to maintain proper indentation level.
     */

    public static void printConstructors(Writer writer, Class<?> clazz, Integer recursionLevel) throws IOException {
        Constructor[] constructors = clazz.getDeclaredConstructors();

        for (Constructor constructor : constructors) {
            String modifiers = Modifier.toString(constructor.getModifiers());
            printIndent(writer, recursionLevel + 1);
            if (!modifiers.equals("")) {
                writer.write(modifiers + " ");
            }
            writer.write(clazz.getSimpleName() + "(");
            Type[] types = constructor.getGenericParameterTypes();
            if (types.length > 0) {
                int cnt = 1;
                while (types.length - cnt > 0) {
                    Class t = (Class) types[cnt - 1];
                    writer.write(getOnlyClassName(t.getPackage().getName(), t.getTypeName()) + " arg" + cnt + ", ");
                    cnt++;
                }
                Class t = (Class) types[cnt - 1];
                writer.write(getOnlyClassName(t.getPackage().getName(), t.getTypeName()) +  " arg" + cnt + ") {");
            }
            else {
                writer.write(") {");
            }
            writer.write("\n");
            printIndent(writer, recursionLevel + 1);
            writer.write("}\n\n");
        }
    }

    /**
     * Prints all fields of a given class.
     * @param writer represents writing object.
     * @param clazz which fields we want to print.
     * @param recursionLevel needed to maintain proper indentation level.
     */

    public static void printFields(Writer writer, Class<?> clazz, Integer recursionLevel) throws IOException {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String modifiers = Modifier.toString(field.getModifiers());
            printIndent(writer, recursionLevel + 1);
            if (!modifiers.equals("")) {
                writer.write(modifiers + " ");
            }
            writer.write(getOnlyClassName(field.getDeclaringClass().getPackage().getName(), field.getGenericType().getTypeName()) + " " + field.getName() + ";\n\n");
        }
    }

    /**
     * Print all methods of a given class.
     * @param writer represents writing object.
     * @param clazz which methods we want to print.
     * @param recursionLevel needed to maintain proper indentation level.
     */

    public static void printMethods(Writer writer, Class<?> clazz, Integer recursionLevel) throws IOException {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            String modifiers = Modifier.toString(method.getModifiers());
            printIndent(writer, recursionLevel + 1);
            if (!modifiers.equals("")) {
                writer.write(modifiers + " ");
            }
            Type type =  method.getGenericReturnType();
            if (!type.equals(Void.TYPE)) {
                writer.write(getOnlyClassName((method.getReturnType()).getPackage().getName(), type.getTypeName()) + " ");
            }
            else {
                writer.write("void ");
            }
            writer.write(method.getName() + "(");
            Type[] types =  method.getGenericParameterTypes();
            Class[] nonGeneric = method.getParameterTypes();
            if (types.length > 0) {
                int cnt = 1;
                while (types.length - cnt > 0) {
                    if (!types[cnt - 1].equals(Void.TYPE)) {
                        writer.write((getOnlyClassName(nonGeneric[cnt - 1].getPackage().getName(), types[cnt - 1].getTypeName()) + " arg" + cnt + ", "));
                    }
                    else {
                        writer.write("void ");
                    }
                    cnt++;
                }
                if (!types[cnt - 1].equals(Void.TYPE)) {
                    writer.write(getOnlyClassName(nonGeneric[cnt - 1].getPackage().getName(), types[cnt - 1].getTypeName()) + " arg" + cnt);
                }
                else {
                    writer.write("void ");
                }
            }

            writer.write(") {\n");
            printIndent(writer, recursionLevel + 1);
            writer.write("}\n\n");
        }
    }

    /**
     * Prints all inner classes in recursive order maintaing correct indent level.
     * @param writer represents writing object.
     * @param clazz which inner classes we want to print.
     * @param recursionLevel needed to maintain proper indentation level.
     */

    public static void printClasses(Writer writer, Class<?> clazz, Integer recursionLevel) throws IOException {
        Class[] classes = clazz.getDeclaredClasses();
        for (Class clazzz : classes) {
            printClass(writer, clazzz, recursionLevel + 1);
        }
    }

    /**
     * Writes all fields that are present in one class and not present in another.
     * @param firstClass we want to compare with the second class.
     * @param secondClass we want to compare wit the first class.
     */

    public static void diffFields(Class<?> firstClass, Class<?> secondClass) {
        Set<String> firstClassFields = Arrays.stream(firstClass.getDeclaredFields()).map(Field::toString).collect(Collectors.toSet());
        Set<String> secondClassFields = Arrays.stream(secondClass.getDeclaredFields()).map(Field::toString).collect(Collectors.toSet());
        System.out.println("Unique fields in " + firstClass.getSimpleName() + " class are:\n");
        System.out.println(firstClassFields.stream().filter(p -> !secondClassFields.contains(p)).collect(Collectors.joining("\n")));
        System.out.println("Unique fields in " + secondClass.getSimpleName() + " class are:\n");
        System.out.println(secondClassFields.stream().filter(p -> !firstClassFields.contains(p)).collect(Collectors.joining("\n")));
    }

    /**
     * Writes all methods that are present in one class and not present in another class.
     * @param firstClass we want to compare with the second class.
     * @param secondClass we want to compare wit the first class.
     */

    public static void diffMethods(Class<?> firstClass, Class<?> secondClass) {
        Set<String> firstClassMethods = Arrays.stream(firstClass.getDeclaredMethods()).map(Method::toString).collect(Collectors.toSet());
        Set<String> secondClassMethods = Arrays.stream(secondClass.getDeclaredMethods()).map(Method::toString).collect(Collectors.toSet());
        System.out.println("Unique methods in " + firstClass.getSimpleName() + " class are:\n");
        System.out.println(secondClassMethods.stream().filter(p -> !firstClassMethods.contains(p)).collect(Collectors.joining("\n")));
        System.out.println("Unique methods in " + secondClass.getSimpleName() + " class are:\n");
    }

    /**
     * Prints difference between two classes.
     * @param firstClass we want to compare with the second class.
     * @param secondClass we want to compare wit the first class.
     */

    public static void diffClasses(Class<?> firstClass, Class<?> secondClass) {
        diffFields(firstClass, secondClass);
        diffMethods(firstClass, secondClass);
    }
}
