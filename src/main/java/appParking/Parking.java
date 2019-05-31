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
import vehiculos.VehiculoDAO;

/**
 *
 * @author FranAragon13
 */
public class Parking {

    public static void main(String[] args) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        ClienteDAO daoPersona = new ClienteDAO();
        TicketsDAO daoTickets = new TicketsDAO();
        PlazaDAO daoPlaza = new PlazaDAO();
        VehiculoDAO daoVehiculo = new VehiculoDAO();

        ClientesVO abonado = new ClientesVO();
        daoPlaza.creacionPlazas();
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
                    System.out.println("Bienvenido al portal de gestión de su abono del parking, ¿qué desea hacer?");
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

                            if (daoPersona.buscarAbonoMatricula(matriculaRetirado) && daoPersona.buscarAbonoNumeroPlaza(numeroPlazaRetirado) && daoPersona.buscarAbonoPin(pinRetirado)) {
                                System.out.println("El vehiculo ha sido retirado");
                                daoPlaza.updatePlazaAbonadoRetirado(numeroPlazaRetirado);
                            } else {
                                System.out.println("Algún dato introducido es incorrecto");
                            }
                            break;

                        case 2:
                            System.out.println("Introduzca su DNI");
                            String dniAbonado = teclado.nextLine();

                            System.out.println("Introduzca su matricula");
                            String matriculaAbonado = teclado.nextLine();
                            if (daoPlaza.updatePlazaAbonadoIngreso(dniAbonado, matriculaAbonado) != 0) {
                                System.out.println("Su coche ha ingresado correctamente");
                            } else {
                                System.out.println("Lo sentimos, ha ocurrido un error");
                            }

                    }

                    break;
                case 2:
                    teclado.nextInt();
                    System.out.println("Bienvenido cliente no abonado, ¿Qué desea hacer?");
                    System.out.println("1. Retirar vehículo del parking");
                    System.out.println("2. Introducir vehículo en el parking");
                    int eleccionNoAbonado = teclado.nextInt();
                    switch (eleccionNoAbonado) {
                        case 1:
                            teclado.nextLine();
                            System.out.println("Introduzca su numero de la plaza");
                            String numeroPlazaRetirado = teclado.nextLine();

                            System.out.println("Introduzca su matricula");
                            String matriculaRetirado = teclado.nextLine();

                            System.out.println("Introduzca su pin");
                            int pinRetirado = teclado.nextInt();

                            if (daoTickets.buscarTicketsMatricula(matriculaRetirado) && daoTickets.buscarTicketsNumeroPlaza(numeroPlazaRetirado) && daoTickets.buscarTicketsPin(pinRetirado)) {
                                System.out.println("Retirando vehiculo");
                                daoPlaza.updatePlazaNoAbonadoSalida(matriculaRetirado);
                                daoTickets.modificarFechaHoraSalida(matriculaRetirado);//Modifica la fecha y la hora de salida buena
                                double precio = daoTickets.calcularPrecio(matriculaRetirado, numeroPlazaRetirado);//Calcula la hora
                                daoTickets.actualizarPrecio(matriculaRetirado, numeroPlazaRetirado);
                                System.out.println("Su precio total es: " + precio);
                                System.out.println("Se ha retirado el vehiculo");
                            } else {
                                System.out.println("Algún dato introducido es incorrecto");
                            }

                            break;

                        case 2:
                            daoVehiculo.insertVehiculoPersonaSinAbono();

                    }
                case 3:
                    if (abonado.registro()) {
                        System.out.println("El cliente se ha registrado correctamente");
                    } else {
                        System.out.println("No se ha podido registrar el usuario");
                    }
                    break;
                case 4:
                    teclado.nextLine();
                    System.out.println("Procediendo a la modificación de sus datos");
                    System.out.println("Por favor, introduzca su dni");
                    String dni = teclado.nextLine();
                    System.out.println("Por favor, introduzca su matricula");
                    String matricula = teclado.nextLine();
                    daoPersona.updateCliente(dni,matricula);
                    System.out.println("Datos actualizados");

                    break;
                case 5:
                    System.out.println("Procediendo a darle de baja");
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
                    System.out.println("3-Mostrar el estado del parking");
                    System.out.println("4-Facturacion entre 2 fechas");
                    int seleccionAdmin = teclado.nextInt();

                    switch (seleccionAdmin) {
                        case 1:
                            System.out.println("Realizando una copia de seguridad");
                            CopiaYRestauracion.copia();
                            System.out.println("Se ha realizado la copia de seguridad correctamente");
                            break;

                        case 2:
                            System.out.println("Restaurando una copia de seguridad");
                            CopiaYRestauracion.restaurar();
                            System.out.println("Se ha restaurado la copia de seguridad correctamente");
                            break;
                        case 3:
                            daoPlaza.estadoPlaza();
                            break;
                        case 4:
                            double precio = daoTickets.calcularFacturacion();
                            System.out.println("el precio es: " + precio);
                            break;
                    }
                    break;
            }
        } catch (SQLException sqle) {
            System.out.println("No se ha podido realizar la operación: ");
            System.out.println(sqle.getMessage());
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println(e);
        }
    }
}
