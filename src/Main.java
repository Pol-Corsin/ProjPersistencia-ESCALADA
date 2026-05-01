import view.MenuTerminal;
import java.util.Scanner;
import utils.ValidadorRegexMenus;

public class Main {
    private static Scanner sc = new Scanner(System.in); // 👈 static y minúscula

    public static void main(String[] args) {
        int opcion = -1;
        //Bucle donde se trata cada apartado del menu 
        do {
            MenuTerminal.menu();
            MenuTerminal.missatge("Escriu una opcio del 0 al 4: ");

            // Regex de validacion
            String input = sc.nextLine();
            opcion = ValidadorRegexMenus.validarRegex(input, "[0-4]");

            // La funcion devuelve -1 asi que se trata de esta manera para poder continuar el codigo
            if (opcion != -1) {
                switch (opcion) {
                    case 1: gestionarCrear(); break;
                    case 2: MenuTerminal.menuLlistar(); break;
                    case 3: MenuTerminal.menuModificar(); break;
                    case 4: MenuTerminal.menuEliminar(); break;
                    case 0: MenuTerminal.missatge("Saliendo del programa"); break;
                }
            }
            else {
                MenuTerminal.missatge("Opció Incorrecta");
            }

        } while (opcion != 0);
    }

    // Aqui se gestiona el apartado de crear
    private static void gestionarCrear() {
        MenuTerminal.menuCrear();
        MenuTerminal.missatge("Que vols crear?");
        
        String input = sc.nextLine();
        int opcion = ValidadorRegexMenus.validarRegex(input, "[0-4]");
        
        if (opcion != -1) {
            switch (opcion) {
                    case 1: break;
                    case 2: break;
                    case 3: break;
                    case 4: break;
                    case 0: break;
            }
        } 

        else {
            MenuTerminal.missatge("Opció Incorrecta");
        }
    }
}