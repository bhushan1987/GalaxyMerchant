package galaxy.parser;

/**
 * Parser for the statements.
 */
public interface Parser {
    /**
     * Parses and interprets the given statement.<p>
     * Storage of extracted information is implementation dependent.
     *
     * @param statement Input statement
     * @return Output after parsing and interpreting the input
     * @throws Exception If grammar of the statement is out of scope of the parser
     */
    String parse(String statement) throws Exception;

    /**
     * Checks whether the given statement can be parsed by this parser
     * @param statement input statement to be parsed
     * @return true if the statement can be parsed by this parser
     */
    // TODO use regex
    boolean canParse(String statement);
}
