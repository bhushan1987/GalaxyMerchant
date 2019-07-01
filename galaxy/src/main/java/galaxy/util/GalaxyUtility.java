package galaxy.util;

import java.util.ArrayList;

import static galaxy.util.GalaxyConstants.SPACE;

public class GalaxyUtility {
    /**
     * Removes empty strings and white space strings from the given array.
     * @param tokens
     * @return
     */
    public static ArrayList<String> removeEmptyTokens(String[] tokens) {
        ArrayList<String> ret = new ArrayList(tokens.length); // max
        for(String tmp : tokens) {
            if(!tmp.isEmpty() && !tmp.equals(SPACE)){
                ret.add(tmp.trim());
            }
        }
        return ret;
    }
}
