package com.escalada.utils;

import java.util.Arrays;
import java.util.List;

public class Validator {

    // ! LLISTA DELS GRAUS ORDENATS DE MENYS A MÉS
    private static final List<String> GRADES = Arrays.asList(
        "4", "4+", "5", "5+", "6a", "6a+", "6b", "6b+", "6c", "6c+", 
        "7a", "7a+", "7b", "7b+", "7c", "7c+", "8a", "8a+", "8b", "8b+", 
        "8c", "8c+", "9a", "9a+", "9b", "9b+", "9c", "9c+"
    );

    // ! RETORNA EL GRAU MÉS ALT
    public static String obtenerMasAlto(String grado1, String grado2) {
        int indice1 = GRADES.indexOf(grado1);
        int indice2 = GRADES.indexOf(grado2);

        return (indice1 >= indice2) ? grado1 : grado2;
    }

    // ! VALIDA SI UN GRAU ES VALID
    public static boolean esGradoValido(String grado) {
        return GRADES.contains(grado);
    }
}