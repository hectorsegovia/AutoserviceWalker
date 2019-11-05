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
public class tipo_cobroDTO {
    private Integer bandera;
    private Integer id_ticobro;
    private String descripcion;
    private Integer monto;

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getId_ticobro() {
        return id_ticobro;
    }

    public void setId_ticobro(Integer id_ticobro) {
        this.id_ticobro = id_ticobro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
