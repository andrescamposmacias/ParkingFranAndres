/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehiculos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import conexion.Conexion;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import plazas.PlazaDAO;
import tickets.TicketsDAO;
import tickets.TicketsVO;

/**
 *
 * @author fran
 */
public class VehiculoDAO implements IVehiculo {

    private Connection con = null;

    public VehiculoDAO() {
        con = Conexion.getInstance();
    }

    //Método que devuelve todos los vehículos
    @Override //Sobrescrito
    public List<VehiculoVO> getAll() throws SQLException {
        List<VehiculoVO> lista = new ArrayList<>();

        // Preparamos la consulta de datos mediante un objeto Statement
        // ya que no necesitamos parametrizar la sentencia SQL
        try (Statement st = con.createStatement()) {
            // Ejecutamos la sentencia y obtenemos las filas en el objeto ResultSet
            ResultSet res = st.executeQuery("select * from vehiculo");
            // Ahora construimos la lista, recorriendo el ResultSet y mapeando los datos
            while (res.next()) {
                VehiculoVO vehiculo = new VehiculoVO();
                // Recogemos los datos de los jugadores, guardamos en un objeto
                vehiculo.setMatricula(res.getString("matricula"));
                vehiculo.setTipo(res.getString("tipo"));

                //Añadimos el objeto a la lista
                lista.add(vehiculo);
            }
        }

        return lista;
    }

