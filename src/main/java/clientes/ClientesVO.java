/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.Scanner;
import plazas.PlazaDAO;
import vehiculos.VehiculoDAO;
import vehiculos.VehiculoVO;

/**
 *
 * @author andres
 */
public class ClientesVO {

    private String dni;
    private String matricula;
    private int tarjetaCredito;
    private String nombre;
    private String apellido;
    private String tipoAbono;
    private String email;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String numeroPlaza;
    private double coste;
    private int pin;

    public ClientesVO(String dni, String matricula, int tarjetaCredito, String nombre, String apellido, String tipoAbono, String email, LocalDate fechaInicio, LocalDate fechaFin, String numeroPlaza, double coste, int pin) {
        this.dni = dni;
        this.matricula = matricula;
        this.tarjetaCredito = tarjetaCredito;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoAbono = tipoAbono;
        this.email = email;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.numeroPlaza = numeroPlaza;
        this.coste = coste;
        this.pin = pin;
    }

    public ClientesVO() {
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getTarjetaCredito() {
        return tarjetaCredito;
    }

    public void setTarjetaCredito(int tarjetaCredito) {
        this.tarjetaCredito = tarjetaCredito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTipoAbono() {
        return tipoAbono;
    }

    public void setTipoAbono(String tipoAbono) {
        this.tipoAbono = tipoAbono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getNumeroPlaza() {
        return numeroPlaza;
    }

    public void setNumeroPlaza(String numeroPlaza) {
        this.numeroPlaza = numeroPlaza;
    }

    public double getCoste() {
        return coste;
    }

    public void setCoste(double coste) {
        this.coste = coste;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return dni + "," + matricula + "," + tarjetaCredito + "," + nombre + "," + apellido + "," + tipoAbono + "," + email + "," + fechaInicio + "," + fechaFin + "," + numeroPlaza + "," + coste + "," + pin;
    }
    
    private int generacionPin(String dni) throws SQLException {
        Random r = new Random();
        
        int pin = r.nextInt(999999-100000+1)+100000;
        
        String pinAbonado = String.valueOf(pin);
        
        File archivoBackup = new File("pinesAbonados");
        archivoBackup.mkdir();
        
        try (BufferedWriter flujo = new BufferedWriter(new FileWriter(archivoBackup + "\\" + dni + ".txt"))) {


                    // Usamos metodo write() para escribir en el buffer
                    flujo.write(pinAbonado);

                    // Metodo newLine() añade línea en blanco
                    flujo.newLine();


                // Metodo flush() guarda cambios en disco 
                flujo.flush();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        
        return pin;
    }

    public boolean registro() throws SQLException {
        Scanner teclado = new Scanner(System.in);

        ClienteDAO comprobacion = new ClienteDAO();
        VehiculoDAO insertarVehiculo = new VehiculoDAO();
        PlazaDAO plaza = new PlazaDAO();

        //atributos vehiculo
        String matriculaRegistro;
        String tipoVehiculo;

        //atributos cliente
        String dniRegistro;
        int tarjetaCreditoRegistro;
        String nombreRegistro;
        String apellidoRegistro;
        String abonoRegistro;
        String emailRegistro;
        LocalDate hoyRegistro = LocalDate.now();
        LocalDate finalRegistro = null;
        String numeroPlazaRegistro;
        int costeRegistro = 0;   

        System.out.println("Introduzca su matricula");
        matriculaRegistro = teclado.nextLine();

        numeroPlazaRegistro = "NoAsignado";
        do {
            System.out.println("Introduce el tipo de vehículo (Turismo, Motocicleta o Caravana)");
            tipoVehiculo = teclado.nextLine();
        } while (!tipoVehiculo.equalsIgnoreCase("Turismo") && !tipoVehiculo.equalsIgnoreCase("Motocicleta") && !tipoVehiculo.equalsIgnoreCase("Caravana"));
        
        if(tipoVehiculo.equalsIgnoreCase("Turismo")){
            if(plaza.plazaTurismoLibre().equalsIgnoreCase("0")){
                System.out.println("Lo siento no quedan plazas de turismo");
                return false;
            }else{
                numeroPlazaRegistro = plaza.sacarNumeroPlazaTurismo();
                plaza.updatePlazaAbonadoRetirado(numeroPlazaRegistro);
            }
        }
        
        if(tipoVehiculo.equalsIgnoreCase("Motocicleta")){
            if(plaza.plazaMotocicletaLibre().equalsIgnoreCase("0")){
                System.out.println("Lo siento no quedan plazas de Motocicleta");
                return false;
            }else{
                numeroPlazaRegistro = plaza.sacarNumeroPlazaMotocicleta();
                plaza.updatePlazaAbonadoRetirado(numeroPlazaRegistro);
            }
        }
        
        if(tipoVehiculo.equalsIgnoreCase("Caravana")){
            if(plaza.plazaCaravanaLibre().equalsIgnoreCase("0")){
                System.out.println("Lo siento no quedan plazas de Caravana");
                return false;
            }else{
                numeroPlazaRegistro = plaza.sacarNumeroPlazaCaravana();
                plaza.updatePlazaAbonadoRetirado(numeroPlazaRegistro);
            }
        }

        System.out.println("Introduzca su DNI");
        dniRegistro = teclado.nextLine();
        
        int pin = generacionPin(dniRegistro);

        System.out.println("Introduzca su Tarjeta de crédito");
        tarjetaCreditoRegistro = teclado.nextInt();

        teclado.nextLine();//limpiamos el buffer

        System.out.println("Introduzca su nombre");
        nombreRegistro = teclado.nextLine();

        System.out.println("Introduzca su apellido");
        apellidoRegistro = teclado.nextLine();

        do {
            System.out.println("Introduzca el tipo de abono (mensual, trimestral, semestral o anual)");
            abonoRegistro = teclado.nextLine();
        } while (!abonoRegistro.equalsIgnoreCase("mensual") && !abonoRegistro.equalsIgnoreCase("trimestral") && !abonoRegistro.equalsIgnoreCase("semestral") && !abonoRegistro.equalsIgnoreCase("anual"));

        System.out.println("Introduzca su email");
        emailRegistro = teclado.nextLine();

        if (abonoRegistro.equalsIgnoreCase("mensual")) {

            finalRegistro = hoyRegistro.plus(1, ChronoUnit.MONTHS);
            costeRegistro = 25;

        } else if (abonoRegistro.equalsIgnoreCase("trimestral")) {

            finalRegistro = hoyRegistro.plus(3, ChronoUnit.MONTHS);
            costeRegistro = 70;

        } else if (abonoRegistro.equalsIgnoreCase("semestral")) {

            finalRegistro = hoyRegistro.plus(6, ChronoUnit.MONTHS);
            costeRegistro = 130;

        } else if (abonoRegistro.equalsIgnoreCase("anual")) {

            finalRegistro = hoyRegistro.plus(1, ChronoUnit.YEARS);
            costeRegistro = 200;

        }

        try {
            if (comprobacion.buscarCliente(dniRegistro, matriculaRegistro)!=null) {
                System.out.println("El cliente que ha introducido ya existe");
                return false;
            } else {
                ClientesVO abonado = new ClientesVO(dniRegistro, matriculaRegistro, tarjetaCreditoRegistro, nombreRegistro, apellidoRegistro, abonoRegistro, emailRegistro, hoyRegistro, finalRegistro, numeroPlazaRegistro, costeRegistro, pin);
                insertarVehiculo.insertVehiculo(new VehiculoVO(matriculaRegistro, tipoVehiculo));
                comprobacion.insertCliente(abonado);
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    

}
