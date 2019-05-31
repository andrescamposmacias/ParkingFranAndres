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
    
    //Atributos
    private String numeroPlaza;
    private String tipoPlaza;
    private String estado;
    private double precioMinuto;

    //constructor parametrizado
    public PlazaVO(String numeroPlaza, String tipoPlaza, String estado, double precioMinuto) {
        this.numeroPlaza = numeroPlaza;
        this.tipoPlaza = tipoPlaza;
        this.estado = estado;
        this.precioMinuto = precioMinuto;
    }

    //Constructor por defecto
    public PlazaVO() {
    }

    //Getter&Setter
    public String getNumeroPlaza() {
        return numeroPlaza;
    }

    public void setNumeroPlaza(String numeroPlaza) {
        this.numeroPlaza = numeroPlaza;
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

    //toString
    @Override //Sobrescrito
    public String toString() {
        return numeroPlaza + ", " +tipoPlaza  + ", " +estado  + ", " +precioMinuto;
    }
    

      
}
