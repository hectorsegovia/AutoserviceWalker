/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DTO;

/**
 *
 * @author HectorSegovia
 */
public class Condiones_compra {
    private Integer bandera;
    private Integer id_condicioncompra;
    private String descripcion; 

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getId_condicioncompra() {
        return id_condicioncompra;
    }

    public void setId_condicioncompra(Integer id_condicioncompra) {
        this.id_condicioncompra = id_condicioncompra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
