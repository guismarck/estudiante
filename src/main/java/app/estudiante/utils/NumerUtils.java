package app.estudiante.utils;

public class NumerUtils {
    public static boolean isInt(String value){
        try{
            Integer.parseInt(value);
            return true;
        }catch (NumberFormatException e) {
            return false;
        }
    }
}
