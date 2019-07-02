package galaxy.parser.impl;

import galaxy.converter.NumberConverter;
import galaxy.numbers.NumberMapper;
import galaxy.parser.Parser;
import galaxy.util.GalaxyUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static galaxy.util.GalaxyConstants.*;

/**
 * Parser for the plane statements made for defining the prices of materials.<p>
 * E.g. input = "glob pish tegj Silver is 3910 Credits", <br>
 * this parser will understand the price for 1 unit Silver, and it will maintain the mapping of it.
 */
public class StatementParser implements Parser {
    private NumberConverter numberConverter;

    public StatementParser(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public String parse(String statement) throws NumberFormatException {
        // glob pish tegj Silver is 3910 Credits
        ArrayList<String> tokens = GalaxyUtility.removeEmptyTokens(statement.split(IS));

        // glob, pish, tegj, Silver
        ArrayList<String> firstPart = GalaxyUtility.removeEmptyTokens(tokens.get(0).split(SPACE));
        Map<String, Object> firstPartOutput = processFirstPart(firstPart);

        String secondPart = tokens.get(1).replace(CREDITS, EMPTY);
        Float totalValue = Float.parseFloat(secondPart);

        // price per unit = totalValue / quantity
        Float quantity = (Float) firstPartOutput.get(QUANTITY);
        Float pricePerUnit = totalValue/quantity;

        NumberMapper.metalValueMap.put((String) firstPartOutput.get(METAL), pricePerUnit);
        return "";
    }

    public boolean canParse(String statement) {
        return statement.endsWith(CREDITS);
    }


    private Map<String, Object> processFirstPart(ArrayList<String> firstPart) throws NumberFormatException {
        Map<String, Object> partialMap = new HashMap();
        String metal = null;
        StringBuilder sb = new StringBuilder();
        for(String tmp: firstPart) {
            if(NumberMapper.aliasRomanMap.containsKey(tmp)) {
                sb.append(NumberMapper.aliasRomanMap.get(tmp));
            } else {
                metal = tmp;
                break; // no need to iterate as the metal name will be last
            }
        }

        float qty = numberConverter.toArabic(sb.toString());
        partialMap.put(QUANTITY, qty);
        partialMap.put(METAL, metal);
        return partialMap;
    }
}
