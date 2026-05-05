package controller;

import java.util.Scanner;

import DAO.interfaces.EscaladorDAO;
import model.Escalador;
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

    // ! CREAR ESCALADOR
    public void crearEscalador() {
        view.missatge("\n=== CREAR ESCALADOR ===");

        view.pedirDato("Alias");
        String alias = sc.nextLine();

        // Verificar que no existeix
        if (escaladorDAO.findByAlias(alias) != null) {
            view.mostrarError("Error: L'alias '" + alias + "' ja està registrat.");
            return;
        }

        view.pedirDato("Nom");
        String nom = sc.nextLine();

        view.pedirDato("Edat");
        int edat = leerEntero();

        String estil = elegirEstil();

        Escalador nou = new Escalador(alias, nom, edat, estil);

        try {
            escaladorDAO.create(nou);
            view.mostrarExito("Escalador creat correctament!");
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().equals("EL_ALIAS_YA_EXISTE")) {
                view.mostrarError("Error: L'alias '" + alias + "' ja està registrat.");
            } else {
                view.mostrarError("Error inesperat en crear l'escalador.");
            }
        }
    }
}