/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import conexion.Conexion;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

/**
 *
 * @author andres
 */
public class ClienteDAO implements ICliente {

    private Connection con = null;

    public ClienteDAO() {
        con = Conexion.getInstance();
    }

    @Override
    public List<ClientesVO> getAll() throws SQLException {
        List<ClientesVO> lista = new ArrayList<>();

        try (Statement st = con.createStatement()) {

            ResultSet res = st.executeQuery("select * from clientes");

            while (res.next()) {
                ClientesVO p = new ClientesVO();

                p.setDni(res.getString("dni"));
                p.setMatricula(res.getString("matricula"));
                p.setTarjetaCredito(res.getInt("tarjetaCredito"));
                p.setNombre(res.getString("nombre"));
                p.setApellido(res.getString("apellido"));
                p.setTipoAbono(res.getString("abono"));
                p.setEmail(res.getString("email"));
                p.setFechaInicio(res.getDate("fechaInicio").toLocalDate());
                p.setFechaFin(res.getDate("fechaFin").toLocalDate());
                p.setNumeroPlaza(res.getString("numeroPlaza"));
                p.setCoste(res.getDouble("coste"));
                p.setPin(res.getInt("pin"));
                lista.add(p);
            }
        }

        return lista;
    }

    @Override
    public String buscarCliente(String dni, String matricula) throws SQLException {
        String numeroPlaza;

        ResultSet res = null;

        String sql = "select numeroPlaza from clientes where dni=? and matricula=?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {

            prest.setString(1, dni);
            prest.setString(2, matricula);

            res = prest.executeQuery();

            if (res.first()) {

                numeroPlaza = res.getString(1);
                return numeroPlaza;
            }

            return null;
        }
    }

    @Override
    public int insertCliente(ClientesVO cliente) throws SQLException {

        int numFilas = 0;
        String sql = "insert into clientes values (?,?,?,?,?,?,?,?,?,?,?,?)";

        if (buscarCliente(cliente.getDni(), cliente.getMatricula()) != null) {

            return numFilas;
        } else {

            try (PreparedStatement prest = con.prepareStatement(sql)) {

                prest.setString(1, cliente.getDni());
                prest.setString(2, cliente.getMatricula());
                prest.setInt(3, cliente.getTarjetaCredito());
                prest.setString(4, cliente.getNombre());
                prest.setString(5, cliente.getApellido());
                prest.setString(6, cliente.getTipoAbono());
                prest.setString(7, cliente.getEmail());
                prest.setDate(8, Date.valueOf(cliente.getFechaInicio()));
                prest.setDate(9, Date.valueOf(cliente.getFechaFin()));
                prest.setString(10, cliente.getNumeroPlaza());
                prest.setDouble(11, cliente.getCoste());
                prest.setInt(12, cliente.getPin());

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }

    }

    @Override
    public int insertCliente(List<ClientesVO> lista) throws SQLException {
        int numFilas = 0;

        for (ClientesVO tmp : lista) {
            numFilas += insertCliente(tmp);
        }

        return numFilas;
    }

    @Override
    public int deleteCliente(String dni) throws SQLException {
        int numFilas = 0;

        String sql = "delete from clientes where dni = ?";

        // Sentencia parametrizada
        try (PreparedStatement prest = con.prepareStatement(sql)) {

            // Establecemos los parámetros de la sentencia
            prest.setString(1, dni);
            // Ejecutamos la sentencia
            numFilas = prest.executeUpdate();
        }
        return numFilas;
    }

    @Override
    public boolean updateCliente(String dni, String matricula) throws SQLException {
        
        
        Scanner teclado = new Scanner(System.in);

        //atributos cliente
        String matriculaRegistro;
        String dniRegistro;
        int tarjetaCreditoRegistro;
        String nombreRegistro;
        String apellidoRegistro;
        String abonoRegistro;
        String emailRegistro;
        LocalDate hoyRegistro = LocalDate.now();
        LocalDate finalRegistro = null;
        int costeRegistro = 0;   

        System.out.println("Introduzca su matricula");
        matriculaRegistro = teclado.nextLine();  

        System.out.println("Introduzca su DNI");
        dniRegistro = teclado.nextLine();

        System.out.println("Introduzca su Tarjeta de crédito");
        tarjetaCreditoRegistro = teclado.nextInt();

        teclado.nextLine();//limpiamos el buffer

        System.out.println("Introduzca su nombre");
        nombreRegistro = teclado.nextLine();

        System.out.println("Introduzca su apellido");
        apellidoRegistro = teclado.nextLine();

        do {
            System.out.println("Introduzca el tipo de abono (mensual, trimestral, semestral o anual)");
            abonoRegistro = teclado.nextLine();
        } while (!abonoRegistro.equalsIgnoreCase("mensual") && !abonoRegistro.equalsIgnoreCase("trimestral") && !abonoRegistro.equalsIgnoreCase("semestral") && !abonoRegistro.equalsIgnoreCase("anual"));

        System.out.println("Introduzca su email");
        emailRegistro = teclado.nextLine();

        if (abonoRegistro.equalsIgnoreCase("mensual")) {

            finalRegistro = hoyRegistro.plus(1, ChronoUnit.MONTHS);
            costeRegistro = 25;

        } else if (abonoRegistro.equalsIgnoreCase("trimestral")) {

            finalRegistro = hoyRegistro.plus(3, ChronoUnit.MONTHS);
            costeRegistro = 70;

        } else if (abonoRegistro.equalsIgnoreCase("semestral")) {

            finalRegistro = hoyRegistro.plus(6, ChronoUnit.MONTHS);
            costeRegistro = 130;

        } else if (abonoRegistro.equalsIgnoreCase("anual")) {

            finalRegistro = hoyRegistro.plus(1, ChronoUnit.YEARS);
            costeRegistro = 200;

        }
        
        String sql = "update clientes set dni=?, matricula=?, tarjetaCredito=?, nombre=?, apellido=?, abono=?, email=?, fechaInicio=?, fechaFin=?, coste=? where dni=?";

        if (buscarCliente(dniRegistro, matriculaRegistro) == null) {

            System.out.println("El cliente que intenta modificar no existe");
        } else {

            try (PreparedStatement prest = con.prepareStatement(sql)) {

                prest.setString(1, dniRegistro);
                prest.setString(2, matriculaRegistro);
                prest.setInt(3, tarjetaCreditoRegistro);
                prest.setString(4, nombreRegistro);
                prest.setString(5, apellidoRegistro);
                prest.setString(6, abonoRegistro);
                prest.setString(7, emailRegistro);
                prest.setDate(8, Date.valueOf(hoyRegistro));
                prest.setDate(9,Date.valueOf(finalRegistro));
                prest.setDouble(10, costeRegistro);
                prest.setString(11, dni);

                prest.executeUpdate();
                return true;
            }
            
        }
        return false;
    }

    @Override
    public int deleteCliente() throws SQLException {
        String sql = "delete from clientes";

        int nfilas = 0;

        // Preparamos el borrado de datos  mediante un Statement
        // No hay parámetros en la sentencia SQL
        try (Statement st = con.createStatement()) {
            // Ejecución de la sentencia
            nfilas = st.executeUpdate(sql);
        }

        // El borrado se realizó con éxito, devolvemos filas afectadas
        return nfilas;
    }

    public int sacarPinAbonado(String dni) throws SQLException {
        int numeroPlaza;

        ResultSet res = null;

        String sql = "select pin from clientes where dni =?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {

            prest.setString(1, dni);

            res = prest.executeQuery();

            if (res.first()) {

                numeroPlaza = res.getInt(1);
                return numeroPlaza;
            }

            return 0;
        }

        //ResultSet rs = st.executeQuery("");
    }
    
    public boolean buscarAbonoMatricula(String matricula) throws SQLException {

        ResultSet res = null;

        String sql = "select matricula from clientes where matricula=?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {

            prest.setString(1, matricula);

            res = prest.executeQuery();

            if (res.first()) {

                return true;
            }

            return false;
        }
    }
    
    public boolean buscarAbonoPin(int pin) throws SQLException {

        ResultSet res = null;

        String sql = "select pin from clientes where pin=?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {

            prest.setInt(1, pin);

            res = prest.executeQuery();

            if (res.first()) {
 
                
                return true;
            }

            return false;
        }
    }
    
    public boolean buscarAbonoNumeroPlaza(String numeroPlaza) throws SQLException {

        ResultSet res = null;

        String sql = "select numeroPlaza from clientes where numeroPlaza=?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {

            prest.setString(1, numeroPlaza);

            res = prest.executeQuery();

            if (res.first()) {
 
                
                return true;
            }

            return false;
        }
    }

}
