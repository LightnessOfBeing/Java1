package ru.spbau.mit.vishnyakov;

public class ClassWithOneClassDependency {

    public final ClassWithoutDependencies dependency;

    public ClassWithOneClassDependency(ClassWithoutDependencies dependency) {
        this.dependency = dependency;
    }
}
