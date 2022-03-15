package comp3350.sceneit.logic;

import org.junit.Test;

import java.util.Calendar;

import comp3350.sceneit.logic.CreditManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CreditManagerTest {

    @Test
    public void testCheckPostal(){
        //Test thats are more so for Canadian Codes
        assertTrue(CreditManager.checkPostal("R0C2Z0"));
        assertTrue(CreditManager.checkPostal("R0C 2Z0"));
        assertFalse(CreditManager.checkPostal("R0C2Z"));
        assertFalse(CreditManager.checkPostal("0C2Z0"));
        assertFalse(CreditManager.checkPostal("RRRRRR"));
        assertFalse(CreditManager.checkPostal("000000"));
        assertFalse(CreditManager.checkPostal(""));
        assertFalse(CreditManager.checkPostal("R0C2Z0R"));
        assertFalse(CreditManager.checkPostal("0R0C2Z0"));

        //tests for the US codes
        assertTrue(CreditManager.checkPostal("08884"));
        assertTrue(CreditManager.checkPostal("12345"));
        assertTrue(CreditManager.checkPostal("12345-6789"));
        assertFalse(CreditManager.checkPostal("1234"));
        assertFalse(CreditManager.checkPostal("123456"));
        assertFalse(CreditManager.checkPostal("123456789"));
        assertFalse(CreditManager.checkPostal("1234-56789"));
    }

    @Test
    public void testCheckEmail(){
        assertTrue(CreditManager.checkEmail("Jonathan.Boisvert@umanitoba.ca"));
        assertTrue(CreditManager.checkEmail("deadservice@hotmail.com"));
        assertTrue(CreditManager.checkEmail("bob@default"));
        assertFalse(CreditManager.checkEmail(""));
        assertFalse(CreditManager.checkEmail("THIS IS NOT EMAIL"));
        assertFalse(CreditManager.checkEmail("google.com"));
        assertFalse(CreditManager.checkEmail("@email.com"));
    }

    @Test
    public void testCheckPhone() {
        //american numbers
        assertTrue(CreditManager.checkPhone("2044613456"));
        assertTrue(CreditManager.checkPhone("204 461 3498"));
        //Not numbers at all
        assertFalse(CreditManager.checkPhone("Frank"));
        assertFalse(CreditManager.checkPhone(""));
        //correct international numbers, also testing both sides of an if
        assertTrue(CreditManager.checkPhone("+1 1234567890123"));
        assertTrue(CreditManager.checkPhone("1 1234567890123"));
        assertTrue(CreditManager.checkPhone("+12 123456789"));
        assertTrue(CreditManager.checkPhone("12 123456789"));
        assertTrue(CreditManager.checkPhone("+123 123456"));
        assertTrue(CreditManager.checkPhone("123 123456"));
        //too long
        assertFalse(CreditManager.checkPhone("+1 123456789012333"));
    }

    @Test
    public void testReverse() {
        assertEquals(CreditManager.reverseNumber(1962), 2691);
        assertEquals(CreditManager.reverseNumber(162), 261);
        assertEquals(CreditManager.reverseNumber(12), 21);
        assertEquals(CreditManager.reverseNumber(0), 0);
    }

    @Test
    public void testCheckDate() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        //This test currently only works when the month is less then 10;
        assertFalse(CreditManager.checkDate(""));//No date
        assertFalse(CreditManager.checkDate("1"));//not enough info
        assertFalse(CreditManager.checkDate("11111"));//to much info
        assertTrue(CreditManager.checkDate("0" + month + year % 100));
        assertTrue(CreditManager.checkDate("0" + month + (year % 100 + 1)));
        assertTrue(CreditManager.checkDate("0" + (month + 1) + (year % 100)));
        assertTrue(CreditManager.checkDate("0" + (month + 1) + (year % 100 + 1)));
        assertFalse(CreditManager.checkDate("0" + (month - 1) + (year % 100)));
        assertFalse(CreditManager.checkDate("0" + (month) + (year % 100 - 1)));
        assertFalse(CreditManager.checkDate("0" + (month - 1) + (year % 100 - 1)));
    }

    @Test
    public void testIsNumeric() {
        assertTrue(CreditManager.isNumeric("12"));
        assertFalse(CreditManager.isNumeric(""));
        assertFalse(CreditManager.isNumeric("F"));
        assertFalse(CreditManager.isNumeric("1F"));
        assertFalse(CreditManager.isNumeric("F1"));
    }

    @Test
    public void testValidateCredit() {
        assertFalse(CreditManager.validateCredit(""));
        assertFalse(CreditManager.validateCredit("111122223333444"));//15 numbers
        assertFalse(CreditManager.validateCredit("11112222333344445"));//17 numbers
        assertFalse(CreditManager.validateCredit("WORDS"));
        //Cards that work
        assertTrue(CreditManager.validateCredit("4024007197504847"));
        assertTrue(CreditManager.validateCredit("4556472298918518"));
        assertTrue(CreditManager.validateCredit("6011589937907554"));
        //cards that dont work
        assertFalse(CreditManager.validateCredit("4024007197504846"));
    }

    @Test
    public void testFieldFilled() {
        assertTrue(CreditManager.fieldFilled("Yo lots of text"));
        assertTrue(CreditManager.fieldFilled("D"));
        assertFalse(CreditManager.fieldFilled(""));
        assertFalse(CreditManager.fieldFilled(null));
        assertFalse(CreditManager.fieldFilled("    "));
        assertTrue(CreditManager.fieldFilled("T    "));
        assertTrue(CreditManager.fieldFilled("    T"));
        assertTrue(CreditManager.fieldFilled("  T  "));
    }
}
