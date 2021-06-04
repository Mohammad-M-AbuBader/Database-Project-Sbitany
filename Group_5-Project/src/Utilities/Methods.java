package Utilities;

public class Methods {
    /**
     * To check the value of the entered numberOfShares if contain only digits or not
     */
    public static boolean isNumber(String number) {
        /* To check the entered number of shares, that it consists of
           only digits
         */
        try {
            long temp = Long.parseLong(number);
            return number.matches("\\d+") && temp > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
