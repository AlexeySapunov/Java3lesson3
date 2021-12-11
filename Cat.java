package ru.geekbrains.lesson3;

import java.io.Serializable;

public class Cat implements Serializable {
    private String name;
    private transient int age; // transient - запрет на сериализацию

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
