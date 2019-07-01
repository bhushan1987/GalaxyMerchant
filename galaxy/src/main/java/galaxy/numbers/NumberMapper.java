package galaxy.numbers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static galaxy.numbers.RomanNumeral.*;

/**
 * Maintains mappings of
 *     <li>Roman numbers and corresponding Arabic numbers</li>
 *     <li>Alias and corresponding Roman Number</li>
 *     <li>Metal and its per unit value</li>
 *
 */
public class NumberMapper {
    /** Mapping of Alias and corresponding Roman Number */
    public static Map<String, String> aliasRomanMap = new HashMap();

    /** Mapping of Roman numbers and corresponding Arabic values */
    public static Map<RomanNumeral, Integer> romanArabicMap = new LinkedHashMap();

    /** Mapping of Metal and its per unit price */
    //Linked hashmap for maintaining key sequence
    public static Map<String, Float> metalValueMap = new HashMap();

    static {
        romanArabicMap.put(I,1);
        romanArabicMap.put(V,5);
        romanArabicMap.put(X,10);
        romanArabicMap.put(L,50);
        romanArabicMap.put(C,100);
        romanArabicMap.put(D,500);
        romanArabicMap.put(M,1000);
    }

    public static void clearAllMaps() {
        aliasRomanMap.clear();
        romanArabicMap.clear();
        metalValueMap.clear();
    }
}
