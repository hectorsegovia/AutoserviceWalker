/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAO;

import Modelos.DTO.EmpleadosDTO;
import Modelos.DTO.PerfilesDTO;
import Modelos.DTO.UsuarioDTO;
import java.util.List;

/**
 *
 * @author AspireES15
 */
public interface UsuarioDAO extends OperacionesSQL<UsuarioDTO>{
      public List<PerfilesDTO> getListPerfil();
      public List<EmpleadosDTO> getListEmpleados();
      public Integer getPermisoUsuario(UsuarioDTO dto);
}
