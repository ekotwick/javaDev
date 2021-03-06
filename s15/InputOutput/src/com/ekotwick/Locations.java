package com.ekotwick;

import java.io.*;
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
    // CHALLENGE: TURN THIS STATIC INITIALIZATION BLOCK INTO A TRY WITH RESOURCES USING JAVA 7
//    static {
//        Scanner scanner = null; // reminder to initialize it as `null` in order to catch it in the `finally` block
//        try {
//            scanner = new Scanner(new FileReader("locations_big.txt"));
//            scanner.useDelimiter(",");
//            while(scanner.hasNextLine()) {
//                int loc = scanner.nextInt();
//                scanner.skip(scanner.delimiter());
//                String description = scanner.nextLine();
//                System.out.println("Imported loc: " + loc + ": " + description);
//                Map<String, Integer> tempExit = new HashMap<>();
//                locations.put(loc, new Location(loc, description, tempExit));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if(scanner != null) {
//                scanner.close();
//            }
//        }
//
//        try {
//            scanner = new Scanner(new BufferedReader(new FileReader("directions_big.txt")));
//            scanner.useDelimiter(",");
//            while(scanner.hasNextLine()) {
//                // ONE WAY TO DO IT
////                int loc = scanner.nextInt();
////                scanner.skip(scanner.delimiter()); // we have to skip the delimiter to go to the next piece of data
////                String direction = scanner.next();
////                scanner.skip(scanner.delimiter());
////                String dest = scanner.nextLine();
////                int destination = Integer.parseInt(dest);
//                // ANOTHER WAY TO DO IT
//                String input = scanner.nextLine();
//                String[] data = input.split(",");
//                int loc = Integer.parseInt(data[0]);
//                String direction = data[1];
//                int destination = Integer.parseInt(data[2]);
//
//                System.out.println(loc + ": " + direction + ": " + destination);
//                Location location = locations.get(loc);
//                location.addExit(direction, destination);
//            }
//        } catch(IOException e) {
//            e.printStackTrace();
//        } finally {
//            if(scanner != null) {
//                scanner.close(); // the file reader is passed to the bufferedReader's constructor, which is passed to the Scanner's constructor; this is by design; closing the scanner will close the buffered reader (can look into the source code for evidence)
//            }
//        }
//    }

    static {
        try(Scanner scanner = new Scanner(new FileReader("locations_big.txt"))) {
            scanner.useDelimiter(",");
            while(scanner.hasNextLine()) {
                int loc = scanner.nextInt();
                scanner.skip(scanner.delimiter());
                String description = scanner.next();
                System.out.println("Imported loc: " + loc + ": " + description);
                Map<String, Integer> tempExit = new HashMap<>();
                locations.put(loc, new Location(loc, description, tempExit));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // here we remove the scanner altogether and work with the BufferedReader
        // but notice that we could dispense with the Scanner here, because the `try with resources` takes care of a checked exception of using the BufferedReader class, which we would have to have caught in a catch block if we didn't use this strategy
        try(BufferedReader dirFile = new BufferedReader(new FileReader("directions_big.txt"))){
            String input;
            while((input = dirFile.readLine()) != null) {
                String[] data = input.split(",");
                int loc = Integer.parseInt(data[0]);
                String direction = data[1];
                int destination = Integer.parseInt(data[2]);

                System.out.println(loc + ": " + direction + ": " + destination);
                Location location = locations.get(loc);
                location.addExit(direction, destination);

            }
        } catch(IOException e) {
            e.printStackTrace();
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
