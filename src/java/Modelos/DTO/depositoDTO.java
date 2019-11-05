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
public class depositoDTO {
    private Integer bandera;
    private Integer id_deposito;
    private String descripcion;
    private Integer id_sucursal;
    private String nom_sucu;

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getId_deposito() {
        return id_deposito;
    }

    public void setId_deposito(Integer id_deposito) {
        this.id_deposito = id_deposito;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(Integer id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public String getNom_sucu() {
        return nom_sucu;
    }

    public void setNom_sucu(String nom_sucu) {
        this.nom_sucu = nom_sucu;
    }

    
    
}
