package com;

import com.collections.SerializableObservableList;
import com.components.IComponent;
import com.core.Entity;
import com.core.IEntity;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Tom on 6/9/2017.
 */
public final class Utilities {
    private Utilities() {} // behave as static class

    // TODO: fix
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

    @Deprecated
    public static <T extends Serializable>T copy(Serializable obj, Class<T> c) {
        File f = new File(""); // TODO: fix
        try {
            serialize(obj, f);
            return deserialize(f, c);
        } catch (Exception ex) {
            return null;
        }
    }

    @Deprecated // simply use deepClone(entity) instead
    public static IEntity cloneEntity(IEntity entity) {
        IEntity newEntity = new Entity(entity.getName(), new ArrayList<>(entity.getGroupIDs()));
        try {
            Set<IComponent> components = entity.getComponents().stream().map(e -> (IComponent) deepClone(e)).collect(Collectors.toSet());
            newEntity.addComponents(components);
        } catch (Exception e) {
            // TODO: warn
        }
        return newEntity;
    }

    // Reference: https://alvinalexander.com/java/java-deep-clone-example-source-code
    public static <T extends Serializable>T deepClone(T object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
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

    public static String getResourceFilename(String filename) {
        return getResourceURL(filename).toString();
    }

    @Deprecated // only for temporary workarounds
    public static String getBaseFilename(String filename) {
        String[] arr = filename.split("/"); // *nix
        if(arr.length == 1) {
            arr = filename.split("\\\\"); // Windows
        }
        return arr[arr.length-1];
    }

    public static Serializable parseAsType(String s, Class c) {
        if(c.equals(Integer.class)) {
            return Integer.parseInt(s);
        } else if(c.equals(Double.class)) {
            return Double.parseDouble(s);
        } else if(c.equals(Boolean.class)) {
            return Boolean.parseBoolean(s);
        } else if(c.equals(String.class)) {
            return s;
        } else {
            return null;
        }
    }
}
