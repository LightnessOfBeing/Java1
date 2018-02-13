package ru.spbau.mit.vishnyakov;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Injector {

    /**
     * Contains list of classes we need to visit.
     */

    private static ArrayList<Class<?>> needToVisit;

    /**
     * Stores a collection of different class instances.
     */

    private static Map<Class<?>, Object> created;

    static {
        needToVisit = new ArrayList<>();
        created = new HashMap<>();
    }

    /**
     * Instanstiates an instance of a class.
     * @param rootClassName is a class from which we start our traversal.
     * @param possibleImplementation list of possible implementations.
     * @return instance of a class.
     */

    public static Object initialize(String rootClassName, ArrayList<String> possibleImplementation) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<?> start = Class.forName(rootClassName);
        return dfs(start, possibleImplementation);
    }

    /**
     * Resolves build dependecies between classes.
     * @param currentClass position in our traversal.
     * @param listOfImplementationNames list of possible implementations.
     * @return an instance of a class.
     */

    public static Object dfs(Class<?> currentClass, ArrayList<String> listOfImplementationNames) throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {

        if (created.containsKey(currentClass)) {
            return created.get(currentClass);
        }

        Object instance;

        needToVisit.add(currentClass);
        Constructor<?>[] constructors = currentClass.getDeclaredConstructors();
        Class<?>[] parameters = constructors[0].getParameterTypes();

        if (parameters.length == 0) {
            instance = constructors[0].newInstance();
            created.put(currentClass, instance);
            needToVisit.remove(needToVisit.size() - 1);
            return instance;
        }

        ArrayList<Object> depend = new ArrayList<>();
        boolean hasImplementation;

        for (Class<?> paramClass : parameters) {
            hasImplementation = false;
            Class<?> implementationOfParamClass = null;

            for (String nameOfClass : listOfImplementationNames) {
                hasImplementation = false;
                Class<?> implementation = Class.forName(nameOfClass);

                hasImplementation = resolve(implementation, paramClass);

                if (hasImplementation && implementationOfParamClass != null) {
                    throw new AmbiguousImplementationException();
                }

                if(hasImplementation){
                    implementationOfParamClass = implementation;
                }
            }
            if (implementationOfParamClass == null) {
                throw new ImplementationNotFoundException();
            }
            depend.add(dfs(implementationOfParamClass, listOfImplementationNames));

        }

        instance = constructors[0].newInstance(depend.toArray());
        created.put(currentClass, instance);

        return instance;
    }

    private static boolean resolve(Class<?> givenClass, Class<?> required) {
        while (givenClass.getSuperclass() != null) {
            if (givenClass.equals(required)) {
                return true;
            }
            givenClass = givenClass.getSuperclass();
        }
        return false;
    }
}
