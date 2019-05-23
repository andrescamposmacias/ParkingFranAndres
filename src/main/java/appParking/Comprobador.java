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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import plazas.PlazaDAO;
import plazas.PlazaVO;
import tickets.TicketsDAO;
import tickets.TicketsVO;

/**
 *
 * @author andres
 */
public class Comprobador {

    public static void main(String[] args) {
        
        ClienteDAO daoPersona = new ClienteDAO();
        VehiculoDAO daoVehiculo = new VehiculoDAO();
        PlazaDAO daoPlaza = new PlazaDAO();
        TicketsDAO daoTickets = new TicketsDAO();
        
        List<ClientesVO> listaPersonas = new ArrayList<>();
        LocalDate inicio = LocalDate.now();
        LocalDate fin = LocalDate.now();
        LocalTime hoyHora = LocalTime.now();
        LocalTime finHora = LocalTime.now();

        listaPersonas.add(new ClientesVO("09113858Q", "3265JHG", 6548, "fran", "martinez", "premium", "miemail@jotmail.com", inicio, fin, "1", 12.35));
        listaPersonas.add(new ClientesVO("12557842H", "3265ASD", 6548, "hugo", "martinez", "premium", "miemail@jotmail.com", inicio, fin, "2", 15.21));
        listaPersonas.add(new ClientesVO("3215687A", "3265FGH", 6548, "andres", "martinez", "premium", "miemail@jotmail.com", inicio, fin, "3", 10.21));
        listaPersonas.add(new ClientesVO("65112895A", "3265JMN", 6548, "luca", "alonso", "premium", "miemail@jotmail.com", inicio, fin, "4", 17.21));
        listaPersonas.add(new ClientesVO("85991567G", "3265POI", 6548, "francisca", "fernandez", "premium", "miemail@jotmail.com", inicio, fin, "5", 20.21));

        List<VehiculoVO> listaVehiculos = new ArrayList<>();
        listaVehiculos.add(new VehiculoVO("3265JHG", "Turismo"));
        listaVehiculos.add(new VehiculoVO("3265ASD", "Furgoneta"));
        listaVehiculos.add(new VehiculoVO("3265FGH", "Motocicleta"));
        listaVehiculos.add(new VehiculoVO("3265JMN", "Turismo"));
        listaVehiculos.add(new VehiculoVO("3265POI", "Furgoneta"));
        
        List<TicketsVO> listaTickets = new ArrayList<>();
        listaTickets.add(new TicketsVO(inicio, fin, hoyHora, finHora, 10.0, 126, "3265JHG", "1"));
        listaTickets.add(new TicketsVO(inicio, fin, hoyHora, finHora, 10.0, 126, "3265ASD", "1"));
        
        List<PlazaVO> listaPlaza = new ArrayList<>();
        listaPlaza.add(new PlazaVO("1", 3.25, "Turismo", "vacio", 10.0));
        listaPlaza.add(new PlazaVO("2", 30.25, "Motocicleta", "ocupada", 30.0));
        
        
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
            
            System.out.println("plaza" + daoPlaza.insertPlaza(listaPlaza));
            System.out.println("eliminar" + daoPlaza.deletePlaza(new PlazaVO("1", 3.25, "Turismo", "vacio", 10.0)));
            System.out.println("------------Lista de plaza-------------");
            listaPlaza.forEach(System.out::println);
            
            System.out.println("tickets" + daoPlaza.insertPlaza(listaPlaza));
            System.out.println("eliminar" + daoPlaza.deletePlaza(new PlazaVO("1", 3.25, "Turismo", "vacio", 10.0)));
            System.out.println("------------Lista de plaza-------------");
            listaPlaza.forEach(System.out::println);
            

        } catch (SQLException sqle) {
            System.out.println("No se ha podido realizar la operación:");
            System.out.println(sqle.getMessage());
        }
    }

}
