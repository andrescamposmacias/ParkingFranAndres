/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plazas;

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
public class PlazaDAO implements IPlaza{
    
        
    private Connection con = null;
    
    public PlazaDAO() {
        con = Conexion.getInstance();
    } 
    
    @Override
    public List<PlazaVO> getAll() throws SQLException {
        List<PlazaVO> lista = new ArrayList<>();

        try (Statement st = con.createStatement()) {

            ResultSet res = st.executeQuery("select * from tickets");

            while (res.next()) {
                PlazaVO p = new PlazaVO();

                p.setCodigo(res.getInt("codigo"));
                p.setNumeroPlaza(res.getString("numeroPlaza"));
                p.setTarifa(res.getDouble("tarifa"));
                p.setTipoPlaza(res.getString("tipoPlaza"));
                p.setEstado(res.getString("estado"));
                p.setPrecioMinuto(res.getDouble("precioMinuto"));


                lista.add(p);
            }
        }

        return lista;
    }
    
    @Override
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
                
                plaza.setCodigo(res.getInt("codigo"));
                plaza.setNumeroPlaza(res.getString("numeroPlaza"));
                plaza.setTarifa(res.getDouble("tarifa"));
                plaza.setTipoPlaza(res.getString("tipoPlaza"));
                plaza.setEstado(res.getString("estado"));
                plaza.setPrecioMinuto(res.getDouble("precioMinuto"));
                        
                return plaza;
            }

            return null;
        }
    }
    
    @Override
    public int insertPlaza(PlazaVO plaza) throws SQLException {

        int numFilas = 0;
        String sql = "insert into plaza values (?,?,?,?,?)";

        if (buscarPlaza(plaza.getNumeroPlaza()) != null) {
            
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = con.prepareStatement(sql)) {

                prest.setString(1, plaza.getNumeroPlaza());
                prest.setDouble(2, plaza.getTarifa());
                prest.setString(3, plaza.getTipoPlaza());
                prest.setString(4, plaza.getEstado());
                prest.setDouble(5, plaza.getPrecioMinuto());               

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }

    }
    
    @Override
    public int insertPlaza(List<PlazaVO> lista) throws SQLException {
        int numFilas = 0;

        for (PlazaVO tmp : lista) {
            numFilas += insertPlaza(tmp);
        }

        return numFilas;
    }
    
    @Override
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
    
    @Override
    public int updatePlaza(String numPlaza, PlazaVO plaza) throws SQLException {

        int numFilas = 0;
        String sql = "update plaza set numeroPlaza = ?, tarifa = ?, tipoPlaza = ?, estado = ?, precioMinuto = ? where numeroPlaza=?";

        if (buscarPlaza(plaza.getNumeroPlaza()) == null) {
            // La persona a actualizar no existe
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = con.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia
                
                
                prest.setString(1, plaza.getNumeroPlaza());
                prest.setDouble(2, plaza.getTarifa());
                prest.setString(3, plaza.getTipoPlaza());
                prest.setString(4, plaza.getEstado());
                prest.setDouble(5, plaza.getPrecioMinuto());                            

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }
    }
    
    @Override
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

    @Override
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
    
}
