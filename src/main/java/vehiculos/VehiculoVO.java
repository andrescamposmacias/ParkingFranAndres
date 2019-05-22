/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehiculos;

/**
 *
 * @author fran
 */
public class VehiculoVO {

    //Atributos
    private String matricula;
    private String tipo;

    //Métodos
    //Constructor parametrizado
    public VehiculoVO(String matricula, String tipo) {
        this.matricula = matricula;
        this.tipo = tipo;
    }

    //Constructor por defecto
    public VehiculoVO() {
    }

    //Getter&Setter
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    //toString
    @Override //Sobrescrito
    public String toString() {
        return "Vehiculo{" + "matricula=" + matricula + ", tipo=" + tipo + '}';
    }

}
