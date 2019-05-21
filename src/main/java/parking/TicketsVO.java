/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author andres
 */
public class TicketsVO {

    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    private String precio;
    private int pin;
    private String matricula;

    public TicketsVO(LocalDate fechaEntrada, LocalDate fechaSalida, LocalTime horaEntrada, LocalTime horaSalida, String precio, int pin, String matricula) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.precio = precio;
        this.pin = pin;
        this.matricula = matricula;
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

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
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

    @Override
    public String toString() {
        return "TicketsVO{" + "fechaEntrada=" + fechaEntrada + ", fechaSalida=" + fechaSalida + ", horaEntrada=" + horaEntrada + ", horaSalida=" + horaSalida + ", precio=" + precio + ", pin=" + pin + ", matricula=" + matricula + '}';
    }

}
