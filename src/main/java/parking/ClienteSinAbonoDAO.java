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
import java.util.List;
import conexion.Conexion;
import java.sql.Time;

/**
 *
 * @author fran
 */
public class ClienteSinAbonoDAO {
    
    private Connection con = null;
     
     public ClienteSinAbonoDAO() {
        con = Conexion.getInstance();
    }
    
     
     public ClienteSinAbonoVO findByMatricula(String matricula) throws SQLException {

        ResultSet res = null;
        ClienteSinAbonoVO clienteSinAbono = new ClienteSinAbonoVO();

        String sql = "select * from clienteSinAbono where matricula=?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {
            // Preparamos la sentencia parametrizada
            prest.setString(1, matricula);

            // Ejecutamos la sentencia y obtenemos las filas en el objeto ResultSet
            res = prest.executeQuery();

            // Nos posicionamos en el primer registro del Resultset. Sólo debe haber una fila
            // si existe esa pk
            if (res.first()) {
                // Recogemos los datos del jugador, guardamos en un objeto
                clienteSinAbono.setMatricula(res.getString("matricula"));
                clienteSinAbono.setCodigo(res.getInt("tipo"));
                clienteSinAbono.setHoraEntrada(res.getTime("horaEntrada").toLocalTime());
                clienteSinAbono.setHoraSalida(res.getTime("horaSalida").toLocalTime());
                return clienteSinAbono;
            }

            return null;
        }
    }

   
    public int insertClienteSinAbono(ClienteSinAbonoVO clienteSinAbono) throws SQLException {

        int numFilas = 0;
        String sql = "insert into clienteSinAbono values (?,?,?,?)";

        if (findByMatricula(clienteSinAbono.getMatricula()) != null) {
            // Existe un registro con esa pk
            // No se hace la inserción
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = con.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia
                prest.setString(1, clienteSinAbono.getMatricula());
                prest.setInt(2, clienteSinAbono.getCodigo());
                prest.setTime(3, Time.valueOf(clienteSinAbono.getHoraEntrada()));
                prest.setTime(4, Time.valueOf(clienteSinAbono.getHoraSalida()));

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }

    }

   
    public int insertClienteSinAbono(List<ClienteSinAbonoVO> lista) throws SQLException {
        int numFilas = 0;

        for (ClienteSinAbonoVO tmp : lista) {
            numFilas += insertClienteSinAbono(tmp);
        }

        return numFilas;
    }

    
    public int deleteClienteSinAbono() throws SQLException {

        String sql = "delete from clienteSinAbono";

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

  
    public int deleteClienteSinAbono(ClienteSinAbonoVO clienteSniAbono) throws SQLException {
        int numFilas = 0;

        String sql = "delete from clienteSniAbono where matricula = ?";

        // Sentencia parametrizada
        try (PreparedStatement prest = con.prepareStatement(sql)) {

            // Establecemos los parámetros de la sentencia
            prest.setString(1, clienteSniAbono.getMatricula());
            // Ejecutamos la sentencia
            numFilas = prest.executeUpdate();
        }
        return numFilas;
    }


    public int updateClienteSinAbono(String matricula, ClienteSinAbonoVO nuevosDatos) throws SQLException {

        int numFilas = 0;
        String sql = "update vehiculo set matricula = ?, codigo = ?, horaEntrada = ?, horaSalida = ?, where matricula=?";

        if (findByMatricula(matricula) == null) {
            // El jugador a actualizar no existe
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = con.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia          
                prest.setString(1, nuevosDatos.getMatricula());
                prest.setInt(2, nuevosDatos.getCodigo());
                prest.setTime(3, Time.valueOf(nuevosDatos.getHoraEntrada()));
                prest.setTime(4, Time.valueOf(nuevosDatos.getHoraSalida()));

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }
    }
}
