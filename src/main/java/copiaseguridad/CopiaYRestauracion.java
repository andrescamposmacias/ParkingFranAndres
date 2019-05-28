/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copiaseguridad;

import clientes.ClienteDAO;
import clientes.ClientesVO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import plazas.PlazaDAO;
import plazas.PlazaVO;
import tickets.TicketsDAO;
import tickets.TicketsVO;
import vehiculos.VehiculoDAO;
import vehiculos.VehiculoVO;

/**
 *
 * @author Andrés
 */
public class CopiaYRestauracion {

    public static void copia() throws SQLException {

        try {

            File archivoBackup = new File("backup");
            archivoBackup.mkdir();

            Date date = new Date();
            DateFormat hourdateFormat = new SimpleDateFormat("HH.mm.ss dd-MM-yyyy");
            String historial = hourdateFormat.format(date);

            File archivoBackupHoy = new File(archivoBackup + "/" + historial);
            archivoBackupHoy.mkdir();

            ClienteDAO daoCliente = new ClienteDAO();
            VehiculoDAO daoVehiculo = new VehiculoDAO();
            PlazaDAO daoPlaza = new PlazaDAO();
            TicketsDAO daoTickets = new TicketsDAO();

            List<VehiculoVO> listaVehi = daoVehiculo.getAll();
            List<ClientesVO> listaPerso = daoCliente.getAll();
            //List<TicketsVO> listaTickets = daoTickets.getAll();
            //List<PlazaVO> listaPlaza2 = daoPlaza.getAll();

            try (BufferedWriter flujo = new BufferedWriter(new FileWriter(archivoBackupHoy + "\\Vehiculo.txt"))) {

                for (VehiculoVO i : listaVehi) {
                    // Usamos metodo write() para escribir en el buffer
                    flujo.write(i + ",");

                    // Metodo newLine() añade línea en blanco
                    flujo.newLine();
                }

                // Metodo flush() guarda cambios en disco 
                flujo.flush();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            try (BufferedWriter flujo = new BufferedWriter(new FileWriter(archivoBackupHoy + "\\Clientes.txt"))) {
                for (ClientesVO i : listaPerso) {
                    // Usamos metodo write() para escribir en el buffer
                    flujo.write(i + ",");

                    // Metodo newLine() añade línea en blanco
                    flujo.newLine();
                }

                // Metodo flush() guarda cambios en disco 
                flujo.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

//            try (BufferedWriter flujo = new BufferedWriter(new FileWriter(archivoBackupHoy + "\\Plaza.txt"))) {
//
//                for (TicketsVO i : listaTickets) {
//                    // Usamos metodo write() para escribir en el buffer
//                    flujo.write(i + ",");
//
//                    // Metodo newLine() añade línea en blanco
//                    flujo.newLine();
//                }
//
//                // Metodo flush() guarda cambios en disco 
//                flujo.flush();
//
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//            }
//            try (BufferedWriter flujo = new BufferedWriter(new FileWriter(archivoBackupHoy + "\\Plaza.txt"))) {
//                for (PlazaVO i : listaPlaza2) {
//                    // Usamos metodo write() para escribir en el buffer
//                    flujo.write(i + ",");
//
//                    // Metodo newLine() añade línea en blanco
//                    flujo.newLine();
//                }
//
//                // Metodo flush() guarda cambios en disco 
//                flujo.flush();
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//            }

        } catch (SQLException sql) {
            System.out.println(sql);
        }

    }

}
