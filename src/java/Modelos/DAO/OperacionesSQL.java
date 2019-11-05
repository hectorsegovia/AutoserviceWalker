
package Modelos.DAO;

import java.util.List;


public interface OperacionesSQL<T> {
public boolean agregar(T dto);
public boolean modificar(T dto);
public boolean eliminar(T dto);
public List<T> consultarTodos();
public List<T> consultarSegunId(Integer id);
}
