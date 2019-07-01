package test.galaxy.test;

import galaxy.converter.NumberConverter;
import org.junit.Assert;
import org.junit.Test;

public class NumberConversionTest {
    NumberConverter numberConverter = NumberConverter.getInstance();

    @Test
    public void testRomanToArabic() {

        float value = numberConverter.toArabic("MMVI");
        Assert.assertTrue(value == 2006F);

        value = numberConverter.toArabic("MMC");
        Assert.assertTrue(value == 2100F);

        value = numberConverter.toArabic("MCMXLIV");
        Assert.assertTrue(value == 1944F);

        value = numberConverter.toArabic("MMCDXXI");
        Assert.assertTrue(value == 2421F);

        value = numberConverter.toArabic("DCCLXXXIX");
        Assert.assertTrue(value == 789F);

        String romanValue = numberConverter.toRoman(1903);
        Assert.assertTrue(romanValue.equals("MCMIII"));
    }


    @Test
    public void testArabicToRoman() throws Exception {
        String roman = numberConverter.toRoman(1903);
        Assert.assertTrue("MCMIII".equals(roman));

        roman = numberConverter.toRoman(2006);
        Assert.assertTrue("MMVI".equals(roman));

        roman = numberConverter.toRoman(2421);
        Assert.assertTrue("MMCDXXI".equals(roman));

        try {
            numberConverter.toRoman(5000);
        } catch (NumberFormatException e) {
            Assert.assertTrue("Number out or range. Minimum allowed = 1 and max allowed is 3999".equals(e.getMessage()));
        }

        try {
            numberConverter.toRoman(0);
        } catch (NumberFormatException e) {
            Assert.assertTrue("Number out or range. Minimum allowed = 1 and max allowed is 3999".equals(e.getMessage()));
        }
    }

    @Test
    public void testValidation() throws Exception {
        try {
            numberConverter.validate("LXVIV");
        } catch (NumberFormatException e) {
            Assert.assertTrue("Invalid Number: LXVIV".equals(e.getMessage()));
        }

        try {
            numberConverter.validate("XXXIXX");
        } catch (Exception e) {
            Assert.assertTrue("Invalid Number: XXXIXX".equals(e.getMessage()));
        }

        numberConverter.validate("DCCLXXXIX");
        numberConverter.validate("MMCDXXI");
        numberConverter.validate("MCMXLIV");
        numberConverter.validate("MMC");
    }
}
