/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

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

/**
 *
 * @author andres
 */
public class ClienteDAO {
    
     private Connection con = null;
     
     public ClienteDAO() {
        con = Conexion.getInstance();
    }
     
     public ClientesVO buscarDni(String dni) throws SQLException {

        ResultSet res = null;
        ClientesVO cliente = new ClientesVO();

        String sql = "select * from clientes where dni=?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {

            prest.setString(1, dni);

            res = prest.executeQuery();



            if (res.first()) {
                
                cliente.setDni(res.getString("dni"));
                cliente.setMatricula(res.getString("matricula"));
                cliente.setTarjetaCredito(res.getInt("tarjetaCredito"));
                cliente.setNombre(res.getString("nombre"));
                cliente.setApellido(res.getString("apellido"));
                cliente.setTipoAbono(res.getString("abono"));
                cliente.setEmail(res.getString("email"));
                return cliente;
            }

            return null;
        }
    }
     
     public int insertCliente(ClientesVO cliente) throws SQLException {

        int numFilas = 0;
        String sql = "insert into clientes values (?,?,?,?,?,?,?)";

        if (buscarDni(cliente.getDni()) != null) {

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
                

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }

    }
     
     public int insertPersona(List<ClientesVO> lista) throws SQLException {
        int numFilas = 0;

        for (ClientesVO tmp : lista) {
            numFilas += insertCliente(tmp);
        }

        return numFilas;
    }
     
     public int deleteCliente(ClientesVO cliente) throws SQLException {
        int numFilas = 0;

        String sql = "delete from clientes where dni = ?";

        // Sentencia parametrizada
        try (PreparedStatement prest = con.prepareStatement(sql)) {

            // Establecemos los parámetros de la sentencia
            prest.setString(1, cliente.getDni());
            // Ejecutamos la sentencia
            numFilas = prest.executeUpdate();
        }
        return numFilas;
    }
     
     public int updateCliente(int dni, ClientesVO cliente) throws SQLException {
         
        int numFilas = 0;
        String sql = "update clientes set dni = ?, matricula = ?, tarjetaCredito = ?, nombre = ?, apellido = ?, abono = ?, email = ? where dni=?";

        if (buscarDni(cliente.getDni()) == null) {

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
                prest.setInt(8, dni);

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }
    }
     
 }
