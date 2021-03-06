package galaxy.parser.impl;

import galaxy.numbers.NumberMapper;
import galaxy.parser.Parser;
import galaxy.util.GalaxyUtility;

import java.util.ArrayList;

import static galaxy.util.GalaxyConstants.IS;
import static galaxy.util.GalaxyConstants.SPACE;

/**
 * Simple assignment statement parser.<p>
 * E.g. input = "glob is I", this parser will understand the mapping between glob and I
 */
public class AssignmentStatementParser implements Parser {

    public AssignmentStatementParser() {

    }

    public String parse(String statement) throws Exception {
        ArrayList<String> tokens = GalaxyUtility.removeEmptyTokens(statement.split(IS));
        // glob is I = (glob, I)
        NumberMapper.aliasRomanMap.put(tokens.get(0).trim(), tokens.get(1).trim());
        return "";
    }

    public boolean canParse(String statement) {
        ArrayList tokens = GalaxyUtility.removeEmptyTokens(statement.split(SPACE));
        return tokens.size() == 3 && tokens.get(1).equals("is");
    }
}
