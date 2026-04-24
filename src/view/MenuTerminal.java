package view;

public class MenuTerminal {
    public static void missatge(String msg){
        System.out.println(msg);
    }
    
    //Menu principal de la aplicació
    public static void menu(){
        missatge("############################");
        missatge("#            MENU          #");
        missatge("############################");
        missatge("1) Crear");
        missatge("2) Llistar");
        missatge("3) Modificar");
        missatge("4) Eliminar");
        missatge("0) Soritr");
    }

    // Menu principal de crear de l'aplicació
    public static void menuCrear(){
        missatge("############################");
        missatge("#           CREAR          #");
        missatge("############################");
        missatge("1) Escalador");
        missatge("2) VIA");
        missatge("3) Escola");
        missatge("4) Sector");
        missatge("0) Soritr");
    }

    // Menu principal de modificar de l'aplicació
    public static void menuModificar(){
        missatge("############################");
        missatge("#         MODIFICAR        #");
        missatge("############################");
        missatge("1) Escalador");
        missatge("2) VIA");
        missatge("3) Escola");
        missatge("4) Sector");
        missatge("0) Soritr");
    }
    
    // Menu principal de llistar de l'aplicació
    public static void menuLlistar(){
        missatge("############################");
        missatge("#          LLISTAR         #");
        missatge("############################");
        missatge("1) Escalador");
        missatge("2) VIA");
        missatge("3) Escola");
        missatge("4) Sector");
        missatge("0) Soritr");
    }

    // Menu principal d'eliminar de l'aplicació
    public static void menuEliminar(){
        missatge("############################");
        missatge("#          ELIMINAR        #");
        missatge("############################");
        missatge("1) Escalador");
        missatge("2) VIA");
        missatge("3) Escola");
        missatge("4) Sector");
        missatge("0) Soritr");
    }
}
