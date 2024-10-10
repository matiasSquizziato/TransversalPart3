
package AccesoADatos;

import Entidades.Materia;
import org.mariadb.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    
    //Modificar Materias
  
    public void modificarMaterias(Materia materia){
        String sql = "UPDATE materia SET nombre = ?, año= ?, estado = ? WHERE idMateria =?";
                
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getAnioMateria());
            ps.setBoolean(3, materia.isActivo());
            ps.setInt(4, materia.getIdMateria());
            
            int exito = ps.executeUpdate();
            
            if (exito == 1) {
                
                JOptionPane.showMessageDialog(null, "Materia: " + materia.getNombre() + ", Modificada con exito!");
            }
            
        } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al acceer a la tabla materia" + ex.getMessage());
        }
        
        
                
    }
    
    
    //Buscar Materias
    public Materia buscarMateriaId(int id){
        
        String sql = "SELECT nombre, año, estado FROM materia WHERE idMateria = ? ";
        
        Materia materia = null;
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                materia = new Materia();
                
                materia.setNombre(rs.getString("nombre"));
                materia.setAnioMateria(rs.getInt("año"));
                materia.setActivo(true);
            } else {
        
            JOptionPane.showMessageDialog(null, "No se econtro materia con ese id: " + id);
        
            }
        } catch (SQLException ex) {
            Logger.getLogger(MateriaData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return materia;
        
    }
    
    //Listar materias
    
    public List<Materia> listarMateria(){
        
        String sql = "SELECT * FROM materia WHERE estado = 1";

        ArrayList <Materia> listaMaterias = new ArrayList();
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                
                Materia  materia = new Materia();
                
                materia.setIdMateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAnioMateria(rs.getInt("año"));
                materia.setActivo(true);
                
                listaMaterias.add(materia);
             
                
            }
               ps.close();
        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla materia: " + ex.getMessage());
            
        }

        return listaMaterias;
    }
    
    public List<Materia> listarMateriaF(){
        
        String sql = "SELECT * FROM materia WHERE estado = 0";

        ArrayList <Materia> listaMateriasF = new ArrayList();
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                
                Materia  materia = new Materia();
                
                materia.setIdMateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAnioMateria(rs.getInt("año"));
                materia.setActivo(false);
                
                listaMateriasF.add(materia);
             
                
            }
               ps.close();
        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla materia: " + ex.getMessage());
            
        }

        return listaMateriasF;
    }
    
}
