/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import conexion.Conexion;
import java.sql.Date;

/**
 *
 * @author andres
 */
public class TicketsDAO {
    
    private Connection con = null;
    
    public TicketsDAO() {
        con = Conexion.getInstance();
    }
    
    public List<TicketsVO> getAll() throws SQLException {
        List<TicketsVO> lista = new ArrayList<>();

        try (Statement st = con.createStatement()) {

            ResultSet res = st.executeQuery("select * from tickets");

            while (res.next()) {
                TicketsVO p = new TicketsVO();
         
                //p.setFecha(res.getDate("fecha").toLocalDate());
                p.setPrecio(res.getString("precio"));
                p.setPin(res.getInt("pin"));
                p.setMatricula("matricula");

                lista.add(p);
            }
        }

        return lista;
    }
    
    public TicketsVO buscarTickets(String matricula) throws SQLException {

        ResultSet res = null;
        TicketsVO ticket = new TicketsVO();

        String sql = "select * from tickets where matricula=?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {

            prest.setString(5, matricula);

            res = prest.executeQuery();

            if (res.first()) {
 
                ticket.setFechaEntrada(res.getDate("fechaEntrada").toLocalDate());
                ticket.setFechaSalida(res.getDate("fechaSalida").toLocalDate());
                ticket.setHoraEntrada(res.getTime("horaEntrada").toLocalTime());
                ticket.setHoraSalida(res.getTime("horaSalida").toLocalTime());
                ticket.setPrecio(res.getString("precio"));
                ticket.setPin(res.getInt("pin"));
                ticket.setMatricula("matricula");
                return ticket;
            }

            return null;
        }
    }
    
    public int insertTickets(TicketsVO ticket) throws SQLException {

        int numFilas = 0;
        String sql = "insert into tickets values (?,?,?,?,?,?,?)";

        if (buscarTickets(ticket.getMatricula()) != null) {
            
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = con.prepareStatement(sql)) {

                prest.setDate(2, Date.valueOf(ticket.getFechaEntrada()));
                prest.setDate(3, Date.valueOf(ticket.getFechaSalida()));
                prest.setTime(4, ticket.getTime().toLocalTime());
                prest.setTime(5, ticket.getTime().toLocalTime());
                prest.setString(6, ticket.getPrecio());
                prest.setInt(7, ticket.getPin());
                prest.setString(8, ticket.getMatricula());                            

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }

    }
    
    public int insertTickets(List<TicketsVO> lista) throws SQLException {
        int numFilas = 0;

        for (TicketsVO tmp : lista) {
            numFilas += insertTickets(tmp);
        }

        return numFilas;
    }
    
    public int deleteTickets(TicketsVO ticket) throws SQLException {
        int numFilas = 0;

        String sql = "delete from ticket where matricula = ?";

        // Sentencia parametrizada
        try (PreparedStatement prest = con.prepareStatement(sql)) {

            // Establecemos los parámetros de la sentencia
            prest.setString(8, ticket.getMatricula());
            // Ejecutamos la sentencia
            numFilas = prest.executeUpdate();
        }
        return numFilas;
    }
    
    public int updateTickets(String matricula, TicketsVO ticket) throws SQLException {

        int numFilas = 0;
        String sql = "update plaza set fechaEntrada = ?, fechaSalida = ?,horaEntrada = ?,horaSalida = ?, precio = ?, pin = ?, matricula = ? where matricula=?";

        if (buscarTickets(ticket.getMatricula()) == null) {

            return numFilas;
        } else {

            try (PreparedStatement prest = con.prepareStatement(sql)) {

                prest.setDate(2, Date.valueOf(ticket.getFechaEntrada()));
                prest.setDate(3, Date.valueOf(ticket.getFechaSalida()));
                prest.setTime(4, ticket.getTime().toLocalTime());
                prest.setTime(5, ticket.getTime().toLocalTime());
                prest.setString(6, ticket.getPrecio());
                prest.setInt(7, ticket.getPin());
                prest.setString(8, ticket.getMatricula());                          

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }
    }
    
}
