/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author fran
 */
public interface ICliente {
    
    public List<ClientesVO> getAll() throws SQLException;
    
    // Méodo para obtener un registro a partir de la PK
    ClientesVO buscarCliente(String dni, String matricula) throws SQLException;
    
    // Método para insertar un registro
    int insertCliente (ClientesVO cliente) throws SQLException;
    
    // Método para insertar varios registros
    int insertCliente (List<ClientesVO> lista) throws SQLException;
    
    // Método para borrar una persona
    int deleteCliente (ClientesVO cliente) throws SQLException;
    
    // Método para modificar una persona. Se modifica a la persona que tenga esa 'pk'
    // con los nuevos datos que traiga la persona 'nuevosDatos'
    int updateCliente (String dni, ClientesVO cliente) throws SQLException;
    
}
