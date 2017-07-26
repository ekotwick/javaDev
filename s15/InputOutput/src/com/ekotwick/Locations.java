package com.ekotwick;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ekotwick on 7/26/17.
 */
// we are implementing an interface here, and when we do that, it is required that we implement all the methods of that interface. To help us out, IntelliJ provides a set of functions to implement, just hit `option + enter` and then select `implement`, then fill out. Since we are implementing a Map, our locations methods should look just like regular Map functions.
public class Locations implements Map<Integer, Location> {
    private static Map<Integer, Location> locations = new HashMap<Integer, Location>();

    public static void main(String[] args) throws IOException {
        try (FileWriter locFile = new FileWriter("locations.txt")) {
            for(Location location : locations.values()) {
                locFile.write(location.getLocationID() + " " + location.getDescription() + "\n");
            }
        }
//        FileWriter localFile = null;
//        try {
//            localFile = new FileWriter("locations.txt");
//            for(Location location : locations.values()) {
//                localFile.write(location.getLocationID() + ", " + location.getDescription() + "\n");
//                throw new IOException("test exception thrown while writing"); // DELETE ME BEFORE PRODUCTION
//            }
//            localFile.close();
//        } finally {
//            System.out.println("In the finally block");
//            if (localFile != null) {
//                System.out.println("attempting to close localfile");
//                localFile.close();
//            }
//        }
    }
    // this is the static initialization block
    static {
        locations.put(0, new Location(0, "You are sitting in front of a computer learning Java"));
        locations.put(1, new Location(1, "You are standing at the end of a road before a small brick building"));
        locations.put(2, new Location(2, "You are at the top of a hill"));
        locations.put(3, new Location(3, "You are inside a building, a well house for a small spring"));
        locations.put(4, new Location(4, "You are in a valley beside a stream"));
    }
    @Override
    public int size() {
        return locations.size();
    }

    @Override
    public boolean isEmpty() {
        return locations.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return locations.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return locations.containsValue(value);
    }

    @Override
    public Location get(Object key) {
        return locations.get(key);
    }

    @Override
    public Location put(Integer key, Location value) {
        return locations.put(key, value);
    }

    @Override
    public Location remove(Object key) {
        return locations.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Location> m) {

    }

    @Override
    public void clear() {
        locations.clear();
    }

    @Override
    public Set<Integer> keySet() {
        return locations.keySet();
    }

    @Override
    public Collection<Location> values() {
        return locations.values();
    }

    @Override
    public Set<Entry<Integer, Location>> entrySet() {
        return locations.entrySet();
    }
}
