/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

import java.time.LocalDateTime;

/**
 *
 * @author andres
 */
public class TicketsVO {
    
    private LocalDateTime fecha;
    private String precio;
    private int pin;
    private String matricula;

    public TicketsVO(LocalDateTime fecha, String precio, int pin, String matricula) {
        this.fecha = fecha;
        this.precio = precio;
        this.pin = pin;
        this.matricula = matricula;
    }

    public TicketsVO() {
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
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
        return "TicketsVO{" + "fecha=" + fecha + ", precio=" + precio + ", pin=" + pin + ", matricula=" + matricula + '}';
    }
    
    
    
}
