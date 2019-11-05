/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAO;

import Modelos.DTO.CategoriainternaDTO;
import java.util.List;

/**
 *
 * @author AspireES15
 */
public interface CategoriainternaDAO extends OperacionesSQL<CategoriainternaDTO>{
    public List<CategoriainternaDTO> getListCategoria();
}
