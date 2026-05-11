import controller.*;
import DAO.interfaces.*;
import DAO.sqlite.*;
import model.*;
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
        viaController = new ViaController(viaDAO, escolaDAO, sectorDAO, escaladorDAO);
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

    // ############################### ALTRES FUNCIONS

    // 1. Mostra les vies d'una Escola que es trobaran disponibles
    private static void funcioViesDisponiblesEscola() {
        MenuTerminal.missatge("\n=== VIES DISPONIBLES D'UNA ESCOLA ===");

        List<Escola> escoles = escolaDAO.findAll();
        if (escoles.isEmpty()) {
            MenuTerminal.mostrarError("No hi ha escoles.");
            return;
        }

        MenuTerminal.missatge("Escoles:");
        for (Escola e : escoles) {
            MenuTerminal.missatge(e.getId() + ". " + e.getNom());
        }

        MenuTerminal.missatge("ID de l'escola:");
        int escolaId = MenuTerminal.leerEntero();

        Escola escola = escolaDAO.findById(escolaId);
        if (escola == null) {
            MenuTerminal.mostrarError("Escola no trobada.");
            return;
        }

        List<Via> vies = viaDAO.findByEscolaId(escolaId);
        long count = vies.stream().filter(v -> "Apte".equals(v.getEstat())).count();

        MenuTerminal.missatge("\nEscola: " + escola.getNom());
        MenuTerminal.missatge("Vies disponibles: " + count);
        for (Via v : vies) {
            if ("Apte".equals(v.getEstat())) {
                MenuTerminal.missatge("  - " + v.getNom() + " (" + v.getTipus() + ") - " + v.getGrau());
            }
        }
    }

    // 2. Cercar vies per dificultat en un rang específic
    private static void funcioCercarPerGrau() {
        MenuTerminal.missatge("\n=== CERCAR VIES PER GRAU ===");

        MenuTerminal.missatge("Grau mínim (ex: 6a):");
        String grauMin = MenuTerminal.leerLinea();
        MenuTerminal.missatge("Grau màxim (ex: 7c):");
        String grauMax = MenuTerminal.leerLinea();

        try {
            List<Via> vies = viaDAO.findByGrau(grauMin, grauMax);
            MenuTerminal.missatge("\nVies trobades: " + vies.size());
            for (Via v : vies) {
                MenuTerminal.missatge("  - " + v.getNom() + " (" + v.getTipus() + ")");
            }
        } catch (RuntimeException e) {
            MenuTerminal.mostrarError("Error: " + e.getMessage());
        }
    }

    // 3. Cercar vies segons estat
    private static void funcioCercarPerEstat() {
        MenuTerminal.missatge("\n=== CERCAR VIES PER ESTAT ===");

        MenuTerminal.missatge("1. Apte");
        MenuTerminal.missatge("2. Construcció");
        MenuTerminal.missatge("3. Tancada");
        int opcio = MenuTerminal.leerEntero(1, 3);

        String estat;
        switch (opcio) {
            case 1:
                estat = "Apte";
                break;
            case 2:
                estat = "construcció";
                break;
            case 3:
                estat = "tancada";
                break;
            default:
                estat = "Apte";
                break;
        }

        List<Via> vies = viaDAO.findByEstat(estat);
        MenuTerminal.missatge("\nVies amb estat '" + estat + "': " + vies.size());
        for (Via v : vies) {
            MenuTerminal.missatge("  - " + v.getNom() + " (Sector ID: " + v.getSectorId() + ")");
        }
    }

    // 4. Consultar escoles amb restriccions actives actualment
    private static void funcioEscolesAmbRestriccions() {
        MenuTerminal.missatge("\n=== ESCOLES AMB RESTRICCIONS ACTIVES ===");

        List<Escola> escoles = escolaDAO.findAmbRestriccionsActives();
        if (escoles.isEmpty()) {
            MenuTerminal.missatge("No hi ha escoles amb restriccions actives.");
            return;
        }

        for (Escola e : escoles) {
            MenuTerminal.missatge("---");
            MenuTerminal.missatge("Escola: " + e.getNom());
            MenuTerminal.missatge("Restriccions: " + e.getRestriccions());
        }
    }

    // 5. Mostrar sectors amb més de X vies disponibles
    private static void funcioSectorsMesDeXVies() {
        MenuTerminal.missatge("\n=== SECTORS AMB MÉS DE X VIES ===");

        MenuTerminal.missatge("Nombre mínim de vies:");
        int x = MenuTerminal.leerEntero();

        List<Sector> sectors = sectorDAO.findAmbMesDeXVies(x);
        MenuTerminal.missatge("\nSectors amb més de " + x + " vies:");
        for (Sector s : sectors) {
            MenuTerminal.missatge("  - " + s.getNom() + " (Escola ID: " + s.getEscolaId() + ")");
        }
    }

    // 6. Mostrar escaladors amb el mateix nivell màxim assolit
    private static void funcioEscaladorsMateixNivell() {
        MenuTerminal.missatge("\n=== ESCALADORS AMB MATEIX NIVELL ===");

        List<Escalador> escaladors = escaladorDAO.findAll();
        if (escaladors.isEmpty()) {
            MenuTerminal.mostrarError("No hi ha escaladors.");
            return;
        }

        // Mostrar nivells únics
        MenuTerminal.missatge("Nivells disponibles:");
        for (Escalador e : escaladors) {
            MenuTerminal.missatge("  - " + e.getAlias() + ": " + (e.getEstilPref() != null ? e.getEstilPref() : "N/A"));
        }
    }

    // 7. Mostrar les vies que han passat a "Apte" recentment
    private static void funcioViesRecentsApte() {
        MenuTerminal.missatge("\n=== VIES RECENTMENT PASSADES A APTE ===");

        MenuTerminal.missatge("Dies enrere:");
        int dies = MenuTerminal.leerEntero();

        List<Via> vies = viaDAO.findRecentsApte(dies);
        MenuTerminal.missatge("\nVies passades a 'Apte' en els últims " + dies + " dies:");
        for (Via v : vies) {
            MenuTerminal.missatge("  - " + v.getNom() + " (Data: " + v.getDataReobertura() + ")");
        }
    }

    // 8. Mostrar les vies més llargues d'una escola determinada
    private static void funcioViesMesLlargues() {
        MenuTerminal.missatge("\n=== VIES MÉS LLARGUES D'UNA ESCOLA ===");

        List<Escola> escoles = escolaDAO.findAll();
        if (escoles.isEmpty()) {
            MenuTerminal.mostrarError("No hi ha escoles.");
            return;
        }

        MenuTerminal.missatge("Escoles:");
        for (Escola e : escoles) {
            MenuTerminal.missatge(e.getId() + ". " + e.getNom());
        }

        MenuTerminal.missatge("ID de l'escola:");
        int escolaId = MenuTerminal.leerEntero();

        MenuTerminal.missatge("Nombre de vies a mostrar:");
        int limit = MenuTerminal.leerEntero();

        List<Via> vies = viaDAO.findMesLlargues(escolaId, limit);
        MenuTerminal.missatge("\nLes " + limit + " vies més llargues:");
        for (Via v : vies) {
            MenuTerminal.missatge("  - " + v.getNom() + " (" + v.getTipus() + ")");
        }
    }
    // do
    // {
    // MenuTerminal.menu();
    // MenuTerminal.missatge("Escriu una opcio del 0 al 4: ");

    // // Regex de validacion
    // String input = sc.nextLine();
    // opcion = ValidadorRegexMenus.validarRegex(input, "[0-4]");

    // // La funcion devuelve -1 asi que se trata de esta manera para poder
    // continuar
    // // el codigo
    // if (opcion != -1) {
    // switch (opcion) {
    // case 1:
    // gestionarCrear();
    // break;
    // case 2:
    // MenuTerminal.menuLlistar();
    // break;
    // case 3:
    // MenuTerminal.menuModificar();
    // break;
    // case 4:
    // MenuTerminal.menuEliminar();
    // break;
    // case 0:
    // MenuTerminal.missatge("Saliendo del programa");
    // break;
    // }
    // } else {
    // MenuTerminal.missatge("Opció Incorrecta");
    // }

    // }while(opcion!=0);
    // }

    // // Aqui se gestiona el apartado de crear
    // private static void gestionarCrear() {
    // MenuTerminal.menuCrear();
    // MenuTerminal.missatge("Que vols crear?");

    // String input = sc.nextLine();
    // int opcion = ValidadorRegexMenus.validarRegex(input, "[0-4]");

    // if (opcion != -1) {
    // switch (opcion) {
    // case 1:
    // break;
    // case 2:
    // break;
    // case 3:
    // break;
    // case 4:
    // break;
    // case 0:
    // break;
    // }
    // }

    // else {
    // MenuTerminal.missatge("Opció Incorrecta");
    // }
    // }
}