package test.galaxy.test;

import galaxy.converter.NumberConverter;
import galaxy.numbers.NumberMapper;
import galaxy.parser.impl.AssignmentStatementParser;
import galaxy.parser.impl.QuestionParser;
import galaxy.parser.impl.StatementParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class ParserTest {

    @Before
    public void clearMaps() {
        NumberMapper.clearAllMaps();
    }

    @Test
    public void testConversion() throws Exception {
        String assignmentStatement1 = "pish is X";
        String assignmentStatement2 = "hnga is C";
        String statement = "pish pish Iron is 3910 Credits";
        NumberConverter numberConverter = NumberConverter.getInstance();

        AssignmentStatementParser assignmentStatementParser = new AssignmentStatementParser();
        assignmentStatementParser.parse(assignmentStatement1);
        assignmentStatementParser.parse(assignmentStatement2);

        new StatementParser(numberConverter).parse(statement);
        for(Map.Entry<String, Float> entry : NumberMapper.metalValueMap.entrySet()) {
            Assert.assertTrue(entry.getKey().equals("Iron"));
            Assert.assertTrue(entry.getValue() == 195.5);
        }

        QuestionParser questionParser = new QuestionParser(numberConverter);
        questionParser.parse("how much is hnga pish ?");

        questionParser.parse("how many Credits is hnga pish Iron ?");
    }
}
