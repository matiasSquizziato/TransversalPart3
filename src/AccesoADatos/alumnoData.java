package AccesoADatos;

import Entidades.Alumno;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Connection; // Usar java.sql en lugar de org.mariadb.jdbc
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author matiSqui
 */
public class AlumnoData {
    
    private Connection con = null;

    public AlumnoData() {
        con = Conexion.getConexion();  
    }
    
    
    //Guardar Alumnos
    public void guardarAlumno(Alumno alumno) {
        String sql = "INSERT INTO alumno(dni, apellido, nombre, fechaNacimiento, estado) VALUES (?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, alumno.getDni());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getNombre());
            ps.setDate(4, Date.valueOf(alumno.getFechaNac()));
            ps.setBoolean(5, alumno.isActivo());
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if (rs.next()) {
                alumno.setIdAlumno(rs.getInt(1));
                JOptionPane.showMessageDialog(null,"alumno guardado");
            }
            
            ps.close();
            
//            JOptionPane.showMessageDialog(null, "Alumno guardado correctamente");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla alumno: " + ex.getMessage());
        }
    }
    
    
    //Modificar Alumnos
    public void modificarAlumno(Alumno alumno){
        
        //Sentencia que se pretende enviar a la db
        String sql = "UPDATE alumno SET dni= ?, apellido=?, nombre=?, fechaNacimiento=?"
        + "WHERE idAlumno= ?";
        
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, alumno.getDni());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getNombre());
            ps.setDate(4, Date.valueOf(alumno.getFechaNac()));
            ps.setInt(5,alumno.getIdAlumno());
            
            int exito = ps.executeUpdate();
            
            if (exito == 1) {
                JOptionPane.showMessageDialog(null, "Alumno: " + alumno.getIdAlumno() + "modificado");
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla alumno: " + ex.getMessage());
        }
        
    }
    
    //Eliminar Alumnos 
    // cambia el valor del estado (estado = 0)
    //Activo = 1
    //Inactivo = 0
    public void eliminarAlumno(int id){
        
        //Sentencia que se pretende enviar a la db
        String sql = "UPDATE alumno SET estado = 0 WHERE idAlumno = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, id);
           int exito = ps.executeUpdate();
            
            if (exito == 1) {
            JOptionPane.showMessageDialog(null, "Alumno" + id +" eliminado");

            }
           
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla alumno: " + ex.getMessage());
        }
        
        
        
    }
    
    
    //Buscar Alumno por id
    //Para este metodo el alumno existe, si esta "activo"
    public Alumno buscarAlumnoId(int id){
        
        String sql= "SELECT dni, apellido, nombre, fechaNacimiento FROM alumno WHERE idAlumno = ? AND estado = 1";
        
        Alumno alumno = null;
        
         try {
            PreparedStatement ps = con.prepareStatement(sql);
           
            ps.setInt(1,id);
            
           ResultSet rs = ps.executeQuery();
           
             if (rs.next()) {
                 
                 alumno = new Alumno();
                 
                 alumno.setIdAlumno(id);
                 alumno.setDni(rs.getInt("dni"));
                 alumno.setApellido(rs.getString("apellido"));
                 alumno.setNombre(rs.getString("nombre"));
                 alumno.setFechaNac(rs.getDate("fechaNacimiento").toLocalDate());
                 alumno.setActivo(true);
        
                 
             } else {
                 
             JOptionPane.showMessageDialog(null, "no existe ese alumno con ese id:  " + id);

                 
             }
           
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla alumno: " + ex.getMessage());
        }
                
                
         return alumno;
         
    }
    
    //Buscar alumno por dni
     public Alumno buscarAlumnoDni(int dni){
        
        String sql= "SELECT idAlumno, dni, apellido, nombre, fechaNacimiento FROM alumno WHERE dni = ? AND estado = 1";
        
        Alumno alumno = null;
        
         try {
            PreparedStatement ps = con.prepareStatement(sql);
           
            ps.setInt(1,dni);
            
           ResultSet rs = ps.executeQuery();
           
             if (rs.next()) {
                 
                 alumno = new Alumno();
                 
                 alumno.setIdAlumno(rs.getInt("idAlumno"));
                 alumno.setDni(rs.getInt("dni"));
                 alumno.setApellido(rs.getString("apellido"));
                 alumno.setNombre(rs.getString("nombre"));
                 alumno.setFechaNac(rs.getDate("fechaNacimiento").toLocalDate());
                 alumno.setActivo(true);
        
                 
             } else {
                 
             JOptionPane.showMessageDialog(null, "no existe ese alumno con ese dni:  " + dni);

                 
             }
           
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla alumno: " + ex.getMessage());
        }
                
                
         return alumno;
         
    }
    
    //Listar alumnos 
         public List<Alumno> listarAlumnos(){
        
        String sql= "SELECT idAlumno, dni, apellido, nombre, fechaNacimiento FROM alumno WHERE estado = 1";
        
        ArrayList <Alumno> listaAlumnos = new ArrayList<>();
        
         try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
             while (rs.next()) {
                 
                Alumno alumno = new Alumno();
                 alumno.setIdAlumno(rs.getInt("idAlumno"));
                 alumno.setDni(rs.getInt("dni"));
                 alumno.setApellido(rs.getString("apellido"));
                 alumno.setNombre(rs.getString("nombre"));
                 alumno.setFechaNac(rs.getDate("fechaNacimiento").toLocalDate());
                 alumno.setActivo(true);
                 
                 listaAlumnos.add(alumno);
                 
             }
            
           ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla alumno: " + ex.getMessage());
        }
                
                
         return listaAlumnos;
         
    }
     
}
