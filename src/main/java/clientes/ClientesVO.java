/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;

import java.time.LocalDate;

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

    public ClientesVO(String dni, String matricula, int tarjetaCredito, String nombre, String apellido, String tipoAbono, String email, LocalDate fechaInicio, LocalDate fechaFin, String numeroPlaza, double coste) {
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

    @Override
    public String toString() {
        return dni + ", " +matricula + ", " +tarjetaCredito + ", " +nombre  + ", " +apellido + ", " +tipoAbono  + ", " +email +  ", " +fechaInicio  + ", " +fechaFin + ", " +numeroPlaza  + ", " +coste;
    }

}
