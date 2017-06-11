package com.core;

import java.io.*;
import java.util.UUID;

/**
 * Created by Tom on 6/9/2017.
 */
public final class UtilityFunctions {
    private UtilityFunctions() {} // behave as static class

    public static boolean isComponent(String name) {
        return true; // TODO: implement
    }

    public static void serialize(Serializable obj, File file) throws IOException, ClassNotFoundException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject(obj);
    }

    public static <T extends Serializable>T deserialize(File file, Class<T> c) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        return (T) in.readObject();
    }

    public static String generateUID() {
        return UUID.randomUUID().toString();
    }
}
