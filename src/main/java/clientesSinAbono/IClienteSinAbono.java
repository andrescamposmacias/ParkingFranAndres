/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientesSinAbono;

import java.sql.SQLException;
import java.util.List;
import plazas.PlazaVO;

/**
 *
 * @author fran
 */
public interface IClienteSinAbono {
    
        
    // Méodo para obtener un registro a partir de la PK
    ClienteSinAbonoVO findByMatricula(String matricula) throws SQLException;
    
    // Método para insertar un registro
    int insertClienteSinAbono (ClienteSinAbonoVO clienteSinAbono) throws SQLException;
    
    // Método para insertar varios registros
    int insertClienteSinAbono (List<ClienteSinAbonoVO> lista) throws SQLException;
    
    // Método para borrar una persona
    int deleteClienteSinAbono (ClienteSinAbonoVO clienteSinAbono) throws SQLException;
    
    // Método para modificar una persona. Se modifica a la persona que tenga esa 'pk'
    // con los nuevos datos que traiga la persona 'nuevosDatos'
    int updateClienteSinAbono (String matricula, ClienteSinAbonoVO nuevosDatos) throws SQLException;
    
}
