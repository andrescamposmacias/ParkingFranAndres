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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
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
            List<TicketsVO> listaTickets = daoTickets.getAll();
            List<PlazaVO> listaPlaza = daoPlaza.getAll();

            try (BufferedWriter flujo = new BufferedWriter(new FileWriter(archivoBackupHoy + "\\Vehiculos.txt"))) {

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

            try (BufferedWriter flujo = new BufferedWriter(new FileWriter(archivoBackupHoy + "\\Tickets.txt"))) {

                for (TicketsVO i : listaTickets) {
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
            try (BufferedWriter flujo = new BufferedWriter(new FileWriter(archivoBackupHoy + "\\Plaza.txt"))) {
                for (PlazaVO i : listaPlaza) {
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
        } catch (SQLException sql) {
            System.out.println(sql);
        }

    }

    public static void restaurar() throws SQLException, FileNotFoundException, UnsupportedEncodingException {

        Scanner teclado = new Scanner(System.in);
        int seleccion;
        File seleccionArchivo = null;
        File archivoRestauracion = new File("backup");

        File[] archivos = archivoRestauracion.listFiles();

        if (archivos == null || archivos.length == 0) {
            System.out.println("No hay elementos dentro de la carpeta actual");
            return;

        } else {

            for (int i = 0; i < archivos.length; i++) {

                System.out.println(archivos[i]);

            }

            do {
                System.out.println("Selecciona desde el 1 al " + archivos.length);
                seleccion = teclado.nextInt();
            } while (!(seleccion >= 1) || !(seleccion <= (archivos.length)));

            seleccionArchivo = archivos[(seleccion - 1)];
            
            System.out.println("Has seleccionado la copia " + seleccionArchivo);

        }
        ClienteDAO daoCliente = new ClienteDAO();
        VehiculoDAO daoVehiculo = new VehiculoDAO();
        TicketsDAO daoTickets = new TicketsDAO();
        PlazaDAO daoPlaza = new PlazaDAO();      

        ArrayList<VehiculoVO> listaVehi = new ArrayList<>();
        ArrayList<ClientesVO> listaPerso = new ArrayList<>();
        List<TicketsVO> listaTickets = daoTickets.getAll();
        List<PlazaVO> listaPlaza = daoPlaza.getAll();

        //un array de String
        String[] tokens;
        //un String
        String linea;
        
        try (Scanner datosFichero = new Scanner(new InputStreamReader(new FileInputStream(seleccionArchivo + "\\Vehiculos.txt"), "ISO-8859-1"))) {
            daoVehiculo.deleteVehiculo();
            //repite el bucle hasta que no encuentre alguna linea
            while (datosFichero.hasNextLine()) {
              
                    //guarda en el String linea lo que hay en la linea del fichero
                    linea = datosFichero.nextLine();

                    //creo un array de String que la condicion sea la barra vertical
                    tokens = linea.split(",");
                    
                    listaVehi.add(new VehiculoVO(tokens[0].trim(),tokens[1].trim()));
                        
                    daoVehiculo.insertVehiculo(listaVehi);
                    }
                  
            }catch(FileNotFoundException | UnsupportedEncodingException e){
                System.out.println(e);
            }

        try (Scanner datosFichero = new Scanner(new InputStreamReader(new FileInputStream(seleccionArchivo + "\\Clientes.txt"), "ISO-8859-1"))) {
            daoCliente.deleteCliente();
            //repite el bucle hasta que no encuentre alguna linea
            while (datosFichero.hasNextLine()) {
              
                    //guarda en el String linea lo que hay en la linea del fichero
                    linea = datosFichero.nextLine();

                    //creo un array de String que la condicion sea la barra vertical
                    tokens = linea.split(",");
                    
                    listaPerso.add(new ClientesVO(tokens[0].trim(),tokens[1].trim(),Integer.parseInt(tokens[2].trim()),tokens[3].trim(),tokens[4].trim(),tokens[5].trim(), tokens[6].trim(),LocalDate.parse(tokens[7].trim()),LocalDate.parse(tokens[8].trim()),tokens[9].trim(),Double.parseDouble(tokens[10].trim())));
                        
                    daoCliente.insertCliente(listaPerso);
                    }
                  
            }catch(FileNotFoundException | UnsupportedEncodingException e){
                System.out.println(e);
            }
        
         try (Scanner datosFichero = new Scanner(new InputStreamReader(new FileInputStream(seleccionArchivo + "\\Tickets.txt"), "ISO-8859-1"))) {
            daoTickets.deleteTickets();
            //repite el bucle hasta que no encuentre alguna linea
            while (datosFichero.hasNextLine()) {
              
                    //guarda en el String linea lo que hay en la linea del fichero
                    linea = datosFichero.nextLine();

                    //creo un array de String que la condicion sea la barra vertical
                    tokens = linea.split(",");
                  
                    listaTickets.add(new TicketsVO(Integer.parseInt(tokens[0].trim()),LocalDate.parse(tokens[1].trim()),LocalDate.parse(tokens[2].trim()),LocalTime.parse(tokens[3].trim()),LocalTime.parse(tokens[4].trim()),Double.parseDouble(tokens[5].trim()), Integer.parseInt(tokens[6].trim()),tokens[7].trim(),tokens[8].trim()));
                        
                    daoTickets.insertTickets(listaTickets);
                    }
                  
            }catch(FileNotFoundException | UnsupportedEncodingException e){
                System.out.println(e);
            }
         
         try (Scanner datosFichero = new Scanner(new InputStreamReader(new FileInputStream(seleccionArchivo + "\\Plaza.txt"), "ISO-8859-1"))) {
            daoPlaza.deletePlaza();
            //repite el bucle hasta que no encuentre alguna linea
            while (datosFichero.hasNextLine()) {
              
                    //guarda en el String linea lo que hay en la linea del fichero
                    linea = datosFichero.nextLine();

                    //creo un array de String que la condicion sea la barra vertical
                    tokens = linea.split(",");
                 
                    listaPlaza.add(new PlazaVO(Integer.parseInt(tokens[0].trim()),tokens[1].trim(),Double.parseDouble(tokens[2].trim()),tokens[3].trim(),tokens[4].trim(),Double.parseDouble(tokens[5].trim())));
                        
                    daoPlaza.insertPlaza(listaPlaza);
                    }
                  
            }catch(FileNotFoundException | UnsupportedEncodingException e){
                System.out.println(e);
            }

        } 

    }
