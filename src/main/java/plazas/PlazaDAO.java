/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plazas;

import clientes.ClienteDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import conexion.Conexion;
import tickets.TicketsDAO;

/**
 *
 * @author andres
 */

//Método para conectar la clase con la base de datos
public class PlazaDAO implements IPlaza {

    private Connection con = null;

    public PlazaDAO() {
        con = Conexion.getInstance();
    }

    //Método que devuelve todos los clientes
    @Override //Sobrescrito
    public List<PlazaVO> getAll() throws SQLException {
        List<PlazaVO> lista = new ArrayList<>();

        try (Statement st = con.createStatement()) {

            ResultSet res = st.executeQuery("select * from plaza");

            while (res.next()) {
                PlazaVO p = new PlazaVO();

                p.setNumeroPlaza(res.getString("numeroPlaza"));
                p.setTipoPlaza(res.getString("tipoPlaza"));
                p.setEstado(res.getString("estado"));
                p.setPrecioMinuto(res.getDouble("precioMinuto"));

                lista.add(p);
            }
        }

        return lista;
    }

    //Método para buscar una plaza en la base de datos
    @Override //Sobrescrito
    public PlazaVO buscarPlaza(String numPlaza) throws SQLException {

        ResultSet res = null;
        PlazaVO plaza = new PlazaVO();

        String sql = "select * from plaza where numeroPlaza=?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {
            // Preparamos la sentencia parametrizada
            prest.setString(1, numPlaza);

            // Ejecutamos la sentencia y obtenemos las filas en el objeto ResultSet
            res = prest.executeQuery();

            // Nos posicionamos en el primer registro del Resultset. Sólo debe haber una fila
            // si existe esa pk
            if (res.first()) {

                plaza.setNumeroPlaza(res.getString("numeroPlaza"));
                plaza.setTipoPlaza(res.getString("tipoPlaza"));
                plaza.setEstado(res.getString("estado"));
                plaza.setPrecioMinuto(res.getDouble("precioMinuto"));

                return plaza;
            }

            return null;
        }
    }

    //Método para insertar una plaza en la base de datos
    @Override //Sobrescrito
    public int insertPlaza(PlazaVO plaza) throws SQLException {

        int numFilas = 0;
        String sql = "insert into plaza values (?,?,?,?)";

        if (buscarPlaza(plaza.getNumeroPlaza()) != null) {

            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = con.prepareStatement(sql)) {

                prest.setString(1, plaza.getNumeroPlaza());
                prest.setString(2, plaza.getTipoPlaza());
                prest.setString(3, plaza.getEstado());
                prest.setDouble(4, plaza.getPrecioMinuto());

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }

    }

    //Método para insertar una plaza en la lista de plazas
    @Override //Sobrescrito
    public int insertPlaza(List<PlazaVO> lista) throws SQLException {
        int numFilas = 0;

        for (PlazaVO tmp : lista) {
            numFilas += insertPlaza(tmp);
        }

        return numFilas;
    }

    //Método para borrar una plaza
    @Override //Sobrescrito
    public int deletePlaza(PlazaVO plaza) throws SQLException {
        int numFilas = 0;

        String sql = "delete from plaza where numeroPlaza = ?";

        // Sentencia parametrizada
        try (PreparedStatement prest = con.prepareStatement(sql)) {

            // Establecemos los parámetros de la sentencia
            prest.setString(1, plaza.getNumeroPlaza());
            // Ejecutamos la sentencia
            numFilas = prest.executeUpdate();
        }
        return numFilas;
    }

    //Método para actualizar una plaza
    @Override //Sobrescrito
    public int updatePlaza(String numPlaza, PlazaVO plaza) throws SQLException {

        int numFilas = 0;
        String sql = "update plaza set numeroPlaza = ?, tipoPlaza = ?, estado = ?, precioMinuto = ? where numeroPlaza=?";

        if (buscarPlaza(plaza.getNumeroPlaza()) == null) {
            // La persona a actualizar no existe
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = con.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia
                prest.setString(1, plaza.getNumeroPlaza());
                prest.setString(2, plaza.getTipoPlaza());
                prest.setString(3, plaza.getEstado());
                prest.setDouble(4, plaza.getPrecioMinuto());
                prest.setString(5, numPlaza);

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }
    }

    //Método para actualizar una plaza de abonado
    @Override //Sobrescrito
    public int updatePlazaAbonadoRetirado(String numPlaza) throws SQLException {

        int numFilas = 0;
        String sql = "update plaza set estado = 'Reservado vacio' where numeroPlaza=?";

        // Instanciamos el objeto PreparedStatement para inserción
        // de datos. Sentencia parametrizada
        try (PreparedStatement prest = con.prepareStatement(sql)) {

            // Establecemos los parámetros de la sentencia
            prest.setString(1, numPlaza);

            numFilas = prest.executeUpdate();
        }
        return numFilas;

    }

    //Método para borrar todas las plazas de la base de datos
    @Override //Sobrescrito
    public int deletePlaza() throws SQLException {
        String sql = "delete from plaza";

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

    //Método para comprobar si está ocupada una plaza de vehiculos tipo turismos
    public String plazaTurismoOcupado() throws SQLException {
        String n = "0";
        Statement st = con.createStatement();

        // almaceno resultado de consulta en ResultSet
        ResultSet rs = st.executeQuery("SELECT COUNT(numeroPlaza) FROM plaza WHERE estado = 'ocupado' and tipoPlaza='turismo'");
        // chequeo que el result set no sea vacío, moviendo el cursor a la 
        // primer fila. (El cursor inicia antes de la primer fila)
        if (rs.next()) {
            //Si hay resultados obtengo el valor. 
            n = rs.getString(1);
        }
        // libero recursos

        return n;
    }

    //Método para comprobar si está libre una plaza de vehiculos tipo turismos
    public String plazaTurismoLibre() throws SQLException {
        String n = "0";
        Statement st = con.createStatement();

        // almaceno resultado de consulta en ResultSet
        ResultSet rs = st.executeQuery("SELECT COUNT(numeroPlaza) FROM plaza WHERE estado = 'libre' and tipoPlaza='turismo'");
        // chequeo que el result set no sea vacío, moviendo el cursor a la 
        // primer fila. (El cursor inicia antes de la primer fila)
        if (rs.next()) {
            //Si hay resultados obtengo el valor. 
            n = rs.getString(1);
        }
        // libero recursos

        return n;
    }

    //Método para comprobar si está ocupada una plaza de vehiculos tipo motocicletas
    public String plazaMotocicletaOcupado() throws SQLException {
        String n = "0";
        Statement st = con.createStatement();

        // almaceno resultado de consulta en ResultSet
        ResultSet rs = st.executeQuery("SELECT COUNT(numeroPlaza) FROM plaza WHERE estado = 'ocupado' and tipoPlaza='motocicleta'");
        // chequeo que el result set no sea vacío, moviendo el cursor a la 
        // primer fila. (El cursor inicia antes de la primer fila)
        if (rs.next()) {
            //Si hay resultados obtengo el valor. 
            n = rs.getString(1);
        }
        // libero recursos

        return n;
    }

    //Método para comprobar si está libre una plaza de vehiculos tipo turismos
    public String plazaMotocicletaLibre() throws SQLException {
        String n = "0";
        Statement st = con.createStatement();

        // almaceno resultado de consulta en ResultSet
        ResultSet rs = st.executeQuery("SELECT COUNT(numeroPlaza) FROM plaza WHERE estado = 'libre' and tipoPlaza='motocicleta'");
        // chequeo que el result set no sea vacío, moviendo el cursor a la 
        // primer fila. (El cursor inicia antes de la primer fila)
        if (rs.next()) {
            //Si hay resultados obtengo el valor. 
            n = rs.getString(1);
        }
        // libero recursos

        return n;
    }

    //Método para comprobar si está ocupada una plaza de vehiculos tipo caravana
    public String plazaCaravanaOcupado() throws SQLException {
        String n = "0";
        Statement st = con.createStatement();

        // almaceno resultado de consulta en ResultSet
        ResultSet rs = st.executeQuery("SELECT COUNT(numeroPlaza) FROM plaza WHERE estado = 'ocupado' and tipoPlaza='Caravana'");
        // chequeo que el result set no sea vacío, moviendo el cursor a la 
        // primer fila. (El cursor inicia antes de la primer fila)
        if (rs.next()) {
            //Si hay resultados obtengo el valor. 
            n = rs.getString(1);
        }
        // libero recursos

        return n;
    }

    //Método para comprobar si está libre una plaza de vehiculos tipo turismos
    public String plazaCaravanaLibre() throws SQLException {
        String n = "0";
        Statement st = con.createStatement();

        // almaceno resultado de consulta en ResultSet
        ResultSet rs = st.executeQuery("SELECT COUNT(numeroPlaza) FROM plaza WHERE estado = 'libre' and tipoPlaza='Caravana'");
        // chequeo que el result set no sea vacío, moviendo el cursor a la 
        // primer fila. (El cursor inicia antes de la primer fila)
        if (rs.next()) {
            //Si hay resultados obtengo el valor. 
            n = rs.getString(1);
        }
        // libero recursos

        return n;
    }

    //Método para crear las 45 plazas
    public void creacionPlazas() {
        String n = "0";
        try {
            Statement st = con.createStatement();

            // almaceno resultado de consulta en ResultSet
            ResultSet rs = st.executeQuery("SELECT COUNT(numeroPlaza) FROM plaza");
            // chequeo que el result set no sea vacío, moviendo el cursor a la 
            // primer fila. (El cursor inicia antes de la primer fila)
            if (rs.next()) {
                //Si hay resultados obtengo el valor. 
                n = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        if (n.equalsIgnoreCase("45")) {

        } else {
            try {
                Statement st = con.createStatement();
                st.executeUpdate("insert into plaza values (1,'Turismo','libre','0.12')");
                st.executeUpdate("insert into plaza values (2,'Turismo','libre','0.12')");
                st.executeUpdate("insert into plaza values (3,'Turismo','libre','0.12')");
                st.executeUpdate("insert into plaza values (4,'Turismo','libre','0.12')");
                st.executeUpdate("insert into plaza values (5,'Turismo','libre','0.12')");
                st.executeUpdate("insert into plaza values (6,'Turismo','libre','0.12')");
                st.executeUpdate("insert into plaza values (7,'Turismo','libre','0.12')");
                st.executeUpdate("insert into plaza values (8,'Turismo','libre','0.12')");
                st.executeUpdate("insert into plaza values (9,'Turismo','libre','0.12')");
                st.executeUpdate("insert into plaza values (10,'Turismo','libre','0.12')");
                st.executeUpdate("insert into plaza values (11,'Turismo','libre','0.12')");
                st.executeUpdate("insert into plaza values (12,'Turismo','libre','0.12')");
                st.executeUpdate("insert into plaza values (13,'Turismo','libre','0.12')");
                st.executeUpdate("insert into plaza values (14,'Turismo','libre','0.12')");
                st.executeUpdate("insert into plaza values (15,'Turismo','libre','0.12')");

                st.executeUpdate("insert into plaza values (16,'Motocicleta','libre','0.08')");
                st.executeUpdate("insert into plaza values (17,'Motocicleta','libre','0.08')");
                st.executeUpdate("insert into plaza values (18,'Motocicleta','libre','0.08')");
                st.executeUpdate("insert into plaza values (19,'Motocicleta','libre','0.08')");
                st.executeUpdate("insert into plaza values (20,'Motocicleta','libre','0.08')");
                st.executeUpdate("insert into plaza values (21,'Motocicleta','libre','0.08')");
                st.executeUpdate("insert into plaza values (22,'Motocicleta','libre','0.08')");
                st.executeUpdate("insert into plaza values (23,'Motocicleta','libre','0.08')");
                st.executeUpdate("insert into plaza values (24,'Motocicleta','libre','0.08')");
                st.executeUpdate("insert into plaza values (25,'Motocicleta','libre','0.08')");
                st.executeUpdate("insert into plaza values (26,'Motocicleta','libre','0.08')");
                st.executeUpdate("insert into plaza values (27,'Motocicleta','libre','0.08')");
                st.executeUpdate("insert into plaza values (28,'Motocicleta','libre','0.08')");
                st.executeUpdate("insert into plaza values (29,'Motocicleta','libre','0.08')");
                st.executeUpdate("insert into plaza values (30,'Motocicleta','libre','0.08')");

                st.executeUpdate("insert into plaza values (31,'Caravana','libre','0.45')");
                st.executeUpdate("insert into plaza values (32,'Caravana','libre','0.45')");
                st.executeUpdate("insert into plaza values (33,'Caravana','libre','0.45')");
                st.executeUpdate("insert into plaza values (34,'Caravana','libre','0.45')");
                st.executeUpdate("insert into plaza values (35,'Caravana','libre','0.45')");
                st.executeUpdate("insert into plaza values (36,'Caravana','libre','0.45')");
                st.executeUpdate("insert into plaza values (37,'Caravana','libre','0.45')");
                st.executeUpdate("insert into plaza values (38,'Caravana','libre','0.45')");
                st.executeUpdate("insert into plaza values (39,'Caravana','libre','0.45')");
                st.executeUpdate("insert into plaza values (40,'Caravana','libre','0.45')");
                st.executeUpdate("insert into plaza values (41,'Caravana','libre','0.45')");
                st.executeUpdate("insert into plaza values (42,'Caravana','libre','0.45')");
                st.executeUpdate("insert into plaza values (43,'Caravana','libre','0.45')");
                st.executeUpdate("insert into plaza values (44,'Caravana','libre','0.45')");
                st.executeUpdate("insert into plaza values (45,'Caravana','libre','0.45')");

            } catch (SQLException e) {
                System.out.println(e);
            }
        }

    }

    //Método que nos devuelve el número de plaza de tipo turismo
    public String sacarNumeroPlazaTurismo() throws SQLException {
        String n = "0";
        Statement st = con.createStatement();

        // almaceno resultado de consulta en ResultSet
        ResultSet rs = st.executeQuery("SELECT numeroPlaza FROM plaza WHERE estado = 'libre' and tipoPlaza='Turismo'");
        // chequeo que el result set no sea vacío, moviendo el cursor a la 
        // primer fila. (El cursor inicia antes de la primer fila)
        if (rs.next()) {
            //Si hay resultados obtengo el valor. 
            n = rs.getString(1);
        }

        return n;
    }

    //Método que nos devuelve el número de plaza de tipo motocicleta
    public String sacarNumeroPlazaMotocicleta() throws SQLException {
        String n = "0";
        Statement st = con.createStatement();

        // almaceno resultado de consulta en ResultSet
        ResultSet rs = st.executeQuery("SELECT numeroPlaza FROM plaza WHERE estado = 'libre' and tipoPlaza='Motocicleta'");
        // chequeo que el result set no sea vacío, moviendo el cursor a la 
        // primer fila. (El cursor inicia antes de la primer fila)
        if (rs.next()) {
            //Si hay resultados obtengo el valor. 
            n = rs.getString(1);
        }

        return n;
    }

    //Método que nos devuelve el número de plaza de tipo caravana
    public String sacarNumeroPlazaCaravana() throws SQLException {
        String n = "0";
        Statement st = con.createStatement();

        // almaceno resultado de consulta en ResultSet
        ResultSet rs = st.executeQuery("SELECT numeroPlaza FROM plaza WHERE estado = 'libre' and tipoPlaza='Caravana'");
        // chequeo que el result set no sea vacío, moviendo el cursor a la 
        // primer fila. (El cursor inicia antes de la primer fila)
        if (rs.next()) {
            //Si hay resultados obtengo el valor. 
            n = rs.getString(1);
        }

        return n;
    }

    //Método para actualizar la plaza de un abonado
    public int updatePlazaAbonadoIngreso(String dni, String matricula) throws SQLException {
        ClienteDAO comprobar = new ClienteDAO();
        int numFilas = 0;

        String numPlaza = comprobar.buscarCliente(dni, matricula);

        if (numPlaza == null) {
            System.out.println("El usuario o la matricula esta mal");
            return 0;
        }
        if (comprobacionEstadoPlaza(numPlaza).equalsIgnoreCase("abono ocupada")) {
            System.out.println("Este vehículo ya se encuentra en el parking");
        } else {
            int pin = comprobar.sacarPinAbonado(dni);

            System.out.println("Su pin para sacar el coche es: " + pin);

            String sql = "update plaza set estado = 'abono ocupada' where numeroPlaza=?";

            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = con.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia
                prest.setString(1, numPlaza);

                numFilas = prest.executeUpdate();
            }
        }

        return numFilas;

    }

    //Método para comprobar el estado de la plaza
    public String comprobacionEstadoPlaza(String numeroPlaza) throws SQLException {

        ResultSet res = null;

        String sql = "select estado from plaza where numeroPlaza =?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {

            prest.setString(1, numeroPlaza);

            res = prest.executeQuery();

            if (res.first()) {

                numeroPlaza = res.getString(1);
                return numeroPlaza;
            }

            return null;

        }

    }

    //Método para actualizar la plaza de un no abonado
    public boolean updatePlazaNoAbonadoIngreso(String matricula) throws SQLException {

        TicketsDAO comprobar = new TicketsDAO();

        String numPlaza = comprobar.buscarClienteNoAbonado(matricula);

        if (numPlaza == null) {
            System.out.println("El usuario o la matricula esta mal");
            return false;
        }
        if (comprobacionEstadoPlaza(numPlaza).equalsIgnoreCase("ocupada")) {
            System.out.println("Ese coche ya esta en el parking");
        } else {

            String sql = "update plaza set estado = 'ocupada' where numeroPlaza=?";

            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = con.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia
                prest.setString(1, numPlaza);

                prest.executeUpdate();
            }
        }

        return true;

    }

    //Método para actualizar la hora de salida de un no abonado
    public boolean updatePlazaNoAbonadoSalida(String matricula) throws SQLException {

        TicketsDAO comprobar = new TicketsDAO();

        String numPlaza = comprobar.buscarClienteNoAbonado(matricula);

        if (numPlaza == null) {
            System.out.println("El usuario o la matricula esta mal");
            return false;
        }
        if (comprobacionEstadoPlaza(numPlaza).equalsIgnoreCase("libre")) {
            System.out.println("Ese coche ya no esta en el parking");
        } else {

            String sql = "update plaza set estado = 'libre' where numeroPlaza=?";

            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = con.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia
                prest.setString(1, numPlaza);

                prest.executeUpdate();
            }
        }

        return true;

    }

    //Método para consultar el estado de una determinada plaza
    public void estadoPlaza() throws SQLException {
        try (Statement st = con.createStatement()) {

            ResultSet res = st.executeQuery("SELECT numeroPlaza, estado FROM plaza");

            while (res.next()) {
                String numPlaza;
                String estado;

                numPlaza = res.getString(1);
                estado = res.getString(2);

                System.out.println("Numero de plaza: " + numPlaza + " estado de la plaza: " + estado);

            }
        }

    }
}
