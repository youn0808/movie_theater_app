package comp3350.sceneit.logic;

import android.content.Intent;

import java.util.Calendar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import comp3350.sceneit.presentation.MainActivity;

public class CreditManager {

    //Check postal code for Canada and USA
    static public boolean checkPostal(String data){
        //Regex for canadaian and us postal codes
        Pattern pCan = Pattern.compile("^[A-Za-z]\\d[A-Za-z][ -]?\\d[A-Za-z]\\d$");
        Pattern pUS = Pattern.compile("^[0-9]{5}(?:-[0-9]{4})?$");

        Matcher mCan = pCan.matcher(data);
        Matcher mUS = pUS.matcher(data);
        //see if the string fits the specifications of the regex.
        Boolean foundCan = (mCan.find() && mCan.group().equals(data));
        Boolean foundUS = (mUS.find() && mUS.group().equals(data));

        return foundCan || foundUS;
    }

    //Check if email is valid
    static public boolean checkEmail(String data){
        Pattern p = Pattern.compile("^(.+)@(.+)$");

        Matcher m = p.matcher(data);
        return (m.find() && m.group().equals(data));
    }

    //Check if phone is valid using the ITU-T E.164 standard
    //https://en.wikipedia.org/wiki/E.164
    //Assuming strings passed could have or could not have a plus sign.
    static public boolean checkPhone(String data) {
        boolean foundNational;
        Matcher mNational;
        Pattern pNational = Pattern.compile("^\\+(?:[0-9] ?){6,14}[0-9]$");

        //Making it so dont have to worry about there being a plus sign of not
        //could change the regex to handle it but thats black magic im not touching
        if(!data.isEmpty() && data.charAt(0) == ('+')) {
            mNational = pNational.matcher(data);
            foundNational = (mNational.find() && mNational.group().equals(data));
        } else {
            mNational = pNational.matcher("+" + data);
            foundNational = (mNational.find() && mNational.group().equals("+" + data));
        }

        return foundNational;
    }

    //Checks if a string has any data in it.
    static public boolean fieldFilled(String data) {
        //return data != null && data.length() != 0;
        return data != null && !data.isEmpty() && data.trim().length() > 0;
    }

    //checks if a the date given in mmyy format (it assumes its that format) is after the currect date
    //This is to check the expiry date on credit cards.
    static public boolean checkDate(String date) {
        boolean validated = false;
        int inputMonth;
        int inputYear;
        int dateNum;
        Calendar c = Calendar.getInstance();
        if (isNumeric(date) && date.length() == 4) {
            dateNum = Integer.parseInt(date);
            inputMonth = dateNum / 100; //removes the year digits
            inputYear = dateNum % 100; //Removes everything that isnt the last two digits
            //the date is correct if the input month and year are less or equal to current month and year.
            if(inputYear == c.get(Calendar.YEAR) % 100) {
                if(inputMonth >= c.get(Calendar.MONTH)) {
                    validated = true;
                }
            } else if(inputYear > c.get(Calendar.YEAR) % 100){
                validated = true;
            }
        }
        return validated;
    }

    //Uses Luhns algorithm to validate credit cards.
    static public boolean validateCredit(String creditString) {
        boolean validated = false;
        long workingOn;
        long creditNum;
        //Make sure the string passed in is numeric
        if (isNumeric(creditString)) {
            //Get a number from the string
            creditNum = Long.parseLong(creditString);
            //Check if credit card has 16 digits
            if (String.valueOf(creditNum).length() == 16) {
                //the next 4 lines basically all do Luhns algorithm for validating credit cards
                workingOn = creditNum / 10;//remove last digit from number
                workingOn = reverseNumber(workingOn);
                workingOn = Luhn(workingOn);

                //
                validated = ((workingOn + creditNum % 10) % 10 == 0);
            }
        }
        return validated;
    }

    //Is this string able to be converted to a number.
    static public boolean isNumeric(String number) {
        boolean isNumber;
        try {
            Long.parseLong(number);
            isNumber = true;
        } catch (NumberFormatException e) {
            isNumber = false;
        }
        return isNumber;
    }

    //Reverse what ever number is passed to it
    static public long reverseNumber(long number) {

        long reversed = 0;
        long storage = 0;

        while (number != 0) {
            storage = number % 10;
            reversed = reversed * 10 + storage;
            number /= 10;
        }

        return reversed;
    }

    //Luhn is all the super weird math parts of Luhns algorithm, look it up on wikipedia
    static private long Luhn(long number) {
        //I need to convert my number to a string so i can look at specific digits on the number
        String numberString = Long.toString(number);
        long[] trackNumbers = new long[numberString.length()];
        long total = 0;
        //Go through number by number and do the doubling to every odd number and subtract 9 if that
        //doubled number is greater then 9. Then add all them to total
        for (int i = 0; i < numberString.length(); i++) {
            trackNumbers[i] = Character.getNumericValue(numberString.charAt(i));
            if (i % 2 == 0) {
                trackNumbers[i] *= 2;
                if (trackNumbers[i] > 9)
                    trackNumbers[i] -= 9;
            }
            total += trackNumbers[i];
        }

        return total;
    }

    //Validate all the info in a credit card is correct, if it is then dont need to send an error message back
    //However if there is a problem then send back an error message.
    static public String validateInput(CreditCard myCard) {
        String message = null;

        if(myCard != null){
            if (!validateCredit(myCard.getNumberCard())) {
                message = "Invalid Credit Card Number";
            } else if (!fieldFilled(myCard.getNameCard())) {
                message = "Please fill in the name located on your Credit card";
            } else if (!fieldFilled(myCard.getCvc())) {
                message = "Please fill in the cvc located on your Credit card";
            } else if (!checkDate(myCard.getExpDate())) {
                message = "This card may be expired, check your expiry date";
            } else if (!fieldFilled(myCard.getCountry())) {
                message = "Please fill in your country";
            } else if (!fieldFilled(myCard.getProvince())) {
                message = "Please fill in your Region/Province/State";
            } else if (!fieldFilled(myCard.getAddressOne())) {
                message = "Please fill in your Address One";
            } else if (!fieldFilled(myCard.getCity())) {
                message = "Please fill in your city";
            } else if (!checkPostal(myCard.getPostalCode())) {
                message = "Please fill in your Postal Code";
            } else if (!checkPhone(myCard.getTelephoneNumber())) {
                message = "Please fill in your Telephone";
            } else if (!checkEmail(myCard.getEmail())) {
                message = "Please fill in your email";
            }
        }

        return message;
    }
}