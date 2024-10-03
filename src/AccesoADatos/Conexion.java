package AccesoADatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {

    // Atributos constantes (final) para la URL, la base de datos, el usuario y la contraseña
    private static final String URL = "jdbc:mariadb://localhost:3306/";
    private static final String DB = "universidadsql";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    // Atributo estático para la conexión
    private static Connection connection;

    private Conexion() {}

    // Método estático para obtener la conexión
    public static Connection getConexion() {
        if (connection == null) {
            try {
                // Registrar el driver de MariaDB
                Class.forName("org.mariadb.jdbc.Driver");

                // Crear la conexión a la base de datos
                connection = DriverManager.getConnection(URL + DB, USUARIO, PASSWORD);

                JOptionPane.showMessageDialog(null, "Conexión exitosa!");

            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Error al cargar los drivers: " + ex.getMessage());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos: " + ex.getMessage());
            }
        }
        return connection;
    }
}
