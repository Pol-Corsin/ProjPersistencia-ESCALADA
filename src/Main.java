import controller.*;
import DAO.interfaces.*;
import DAO.sqlite.*;
import model.*;
import utils.ValidadorRegexMenus;
import view.MenuTerminal;
import java.util.List;

public class Main {
    // DAOs
    private static EscaladorDAO escaladorDAO;
    private static EscolaDAO escolaDAO;
    private static SectorDAO sectorDAO;
    private static ViaDAO viaDAO;
    private static PoblacioDAO poblacioDAO;
    private static AssolimentDAO assolimentDAO;

    // Controllers
    private static EscaladorController escaladorController;
    private static EscolaController escolaController;
    private static SectorController sectorController;
    private static ViaController viaController;

    public static void main(String[] args) {
        // Inicialitzar DAOs
        inicialitzarDAOs();

        // Inicialitzar Controllers
        inicialitzarControllers();

        // Menú principal
        menuPrincipal();
    }

    private static void inicialitzarDAOs() {
        escaladorDAO = new SQLiteEscaladorDAO();
        escolaDAO = new SQLiteEscolaDAO();
        sectorDAO = new SQLiteSectorDAO();
        viaDAO = new SQLiteViaDAO();
        poblacioDAO = new SQLitePoblacioDAO();
        assolimentDAO = new SQLiteAssolimentDAO();
    }

    private static void inicialitzarControllers() {
        escaladorController = new EscaladorController(escaladorDAO);
        escolaController = new EscolaController(escolaDAO);
        sectorController = new SectorController(sectorDAO, escolaDAO);
        viaController = new ViaController(viaDAO, sectorDAO, escaladorDAO);
    }

    private static void menuPrincipal() {
        boolean sortir = false;

        while (!sortir) {
            MenuTerminal.menu();
            int opcio = MenuTerminal.leerEntero(0, 5);

            switch (opcio) {
                case 1:
                    menuCrear();
                    break;
                case 2:
                    menuLlistar();
                    break;
                case 3:
                    menuModificar();
                    break;
                case 4:
                    menuEliminar();
                    break;
                case 5:
                    menuAltresFuncions();
                    break;
                case 0:
                    MenuTerminal.missatge("👋 Adeu!");
                    sortir = true;
                    break;
            }
        }
    }

    private static void menuCrear() {
        boolean tornar = false;

        while (!tornar) {
            MenuTerminal.menuCrear();
            int opcio = MenuTerminal.leerEntero(0, 4);

            switch (opcio) {
                case 1:
                    escaladorController.crearEscalador();
                    break;
                case 2:
                    viaController.crearVia();
                    break;
                case 3:
                    escolaController.crearEscola();
                    break;
                case 4:
                    sectorController.crearSector();
                    break;
                case 0:
                    tornar = true;
                    break;
            }
        }
    }

    private static void menuLlistar() {
        boolean tornar = false;

        while (!tornar) {
            MenuTerminal.menuLlistar();
            int opcio = MenuTerminal.leerEntero(0, 4);

            switch (opcio) {
                case 1:
                    escaladorController.llistarEscaladors();
                    break;
                case 2:
                    viaController.llistarVies();
                    break;
                case 3:
                    escolaController.llistarEscoles();
                    break;
                case 4:
                    sectorController.llistarSectors();
                    break;
                case 0:
                    tornar = true;
                    break;
            }
        }
    }

    private static void menuModificar() {
        boolean tornar = false;

        while (!tornar) {
            MenuTerminal.menuModificar();
            int opcio = MenuTerminal.leerEntero(0, 4);

            switch (opcio) {
                case 1:
                    escaladorController.modificarEscalador();
                    break;
                case 2:
                    viaController.modificarVia();
                    break;
                case 3:
                    escolaController.modificarEscola();
                    break;
                case 4:
                    sectorController.modificarSector();
                    break;
                case 0:
                    tornar = true;
                    break;
            }
        }
    }

    private static void menuEliminar() {
        boolean tornar = false;

        while (!tornar) {
            MenuTerminal.menuEliminar();
            int opcio = MenuTerminal.leerEntero(0, 4);

            switch (opcio) {
                case 1:
                    escaladorController.eliminarEscalador();
                    break;
                case 2:
                    viaController.eliminarVia();
                    break;
                case 3:
                    escolaController.eliminarEscola();
                    break;
                case 4:
                    sectorController.eliminarSector();
                    break;
                case 0:
                    tornar = true;
                    break;
            }
        }
    }

    private static void menuAltresFuncions() {
        boolean tornar = false;

        while (!tornar) {
            MenuTerminal.menuAltresFuncions();
            int opcio = MenuTerminal.leerEntero(0, 8);

            switch (opcio) {
                case 1:
                    funcioViesDisponiblesEscola();
                    break;
                case 2:
                    funcioCercarPerGrau();
                    break;
                case 3:
                    funcioCercarPerEstat();
                    break;
                case 4:
                    funcioEscolesAmbRestriccions();
                    break;
                case 5:
                    funcioSectorsMesDeXVies();
                    break;
                case 6:
                    funcioEscaladorsMateixNivell();
                    break;
                case 7:
                    funcioViesRecentsApte();
                    break;
                case 8:
                    funcioViesMesLlargues();
                    break;
                case 0:
                    tornar = true;
                    break;
            }
        }
    }


    // do
    // {
    //     MenuTerminal.menu();
    //     MenuTerminal.missatge("Escriu una opcio del 0 al 4: ");

    //     // Regex de validacion
    //     String input = sc.nextLine();
    //     opcion = ValidadorRegexMenus.validarRegex(input, "[0-4]");

    //     // La funcion devuelve -1 asi que se trata de esta manera para poder continuar
    //     // el codigo
    //     if (opcion != -1) {
    //         switch (opcion) {
    //             case 1:
    //                 gestionarCrear();
    //                 break;
    //             case 2:
    //                 MenuTerminal.menuLlistar();
    //                 break;
    //             case 3:
    //                 MenuTerminal.menuModificar();
    //                 break;
    //             case 4:
    //                 MenuTerminal.menuEliminar();
    //                 break;
    //             case 0:
    //                 MenuTerminal.missatge("Saliendo del programa");
    //                 break;
    //         }
    //     } else {
    //         MenuTerminal.missatge("Opció Incorrecta");
    //     }

    // }while(opcion!=0);
    // }

    // // Aqui se gestiona el apartado de crear
    // private static void gestionarCrear() {
    //     MenuTerminal.menuCrear();
    //     MenuTerminal.missatge("Que vols crear?");

    //     String input = sc.nextLine();
    //     int opcion = ValidadorRegexMenus.validarRegex(input, "[0-4]");

    //     if (opcion != -1) {
    //         switch (opcion) {
    //             case 1:
    //                 break;
    //             case 2:
    //                 break;
    //             case 3:
    //                 break;
    //             case 4:
    //                 break;
    //             case 0:
    //                 break;
    //         }
    //     }

    //     else {
    //         MenuTerminal.missatge("Opció Incorrecta");
    //     }
    // }
}