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
    
    private int codigo;
    private String numeroPlaza;
    private double tarifa;
    private String tipoPlaza;
    private String estado;
    private double precioMinuto;

    //constructor parametrizado

    public PlazaVO(int codigo, String numeroPlaza, double tarifa, String tipoPlaza, String estado, double precioMinuto) {
        this.codigo = codigo;
        this.numeroPlaza = numeroPlaza;
        this.tarifa = tarifa;
        this.tipoPlaza = tipoPlaza;
        this.estado = estado;
        this.precioMinuto = precioMinuto;
    }

    public PlazaVO() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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
        return codigo + ", " +numeroPlaza  + ", " +tarifa  + ", " +tipoPlaza  + ", " +estado  + ", " +precioMinuto;
    }
    

      
}
