package ru.geekbrains.lesson3;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Dog implements Externalizable {

    private String name;
    private Integer age;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name.toLowerCase());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        in.readObject();
    }
}
