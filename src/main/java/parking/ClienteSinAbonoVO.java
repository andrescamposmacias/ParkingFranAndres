/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author fran
 */
public class ClienteSinAbonoVO {

    //Atributos
    private String matricula;
    private int codigo;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;

    //Métodos
    //Constructor parametrizado
    public ClienteSinAbonoVO(String matricula, int codigo, LocalTime horaEntrada, LocalTime horaSalida) {
        this.matricula = matricula;
        this.codigo = codigo;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }

    //Constructor por defecto
    public ClienteSinAbonoVO() {
    }

    //Getter&Setter
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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

    //toString
    @Override //Sobrescrito
    public String toString() {
        return "ClienteSinAbono{" + "matricula=" + matricula + ", codigo=" + codigo + ", horaEntrada=" + horaEntrada + ", horaSalida=" + horaSalida + '}';
    }

}
