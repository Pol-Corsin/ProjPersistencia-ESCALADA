package view;

public class MenuTerminal {
    public static void missatge(String msg){
        System.out.println(msg);
    }
    
    public static void menu(){
        missatge("#####################");
        missatge("#        MENU       #");
        missatge("#####################");
        missatge("1) Crear");
        missatge("2) Listar");
        missatge("3) Modificar");
        missatge("4) Eliminar");
    }
}
