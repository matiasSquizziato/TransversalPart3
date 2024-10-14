
package AccesoADatos;

import Entidades.Inscripcion;
import org.mariadb.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.ResultSet;



/**
 *
 * @author matiSqui
 */
public class InscripcionData {
    
    private Connection con = null;
    
    public InscripcionData(){
        
        this.con=(Connection) Conexion.getConexion();
        
    }
    
    //Guardar Inscripciones
    public void guardarInscripciones(Inscripcion inscrip){
        
        String sql = "INSERT INTO inscripcion(idAlumno, idMateria, nota) VALUES (?,?,?)";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1,inscrip.getAlumno().getIdAlumno());
            ps.setInt(2, inscrip.getMateria().getIdMateria());
            ps.setDouble(3,inscrip.getNota());
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if (rs.next()) {
                
                inscrip.setIdInscripcion(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Incripcion realizada");
                
            }
            
            ps.close();
            
        } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null,"Error al ingresar a la tabla ´´incripcion´´ " + ex.getMessage());    
        }
        
        
        
        
    }
    
    
    
    
}
