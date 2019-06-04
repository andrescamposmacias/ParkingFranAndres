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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author andres
 */
public class TicketsDAO implements ITicket {

    private Connection con = null;

    public TicketsDAO() {
        con = Conexion.getInstance();
    }

    //Método que devuelve una lista de tickets
    @Override //Sobrescrito
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

    //Método para buscar un tickets por una matricula
    @Override //Sobrescrito
    public boolean buscarTicketsMatricula(String matricula) throws SQLException {

        ResultSet res = null;

        String sql = "select * from tickets where matricula=?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {

            prest.setString(1, matricula);

            res = prest.executeQuery();

            if (res.first()) {

                return true;
            }

            return false;
        }
    }

    //Método para buscar un ticket por un pin
    @Override //Sobrescrito
    public boolean buscarTicketsPin(int pin) throws SQLException {

        ResultSet res = null;

        String sql = "select * from tickets where pin=?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {

            prest.setInt(1, pin);

            res = prest.executeQuery();

            if (res.first()) {

                return true;
            }

            return false;
        }
    }

    //Método para buscar tickets por un número de plaza
    @Override //tickets
    public boolean buscarTicketsNumeroPlaza(String numeroPlaza) throws SQLException {

        ResultSet res = null;

        String sql = "select * from tickets where numeroPlaza=?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {

            prest.setString(1, numeroPlaza);

            res = prest.executeQuery();

            if (res.first()) {

                return true;
            }

            return false;
        }
    }

    //Método para insertar un ticket nuevo
    @Override //Sobrescrito
    public int insertTickets(TicketsVO ticket) throws SQLException {

        int numFilas = 0;
        String sql = "insert into tickets values (?,?,?,?,?,?,?,?)";

        if (buscarTicketsMatricula(ticket.getMatricula())) {

            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = con.prepareStatement(sql)) {

                prest.setDate(1, Date.valueOf(ticket.getFechaEntrada()));
                prest.setDate(2, Date.valueOf(ticket.getFechaSalida()));
                prest.setTime(3, Time.valueOf(ticket.getHoraEntrada()));
                prest.setTime(4, Time.valueOf(ticket.getHoraSalida()));
                prest.setDouble(5, ticket.getPrecio());
                prest.setInt(6, ticket.getPin());
                prest.setString(7, ticket.getMatricula());
                prest.setString(8, ticket.getNumeroPlaza());

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }

    }

    //Método para insettar un ticket en una lista
    @Override //Sobrescrito
    public int insertTickets(List<TicketsVO> lista) throws SQLException {
        int numFilas = 0;

        for (TicketsVO tmp : lista) {
            numFilas += insertTickets(tmp);
        }

        return numFilas;
    }

    //Método para borrar un ticket de la base de datos
    @Override //Sobrescrito
    public int deleteTickets(TicketsVO ticket) throws SQLException {
        int numFilas = 0;

        String sql = "delete from ticket where matricula = ?";

        // Sentencia parametrizada
        try (PreparedStatement prest = con.prepareStatement(sql)) {

            // Establecemos los parámetros de la sentencia
            prest.setString(1, ticket.getMatricula());
            // Ejecutamos la sentencia
            numFilas = prest.executeUpdate();
        }
        return numFilas;
    }

    //Método para actualizar un ticket
    @Override //Sobrescrito
    public int updateTickets(String matricula, TicketsVO ticket) throws SQLException {

        int numFilas = 0;
        String sql = "update tickets set fechaEntrada = ?, fechaSalida = ?,horaEntrada = ?,horaSalida = ?, precio = ?, pin = ?, matricula = ?, numeroPlaza = ? where matricula=?";

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

    //Método para borrar todos los tickets de la base de datos
    @Override //Sobrescrito
    public int deleteTickets() throws SQLException {
        String sql = "delete from tickets";

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

    //Método para genetar aleatoriamente un pin a un cliente no abonado
    public int generacionPinNoAbonado() throws SQLException {
        Random r = new Random();

        int pin = r.nextInt(999999 - 100000 + 1) + 100000;

        String pinAbonado = String.valueOf(pin);

        File archivoBackup = new File("pinesNoAbonados");
        archivoBackup.mkdir();

        try (BufferedWriter flujo = new BufferedWriter(new FileWriter(archivoBackup + "\\" + pin + ".txt"))) {

            // Usamos metodo write() para escribir en el buffer
            flujo.write(pinAbonado);

            // Metodo newLine() añade línea en blanco
            flujo.newLine();

            // Metodo flush() guarda cambios en disco 
            flujo.flush();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return pin;
    }

    //Método para buscar un cliente no abonado
    public String buscarClienteNoAbonado(String matricula) throws SQLException {
        String numeroPlaza;

        ResultSet res = null;

        String sql = "select numeroPlaza from tickets where matricula = ?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {

            prest.setString(1, matricula);

            res = prest.executeQuery();

            if (res.first()) {

                numeroPlaza = res.getString(1);
                return numeroPlaza;
            }

            return null;
        }
    }

    //Método para modificar la hora de salida
    public void modificarFechaHoraSalida(String matricula) throws SQLException {
        LocalDate fecha = LocalDate.now();
        LocalTime hoy = LocalTime.now();

        String sql = "update tickets set fechaSalida = ?,horaSalida = ? where matricula = ?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {

            prest.setDate(1, Date.valueOf(fecha));
            prest.setTime(2, Time.valueOf(hoy));
            prest.setString(3, matricula);

            prest.executeUpdate();

        }
    }

    //Método para calcular el precio final de la estancia del vehículo en el parking
    public double calcularPrecio(String matricula, String numeroPlaza) throws SQLException {
        double precio = 0;
        LocalDateTime salida = null;
        LocalDateTime entrada = null;

        ResultSet res = null;

        String sql = "select fechaEntrada, fechaSalida, horaEntrada, horaSalida from tickets where matricula = ?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {

            prest.setString(1, matricula);

            res = prest.executeQuery();

            if (res.next()) {
                entrada = LocalDateTime.of(res.getDate("fechaEntrada").toLocalDate(), res.getTime("horaEntrada").toLocalTime());
                salida = LocalDateTime.of(res.getDate("fechaSalida").toLocalDate(), res.getTime("horaSalida").toLocalTime());

            }

        }
        ResultSet prec = null;

        String sqlprec = "select precioMinuto from plaza where numeroPlaza = ?";

        try (PreparedStatement prest = con.prepareStatement(sqlprec)) {

            prest.setString(1, numeroPlaza);

            prec = prest.executeQuery();

            if (prec.next()) {
                precio = prec.getDouble("precioMinuto");

            }
        }
        long duracion = ChronoUnit.MINUTES.between(entrada, salida);
        System.out.println(duracion);
        System.out.println(precio);
        double precioFinal = precio * duracion;
        return precioFinal;

    }

    //Método para actualizar el precio
    public void actualizarPrecio(String matricula, String numeroPlaza) {

        String sql = "update tickets set precio = ? where matricula = ?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {

            double precio = calcularPrecio(matricula, numeroPlaza);

            prest.setDouble(1, precio);
            prest.setString(2, matricula);

            prest.executeUpdate();

        } catch (SQLException e) {

            System.out.println(e);

        }
    }

    //Método que calcula la facturación dependiendo de la hora de entrada y salida
    public double calcularFacturacion() throws SQLException {
        double precioTotal = 0;
        LocalDate fechaInicio;
        LocalDate fechaFinal;
        LocalTime horaInicio;
        LocalTime horafinal;

        Scanner teclado = new Scanner(System.in);

        System.out.println("Introduce el año de inicio");
        int anio = teclado.nextInt();

        System.out.println("Introduce el mes de inicio");
        int mes = teclado.nextInt();

        System.out.println("Introduce el dia de inicio");
        int dia = teclado.nextInt();

        fechaInicio = LocalDate.of(anio, mes, dia);

        System.out.println("Introduce el año de final");
        int anioFinal = teclado.nextInt();

        System.out.println("Introduce el mes de final");
        int mesFinal = teclado.nextInt();

        System.out.println("Introduce el dia de final");
        int diaFinal = teclado.nextInt();

        fechaFinal = LocalDate.of(anioFinal, mesFinal, diaFinal);

        System.out.println("Introduce la hora de inicio");
        int hora = teclado.nextInt();

        System.out.println("Introduce los minutos de inicio");
        int min = teclado.nextInt();

        horaInicio = LocalTime.of(hora, min);

        System.out.println("Introduce la hora de final");
        int horaFinal = teclado.nextInt();

        System.out.println("Introduce los minutos de final");
        int minFinal = teclado.nextInt();

        horafinal = LocalTime.of(horaFinal, minFinal);
       
        if (horaInicio.isAfter(horafinal)){
            horaInicio = LocalTime.of(horaFinal, minFinal);
            horafinal = LocalTime.of(hora, min);
        }
       
        System.out.println("fecha ini " + fechaInicio);
        System.out.println("fecha fin " + fechaFinal);
        System.out.println("hora ini " + horaInicio);
        System.out.println("hora fin " + horaFinal);
       
        //la hora de entrada seran las 00 y la hora de salida sera las 23 para comprobar la facturacion
        String sql = "SELECT precio FROM tickets where precio!=0 and fechaEntrada>=? and fechaSalida<=? and horaEntrada>=? and horaSalida<=?";
       
        ResultSet prec = null;

        try (PreparedStatement prest = con.prepareStatement(sql)) {

            prest.setDate(1, Date.valueOf(fechaInicio));
            prest.setDate(2, Date.valueOf(fechaFinal));
            prest.setTime(3, Time.valueOf(horaInicio));
            prest.setTime(4, Time.valueOf(horafinal));
           
            prec = prest.executeQuery();

            while (prec.next()) {
                double precio;

                precio = prec.getDouble("precio");

                precioTotal = precioTotal + precio;
            }
        }
        return precioTotal;
    }

}
