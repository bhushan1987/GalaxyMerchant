package galaxy.numbers;

/**
 * Defines the Roman Numerals that are being used for representing a number.
 */
public enum RomanNumeral {
    I(1, "I"),
    V(5, "V"),
    X(10, "X"),
    L(50, "L"),
    C(100, "C"),
    D(500, "D"),
    M(1000, "M");

    private final int arabicValue;
    private final String name;

    RomanNumeral(int arabicValue, String name) {
        this.name = name;
        this.arabicValue = arabicValue;
    }

    public String getName() {
        return name;
    }

    public int getArabicValue() {
        return arabicValue;
    }

    public static RomanNumeral getRomanNumeralFromName(String name) {
        RomanNumeral[] values = RomanNumeral.values();
        for(RomanNumeral romanNumeral : values) {
            if (romanNumeral.getName().equals(name)){
                return romanNumeral;
            }
        }
        return null;
    }

    public static String getRomanNumeralFromArabic(int number) {
        RomanNumeral[] values = RomanNumeral.values();
        for(RomanNumeral romanNumeral : values) {
            if (romanNumeral.getArabicValue() == number){
                return romanNumeral.getName();
            }
        }
        return null;
    }
}
