package galaxy.parser.impl;

import galaxy.converter.NumberConverter;
import galaxy.exception.ParserException;
import galaxy.numbers.NumberMapper;
import galaxy.parser.Parser;
import galaxy.util.GalaxyUtility;

import java.util.ArrayList;

import static galaxy.util.GalaxyConstants.*;

/**
 * Parser for parsing questions in the input.<p>
 * E.g. "how much is pish tegj glob glob ?" or "how many Credits is glob prok Silver ?"
 */
public class QuestionParser implements Parser {
    private NumberConverter numberConverter;

    public QuestionParser(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }
    public String parse(String statement) throws Exception {
        // how much is pish tegj glob glob ?
        if(statement.startsWith(HOW_MUCH_IS)) {
            return parseHowMuchQuery(statement);
        } else if(statement.startsWith(HOW_MANY_CREDITS_IS)) {
            return parseHowManyQuery(statement);
        } else if(statement.startsWith(HOW_MANY)) {
            return parseInterconversionQuery(statement);
        } else {
            throw new ParserException("Invalid input. Parsing failed.");
        }
    }

    private String parseInterconversionQuery(String statement) {
        // how many silver is glob gold
        String romanNumeralsWithSpaces = statement.replace(HOW_MANY, EMPTY).replace(QUESTION_MARK, EMPTY).replace("is ", EMPTY).trim();

        // silver glob prok prok gold
        ArrayList<String> numerals = GalaxyUtility.removeEmptyTokens(romanNumeralsWithSpaces.split(SPACE));
        String firstMetal = numerals.get(0);
        Float metalValue = NumberMapper.metalValueMap.get(firstMetal);

        String targetMetal = numerals.get(numerals.size() -1);

        StringBuilder sb = new StringBuilder();
        StringBuilder aliasString = new StringBuilder();
        for(String alias : numerals){
            String romanChar = NumberMapper.aliasRomanMap.get(alias);
            if(romanChar != null) {
                sb.append(romanChar);
                aliasString.append(alias).append(SPACE);
            } else if(alias.equals(targetMetal)) {
                break;
            }
        }

        float targetQty = numberConverter.toArabic(sb.toString());
        Float targetMetalValuePerUnit = NumberMapper.metalValueMap.get(targetMetal);

        float total = targetMetalValuePerUnit * targetQty;
        total = total/metalValue;
        return (aliasString.toString() + " " +targetMetal + IS + total + " " + firstMetal.trim());
    }

    private String parseHowManyQuery(String statement) {
        // how many Credits is glob prok prok Silver ?
        String romanNumeralsWithSpaces = statement.replace(HOW_MANY_CREDITS_IS, "").replace(QUESTION_MARK, "").trim();
        ArrayList<String> tokens = GalaxyUtility.removeEmptyTokens(romanNumeralsWithSpaces.split(SPACE));
        StringBuilder sb = new StringBuilder();
        String metal = null;
        for(String tmp : tokens) {
            tmp = tmp.trim();
            if(NumberMapper.aliasRomanMap.containsKey(tmp)) {
                sb.append(NumberMapper.aliasRomanMap.get(tmp));
            } else {
                metal = tmp;
                break; // considering last word is metal name
            }
        }
        float metalQuantity = numberConverter.toArabic(sb.toString());
        // total credits = qty * price per unit
        float totalCredits = metalQuantity * NumberMapper.metalValueMap.get(metal);
        return romanNumeralsWithSpaces + IS + totalCredits + " " + CREDITS;
    }

    private String parseHowMuchQuery(String statement) {
        String romanNumeralsWithSpaces = statement.replace(HOW_MUCH_IS, "").replace(QUESTION_MARK, "").trim();
        ArrayList<String> tokens = GalaxyUtility.removeEmptyTokens(romanNumeralsWithSpaces.split(SPACE));
        StringBuilder sb = new StringBuilder();
        for(String tmp : tokens) {
            sb.append(NumberMapper.aliasRomanMap.get(tmp));
        }
        float value = numberConverter.toArabic(sb.toString());
        return romanNumeralsWithSpaces + IS + value;
    }

    public boolean canParse(String statement) {
        return statement.trim().endsWith("?");
    }
}
