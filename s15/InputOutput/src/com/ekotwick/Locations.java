package com.ekotwick;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by ekotwick on 7/26/17.
 */
// we are implementing an interface here, and when we do that, it is required that we implement all the methods of that interface. To help us out, IntelliJ provides a set of functions to implement, just hit `option + enter` and then select `implement`, then fill out. Since we are implementing a Map, our locations methods should look just like regular Map functions.
public class Locations implements Map<Integer, Location> {
    private static Map<Integer, Location> locations = new HashMap<Integer, Location>();

    public static void main(String[] args) throws IOException {
        try (FileWriter locFile = new FileWriter("locations.txt");
             FileWriter dirFile = new FileWriter("directions.txt")
        ) {
            for(Location location : locations.values()) {
                locFile.write(location.getLocationID() + " " + location.getDescription() + "\n");
                for(String direction : location.getExits().keySet()) {
                    dirFile.write(location.getLocationID() + "," + direction + "," + location.getExits().get(direction) + "\n");
                }
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
        Scanner scanner = null; // reminder to initialize it as `null` in order to catch it in the `finally` block
        try {
            scanner = new Scanner(new FileReader("locations.txt"));
            scanner.useDelimiter(",");
            while(scanner.hasNextLine()) {
                int loc = scanner.nextInt();
                scanner.skip(scanner.delimiter());
                String description = scanner.nextLine();
                System.out.println("Imported loc: " + loc + ": " + description);
                Map<String, Integer> tempExit = new HashMap<>();
                locations.put(loc, new Location(loc, description, tempExit));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(scanner != null) {
                scanner.close();
            }
        }
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
