package dk.dodgame.util.rules;

import dk.dodgame.data.ItemDTO;

import java.util.Map;
import java.util.TreeMap;

public class ArmorRules {


    public static final String CANNOT_PROCESS_WEIGHT_REF = "Cannot process weightRef: ";

    private ArmorRules() {
    }

    private static final TreeMap<Integer, Map<Character, Double>> SIZE_TO_WEIGHT_REF_MAP = new TreeMap<>();

    static {
        // Initialize the map with the starting point of each characterSize range mapping to its corresponding weightRef values.
        SIZE_TO_WEIGHT_REF_MAP.put(5, Map.ofEntries(
                Map.entry('A', 1.5), Map.entry('B', 2.0), Map.entry('C', 2.5), Map.entry('D', 3.0),
                Map.entry('E', 4.0), Map.entry('F', 4.5), Map.entry('G', 5.5), Map.entry('H', 6.0),
                Map.entry('J', 7.0), Map.entry('K', 7.5), Map.entry('M', 8.0), Map.entry('N', 9.0)));
        SIZE_TO_WEIGHT_REF_MAP.put(9, Map.ofEntries(
                Map.entry('A', 2.0), Map.entry('B', 2.5), Map.entry('C', 3.0), Map.entry('D', 4.0),
                Map.entry('E', 5.0), Map.entry('F', 6.0), Map.entry('G', 7.0), Map.entry('H', 8.0),
                Map.entry('J', 9.0), Map.entry('K', 10.0), Map.entry('M', 11.0), Map.entry('N', 12.0)));
        SIZE_TO_WEIGHT_REF_MAP.put(13, Map.ofEntries(
                Map.entry('A', 2.5), Map.entry('B', 3.0), Map.entry('C', 3.5), Map.entry('D', 5.0),
                Map.entry('E', 6.5), Map.entry('F', 7.5), Map.entry('G', 9.0), Map.entry('H', 10.0),
                Map.entry('J', 11.5), Map.entry('K', 12.5), Map.entry('M', 13.5), Map.entry('N', 15.0)));
        SIZE_TO_WEIGHT_REF_MAP.put(17, Map.ofEntries(
                Map.entry('A', 3.0), Map.entry('B', 3.5), Map.entry('C', 4.5), Map.entry('D', 6.0),
                Map.entry('E', 7.5), Map.entry('F', 9.0), Map.entry('G', 10.5), Map.entry('H', 12.0),
                Map.entry('J', 13.5), Map.entry('K', 15.0), Map.entry('M', 16.5), Map.entry('N', 18.5)));
        SIZE_TO_WEIGHT_REF_MAP.put(21, Map.ofEntries(
                Map.entry('A', 3.5), Map.entry('B', 4.0), Map.entry('C', 5.0), Map.entry('D', 7.0),
                Map.entry('E', 9.0), Map.entry('F', 10.5), Map.entry('G', 11.5), Map.entry('H', 14.0),
                Map.entry('J', 16.5), Map.entry('K', 17.5), Map.entry('M', 19.0), Map.entry('N', 21.0)));
        SIZE_TO_WEIGHT_REF_MAP.put(26, Map.ofEntries(
                Map.entry('A', 4.0), Map.entry('B', 4.5), Map.entry('C', 6.0), Map.entry('D', 8.0),
                Map.entry('E', 10.0), Map.entry('F', 12.0), Map.entry('G', 13.0), Map.entry('H', 16.0),
                Map.entry('J', 18.5), Map.entry('K', 20.0), Map.entry('M', 22.0), Map.entry('N', 24.5)));
    }

    public static Double getArmorPriceForSize(int characterSize, ItemDTO item) {
        return item.getWeightReference() != null
                ? (item.getPrice() * getArmorWeightForCharSize(characterSize, item.getWeightReference().charAt(0)))
                : item.getPrice();
    }

    public static Double getArmorWeightForCharSize(int characterSize, char weightRef) {
        // Find the highest entry less than or equal to the given characterSize
        Map.Entry<Integer, Map<Character, Double>> entry = SIZE_TO_WEIGHT_REF_MAP.floorEntry(characterSize);
        if (entry != null) {
            Map<Character, Double> weightRefMap = entry.getValue();
            Double weight = weightRefMap.get(weightRef);
            if (weight != null) {
                return weight;
            } else {
                throw new IllegalArgumentException(CANNOT_PROCESS_WEIGHT_REF + weightRef);
            }
        } else {
            throw new IllegalArgumentException("Invalid characterSize: " + characterSize);
        }
    }
}
