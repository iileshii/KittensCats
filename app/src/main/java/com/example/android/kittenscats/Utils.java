package com.example.android.kittenscats;

/**
 * Created by Leshii on 6/29/2015.
 * Utils to use
 */
public class Utils {

    // this class works with Base58 code for short URLs
    public static class Base58 {

        private static final char[] BASE58_CHARS =
                "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();

        public static String numberToAlpha(long number) {
            char[] buffer = new char[20];
            int index = 0;
            do {
                buffer[index++] = BASE58_CHARS[(int) (number % BASE58_CHARS.length)];
                number = number / BASE58_CHARS.length;
            } while (number > 0);
            return new String(buffer, 0, index);
        }

        public static long alphaToNumber(String text) {
            char[] chars = text.toCharArray();
            long result = 0;
            long multiplier = 1;
            for (char c : chars) {
                int digit;
                if (c >= '1' && c <= '9') {
                    digit = c - '1';
                } else if (c >= 'A' && c < 'I') {
                    digit = (c - 'A') + 9;
                } else if (c > 'I' && c < 'O') {
                    digit = (c - 'J') + 17;
                } else if (c > 'O' && c <= 'Z') {
                    digit = (c - 'P') + 22;
                } else if (c >= 'a' && c < 'l') {
                    digit = (c - 'a') + 33;
                } else if (c > 'l' && c <= 'z') {
                    digit = (c - 'l') + 43;
                } else {
                    throw new IllegalArgumentException("Illegal character found: '" + c + "'");
                }

                result += digit * multiplier;
                multiplier = multiplier * BASE58_CHARS.length;
            }
            return result;
        }
    }
}
