package controller;

import java.util.Scanner;
import model.Escalador;
import DAO.interfaces.EscaladorDAO; // Importante usar la interfaz
import DAO.sqlite.SQLiteEscaladorDAO;
import view.MenuTerminal;

public class EscaladorController {

    private final EscaladorDAO escaladorDAO;
    private final MenuTerminal view;
    private final Scanner sc;

    public EscaladorController(EscaladorDAO escaladorDAO) {
        this.escaladorDAO = escaladorDAO;
        this.view = new MenuTerminal();
        this.sc = new Scanner(System.in);
    }

    // --- MÉTODO PRINCIPAL DE CREACIÓN ---
    public void crearEscalador() {
        view.pedirDato("Alias");
        String alias = sc.nextLine();
        
        view.pedirDato("Nom");
        String nom = sc.nextLine();

        view.pedirDato("Edat");
        int edad = leerEntero();

        // Llamamos al método que hemos separado abajo
        String estil = elegirEstil(); 

        // Creamos el objeto con los datos limpios
        Escalador nuevo = new Escalador(alias, nom, edad, estil);

        try {
            escaladorDAO.create(nuevo);
            view.mostrarExito();
        } catch (RuntimeException e) {
            if (e.getMessage().equals("EL_ALIAS_YA_EXISTE")) {
                view.mostrarError("Error: El alias '" + alias + "' ya está registrado.");
            } else {
                view.mostrarError("Ocurrió un error inesperado al guardar.");
            }
        }
    }

    // --- MÉTODO PARA ELEGIR ESTILO (FUERA DE CREAR) ---
    private String elegirEstil() {
        String estil = "";
        boolean valido = false;

        while (!valido) {
            view.mostrarOpcionesEstil(); 
            int seleccion = leerEntero(); 

            switch (seleccion) {
                case 1 -> { estil = "esportiva"; valido = true; }
                case 2 -> { estil = "clàssica"; valido = true; }
                case 3 -> { estil = "gel"; valido = true; }
                default -> view.mostrarError("Opción no válida. Inténtelo de nuevo.");
            }
        }
        return estil;
    }

    // --- UTILIDAD PARA LEER ENTEROS SIN ERRORES ---
    private int leerEntero() {
        try {
            int num = Integer.parseInt(sc.nextLine());
            return num;
        } catch (NumberFormatException e) {
            return -1; 
        }
    }
}