    //Método para encontrar un vehículo por la matrícula
    @Override //Sobrescrito
    public VehiculoVO findByMatricula(String matricula) throws SQLException {

        ResultSet res = null;
        VehiculoVO vehiculo = new VehiculoVO();

        String sql = "select * from vehiculo where matricula=?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {
            // Preparamos la sentencia parametrizada
            prest.setString(1, matricula);

            // Ejecutamos la sentencia y obtenemos las filas en el objeto ResultSet
            res = prest.executeQuery();

            // Nos posicionamos en el primer registro del Resultset. Sólo debe haber una fila
            // si existe esa pk
            if (res.first()) {
                // Recogemos los datos del jugador, guardamos en un objeto
                vehiculo.setMatricula(res.getString("matricula"));
                vehiculo.setTipo(res.getString("tipo"));
                return vehiculo;
            }

            return null;
        }
    }

    //Método para insertar un vehículo
    @Override //Sobrescrito
    public int insertVehiculo(VehiculoVO vehiculo) throws SQLException {

        int numFilas = 0;
        String sql = "insert into vehiculo values (?,?)";

        if (findByMatricula(vehiculo.getMatricula()) != null) {
            // Existe un registro con esa pk
            // No se hace la inserción
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = con.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia
                prest.setString(1, vehiculo.getMatricula());
                prest.setString(2, vehiculo.getTipo());

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }

    }

    //Método para insertar un vehículo en la lista de vehículos
    @Override //Sobrescrito
    public int insertVehiculo(List<VehiculoVO> lista) throws SQLException {
        int numFilas = 0;

        for (VehiculoVO tmp : lista) {
            numFilas += insertVehiculo(tmp);
        }

        return numFilas;
    }

    //Método para borrar los vehículos de la base de datos
    @Override //Sobrescrito
    public int deleteVehiculo() throws SQLException {

        String sql = "delete from vehiculo";

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

    //Método para borrar un vehículo
    @Override //Sobrescrito
    public int deleteVehiculo(VehiculoVO vehiculo) throws SQLException {
        int numFilas = 0;

        String sql = "delete from vehiculo where matricula = ?";

        // Sentencia parametrizada
        try (PreparedStatement prest = con.prepareStatement(sql)) {

            // Establecemos los parámetros de la sentencia
            prest.setString(1, vehiculo.getMatricula());
            // Ejecutamos la sentencia
            numFilas = prest.executeUpdate();
        }
        return numFilas;
    }

    //Método para actualizar un vehículo
    @Override //Sobrescrito
    public int updateVehiculo(String matricula, VehiculoVO nuevosDatos) throws SQLException {

        int numFilas = 0;
        String sql = "update vehiculo set matricula = ?, tipo = ? where matricula=?";

        if (findByMatricula(matricula) == null) {
            // El jugador a actualizar no existe
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = con.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia          
                prest.setString(1, nuevosDatos.getMatricula());
                prest.setString(2, nuevosDatos.getTipo());

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }
    }

    //Método para borrar todos los vehículos de la base de datos 
    @Override //Sobrescrito
    public int deleteVehiculos() throws SQLException {
        String sql = "delete from vehiculo";

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

    //Método para insettar un vehículo de un cliente sin abono
    public boolean insertVehiculoPersonaSinAbono() throws SQLException {

        Scanner teclado = new Scanner(System.in);

        TicketsDAO tickets = new TicketsDAO();
        PlazaDAO plaza = new PlazaDAO();

        //atributos vehiculo
        String matriculaRegistro;
        String tipoVehiculo;

        String numeroPlazaRegistro="";

        System.out.println("Introduzca su matricula");
        matriculaRegistro = teclado.nextLine();

        do {
            System.out.println("Introduce el tipo de vehículo (Turismo, Motocicleta o Caravana)");
            tipoVehiculo = teclado.nextLine();
        } while (!tipoVehiculo.equalsIgnoreCase("Turismo") && !tipoVehiculo.equalsIgnoreCase("Motocicleta") && !tipoVehiculo.equalsIgnoreCase("Caravana"));

        if (tipoVehiculo.equalsIgnoreCase("Turismo")) {
            if (plaza.plazaTurismoLibre().equalsIgnoreCase("0")) {
                System.out.println("Lo siento no quedan plazas de turismo");
                return false;
            } else {
                numeroPlazaRegistro = plaza.sacarNumeroPlazaTurismo();
                plaza.updatePlazaAbonadoRetirado(numeroPlazaRegistro);
            }
        }

        if (tipoVehiculo.equalsIgnoreCase("Motocicleta")) {
            if (plaza.plazaMotocicletaLibre().equalsIgnoreCase("0")) {
                System.out.println("Lo siento no quedan plazas de Motocicleta");
                return false;
            } else {
                numeroPlazaRegistro = plaza.sacarNumeroPlazaMotocicleta();
                plaza.updatePlazaAbonadoRetirado(numeroPlazaRegistro);
            }
        }

        if (tipoVehiculo.equalsIgnoreCase("Caravana")) {
            if (plaza.plazaCaravanaLibre().equalsIgnoreCase("0")) {
                System.out.println("Lo siento no quedan plazas de Caravana");
                return false;
            } else {
                numeroPlazaRegistro = plaza.sacarNumeroPlazaCaravana();
                plaza.updatePlazaAbonadoRetirado(numeroPlazaRegistro);
            }
        }

        String sql = "insert into vehiculo values (?,?)";

        if (findByMatricula(matriculaRegistro) != null) {
            // Existe un registro con esa pk
            // No se hace la inserción
            return false;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = con.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia
                prest.setString(1, matriculaRegistro);
                prest.setString(2, tipoVehiculo);

                prest.executeUpdate();
            }

        }

        //atributos del no abonado
        LocalDate hoyRegistro = LocalDate.now();
        LocalTime hoyHora = LocalTime.now();
        LocalDate finalRegistro = LocalDate.now();
        LocalTime finalHora = LocalTime.now();
        int pinNoAbonado = tickets.generacionPinNoAbonado();
        
        LocalDateTime fechaYHora = LocalDateTime.of(hoyRegistro, hoyHora);
        
        System.out.println("-------TICKET-------");
        System.out.println("La matricula del coche es: " + matriculaRegistro);
        System.out.println("La fecha y hora de ingreso es: " + fechaYHora);
        System.out.println("El número de su plaza es: " + numeroPlazaRegistro);
        System.out.println("Su pin para retirar el coche es: " + pinNoAbonado);
        
        tickets.insertTickets(new TicketsVO(hoyRegistro,finalRegistro,hoyHora,finalHora,0.0,pinNoAbonado,matriculaRegistro,numeroPlazaRegistro));
        
        plaza.updatePlazaNoAbonadoIngreso(matriculaRegistro);
        
        return true;
    }

}
