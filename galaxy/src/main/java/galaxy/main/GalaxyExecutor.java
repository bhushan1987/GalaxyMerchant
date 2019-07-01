package galaxy.main;

import galaxy.converter.NumberConverter;
import galaxy.parser.Parser;
import galaxy.parser.impl.AssignmentStatementParser;
import galaxy.parser.impl.QuestionParser;
import galaxy.parser.impl.StatementParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Bootstrap for Galaxy unit conversion.<p>
 * Initializes parsers, reads the instructions and interprets them.
 */
public class GalaxyExecutor {
    /** Fully qualified path of instruction file*/
    private String fullFilePath;
    private List<String> instructions;
    private List<Parser> parserRegistry;
    private NumberConverter numberConverter;

    public GalaxyExecutor(String fullFilePath) throws IOException {
        this.fullFilePath = fullFilePath;
        instructions = new LinkedList<String>(); // no lookup, only traverse ahead
        parserRegistry = new ArrayList<Parser>(3);
        numberConverter = NumberConverter.getInstance();

        init();
    }

    private void init() throws IOException {
        populateInstructions();
        registerParsers();
    }

    private void registerParsers() {
        parserRegistry.add(new AssignmentStatementParser());
        parserRegistry.add(new QuestionParser(numberConverter));
        parserRegistry.add(new StatementParser(numberConverter));
    }

    // get all the instructions first, no need to block the IO
    private void populateInstructions() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fullFilePath));
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                instructions.add(line);
            }
        } finally {
            br.close();
        }
    }

    public void execute() {
        // interpret each statement
        // best case O(n), worst case O(n^2)
        for(String statement : instructions) {
            boolean parserFound = false;

            if(statement.isEmpty()) {
                continue;
            }
            try {
                for(Parser parser: parserRegistry) {
                    if(parser.canParse(statement)) {
                        parserFound = true;
                        String output = parser.parse(statement);
                        if(!output.isEmpty())
                            System.out.println(output);
                        break;
                    }
                }
                if(!parserFound) {
                    throw new Exception("Can not interpret the given statement. "+statement);
                }
            } catch (Exception e) {
                // logger.error("Error while parsing the instruction ", e);
                System.out.println("I have no idea what you are talking about");
            }
        }
    }
}
