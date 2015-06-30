package com.example.android.kittenscats;

/**
 * Created by Leshii on 6/29/2015.
 * Utils to use
 */
public class Utils {

    // this class works with Base58 code for short URLs
    public static class Base58 {

        private static final String BASE58_ALPHABET =
                "123456789abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";

        public static String encode(long number) {
            String result = "";
            int baseLength = BASE58_ALPHABET.length();

            while (number >= baseLength) {
                double div = number / baseLength;
                int mod = (int) (number - (baseLength * (long) div));
                result = BASE58_ALPHABET.substring(mod, mod + 1) + result;
                number = (long) div;
            }

            if (number != 0) result =
                    BASE58_ALPHABET.substring((int) number, (int) number + 1) + result;
            return result;

        }

        public static long decode(String text) {
            String line = text;
            long result = 0;
            long multi = 1;

            while (line.length() > 0) {
                int length = line.length();
                String digit = line.substring(length - 1, length);
                result += multi * BASE58_ALPHABET.indexOf(digit);
                multi = multi * BASE58_ALPHABET.length();
                line = line.substring(0, length - 1);
            }

            return result;
        }
    }
}
