/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package transversalpart1;


import AccesoADatos.Conexion;
import AccesoADatos.alumnoData;
import Entidades.Alumno;
import java.time.LocalDate;
import org.mariadb.jdbc.Connection;

/**
 *
 * @author matiSqui
 */
public class TransversalPart1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        
      Connection con =(Connection) Conexion.getConexion();
        
        //Cargo alumnos
//        Alumno mati = new Alumno(1, 1234321,"Jesus","Mati",LocalDate.of(1999,11,3), true);
//        Alumno rod = new Alumno (3, 898763, "Stewart", "Roderick", LocalDate.of(1945, 1,10), true);

        //Dejo un ejemplo para que prueben los metodos. 
        
//        Alumno mick = new Alumno (4, 89653, "Jagger", "Michael Philip", LocalDate.of(1943, 8,26), true);



        //aluData nos permite ejecutar las queries
        alumnoData aluData = new alumnoData();
        
        //Metodo: guardar alumno
//        aluData.guardarAlumno(mick);
        
        //Metodo: modificar alumno
//        aluData.modificarAlumno(mick);

        //Metodo: eliminar alumno (estado = 0) por id
//        aluData.eliminarAlumno(4);
        
         
//        Buscar alumno por dni
          Alumno alue = aluData.buscarAlumnoId(2);
          System.out.println(alue.toString());
          
//        Buscar alumno por dni
          Alumno alue2 = aluData.buscarAlumnoDni(898763);
          System.out.println(alue2.toString());
          
          
          
//          Listar Alumnos
//            aluData.listarAlumnos();
          
        System.out.println("Lista ALUMNOS");

            for (Alumno alumnos : aluData.listarAlumnos()) {
            
                System.out.println(alumnos.toString());
                
        }
            
            
    }
    
}
