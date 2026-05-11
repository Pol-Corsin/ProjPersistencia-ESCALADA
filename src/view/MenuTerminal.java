package view;

import java.util.Scanner;

public class MenuTerminal {
    private static final Scanner sc = new Scanner(System.in);

    public static void missatge(String msg) {
        System.out.println(msg);
    }

    // Menu principal de la aplicació
    public static void menu() {
        missatge("############################");
        missatge("#            MENU          #");
        missatge("############################");
        missatge("1) Crear");
        missatge("2) Llistar");
        missatge("3) Modificar");
        missatge("4) Eliminar");
        missatge("5) Altres funcions");
        missatge("0) Sortir");
    }

    // Menu principal de crear de l'aplicació
    public static void menuCrear() {
        missatge("############################");
        missatge("#           CREAR          #");
        missatge("############################");
        missatge("1) Escalador");
        missatge("2) VIA");
        missatge("3) Escola");
        missatge("4) Sector");
        missatge("0) Sortir");
    }

    // Menu principal de modificar de l'aplicació
    public static void menuModificar() {
        missatge("############################");
        missatge("#         MODIFICAR        #");
        missatge("############################");
        missatge("1) Escalador");
        missatge("2) VIA");
        missatge("3) Escola");
        missatge("4) Sector");
        missatge("0) Sortir");
    }

    // Menu principal de llistar de l'aplicació
    public static void menuLlistar() {
        missatge("############################");
        missatge("#          LLISTAR         #");
        missatge("############################");
        missatge("1) Escalador");
        missatge("2) VIA");
        missatge("3) Escola");
        missatge("4) Sector");
        missatge("0) Sortir");
    }

    // Menu principal d'eliminar de l'aplicació
    public static void menuEliminar() {
        missatge("############################");
        missatge("#          ELIMINAR        #");
        missatge("############################");
        missatge("1) Escalador");
        missatge("2) VIA");
        missatge("3) Escola");
        missatge("4) Sector");
        missatge("0) Sortir");
    }

    // Menu d'altres funcions
    public static void menuAltresFuncions() {
        missatge("############################");
        missatge("#     ALTRES FUNCIONS      #");
        missatge("############################");
        missatge("1) Vies disponibles d'una Escola");
        missatge("2) Cercar vies per dificultat (rang)");
        missatge("3) Cercar vies per estat");
        missatge("4) Escoles amb restriccions actives");
        missatge("5) Sectors amb més de X vies");
        missatge("6) Escaladors mateix nivell màxim");
        missatge("7) Vies recentment passades a Apte");
        missatge("8) Vies més llargues d'una escola");
        missatge("0) Sortir");
    }

    // #### FUNCIONES AUXILIARES (se usan en el controlador y queda mas limpio)
    public static void pedirDato(String dato) {
        System.out.print(dato + ": ");
    }

    public static void mostrarOpcionesEstil() {
        missatge("\nEstil preferit:");
        missatge("1. Esportiva");
        missatge("2. Clàssica");
        missatge("3. Gel");
    }

    public static void mostrarExito() {
        missatge("✅ Operació realitzada amb èxit!");
    }

    public static void mostrarExito(String msg) {
        missatge("✅ " + msg);
    }

    public static void mostrarError(String msg) {
        missatge("❌ " + msg);
    }

    public static String leerLinea() {
        return sc.nextLine();
    }

    public static int leerEntero() {
        while (true) {
            try {
                String input = sc.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                missatge("Introdueix un número vàlid:");
            }
        }
    }

    public static int leerEntero(int min, int max) {
        while (true) {
            try {
                int valor = Integer.parseInt(sc.nextLine());
                if (valor >= min && valor <= max) {
                    return valor;
                }
                missatge("Introdueix un número entre " + min + " i " + max + ":");
            } catch (NumberFormatException e) {
                missatge("Introdueix un número vàlid:");
            }
        }
    }
}
