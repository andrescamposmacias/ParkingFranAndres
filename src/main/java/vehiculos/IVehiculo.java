/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehiculos;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author fran
 */
public interface IVehiculo {
    
    // Método para obtener todos los registros de la tabla
    List<VehiculoVO> getAll() throws SQLException;
    
    // Méodo para obtener un registro a partir de la PK
    VehiculoVO findByMatricula(String matricula) throws SQLException;
    
    // Método para insertar un registro
    int insertVehiculo(VehiculoVO vehiculo) throws SQLException;
    
    // Método para insertar varios registros
    int insertVehiculo (List<VehiculoVO> lista) throws SQLException;
    
    // Método para borrar una persona
    int deleteVehiculo(VehiculoVO vehiculo) throws SQLException;
    
    // Método para borrar toda la tabla
    int deleteVehiculo() throws SQLException;
    
    // Método para modificar una persona. Se modifica a la persona que tenga esa 'pk'
    // con los nuevos datos que traiga la persona 'nuevosDatos'
    int updateVehiculo(String matricula, VehiculoVO nuevosDatos) throws SQLException;
    
    public int deleteVehiculos() throws SQLException;
}
