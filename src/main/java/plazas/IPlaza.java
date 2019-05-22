/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plazas;

import clientes.ClientesVO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author fran
 */
public interface IPlaza {
    
    // Méodo para obtener un registro a partir de la PK
    PlazaVO buscarPlaza(String numPlaza) throws SQLException;
    
    // Método para insertar un registro
    int insertPlaza (PlazaVO plaza) throws SQLException;
    
    // Método para insertar varios registros
    int insertPlaza (List<PlazaVO> lista) throws SQLException;
    
    // Método para borrar una persona
    int deletePlaza (PlazaVO plaza) throws SQLException;
    
    // Método para modificar una persona. Se modifica a la persona que tenga esa 'pk'
    // con los nuevos datos que traiga la persona 'nuevosDatos'
    int updatePlaza (String numPlaza, PlazaVO plaza) throws SQLException;
    
    // Método para borrar toda la tabla
    int deletePlaza() throws SQLException;
}
