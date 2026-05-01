package utils;

public class ValidadorRegexMenus {
    
    public static Integer validarRegex(String respuesta, String regex){
        if (respuesta.matches(regex)){
            Integer opcion = Integer.parseInt(respuesta);
            return opcion;
        }
        return -1;
    }
}
