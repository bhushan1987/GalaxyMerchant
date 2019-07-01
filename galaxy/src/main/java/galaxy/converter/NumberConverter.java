package galaxy.converter;

import galaxy.numbers.RomanNumeral;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for interconversion of Roman to Arabic numerals.<p>
 */
public class NumberConverter {

    private Pattern pattern;
    private static final int MIN_ALLOWED = 1;
    private static final int MAX_ALLOWED = 3999;

    // empty strings for 0, max consecutive letters allowed 3
    private static final String[] COMBINATION_M = {"", "M", "MM", "MMM"}; // 1000, 2000, 3000
    private static final String[] COMBINATION_C = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"}; // 100, 200, 300, 400 (CD), 500, 600, 700, 800, 900
    private static final String[] COMBINATION_X = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"}; // 10, 20, 30, 40, 50, 60, 70, 80, 90
    private static final String[] COMBINATION_I = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"}; // 1, 2, 3, 4, 5, 6, 7, 8, 9


    private static NumberConverter numberConverter;

    private NumberConverter() {
        pattern = Pattern.compile("^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");
    }

    public static NumberConverter getInstance() {
        // skip synchronization for now, this is not multithreaded env
        if (numberConverter == null) {
            numberConverter = new NumberConverter();
        }
        return numberConverter;
    }

    /**
     * Converts the given Roman number into arabic value.
     *
     * @param romanNumber Roman number in String
     * @return Arabic value for the given Roman Number
     */
    public float toArabic(String romanNumber) throws NumberFormatException {
        validate(romanNumber);

        // flag to skip the next roman because it is already processed
        boolean considerNext = true;

        float total = 0F;

        RomanNumeral current;

        for (int j = 0, i = 1; j < romanNumber.length(); i++, j++) {
            if (!considerNext) {
                considerNext = true;
                continue;
            }

            // get current value
            current = RomanNumeral.getRomanNumeralFromName(String.valueOf(romanNumber.charAt(j)));
            int currentArabicValue = current.getArabicValue();

            // handle last char
            if (i >= romanNumber.length()) {
                total += currentArabicValue;
                break;
            }

            RomanNumeral next = RomanNumeral.getRomanNumeralFromName(String.valueOf(romanNumber.charAt(i)));
            int nextArabicValue = next.getArabicValue();

            if (currentArabicValue < nextArabicValue) {
                total += nextArabicValue - currentArabicValue;
                considerNext = false; // processed two consecutive roman numbers in this step, hence jump by two positions
            } else {
                total += currentArabicValue;
                considerNext = true;
            }
        }
        return total;
    }

    public String toRoman(int arabicNumber) throws NumberFormatException {
        if (arabicNumber < MIN_ALLOWED || arabicNumber > MAX_ALLOWED) {
            throw new NumberFormatException("Number out or range. Minimum allowed = 1 and max allowed is 3999");
        }
        String thousandsPlace = COMBINATION_M[arabicNumber / 1000];
        String hundredsPlace = COMBINATION_C[arabicNumber % 1000 / 100];
        String tensPlace = COMBINATION_X[arabicNumber % 100 / 10];
        String unitsPlace = COMBINATION_I[arabicNumber % 10];

        return new StringBuilder()
                .append(thousandsPlace)
                .append(hundredsPlace)
                .append(tensPlace)
                .append(unitsPlace)
                .toString();
    }

    /**
     * Validates the given roman number against the rules.
     * @param romanNumber
     * @return true if number is valid
     * @throws NumberFormatException If given roman number is not valid
     */
    public boolean validate(String romanNumber) throws NumberFormatException {
        Matcher matcher = pattern.matcher(romanNumber);
        if (matcher.matches()) {
            return true;
        } else {
            throw new NumberFormatException("Invalid Number: "+romanNumber);
        }
    }
}
