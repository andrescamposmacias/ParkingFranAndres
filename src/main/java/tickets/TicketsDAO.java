/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tickets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import conexion.Conexion;
import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author andres
 */
public class TicketsDAO implements ITicket{
    
    private Connection con = null;
    
    public TicketsDAO() {
        con = Conexion.getInstance();
    }
    
    @Override
    public List<TicketsVO> getAll() throws SQLException {
        List<TicketsVO> lista = new ArrayList<>();

        try (Statement st = con.createStatement()) {

            ResultSet res = st.executeQuery("select * from tickets");

            while (res.next()) {
                TicketsVO p = new TicketsVO();
         
                p.setFechaEntrada(res.getDate("fechaEntrada").toLocalDate());
                p.setHoraEntrada(res.getTime("horaEntrada").toLocalTime());
                p.setFechaSalida(res.getDate("fechaSalida").toLocalDate());
                p.setHoraSalida(res.getTime("horaSalida").toLocalTime());
                p.setPrecio(res.getDouble("precio"));
                p.setPin(res.getInt("pin"));
                p.setMatricula(res.getString("matricula"));
                p.setNumeroPlaza(res.getString("numeroPlaza"));

                lista.add(p);
            }
        }

        return lista;
    }
    
    @Override
    public boolean buscarTicketsMatricula(String matricula) throws SQLException {

        ResultSet res = null;
        TicketsVO ticket = new TicketsVO();

        String sql = "select * from tickets where matricula=?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {

            prest.setString(1, matricula);

            res = prest.executeQuery();

            if (res.first()) {
 
                ticket.setFechaEntrada(res.getDate("fechaEntrada").toLocalDate());
                ticket.setFechaSalida(res.getDate("fechaSalida").toLocalDate());
                ticket.setHoraEntrada(res.getTime("horaEntrada").toLocalTime());
                ticket.setHoraSalida(res.getTime("horaSalida").toLocalTime());
                ticket.setPrecio(res.getDouble("precio"));
                ticket.setPin(res.getInt("pin"));
                ticket.setMatricula(res.getString("matricula"));
                ticket.setNumeroPlaza(res.getString("numeroPlaza"));
                return true;
            }

            return false;
        }
    }
    
    @Override
    public boolean buscarTicketsPin(int pin) throws SQLException {

        ResultSet res = null;
        TicketsVO ticket = new TicketsVO();

        String sql = "select * from tickets where pin=?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {

            prest.setInt(1, pin);

            res = prest.executeQuery();

            if (res.first()) {
 
                ticket.setFechaEntrada(res.getDate("fechaEntrada").toLocalDate());
                ticket.setFechaSalida(res.getDate("fechaSalida").toLocalDate());
                ticket.setHoraEntrada(res.getTime("horaEntrada").toLocalTime());
                ticket.setHoraSalida(res.getTime("horaSalida").toLocalTime());
                ticket.setPrecio(res.getDouble("precio"));
                ticket.setPin(res.getInt("pin"));
                ticket.setMatricula(res.getString("matricula"));
                ticket.setNumeroPlaza(res.getString("numeroPlaza"));
                return true;
            }

            return false;
        }
    }
    
    @Override
    public boolean buscarTicketsNumeroPlaza(String numeroPlaza) throws SQLException {

        ResultSet res = null;
        TicketsVO ticket = new TicketsVO();

        String sql = "select * from tickets where numeroPlaza=?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {

            prest.setString(1, numeroPlaza);

            res = prest.executeQuery();

            if (res.first()) {
 
                ticket.setFechaEntrada(res.getDate("fechaEntrada").toLocalDate());
                ticket.setFechaSalida(res.getDate("fechaSalida").toLocalDate());
                ticket.setHoraEntrada(res.getTime("horaEntrada").toLocalTime());
                ticket.setHoraSalida(res.getTime("horaSalida").toLocalTime());
                ticket.setPrecio(res.getDouble("precio"));
                ticket.setPin(res.getInt("pin"));
                ticket.setMatricula(res.getString("matricula"));
                ticket.setNumeroPlaza(res.getString("numeroPlaza"));
                return true;
            }

            return false;
        }
    }
    
    @Override
    public int insertTickets(TicketsVO ticket) throws SQLException {

        int numFilas = 0;
        String sql = "insert into tickets values (?,?,?,?,?,?,?,?)";

        if (buscarTicketsMatricula(ticket.getMatricula())) {
            
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = con.prepareStatement(sql)) {

                prest.setDate(2, Date.valueOf(ticket.getFechaEntrada()));
                prest.setDate(3, Date.valueOf(ticket.getFechaSalida()));
                prest.setTime(4, Time.valueOf(ticket.getHoraEntrada()));
                prest.setTime(5, Time.valueOf(ticket.getHoraSalida()));
                prest.setDouble(6, ticket.getPrecio());
                prest.setInt(7, ticket.getPin());
                prest.setString(8, ticket.getMatricula()); 
                prest.setString(9, ticket.getNumeroPlaza());

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }

    }
    
    @Override
    public int insertTickets(List<TicketsVO> lista) throws SQLException {
        int numFilas = 0;

        for (TicketsVO tmp : lista) {
            numFilas += insertTickets(tmp);
        }

        return numFilas;
    }
    
    @Override
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
    
    @Override
    public int updateTickets(String matricula, TicketsVO ticket) throws SQLException {

        int numFilas = 0;
        String sql = "update plaza set fechaEntrada = ?, fechaSalida = ?,horaEntrada = ?,horaSalida = ?, precio = ?, pin = ?, matricula = ?, numeroPlaza = ? where matricula=?";

        if (buscarTicketsMatricula(ticket.getMatricula())) {

            return numFilas;
        } else {

            try (PreparedStatement prest = con.prepareStatement(sql)) {

                prest.setDate(2, Date.valueOf(ticket.getFechaEntrada()));
                prest.setDate(3, Date.valueOf(ticket.getFechaSalida()));
                prest.setTime(4, Time.valueOf(ticket.getHoraEntrada()));
                prest.setTime(5, Time.valueOf(ticket.getHoraSalida()));
                prest.setDouble(6, ticket.getPrecio());
                prest.setInt(7, ticket.getPin());
                prest.setString(8, ticket.getMatricula());    
                prest.setString(9, ticket.getNumeroPlaza());

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }
    }
    
}
