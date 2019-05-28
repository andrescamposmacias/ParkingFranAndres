/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copiaseguridad;

import conexion.Conexion;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Andrés
 */
public class CopiaDeSeguridad {

    private Connection con = null;

    public CopiaDeSeguridad() {
        con = Conexion.getInstance();
    }

    public static void copiaSeguridad() {
        try {

            File archivoBackup = new File("backup");
            archivoBackup.mkdir();

            Date date = new Date();
            DateFormat hourdateFormat = new SimpleDateFormat("HH.mm.ss dd-MM-yyyy");
            String historial = hourdateFormat.format(date);

            File archivoBackupHoy = new File(archivoBackup + "/" + historial);
            archivoBackupHoy.mkdir();

            Process clientes = Runtime.getRuntime().exec("mysqldump -u andres --host=192.168.56.101 --protocol=tcp --port=3306 -pwhiz3000 parking clientes");
            Process vehiculo = Runtime.getRuntime().exec("mysqldump -u andres --host=192.168.56.101 --protocol=tcp --port=3306 -pwhiz3000 parking vehiculo");
            Process plaza = Runtime.getRuntime().exec("mysqldump -u andres --host=192.168.56.101 --protocol=tcp --port=3306 -pwhiz3000 parking plaza");
            Process tickets = Runtime.getRuntime().exec("mysqldump -u andres --host=192.168.56.101 --protocol=tcp --port=3306 -pwhiz3000 parking tickets");

            new HiloLector(clientes.getErrorStream()).start();
            new HiloLector(vehiculo.getErrorStream()).start();
            new HiloLector(plaza.getErrorStream()).start();
            new HiloLector(tickets.getErrorStream()).start();

            InputStream isClientes = clientes.getInputStream();
            FileOutputStream fosClientes = new FileOutputStream(archivoBackupHoy + "/clientes.txt");
            byte[] bufferClientes = new byte[1000];

            InputStream isVehiculo = vehiculo.getInputStream();
            FileOutputStream fosVehiculo = new FileOutputStream(archivoBackupHoy + "/vehiculo.txt");
            byte[] bufferVehiculo = new byte[1000];

            InputStream isPlaza = plaza.getInputStream();
            FileOutputStream fosPlaza = new FileOutputStream(archivoBackupHoy + "/plaza.txt");
            byte[] bufferPlaza = new byte[1000];

            InputStream isTickets = tickets.getInputStream();
            FileOutputStream fosTickets = new FileOutputStream(archivoBackupHoy + "/tickets.txt");
            byte[] bufferTickets = new byte[1000];

            int leidoClientes = isClientes.read(bufferClientes);
            while (leidoClientes > 0) {
                fosClientes.write(bufferClientes, 0, leidoClientes);
                leidoClientes = isClientes.read(bufferClientes);
            }

            fosClientes.close();

            int leidoVehiculo = isVehiculo.read(bufferVehiculo);
            while (leidoVehiculo > 0) {
                fosVehiculo.write(bufferVehiculo, 0, leidoVehiculo);
                leidoVehiculo = isVehiculo.read(bufferVehiculo);
            }

            fosVehiculo.close();

            int leidoPlaza = isPlaza.read(bufferPlaza);
            while (leidoPlaza > 0) {
                fosPlaza.write(bufferPlaza, 0, leidoPlaza);
                leidoPlaza = isPlaza.read(bufferPlaza);
            }

            fosPlaza.close();

            int leidoTickets = isTickets.read(bufferTickets);
            while (leidoTickets > 0) {
                fosTickets.write(bufferTickets, 0, leidoTickets);
                leidoTickets = isTickets.read(bufferTickets);
            }

            fosTickets.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void restauracion() {
        try {

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
               
            }
                }
               
            }

            Process clientes = Runtime.getRuntime().exec("C:\\Program Files\\MySQL\\MySQL Workbench 8.0 CE\\mysql.exe -u root -pandres parking clientes");

            new HiloLector(clientes.getErrorStream()).start();

            OutputStream osClientes = clientes.getOutputStream();
            FileInputStream fisClientes = new FileInputStream(seleccionArchivo + "\\clientes.txt");
            byte[] bufferClientes = new byte[1000];

            int leidoClientes = fisClientes.read(bufferClientes);
            while (leidoClientes > 0) {
                osClientes.write(bufferClientes, 0, leidoClientes);
                leidoClientes = fisClientes.read(bufferClientes);
            }

            osClientes.flush();
            osClientes.close();
            fisClientes.close();
            
            Process vehiculo = Runtime.getRuntime().exec("mysql -u andres --host=192.168.56.101 --protocol=tcp --port=3306 -pwhiz3000 parking vehiculo");

            new HiloLector(vehiculo.getErrorStream()).start();

            OutputStream osVehiculo = vehiculo.getOutputStream();
            FileInputStream fisVehiculo = new FileInputStream(seleccionArchivo + "/vehiculo.txt");
            byte[] bufferVehiculo = new byte[1000];

            int leidoVehiculo = fisVehiculo.read(bufferVehiculo);
            while (leidoVehiculo > 0) {
                osVehiculo.write(bufferVehiculo, 0, leidoVehiculo);
                leidoVehiculo = fisVehiculo.read(bufferVehiculo);
            }

            osVehiculo.flush();
            osVehiculo.close();
            fisVehiculo.close();

            Process clientes = Runtime.getRuntime().exec("mysql -u andres --host=192.168.56.101 --protocol=tcp --port=3306 -pwhiz3000 parking clientes");

            new HiloLector(clientes.getErrorStream()).start();

            OutputStream osClientes = clientes.getOutputStream();
            FileInputStream fisClientes = new FileInputStream(seleccionArchivo + "/clientes.txt");
            byte[] bufferClientes = new byte[1000];

            int leidoClientes = fisClientes.read(bufferClientes);
            while (leidoClientes > 0) {
                osClientes.write(bufferClientes, 0, leidoClientes);
                leidoClientes = fisClientes.read(bufferClientes);
            }

            osClientes.flush();
            osClientes.close();
            fisClientes.close();          
            
            Process plaza = Runtime.getRuntime().exec("mysql -u andres --host=192.168.56.101 --protocol=tcp --port=3306 -pwhiz3000 parking plaza");

            new HiloLector(plaza.getErrorStream()).start();

            OutputStream osPlaza = plaza.getOutputStream();
            FileInputStream fisPlaza = new FileInputStream(seleccionArchivo + "/plaza.txt");
            byte[] bufferPlaza = new byte[1000];

            int leidoPlaza = fisPlaza.read(bufferPlaza);
            while (leidoPlaza > 0) {
                osPlaza.write(bufferPlaza, 0, leidoPlaza);
                leidoPlaza = fisPlaza.read(bufferPlaza);
            }

            osPlaza.flush();
            osPlaza.close();
            fisPlaza.close();
            
            Process tickets = Runtime.getRuntime().exec("mysql -u andres --host=192.168.56.101 --protocol=tcp --port=3306 -pwhiz3000 parking tickets");

            new HiloLector(clientes.getErrorStream()).start();

            OutputStream osTickets = tickets.getOutputStream();
            FileInputStream fisTickets = new FileInputStream(seleccionArchivo + "/tickets.txt");
            byte[] bufferTickets = new byte[1000];

            int leidoTickets = fisTickets.read(bufferTickets);
            while (leidoTickets > 0) {
                osTickets.write(bufferTickets, 0, leidoTickets);
                leidoTickets = fisTickets.read(bufferTickets);
            }

            osTickets.flush();
            osTickets.close();
            fisTickets.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class HiloLector extends Thread {

        private InputStream is;

        public HiloLector(InputStream is) {
            this.is = is;
        }

        @Override
        public void run() {
            try {
                byte[] buffer = new byte[1000];
                int leido = is.read(buffer);
                while (leido > 0) {
                    String texto = new String(buffer, 0, leido);
                    System.out.print(texto);
                    leido = is.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
