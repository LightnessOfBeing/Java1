package ru.spbau.vishnyakov;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.*;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class that gets all inner structure of other class and prints it to file.
 */

public class Reflector {

    /**
     * Represents indent.
     */
    @NotNull
    private static final String INDENT = "    ";

    /**
     * Prints all contents of the gicen class.
     * @param writer represents writing object.
     * @param clazz which content we want to write.
     * @param recursionLevel needed to maintain proper indentation level.
     */

    public static void printClass(@NotNull Writer writer, @NotNull Class<?> clazz, Integer recursionLevel) throws IOException {
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

    public static void printIndent(@NotNull Writer writer, Integer recursionLevel) throws IOException {
        for (int i = 0; i < recursionLevel; i++) {
            writer.write(INDENT);
        }
    }

    /**
     * Mathod that prints structure of a given class.
     * @param clazz which we want to print.
     */

    public static void printStructure(@NotNull Class<?> clazz) {
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

    public static String getOnlyClassName(String forRemove, @NotNull String className) {
        return className.replace(forRemove + ".","");
    }

    /**
     * Prints superclass of a given class.
     * @param writer represents writing object.
     * @param clazz which superclass we want to print.
     * @param recursionLevel needed to maintain proper indentation level.
     */

    public static void printSuperClass(@NotNull Writer writer, @NotNull Class<?> clazz, Integer recursionLevel) throws IOException {
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

    public static void printInterfaces(@NotNull Writer writer, @NotNull Class<?> clazz, Integer recursionLevel) throws IOException {
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

    public static void printClassSignature(@NotNull Writer writer, @NotNull Class<?> clazz, Integer recursionLevel) throws IOException {
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

    public static void printConstructors(@NotNull Writer writer, @NotNull Class<?> clazz, Integer recursionLevel) throws IOException {
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

    public static void printFields(@NotNull Writer writer, @NotNull Class<?> clazz, Integer recursionLevel) throws IOException {
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

    public static void printMethods(@NotNull Writer writer, @NotNull Class<?> clazz, Integer recursionLevel) throws IOException {
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

    public static void printClasses(@NotNull Writer writer, @NotNull Class<?> clazz, Integer recursionLevel) throws IOException {
        Class[] classes = clazz.getDeclaredClasses();
        for (Class clazzz : classes) {
            printClass(writer, clazzz, recursionLevel + 1);
        }
    }

    /**
     * Prints all fields that are present in one class and not present in another.
     * @param firstClass we want to compare with the second class.
     * @param secondClass we want to compare wit the first class.
     */

    public static void diffFields(@NotNull Class<?> firstClass, @NotNull Class<?> secondClass) {
        HashSet<Field> first = new HashSet<>();
        HashSet<Field> second = new HashSet<>();
        Field[] firstClassFields = firstClass.getDeclaredFields();
        Field[] secondClassFields = secondClass.getDeclaredFields();
        for (Field firstField : firstClassFields) {
            for (Field secondField : secondClassFields) {
                if (compareTypes(firstField.getGenericType(), secondField.getGenericType())
                        && firstField.getName().equals(secondField.getName())) {
                    first.add(firstField);
                    second.add(secondField);
                }
            }
        }
        printUniqueFields(first, firstClass);
        printUniqueFields(second, secondClass);
    }

    /**
     * Prints all unique fields of one class.
     * @param set that contains field name.
     * @param clazz which fields we want to print.
     */

    public static void printUniqueFields(@NotNull HashSet<Field> set, @NotNull Class<?> clazz) {
        Field[] arrayOfFields = clazz.getDeclaredFields();
        if (set.size() != arrayOfFields.length) {
            System.out.println("Unique fields in " + clazz.getName() + " are:\n");
            for (Field f : arrayOfFields) {
                if (!set.contains(f)) {
                    System.out.println(f.getName() + "\n");
                }
            }
        }
        else {
            System.out.println("There is no unique fields in " + clazz.getName() + "\n\n");
        }
    }

    /**
     * Compares types of two different fields.
     * @param t1 type of the first field.
     * @param t2 type of the second field.
     * @return result of comparison.
     */

    public static boolean compareTypes(@NotNull Type t1, @NotNull Type t2) {
        String s1 = deleteExtends(t1.getTypeName());
        String s2 = deleteExtends(t2.getTypeName());
        return s1.equals(s2);
    }

    /**
     * Handles comparison between E extends Object and E.
     * @param typeName name of type.
     * @return corrected typename.
     */

    @NotNull
    public static String deleteExtends(@NotNull String typeName) {
        int index = typeName.indexOf("extends Object");
        if (index != -1) {
            return typeName.substring(0, index) + typeName.substring(index + 13, typeName.length());
        }
        return typeName;
    }

    /**
     * Writes all methods that are present in one class and not present in another class.
     * @param firstClass we want to compare with the second class.
     * @param secondClass we want to compare wit the first class.
     */

    public static void diffMethods(@NotNull Class<?> firstClass, @NotNull Class<?> secondClass) {
        HashSet<Method> first = new HashSet<>();
        HashSet<Method> second = new HashSet<>();
        Method[] firstClassMethods = firstClass.getDeclaredMethods();
        Method[] secondClassMethods = secondClass.getDeclaredMethods();
        for (Method firstMethod : firstClassMethods) {
            for (Method secondMethod : secondClassMethods) {
                if (compareMethods(firstMethod, secondMethod)) {
                    first.add(firstMethod);
                    second.add(secondMethod);
                }
            }
        }
        printUniqueMethods(first, firstClass);
        printUniqueMethods(second, secondClass);
    }

    /**
     * Prints unique methods in each class.
     * @param set of non-unique methods.
     * @param clazz which methods we want to print.
     */

    public static void printUniqueMethods(@NotNull HashSet<Method> set, @NotNull Class<?> clazz) {
        Method[] arrayOfMethods = clazz.getDeclaredMethods();
        if (set.size() != arrayOfMethods.length) {
            System.out.println("Unique methods in " + clazz.getName() + " are:\n");
            for (Method f : arrayOfMethods) {
                if (!set.contains(f)) {
                    System.out.println(f.getName() + "\n");
                }
            }
        }
        else {
            System.out.println("There is no unique fields in " + clazz.getName() + "\n\n");
        }
    }

    /**
     * Compares two methods.
     * @param m1 first method to compare.
     * @param m2 second method to compare.
     * @return result of comparison.
     */

    public static boolean compareMethods(@NotNull Method m1, @NotNull Method m2) {
        if (!compareTypes(m1.getGenericReturnType(), m2.getGenericReturnType())) {
            return false;
        }
        if (m1.getGenericParameterTypes().length != m2.getGenericParameterTypes().length) {
            return false;
        }
        for (int i = 0; i < m1.getGenericParameterTypes().length; i++) {
            if (!compareTypes(m1.getGenericParameterTypes()[i], m2.getGenericParameterTypes()[i])) {
                return false;
            }
        }
        return m1.getName().equals(m2.getName());
    }

    /**
     * Prints difference between two classes.
     * @param firstClass we want to compare with the second class.
     * @param secondClass we want to compare wit the first class.
     */

    public static void diffClasses(@NotNull Class<?> firstClass, @NotNull Class<?> secondClass) {
        diffFields(firstClass, secondClass);
        diffMethods(firstClass, secondClass);
    }
}
