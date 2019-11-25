/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAO;

import Modelos.DTO.OrdenDTO;
import Modelos.DTO.RecepcionDTO;
import java.util.List;

/**
 *
 * @author HectorSegovia
 */
public interface RecepcionesDAO {
     public boolean reg_recepcion( RecepcionDTO dto);
    public boolean anular_recepcion( RecepcionDTO dto);
    public boolean aprobar_recepcion( RecepcionDTO dto);
    public List<RecepcionDTO> recuperar_recepciones(RecepcionDTO dto);
    public List<OrdenDTO> recuperar_OrdenCompras(RecepcionDTO dto);
    public List<RecepcionDTO> getListOrdenCompras();
    public List<RecepcionDTO> getListConsultarTodo();
    public List<RecepcionDTO> getListcodigoS();

}
