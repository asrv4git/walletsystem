package com.fabhotels.walletsystem.utils;

public class Constants {
    //  minimum length 8 and must have combination of uppercase, lower case character, number and special character
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    public static final String USERNAME_REGEX = "^[a-zA-Z0-9]*$";
    //mobile number must have 10 digit
    public static final String MOBILENUMBER_REGEX = "^\\d{10}$";
}
