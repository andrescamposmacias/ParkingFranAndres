/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import conexion.Conexion;


/**
 *
 * @author fran
 */
public class VehiculoDAO {
    
    private Connection con = null;
     
     public VehiculoDAO() {
        con = Conexion.getInstance();
    }
     
    
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

   
    public int insertVehiculo(List<VehiculoVO> lista) throws SQLException {
        int numFilas = 0;

        for (VehiculoVO tmp : lista) {
            numFilas += insertVehiculo(tmp);
        }

        return numFilas;
    }

    
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
}


