/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appParking;

import vehiculos.VehiculoDAO;
import vehiculos.VehiculoVO;
import clientes.ClientesVO;
import clientes.ClienteDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andres
 */
public class Comprobador {
    
    public static void main(String[] args) {
    ClienteDAO daoPersona = new ClienteDAO();
        VehiculoDAO daoVehiculo = new VehiculoDAO();
        List<ClientesVO> listaPersonas = new ArrayList<>();
        
        listaPersonas.add(new ClientesVO("09113858Q", "3265JHG",6548,"fran","martinez","premium" ,"miemail@jotmail.com"));
        listaPersonas.add(new ClientesVO("12557842H", "3265JHG",6548,"hugo","martinez","premium" ,"miemail@jotmail.com"));
        listaPersonas.add(new ClientesVO("3215687A", "3265JHG",6548,"andres","martinez","premium" ,"miemail@jotmail.com"));
        listaPersonas.add(new ClientesVO("65112895A", "3265JHG",6548,"luca","alonso","premium" ,"miemail@jotmail.com"));
        listaPersonas.add(new ClientesVO("85991567G", "3265JHG",6548,"francisca","fernandez","premium" ,"miemail@jotmail.com"));

        List<VehiculoVO> listaVehiculos = new ArrayList<>();
        listaVehiculos.add(new VehiculoVO("6515LKJ", "Turismo"));
        listaVehiculos.add(new VehiculoVO("8126GHY", "Furgoneta"));
        listaVehiculos.add(new VehiculoVO("1234KJH", "Motocicleta"));
        listaVehiculos.add(new VehiculoVO("9512CVN", "Turismo"));
        listaVehiculos.add(new VehiculoVO("9841ASD", "Furgoneta"));
        try {
            System.out.println("Nº vehiculos insertados " + daoVehiculo.insertVehiculo(listaVehiculos));
            System.out.println("-----------------------------------------");
            System.out.println("Buscamos matricula 1234KJH: " + daoVehiculo.findByMatricula("1234KJH"));
            System.out.println("----------Lista de Vehiculos-------------");
            listaVehiculos.forEach(System.out::println);
            System.out.println("Borramos el vehiculo con matricula 9841ASD, furgoneta(sale de su plaza del parking). Vehiculos borrados: " + daoVehiculo.deleteVehiculo(new VehiculoVO("9841ASD", "Furgoneta")));
            System.out.println("----------Lista de Vehiculos(SIN MATRICULA 9841ASD)-------------");
            List<VehiculoVO> nuevaLista = daoVehiculo.getAll();
            nuevaLista.forEach(System.out::println);
            System.out.println("-----------------------------------------");
            
            System.out.println("Nº personas insertadas " + daoPersona.insertCliente(listaPersonas));
            System.out.println("-----------------------------------------");
            System.out.println("Buscamos DNI 09113858Q: " + daoPersona.buscarDni("09113858Q"));
            System.out.println("------------Lista de personas-------------");
            listaPersonas.forEach(System.out::println);
            
            
            
            
        } catch (SQLException sqle) {
            System.out.println("No se ha podido realizar la operación:");
            System.out.println(sqle.getMessage());
        }
    }

}
