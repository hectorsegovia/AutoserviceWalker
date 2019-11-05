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
public class tipo_familiaDTO {
 private Integer bandera;
 private Integer id_tifamilia;
 private String descripcion;

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getId_tifamilia() {
        return id_tifamilia;
    }

    public void setId_tifamilia(Integer id_tifamilia) {
        this.id_tifamilia = id_tifamilia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
 
}
