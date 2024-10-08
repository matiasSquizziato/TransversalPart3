
package AccesoADatos;

import Entidades.Materia;
import org.mariadb.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;


public class MateriaData {
    
    private Connection con = null;
    
    public MateriaData(){
        
        con = (Connection) Conexion.getConexion();
        
    }
    
    
    //Guardar Materias
    public void guardarMaterias(Materia materia){
        
        String sql = "INSERT INTO materia( nombre, año, estado) VALUES (?,?,?)";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
//            ps.setInt(1, materia.getIdMateria());
            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getAnioMateria());
            ps.setBoolean(3, materia.isActivo());
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if (rs.next()) {
                
                materia.setIdMateria(rs.getInt(1));
                JOptionPane.showMessageDialog(null,"Materia" + materia.getNombre() +" guardada");
            }
            
            
            ps.close();
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"error al ingresar la tabla materia" + ex.getMessage());
        }
        
        
    }
    
    
    
    
            
            
    
}
