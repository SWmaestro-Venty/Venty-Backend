package com.swm.ventybackend.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRegex {
    public static boolean isRegexEmail(String target) {
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    public static boolean   canConvertLong(String   id){
        int length = id.length();

        if(length > 19){
            return false;
        }

        for(int i=0;i<length;++i){
            if(id.charAt(i)<'0' || id.charAt(i)>'9'){
                return false;
            }
        }

        if(length == 19){
            return id.compareTo("9223372036854775809") < 0;
        }
        return true;
    }

    public static   boolean isBornYear(String   year){
        int length = year.length();

        if(length != 4) return false;

        for(int i=0;i<4;++i)
            if(year.charAt(i)<'0' || year.charAt(i)>'9')
                return false;
        return true;
    }

    public static   boolean isRegexUrl(String   url){
        String regex = "((http|https)://)(www.)?"
                + "[a-zA-Z0-9@:%._\\+~#?&//=]"
                + "{2,256}\\.[a-z]"
                + "{2,6}\\b([-a-zA-Z0-9@:%"
                + "._\\+~#?&//=]*)";

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);
        return  matcher.find();
    }

    public static   boolean isBoolean(String    str){
        return  str.equals("true") || str.equals("false");
    }

    public  static  boolean canConvertInt(String    str){
        int length = str.length();
        if(length>9) return false;

        for(int i=0;i<length;++i)
            if(str.charAt(i)<'0'||str.charAt(i)>'9')
                return false;
        if(length == 9){
            return str.compareTo("2147483647") < 0;
        }
        return true;
    }

    public static boolean isAddressCode(String addressCode) {
        int length = addressCode.length();

        if(length != 5){
            return false;
        }

        for(int i=0;i<length;++i)
            if(addressCode.charAt(i)<'0'||addressCode.charAt(i)>'9')
                return false;
        return true;

    }
}
