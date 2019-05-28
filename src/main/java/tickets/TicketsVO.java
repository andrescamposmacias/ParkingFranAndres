/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tickets;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author andres
 */
public class TicketsVO {

    private int codigo;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    private double precio;
    private int pin;
    private String matricula;
    private String numeroPlaza;

    public TicketsVO(int codigo, LocalDate fechaEntrada, LocalDate fechaSalida, LocalTime horaEntrada, LocalTime horaSalida, double precio, int pin, String matricula, String numeroPlaza) {
        this.codigo = codigo;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.precio = precio;
        this.pin = pin;
        this.matricula = matricula;
        this.numeroPlaza = numeroPlaza;
    }

    public TicketsVO(LocalDate fechaEntrada, LocalDate fechaSalida, LocalTime horaEntrada, LocalTime horaSalida, double precio, int pin, String matricula, String numeroPlaza) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.precio = precio;
        this.pin = pin;
        this.matricula = matricula;
        this.numeroPlaza = numeroPlaza;
    }

    public TicketsVO() {
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNumeroPlaza() {
        return numeroPlaza;
    }

    public void setNumeroPlaza(String numeroPlaza) {
        this.numeroPlaza = numeroPlaza;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return codigo + ", " +fechaEntrada + ", " +fechaSalida  + ", " +horaEntrada  + ", " +horaSalida  + ", " +precio  + ", " +pin  + ", " +matricula  + ", " +numeroPlaza;
    }

}
