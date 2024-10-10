/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Vistas;


import AccesoADatos.Conexion;
import Vistas.VistaPrincipal;
import org.mariadb.jdbc.Connection;

/**
 *
 * @author matiSqui
 */
public class TransversalPart2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        
      Connection con =(Connection) Conexion.getConexion();
      
        VistaPrincipal menu = new VistaPrincipal();
        menu.setVisible(true);
   
            
    }
    

}
