package com;

import java.io.*;
import java.util.UUID;

/**
 * Created by Tom on 6/9/2017.
 */
public final class UtilityFunctions {
    private UtilityFunctions() {} // behave as static class

    // TODO: test
    public static void serialize(Serializable obj, File file) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(obj);
        oos.close();
    }

    public static <T extends Serializable>T deserialize(File file, Class<T> c) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Object obj = ois.readObject();
        ois.close();
        return (T) obj;
    }

    public static String generateUID() {
        return UUID.randomUUID().toString();
    }

    public static String getFilePath(String filename) {
        return (new UtilityFunctions()).getClass().getResource(filename).getFile();
    }
}
