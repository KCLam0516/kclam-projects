package com.example.keanchin.domain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.regex.Pattern;

public class Validator {
        public String hashPassword(String password) throws Exception {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashPassword = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return new String(hashPassword);
        }

        public static boolean isValidContactNumber(String contactNumber){
            String contactNumberRegex = "^[0-9]*$";
            Pattern pat = Pattern.compile(contactNumberRegex);
            if(contactNumber == null || contactNumber.length() < 9 || contactNumber.length() > 13) {
                return false;
            }
            return pat.matcher(contactNumber).matches();
        }

        public static boolean isValidEmail(String email) {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";

            Pattern pat = Pattern.compile(emailRegex);
            if (email == null) {
                return false;
            }
            return pat.matcher(email).matches();
        }
}
