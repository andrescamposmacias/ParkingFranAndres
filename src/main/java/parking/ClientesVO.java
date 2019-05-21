/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

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

    public ClientesVO(String dni, String matricula, int tarjetaCredito, String nombre, String apellido, String tipoAbono, String email) {
        this.dni = dni;
        this.matricula = matricula;
        this.tarjetaCredito = tarjetaCredito;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoAbono = tipoAbono;
        this.email = email;
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

    @Override
    public String toString() {
        return "Clientes{" + "dni=" + dni + ", matricula=" + matricula + ", tarjetaCredito=" + tarjetaCredito + ", nombre=" + nombre + ", apellido=" + apellido + ", tipoAbono=" + tipoAbono + ", email=" + email + '}';
    }

      
}
