/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Date;

/**
 *
 * @author linyuxuan
 */
public  class  Validator {

    public static boolean validateBoolean(String test) {
        try {
            Boolean.parseBoolean(test.trim());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean validateDate(String test) {
        try {
            Date.valueOf(test.trim());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean validateInt(String test) {
        try {
            Integer.parseInt(test.trim());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean validateDouble(String test) {
        try {
            Double.parseDouble(test.trim());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
