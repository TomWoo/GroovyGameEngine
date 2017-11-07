package com;

import java.io.*;
import java.net.URL;
import java.util.UUID;

/**
 * Created by Tom on 6/9/2017.
 */
public final class Utilities {
    private Utilities() {} // behave as static class

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
        return (T) obj; // TODO: handle?
    }

    public static String generateUID() {
        return UUID.randomUUID().toString();
    }

    public static URL getResourceURL(String filename) {
        return (new Utilities()).getClass().getResource(filename);
    }

    public static String getResourcePath(String filename) {
        return getResourceURL(filename).getPath();
    }

    public static File getResourceFile(String filename) {
        return new File(getResourcePath(filename));
    }
}
