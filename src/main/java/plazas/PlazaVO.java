/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plazas;

import java.time.LocalDate;

/**
 *
 * @author andres
 */
public class PlazaVO {
    
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private String numeroPlaza;
    private double tarifa;
    private String tipoPlaza;
    private String estado;
    private double precioMinuto;

    //constructor parametrizado

    public PlazaVO(LocalDate fechaEntrada, LocalDate fechaSalida, String numeroPlaza, double tarifa, String tipoPlaza, String estado, double precioMinuto) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.numeroPlaza = numeroPlaza;
        this.tarifa = tarifa;
        this.tipoPlaza = tipoPlaza;
        this.estado = estado;
        this.precioMinuto = precioMinuto;
    }
    

    public PlazaVO() {
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

    public String getNumeroPlaza() {
        return numeroPlaza;
    }

    public void setNumeroPlaza(String numeroPlaza) {
        this.numeroPlaza = numeroPlaza;
    }

    public double getTarifa() {
        return tarifa;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }

    public String getTipoPlaza() {
        return tipoPlaza;
    }

    public void setTipoPlaza(String tipoPlaza) {
        this.tipoPlaza = tipoPlaza;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getPrecioMinuto() {
        return precioMinuto;
    }

    public void setPrecioMinuto(double precioMinuto) {
        this.precioMinuto = precioMinuto;
    }

    @Override
    public String toString() {
        return "PlazaVO{" + "fechaEntrada=" + fechaEntrada + ", fechaSalida=" + fechaSalida + ", numeroPlaza=" + numeroPlaza + ", tarifa=" + tarifa + ", tipoPlaza=" + tipoPlaza + ", estado=" + estado + ", precioMinuto=" + precioMinuto + '}';
    }

      
}
