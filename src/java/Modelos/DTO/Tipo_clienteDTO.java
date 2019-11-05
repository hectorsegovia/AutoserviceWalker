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
public class Tipo_clienteDTO {
    private Integer bandera;
    private Integer id_ticliente;
    private String descripcion;

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getId_ticliente() {
        return id_ticliente;
    }

    public void setId_ticliente(Integer id_ticliente) {
        this.id_ticliente = id_ticliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
