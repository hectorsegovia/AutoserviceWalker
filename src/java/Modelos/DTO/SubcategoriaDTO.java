/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DTO;

/**
 *
 * @author AspireES15
 */
public class SubcategoriaDTO {
    private Integer bandera;
    private Integer id_subcategoria;
    private String descripcion;

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getId_subcategoria() {
        return id_subcategoria;
    }

    public void setId_subcategoria(Integer id_subcategoria) {
        this.id_subcategoria = id_subcategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
