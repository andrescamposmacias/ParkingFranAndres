/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appParking;

import java.util.Scanner;
import clientes.ClienteDAO;
import clientes.ClientesVO;
import copiaseguridad.CopiaYRestauracion;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import plazas.PlazaDAO;
import tickets.TicketsDAO;

/**
 *
 * @author FranAragon13
 */
public class Parking {

    public static void main(String[] args) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        ClienteDAO daoPersona = new ClienteDAO();
        TicketsDAO daoTickets = new TicketsDAO();
        PlazaDAO daoPlaza = new PlazaDAO();

        ClientesVO abonado = new ClientesVO();

        Scanner teclado = new Scanner(System.in);
        System.out.println("Bienvenido a HINOBEPA SL, empresa que se dedica a la gestión de parkings de todo el mundo");
        System.out.println("Si usted es ya un cliente abonado, pulse 1\n"
                + "Si usted es un cliente no abonado, pulse 2\n"
                + "Si usted quiere darse de alta como abonado, pulse 3\n"
                + "Si usted quiere modificar algun dato de abonado, pulse 4\n"
                + "Si usted quiere darse de baja como abonado, pulse 5\n"
                + "Si usted es un administrador, pulse 6");
        int tipoCliente = teclado.nextInt();
        try {
            switch (tipoCliente) {
                case 1:
                    teclado.nextLine(); //Limpiamos el buffer
                    System.out.println("Bienvenido al portal de gestión de su abono del parking, por favor, introduzca su nombre completo: ");
                    String nombreAbonado = teclado.nextLine();
                    System.out.println("Bienvenido " + nombreAbonado + ", ¿Qué desea hacer?");
                    System.out.println("1. Retirar vehículo del parking");
                    System.out.println("2. Introducir vehículo en el parking");
                    int eleccionAbonado = teclado.nextInt();
                    switch (eleccionAbonado) {
                        case 1:
                            System.out.println("Introduzca su numero de la plaza");
                            String numeroPlazaRetirado = teclado.nextLine();

                            System.out.println("Introduzca su matricula");
                            String matriculaRetirado = teclado.nextLine();

                            System.out.println("Introduzca su pin");
                            int pinRetirado = teclado.nextInt();

                            if (daoTickets.buscarTicketsMatricula(matriculaRetirado) && daoTickets.buscarTicketsNumeroPlaza(numeroPlazaRetirado) && daoTickets.buscarTicketsPin(pinRetirado)) {
                                System.out.println("Retirando vehiculo");
                                daoPlaza.updatePlazaAbonadoRetirado(numeroPlazaRetirado);
                            } else {
                                System.out.println("La matricula o el numero de plaza o el pin es incorrecto");
                            }
                            break;

                        case 2:
                            System.out.println("Introduzca su DNI");
                            String dniAbonado = teclado.nextLine();

                            System.out.println("Introduzca su matricula");
                            String matriculaAbonado = teclado.nextLine();
                            if (daoPersona.buscarCliente(dniAbonado, matriculaAbonado)) {
                                System.out.println("Ingresando vehiculo");

                            } else {
                                System.out.println("El DNI o la matricua es incorrecto");
                            }

                    }

                    break;
                case 2:
                    System.out.println("Bienvenido al portal de gestión de su vehiculo del parking, por favor, introduzca su nombre completo: ");
                    String nombreNoAbonado = teclado.nextLine();
                    System.out.println("Bienvenido " + nombreNoAbonado + ", ¿Qué desea hacer?");
                    System.out.println("1. Retirar vehículo del parking");
                    System.out.println("2. Introducir vehículo en el parking");
                    int eleccionNoAbonado = teclado.nextInt();
                    switch (eleccionNoAbonado) {
                        case 1:
                            System.out.println("Introduzca su numero de la plaza");
                            String numeroPlazaRetirado = teclado.nextLine();

                            System.out.println("Introduzca su matricula");
                            String matriculaRetirado = teclado.nextLine();

                            System.out.println("Introduzca su pin");
                            int pinRetirado = teclado.nextInt();

                            if (daoTickets.buscarTicketsMatricula(matriculaRetirado) && daoTickets.buscarTicketsNumeroPlaza(numeroPlazaRetirado) && daoTickets.buscarTicketsPin(pinRetirado)) {
                                System.out.println("Retirando vehiculo");
                                daoPlaza.updatePlazaAbonadoRetirado(numeroPlazaRetirado);
                            } else {
                                System.out.println("La matricula o el numero de plaza o el pin es incorrecto");
                            }

                            break;

                        case 2:
                            System.out.println("Introduzca su DNI");
                            String dniAbonado = teclado.nextLine();

                            System.out.println("Introduzca su matricula");
                            String matriculaAbonado = teclado.nextLine();
                            if (daoPersona.buscarCliente(dniAbonado, matriculaAbonado)) {
                                System.out.println("Ingresando vehiculo");

                            } else {
                                System.out.println("El DNI o la matricua es incorrecto");
                            }
                            break;
                    }
                case 3:
                    if (abonado.registro()) {
                        System.out.println("El cliente se ha registrado correctamente");
                    }
                    break;
                case 4:

                    break;
                case 5:
                    System.out.println("Va a darse de baja");
                    System.out.println("Introduzca su DNI");
                    String dniBaja = teclado.nextLine();
                    if (daoPersona.deleteCliente(dniBaja) != 0) {
                        System.out.println("Se ha dado de baja correctamente");
                    } else {
                        System.out.println("El DNI introducido no concuerda con ningun abonado");
                    }
                    break;
                case 6:
                    System.out.println("Bienvenido administrador");
                    System.out.println("¿Qué desea realizar?");
                    System.out.println("1-Copia de seguridad");
                    System.out.println("2-Restaurar una copia de seguridad");
                    int seleccionAdmin = teclado.nextInt();

                    switch (seleccionAdmin) {
                        case 1:
                            System.out.println("Realizando una copia de seguridad");
                            CopiaYRestauracion.copia();
                            System.out.println("Se ha realizado la copia de seguridad correctamente");
                            break;

                        case 2:
                            System.out.println("Vamos a restaurar una copia de seguridad");
                            CopiaYRestauracion.restaurar();
                            System.out.println("Se ha restaurado la copia de seguridad correctamente");
                    }
                    break;
            }
        } catch (SQLException sqle) {
            System.out.println("No se ha podido realizar la operación:");
            System.out.println(sqle.getMessage());
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println(e);
        }
    }
}
