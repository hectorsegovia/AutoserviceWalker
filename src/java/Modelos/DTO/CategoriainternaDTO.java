package Modelos.DTO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AspireES15
 */
public class CategoriainternaDTO {
    private Integer bandera;
    private Integer id_categoria;
    private String nombre;
    private Integer id_padre;

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Integer id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId_padre() {
        return id_padre;
    }

    public void setId_padre(Integer id_padre) {
        this.id_padre = id_padre;
    }
    
    
}
