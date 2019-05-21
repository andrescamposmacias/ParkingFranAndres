/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andres
 */
public class Comprobador {
    
    public static void main(String[] args) {
    ClienteDAO daoPersona = new ClienteDAO();
        List<ClientesVO> listaPersonas = new ArrayList<>();
//String dni, String matricula, int tarjetaCredito, String nombre, String apellido, String tipoAbono, String email
        listaPersonas.add(new ClientesVO("09113858Q", "3265JHG",6548,"fran","martinez","premium" ,"miemail@jotmail.com"));
        listaPersonas.add(new ClientesVO("12557842H", "3265JHG",6548,"hugo","martinez","premium" ,"miemail@jotmail.com"));
        listaPersonas.add(new ClientesVO("3215687A", "3265JHG",6548,"andres","martinez","premium" ,"miemail@jotmail.com"));
        listaPersonas.add(new ClientesVO("65112895A", "3265JHG",6548,"luca","alonso","premium" ,"miemail@jotmail.com"));
        listaPersonas.add(new ClientesVO("85991567G", "3265JHG",6548,"francisca","fernandez","premium" ,"miemail@jotmail.com"));

        try {
            
            System.out.println("Nº personas insertadas " + daoPersona.insertPersona(listaPersonas));
            System.out.println("-----------------------------------------");
            System.out.println("Buscamos DNI 09113858Q" + daoPersona.buscarDni("09113858Q"));
            
            
            
            
        } catch (SQLException sqle) {
            System.out.println("No se ha podido realizar la operación:");
            System.out.println(sqle.getMessage());
        }
        System.out.println();
        listaPersonas.forEach(System.out::println);

    }

}
