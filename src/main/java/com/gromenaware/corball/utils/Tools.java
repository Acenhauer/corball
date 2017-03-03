package com.gromenaware.corball.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Guillem Hernández Sola
 */
public class Tools {
    /**
     * Method used to return a bigdecimal from a string
     *
     * @param bigDecimalString
     * @return
     */
    public static BigDecimal stringToBigdecimal(String bigDecimalString) {
        BigDecimal bigDecimalDetails = BigDecimal.ZERO;
        if (!bigDecimalString.equals("")) {
            bigDecimalString = bigDecimalString.replace(",", ".").replaceAll("[^\\d.]", "");
            bigDecimalDetails = new BigDecimal(bigDecimalString);
        }
        return bigDecimalDetails;
    }

    /**
     * Method used to execute javaScript
     *
     * @param driver
     * @param action
     * @return
     */
    public static Object getEval(WebDriver driver, String action) {
        JavascriptExecutor js;
        Object getEval = null;
        if (driver instanceof JavascriptExecutor) {
            js = (JavascriptExecutor) driver;
            getEval = js.executeScript("return " + action);
        }
        return getEval;
    }

    /**
     * Method used to return a string
     *
     * @param word
     * @return
     */
    public static String replaceUnAsciiCharacters(String word) {
        word = word.replaceAll(
                "[\\u00C1/\\u00E1/\\u00C0/\\u00C2/\\u00C3/\\u00C4/\\u00E0/\\u00E2/\\u00E3/\\u00E4]",
                "a");
        word = word.replaceAll("[\\u00E9/\\u00C9/\\u00C8/\\u00CA/\\u00CB/\\u00E8/\\u00EA/\\u00EB]",
                "e");
        word = word.replaceAll("[\\u00CD/\\u00ED/\\u00CC/\\u00CF/\\u00EC/\\u00EE/\\u00EF]", "i");
        word = word.replaceAll(
                "[\\u00D3/\\u00F3/\\u00D2/\\u00D4/\\u00D5/\\u00D6/\\u00F2/\\u00F4/\\u00F5/\\u00F6]",
                "o");
        word = word.replaceAll("[\\u00DA/\\u00FA/\\u00DC/\\u00FC/\\u00F9/\\u00FB]", "u");

        word = word.replaceAll("[\\u00F1/\\u00D1]", "n");

        word = word.toLowerCase().replaceAll("\\P{InBasic_Latin}", "");
        return word;
    }

    /**
     * Method used to select an item from WebElements list. If seleceted Item isn't displayed, run
     * again to select any other item.
     *
     * @param itemsList
     * @return
     */
    public static WebElement selectAnItemFromList(List<WebElement> itemsList) {
        Random random = new Random();
        int position = random.nextInt(itemsList.size());
        WebElement selectedItem = itemsList.get(position);
        if (!selectedItem.isDisplayed()) {
            selectedItem = selectAnItemFromList(itemsList);
        }
        return selectedItem;
    }

    /**
     * Method used to verify consistence between two strings. You can choose if you want to do equals
     * (1) or contains (2). It do Assert id it fail.
     *
     * @param type
     * @param expected
     * @param actually
     */
    public static void verifyConsistence(Integer type, String expected, String actually) {
        expected = expected.toLowerCase();
        actually = actually.toLowerCase();
        switch (type) {
            case 1: // to do equals
                Assert.assertTrue(expected.equals(actually),
                        "ERROR: Information is NOT consistence. " + "Expected: '" + expected
                                + "' but found: '" + actually + "'.");
                break;
            case 2: // To do contains
                boolean itContainsIt = false;
                if (actually.contains(expected))
                    itContainsIt = true;
                if (expected.contains(actually))
                    itContainsIt = true;
                Assert.assertTrue(itContainsIt,
                        "ERROR: Information is NOT consistence. " + "Actually ('" + actually
                                + "') NOT contains: '" + expected + "' and vice versa.");
                break;
        }
    }

    /**
     * Method used to compare information between two strings. You can choose if you want to do equals
     * (1) or contains (2).
     *
     * @param type
     * @param expected
     * @param actually
     */
    public static boolean compareInformation(Integer type, String expected, String actually) {
        boolean isSimilar = false;
        expected = replaceUnAsciiCharacters(expected.toLowerCase());
        actually = replaceUnAsciiCharacters(actually.toLowerCase());
        switch (type) {
            case 1: // to do equals
                if (expected.equals(actually))
                    isSimilar = true;
                break;

            case 2: // To do contains
                boolean itContainsIt = false;
                if (actually.contains(expected))
                    itContainsIt = true;
                if (expected.contains(actually))
                    itContainsIt = true;
                if (itContainsIt)
                    isSimilar = true;
                break;
        }

        return isSimilar;
    }

    /**
     * Method used obtain text from String before an element.
     *
     * @param beforeThisElement
     * @param fullString
     * @return
     */
    public static String obtainTextBeforeIt(String beforeThisElement, String fullString) {
        String obtainedName = "";
        if (fullString.contains(beforeThisElement)) {
            obtainedName = fullString.substring(0, fullString.indexOf(beforeThisElement));
        } else
            obtainedName = fullString;

        return obtainedName;
    }

    public static String findSubstring(String string, String regex) {
        String subString = null;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            subString = matcher.group(0);
        }
        return subString;
    }

    public static String formatDateToUseFullFormat(String dateToFormat, String OLD_FORMAT,
                                                   String NEW_FORMAT, String language) {
        String newDateString;
        SimpleDateFormat formatter = new SimpleDateFormat(OLD_FORMAT);
        SimpleDateFormat newFormatter =
                new SimpleDateFormat(NEW_FORMAT, Locale.forLanguageTag(language));
        try {
            Date date = formatter.parse(String.valueOf(dateToFormat));
            newDateString = newFormatter.format(date);
        } catch (ParseException p) {
            return "";
        }
        return newDateString;
    }


}
