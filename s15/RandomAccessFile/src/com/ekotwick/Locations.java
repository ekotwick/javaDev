package com.ekotwick;

import java.io.*;
import java.util.*;

/**
 * Created by timbuchalka on 2/04/2016.
 */
public class Locations implements Map<Integer, Location> {
    private static Map<Integer, Location> locations = new LinkedHashMap<Integer, Location>();
    private static Map<Integer, IndexRecord> index = new LinkedHashMap<>();
    private static RandomAccessFile ra;

    public static void main(String[] args) throws IOException {
        try (RandomAccessFile rao = new RandomAccessFile("locations_random.dat", "rwd")) {
            rao.writeInt(locations.size());
            int indexSize = locations.size() * 3 * Integer.BYTES; // here we have to calculate how large the index size will be; we multiply by '3' because there will be three things stored (id, offset, and size of record), then we multply all that by the size of an integer in BYTES.
            int locationStart = (int) (indexSize + rao.getFilePointer() + Integer.BYTES); // this gives us the offset for the location section
            rao.writeInt(locationStart);
            // now we write the index; but we write this only after we have written the locations, because each index record requires the offset location, which we don't know until we've written the location
            long indexStart = rao.getFilePointer();
            // now we can write teh locations. We have the offset, which we will assign to a variable, which is updated after writing each location; then we loop through the locations, write each location, create an index for it, and add the index record to a map. all this requires a class in its own right

            int startPointer = locationStart; // this is the offset; we use it to calculate a record's length once we've written it to file
            rao.seek(startPointer); // this moves the pointer to the first location's offset; we do this only for the first location, because afterwards we write the data sequentially

            for(Location location : locations.values()) {
                rao.writeInt(location.getLocationID());
                rao.writeUTF(location.getDescription());
                StringBuilder builder = new StringBuilder();
                for(String direction : location.getExits().keySet()) {
                    if(!direction.equalsIgnoreCase("Q")) {
                        builder.append(direction);
                        builder.append(",");
                        builder.append(location.getExits().get(direction));
                        builder.append((","));
                        // direction. locationId, direction, locationId (this is how the file is getting written)
                        // the string ends with a trailing comma; if we were diligent, we wouldn't include it (though it is harmless in this case)
                    }
                }
                rao.writeUTF(builder.toString());

                IndexRecord record = new IndexRecord(startPointer, (int) (rao.getFilePointer() - startPointer));
                index.put(location.getLocationID(), record);

                startPointer = (int) rao.getFilePointer();
            }

            rao.seek(indexStart);
            for(Integer locationID : index.keySet()) {
                rao.writeInt(locationID);
                rao.writeInt(index.get(locationID).getStartByte());
                rao.writeInt(index.get(locationID).getLength());
            }

        }

    }

    // 1. This first four bytes will contain the number of locations (bytes 0-3)
    // 2. The next four bytes will contain the start offset of the locations section (bytes 4-7)
    // 3. The next section of the file will contain the index (the index is 1692 bytes long.  It will start at byte 8 and end at byte 1699
    // 4. The final section of the file will contain the location records (the data). It will start at byte 1700


    static {
        try {
            ra = new RandomAccessFile("locations_rand.dat", "rwd");
            int numLocations = ra.readInt(); // it's good practice to write this, though it isn't currently being used
            long locationStartPoint = ra.readInt();

            while(ra.getFilePointer() < locationStartPoint) {
                int locationId = ra.readInt();
                int locationStart = ra.readInt();
                int locationLength = ra.readInt();

                IndexRecord record = new IndexRecord(locationStart, locationLength);
                index.put(locationId, record);
            }
        } catch(IOException e) {
            System.out.println("IOException in static initializer: " + e.getMessage());
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
