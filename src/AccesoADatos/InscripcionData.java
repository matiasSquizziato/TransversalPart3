
package AccesoADatos;

import Entidades.Alumno;
import Entidades.Inscripcion;
import Entidades.Materia;
import org.mariadb.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author matiSqui
 */
public class InscripcionData {
    
    private Connection con = null;
    private MateriaData mateData = new MateriaData();
    private AlumnoData aluData = new AlumnoData();
    
    
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
    
    //Modificar notas
    public void updateNotas(int idAlumno, int idMateria, double nota) {

    String sql = "UPDATE inscripcion SET nota = ? WHERE idAlumno = ? AND idMateria = ?";

    try {
        PreparedStatement ps = con.prepareStatement(sql);
        
        ps.setDouble(1, nota);
        ps.setInt(2, idAlumno);
        ps.setInt(3, idMateria);
        
        int result = ps.executeUpdate();
        
        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Nota: " + nota + ", actualizada con éxito!");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar la nota.");
        }

        ps.close();
        
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al ingresar a la tabla 'inscripcion': " + ex.getMessage());
    }
}

       
    //Eliminar inscripciones
     public void anularInscripciones(int idAlumno,int idMateria){
        
        String sql = "DELETE FROM inscripcion WHERE idAlumno = ? and idMateria = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, idAlumno);
            ps.setInt(2,idMateria);
            
            int result = ps.executeUpdate();
            
            if (result > 0) {
                
                JOptionPane.showMessageDialog(null, "Inscripcion anulada");
                
            }
            
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al ingresar a la tabla ´´incripcion´´ " + ex.getMessage()); 
        }
    }
    
    //Consultar por todas las Inscripciones
     public List<Inscripcion> consultarInscripciones(){
         
         ArrayList<Inscripcion> mateInscriptas = new ArrayList<>();
         
         String sql = "SELECT * FROM inscripcion";
         
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                
                Inscripcion inscrip = new Inscripcion();
                
                inscrip.setIdInscripcion(rs.getInt("idInscripto"));
                rs.getInt("idMateria");
                rs.getInt("idAlumno");
                
                //Uso los metodos de alumno y materia data para extraer los id´s
                Alumno alumno =aluData.buscarAlumnoId(rs.getInt("idAlumno"));
                Materia materia = mateData.buscarMateriaId(rs.getInt("idMateria"));
                
                inscrip.setAlumno(alumno);
                inscrip.setMateria(materia);
                
                inscrip.setNota(rs.getDouble("nota"));
                
                mateInscriptas.add(inscrip);
     
            }
     
            ps.close();
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al ingresar a la tabla ´´incripcion´´ " + ex.getMessage()); 
        }
         
         return mateInscriptas;
         
     }
     
     
    
   //Consulto todas las inscripciones segun x alumno 
     public List<Inscripcion> consultarInscripcionesAlumno(int idAlumno){
         
         ArrayList<Inscripcion> mateInscriptas = new ArrayList<>();
         
         String sql = "SELECT * FROM inscripcion WHERE idAlumno =?";
         
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                
                Inscripcion inscrip = new Inscripcion();
                
                inscrip.setIdInscripcion(rs.getInt("idInscripto"));
                

                
                rs.getInt("idMateria");
                rs.getInt("idAlumno");
                
                //Uso los metodos de alumno y materia data para extraer los id´s
                Alumno alumno =aluData.buscarAlumnoId(rs.getInt("idAlumno"));
                Materia materia = mateData.buscarMateriaId(rs.getInt("idMateria"));
                
                inscrip.setAlumno(alumno);
                inscrip.setMateria(materia);
                
                
                inscrip.setNota(rs.getDouble("nota"));
                
                mateInscriptas.add(inscrip);
     
            }
     
            ps.close();
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al ingresar a la tabla ´´incripcion´´ " + ex.getMessage()); 
        }
         
         return mateInscriptas;
         
     }

     
     //Consultar materias inscriptas por alumno
     public List<Materia> consultarMateriasInscriptas(int idAlumno){
         
         ArrayList <Materia> materiasIns = new ArrayList<>();
         
        String sql = "SELECT inscripcion.idMateria, nombre, año FROM inscripcion " 
               + "INNER JOIN materia ON inscripcion.idMateria = materia.idMateria " 
               + "WHERE inscripcion.idAlumno = ?";
         
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, idAlumno);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                
                Materia nMateria = new Materia();
                nMateria.setIdMateria(rs.getInt("idMateria"));
                nMateria.setNombre(rs.getString("nombre"));
                nMateria.setAnioMateria(rs.getInt("año"));
                
                materiasIns.add(nMateria);
                
            }
            
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al ingresar a la tabla inscripcion" + ex.getMessage());
        }
         
         return materiasIns;
         
     }
     
     //Consultar materias no inscriptas por alumno
     public List<Materia> consultarMateriasInscriptasNull(int idAlumno){
         
         ArrayList <Materia> materiasNullIns = new ArrayList<>();
         
       String sql = "SELECT * FROM materia WHERE estado = 1 AND idMateria NOT IN "
             + "(SELECT idMateria FROM inscripcion WHERE idAlumno = ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, idAlumno);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                
                Materia nMateria = new Materia();
                nMateria.setIdMateria(rs.getInt("idMateria"));
                nMateria.setNombre(rs.getString("nombre"));
                nMateria.setAnioMateria(rs.getInt("año"));
                
                materiasNullIns.add(nMateria);
                
            }
            
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al ingresar a la tabla inscripcion" + ex.getMessage());
        }
         
         return materiasNullIns;
         
     }
     
     //Consultar alumnos por materias
     
     public List<Alumno> consultarAlumnosPorMaterias(int idMateria){
         
         ArrayList<Alumno> alumnosPorMate = new ArrayList<>();
         
         String sql = "SELECT a.idAlumno, dni, nombre, apellido, fechaNacimiento, estado"
                 +"FROM inscripcion i,alumno a WHERE i.idAlumno = a.idAlumno AND idMateria = ?"
                 + "AND a.estado =1";
         
        try {
            PreparedStatement ps = con.prepareCall(sql);
            
            ps.setInt(1, idMateria);
            
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()) {
                
                Alumno nAlumno = new Alumno();
                nAlumno.setIdAlumno(rs.getInt("idAlumno"));
                nAlumno.setApellido(rs.getString("apellido"));
                nAlumno.setNombre(rs.getString("nombre"));
                nAlumno.setFechaNac(rs.getDate("fechaNacimiento").toLocalDate());
                nAlumno.setActivo(rs.getBoolean("estado"));
                
                alumnosPorMate.add(nAlumno);
                
                
                
            }
            ps.close();
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null,"Error al ingresar a la tabla inscripcion" + ex.getMessage());
        }
         
         
         return alumnosPorMate;
     }
     
     
}
