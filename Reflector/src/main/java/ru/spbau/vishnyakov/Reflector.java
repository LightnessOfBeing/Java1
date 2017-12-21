package ru.spbau.vishnyakov;

import sun.reflect.generics.tree.ReturnType;

import java.io.*;
import java.lang.reflect.*;
import java.util.Objects;

public class Reflector {

    private static final String indent = "  ";

    public static void printStructure(Class<?> clazz) {
        try {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(clazz.getSimpleName() + ".java"), "utf-8"))) {
                printClassSignature(writer, clazz);
                printFields(writer, clazz);
                printConstructors(writer, clazz);
                printMethods(writer, clazz);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getOnlyClassName(String pack, String className) {
        return className.replace(pack + ".","");
    }

    public static void printClassSignature(Writer writer, Class<?> clazz) throws IOException {
        writer.write("package " + clazz.getPackage().getName() + ";\n\n");
        String modifiers = Modifier.toString(clazz.getModifiers());
        writer.write(modifiers);

        if (!Objects.equals(modifiers, "")) {
            writer.write(" ");
        }

        writer.write("class " + clazz.getSimpleName() + " ");

        Class<?> superClazz = clazz.getSuperclass();
        if (!superClazz.equals(null) && !superClazz.equals(Object.class)) {
            Type t = clazz.getGenericSuperclass();
            writer.write("extends " + Reflector.getOnlyClassName(clazz.getPackage().getName(), t.getTypeName()) + " ");
        }

        Type[] interfaces = clazz.getInterfaces();

        if (interfaces.length != 0) {
            writer.write("implements ");
            int cnt = 0;
            while (cnt < interfaces.length - 1) {
                Type t = interfaces[cnt];
                writer.write(Reflector.getOnlyClassName(clazz.getPackage().getName(), t.getTypeName()) + ", ");
                cnt++;
            }
            writer.write(Reflector.getOnlyClassName(clazz.getPackage().getName(), interfaces[cnt].getTypeName()) + " ");
        }

        writer.write("{\n\n");
    }

    public static void printConstructors(Writer writer, Class<?> clazz) throws IOException {
        Constructor[] constructors = clazz.getDeclaredConstructors();

        for (Constructor constructor : constructors) {
            String modifiers = Modifier.toString(constructor.getModifiers());
            writer.write(indent);
            if (!modifiers.equals("")) {
                writer.write(modifiers + " ");
            }
            writer.write(clazz.getSimpleName() + "(");
            Type[] types = constructor.getGenericParameterTypes();
            if (types.length > 0) {
                int cnt = 1;
                while (types.length - cnt > 0) {
                    Type t = types[cnt - 1];
                    writer.write(Reflector.getOnlyClassName(clazz.getPackage().getName(), t.getTypeName()) + " arg" + cnt + ", ");
                    cnt++;
                }
                Type t = types[cnt - 1];
                writer.write(Reflector.getOnlyClassName(clazz.getPackage().getName(), t.getTypeName()) +  " arg" + cnt + ")");
            }
            writer.write("\n" + indent + "}\n\n");
        }
    }

    public static void printFields(Writer writer, Class<?> clazz) throws IOException {
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            String modifiers = Modifier.toString(field.getModifiers());
            writer.write(indent);
            if (!modifiers.equals("")) {
                writer.write(modifiers + " ");
            }
            writer.write(getOnlyClassName(field.getDeclaringClass().getPackage().getName(), field.getGenericType().getTypeName()) + " " + getOnlyClassName(clazz.getPackage().getName(), field.getName() + ";\n\n"));
        }
    }

    public static void printMethods(Writer writer, Class<?> clazz) throws IOException {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            String modifiers = Modifier.toString(method.getModifiers());
            writer.write(indent);
            if (!modifiers.equals("")) {
                writer.write(modifiers + " ");
            }
            writer.write(method.getName() + "(");
            Type[] types = method.getGenericParameterTypes();
            if (types.length > 0) {
                int cnt = 1;
                while (types.length - cnt > 0) {
                    writer.write(types[cnt - 1].getTypeName() + " arg" + cnt + ", ");
                    cnt++;
                }
                writer.write(types[cnt - 1].getTypeName() + " arg" + cnt);
            }
            writer.write(") {\n" + indent + "}\n\n");
        }
    }
}
