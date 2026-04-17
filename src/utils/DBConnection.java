package utils;

public class DBConnection {
    private static Connection connection = null;

    // ! OPEN CONNECTION
    public static Connection openCon() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:escalada.db");
            System.out.println("[  ✔  ] Connexió Establerta correctament!");

        } catch (SQLException e) {
            System.out.println("Error: No s'ha pogut establir la connexió");
            System.out.println(e.getMessage());
        }
        return connection;
    }

    // ! CLOSE CONNECTION
    public static void closeCon(Connection con) {
        if (con != null) {
            try {
                con.close();
                System.out.println("[  ✘  ] Connexió tancada");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // ! CONNECTIONN
    public static Connection getConnection() {
        return connection;
    }
}
